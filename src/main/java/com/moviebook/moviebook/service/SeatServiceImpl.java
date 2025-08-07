package com.moviebook.moviebook.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.model.Seat;
import com.moviebook.moviebook.model.Show;
import com.moviebook.moviebook.payload.SeatDTO;
import com.moviebook.moviebook.payload.SeatResponse;
import com.moviebook.moviebook.payload.ShowDTO;
import com.moviebook.moviebook.payload.ShowResponse;
import com.moviebook.moviebook.repositories.ScreenRepository;
import com.moviebook.moviebook.repositories.SeatRepository;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    SeatRepository seatRepository;
    @Autowired
    ScreenRepository screenRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public SeatResponse getAllSeatsByScreenId(int pageNumber, int pageSize, String sortBy, String sortDir,
            Long screenId) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
       Page<Seat> pageSeats = seatRepository.findByScreen_ScreenId(screenId, pageable);
        List<SeatDTO> seatDTOs = pageSeats.getContent()
                .stream()
                .map(seat -> modelMapper.map(seat, SeatDTO.class))
                .toList();

        SeatResponse response = new SeatResponse();
        response.setContent(seatDTOs);
        response.setPageNumber(pageSeats.getNumber());
        response.setPageSize(pageSeats.getSize());
        response.setTotalElements(pageSeats.getTotalElements());
        response.setTotalPages(pageSeats.getTotalPages());
        response.setLastPage(pageSeats.isLast());

        return response;

    }

}
