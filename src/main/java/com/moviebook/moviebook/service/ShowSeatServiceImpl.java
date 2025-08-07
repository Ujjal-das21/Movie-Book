package com.moviebook.moviebook.service;

import java.util.Collection;
import java.util.List;

import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Show;
import com.moviebook.moviebook.model.ShowSeat;
import com.moviebook.moviebook.payload.SeatResponse;
import com.moviebook.moviebook.payload.ShowSeatDTO;
import com.moviebook.moviebook.payload.ShowSeatResponse;
import com.moviebook.moviebook.repositories.ShowRepository;
import com.moviebook.moviebook.repositories.ShowSeatRepository;

@Service
public class ShowSeatServiceImpl implements ShowSeatService {

    @Autowired
    ShowRepository showRepository;
    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ShowSeatDTO> getAllShowSeatOfShow(Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show", "showId", showId));
        List<ShowSeat> showSeatList = showSeatRepository.findByShow_ShowId(showId);

        List<ShowSeatDTO> showSeatDTOs = showSeatList.stream()
                .map(showSeat -> modelMapper.map(showSeat, ShowSeatDTO.class)).toList();
        return showSeatDTOs;

    }

    @Override
    public ShowSeatResponse getFilteredShowSeats(Long showId, String status, int pageNumber, int pageSize, String sortBy,
            String sortDir) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show", "showId", showId));

                
    Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

     Page<ShowSeat> showSeats;

    if (status == null || status.isBlank()) {
        showSeats = showSeatRepository.findByShow_ShowId(showId, pageable);
    } else {
        SeatStatus seatStatus = SeatStatus.valueOf(status.toUpperCase());
        showSeats = showSeatRepository.findByShow_ShowIdAndSeatStatus(showId, seatStatus, pageable);
    }

    List<ShowSeatDTO> dtos = showSeats.getContent()
    .stream()
    .map(showSeat -> {
        ShowSeatDTO dto = modelMapper.map(showSeat, ShowSeatDTO.class);
        dto.setShowRefId(showSeat.getShow().getShowId());   // ✅ Now no conflict
        dto.setSeatId(showSeat.getSeat().getSeatId());      // ✅ Map seat ID as well
        return dto;
    })
    .toList();
    ShowSeatResponse response = new ShowSeatResponse();
    response.setContent(dtos);
    response.setPageNumber(showSeats.getNumber());
    response.setPageSize(showSeats.getSize());
    response.setTotalElements(showSeats.getTotalElements());
    response.setTotalPages(showSeats.getTotalPages());
    response.setLastPage(showSeats.isLast());

    return response;

    }

}
