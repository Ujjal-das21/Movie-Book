package com.moviebook.moviebook.service;
import java.util.List;

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
import com.moviebook.moviebook.payload.MovieDTO;
import com.moviebook.moviebook.payload.MovieResponse;
import com.moviebook.moviebook.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
     @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieDTO createMovie(MovieDTO movieDTO) {
        Movie movie=modelMapper.map(movieDTO,Movie.class);

        Movie movieFromDB=movieRepository.findByTitle(movie.getTitle());
        if(movieFromDB!=null)
        {
            throw new  APIException("Movie with the name "+movie.getTitle()+" already exists!!");
        }

        Movie savedMovie=movieRepository.save(movie);

        return modelMapper.map(savedMovie, MovieDTO.class);
       
    }

    @Override
    public MovieDTO deleteMovie(Long movieId) {
       
        Movie deletedMovie=movieRepository.findById(movieId).orElseThrow(()->
            new ResourceNotFoundException("Movie","movieId",movieId));

            movieRepository.delete(deletedMovie);
            return modelMapper.map(deletedMovie, MovieDTO.class);
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO, Long movieId) {

        Movie movieToUpdate=movieRepository.findById(movieId).orElseThrow(()->
            new ResourceNotFoundException("Movie","movieId",movieId));

            movieToUpdate.setTitle(movieDTO.getTitle());
            movieToUpdate.setDescription(movieDTO.getDescription());
            movieToUpdate.setDuration(movieDTO.getDuration());
            movieToUpdate.setGenre(movieDTO.getGenre());
            movieToUpdate.setReleaseDate(movieDTO.getReleaseDate());

            Movie updatedMovie=movieRepository.save(movieToUpdate);

            return modelMapper.map(updatedMovie, MovieDTO.class);

    }

    @Override
    public MovieDTO getMovieById(Long movieId) {
        
        Movie foundMovie=movieRepository.findById(movieId).orElseThrow(()->
            new ResourceNotFoundException("Movie","movieId",movieId));

            return modelMapper.map(foundMovie, MovieDTO.class);
    }

    @Override
    public MovieResponse getAllMovies(int pageNumber, int pageSize, String sortBy, String sortDir) {
       
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                 : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    Page<Movie> pageMovies = movieRepository.findAll(pageable);
     List<MovieDTO> movieDTOs = pageMovies.getContent()
                                         .stream()
                                         .map(movie -> modelMapper.map(movie, MovieDTO.class))
                                         .toList();

      MovieResponse response = new MovieResponse();
    response.setContent(movieDTOs);
    response.setPageNumber(pageMovies.getNumber());
    response.setPageSize(pageMovies.getSize());
    response.setTotalElements(pageMovies.getTotalElements());
    response.setTotalPages(pageMovies.getTotalPages());
    response.setLastPage(pageMovies.isLast());

    return response;
    }

    @Override
    public MovieResponse getMoviesByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                                 : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

    // Use containing() for partial matching (case-insensitive with IgnoreCase)
    Page<Movie> moviePage = movieRepository.findByTitleContainingIgnoreCase(title, pageable);

    List<MovieDTO> dtos = moviePage.getContent()
                                   .stream()
                                   .map(movie -> modelMapper.map(movie, MovieDTO.class))
                                   .toList();

    MovieResponse response = new MovieResponse();
    response.setContent(dtos);
    response.setPageNumber(moviePage.getNumber());
    response.setPageSize(moviePage.getSize());
    response.setTotalElements(moviePage.getTotalElements());
    response.setTotalPages(moviePage.getTotalPages());
    response.setLastPage(moviePage.isLast());

    return response;
    }

    

}
