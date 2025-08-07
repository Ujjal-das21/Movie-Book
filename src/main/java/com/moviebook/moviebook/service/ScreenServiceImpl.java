package com.moviebook.moviebook.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.config.constants.SeatType;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Screen;
import com.moviebook.moviebook.model.Seat;
import com.moviebook.moviebook.model.Theater;
import com.moviebook.moviebook.payload.ScreenDTO;
import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.repositories.ScreenRepository;
import com.moviebook.moviebook.repositories.TheaterRepository;

@Service
public class ScreenServiceImpl implements ScreenService {

    @Autowired
    ScreenRepository screenRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ScreenDTO createScreen(Long theaterId, ScreenDTO screenDTO) {

        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater", "theaterId", theaterId));

        Screen screenFromDb = screenRepository.findByNameAndTheater_TheaterId(screenDTO.getName(), theaterId);
        if (screenFromDb != null) {
            throw new APIException("Screen with the name " + screenDTO.getName() + " already exists in this theater!");
        }

        Screen screen = modelMapper.map(screenDTO, Screen.class);
        screen.setTheater(theater);

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber((long) i);
            seat.setScreen(screen);

            if (i <= 20)
                seat.setSeatType(SeatType.REGULAR);
            else if (i <= 40)
                seat.setSeatType(SeatType.PREMIUM);
            else
                seat.setSeatType(SeatType.RECLINER);

            seats.add(seat);
        }
        screen.setSeats(seats);

        Screen savedScreen = screenRepository.save(screen);
        theater.getScreens().add(screen);

        return modelMapper.map(savedScreen, ScreenDTO.class);
    }

    @Override
    public ScreenDTO updateScreen(Long screenId, ScreenDTO screenDTO) {

        Screen screenToUpdate = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen", "screenId", screenId));

        Screen screenFromDb = screenRepository.findByNameAndTheater_TheaterId(screenDTO.getName(),
                screenToUpdate.getTheater().getTheaterId());
        if (screenFromDb != null) {
            throw new APIException("Screen with the name " + screenDTO.getName() + " already exists in this theater!");
        }
        screenToUpdate.setName(screenDTO.getName());
        Screen updatedScreen = screenRepository.save(screenToUpdate);

        return modelMapper.map(updatedScreen, ScreenDTO.class);
    }

    @Override
    public ScreenDTO deleteScreen(Long screenId) {

        Screen screenToDelete = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen", "screenId", screenId));
        screenRepository.delete(screenToDelete);

        return modelMapper.map(screenToDelete, ScreenDTO.class);
    }

    @Override
    public List<ScreenDTO> getScreenByTheaterId(Long theaterId) {

        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new ResourceNotFoundException("Theater", "theaterId", theaterId));

        List<Screen> screenList = theater.getScreens();

        List<ScreenDTO> screenDTOList = screenList.stream()
                .map(screen -> modelMapper.map(screen, ScreenDTO.class))
                .toList();

        return screenDTOList;

    }

    @Override
    public ScreenDTO findScreenById(Long screenId) {

        Screen searchedScreen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException("Screen", "screenId", screenId));
        return modelMapper.map(searchedScreen, ScreenDTO.class);
    }

}
