package com.moviebook.moviebook.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Movie;
import com.moviebook.moviebook.model.Theater;
import com.moviebook.moviebook.payload.MovieDTO;
import com.moviebook.moviebook.payload.MovieResponse;
import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.payload.TheaterResponse;
import com.moviebook.moviebook.repositories.TheaterRepository;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TheaterDTO createTheater(TheaterDTO theaterDTO) {

        // Check if (name, location) already exists
        Optional<Theater> existing = theaterRepository.findByNameAndLocation(
                theaterDTO.getName(), theaterDTO.getLocation());

                if(existing.isPresent())
                {
                     throw new  APIException( "A theater with the same name already exists at this location.");
                }

                 Theater theater = modelMapper.map(theaterDTO, Theater.class);
                 Theater saved = theaterRepository.save(theater);
    return modelMapper.map(saved, TheaterDTO.class);

    }

   
    @Override
    public TheaterDTO updateTheater(TheaterDTO theaterDTO, Long theaterId) {
       
        Theater theaterToUpdate=theaterRepository.findById(theaterId).orElseThrow(()->(new ResourceNotFoundException("Theater","theaterId",theaterId)));

        theaterToUpdate.setName(theaterDTO.getName());
        theaterToUpdate.setLocation(theaterDTO.getLocation());

        Theater updatedTheater=theaterRepository.save(theaterToUpdate);

        return modelMapper.map(updatedTheater,TheaterDTO.class);


    }


    @Override
    public TheaterDTO deleteTheater(Long theaterId) {
        
         Theater theaterToDelete=theaterRepository.findById(theaterId).orElseThrow(()->(new ResourceNotFoundException("Theater","theaterId",theaterId)));

          theaterRepository.delete(theaterToDelete);

         return modelMapper.map(theaterToDelete, TheaterDTO.class);

    }


    @Override
    public TheaterResponse getAllTheaters(int pageNumber, int pageSize, String sortBy, String sortDir) {
       
           Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                 : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Theater> pageTheaters = theaterRepository.findAll(pageable);
     List<TheaterDTO> theaterDTOs = pageTheaters.getContent()
                                         .stream()
                                         .map(theater -> modelMapper.map(theater, TheaterDTO.class))
                                         .toList();

      TheaterResponse response = new TheaterResponse();
      response.setContent(theaterDTOs);
    response.setPageNumber(pageTheaters.getNumber());
    response.setPageSize(pageTheaters.getSize());
    response.setTotalElements(pageTheaters.getTotalElements());
    response.setTotalPages(pageTheaters.getTotalPages());
    response.setLastPage(pageTheaters.isLast());

    return response;

    }


    @Override
    public TheaterDTO findTheaterById(Long theaterId) {
        
        Theater theaterById=theaterRepository.findById(theaterId).orElseThrow(()->(new ResourceNotFoundException("Theater","theaterId",theaterId)));
        
         return modelMapper.map(theaterById, TheaterDTO.class);
    }


    @Override
    public List<TheaterDTO> searchTheatersByKeyword(String keyword) {
       
         List<Theater> theaters = theaterRepository.searchByNameOrLocation(keyword);
    return theaters.stream()
        .map(theater -> modelMapper.map(theater, TheaterDTO.class))
        .collect(Collectors.toList());
    }

}
