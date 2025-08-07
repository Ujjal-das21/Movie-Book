package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.ScreenDTO;

public interface ScreenService {

    ScreenDTO createScreen(Long theaterId, ScreenDTO screenDTO);

    ScreenDTO updateScreen(Long screenId, ScreenDTO screenDTO);

    ScreenDTO deleteScreen(Long screenId);

    List<ScreenDTO> getScreenByTheaterId(Long theaterId);

    ScreenDTO findScreenById(Long screenId);

}
