package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.ShowDTO;
import com.moviebook.moviebook.payload.ShowResponse;

public interface ShowService {

    ShowDTO createShow(Long theaterId, Long screenId, Long movieId, ShowDTO showDTO);

    ShowResponse getAllShows(int pageNumber, int pageSize, String sortBy, String sortDir);

    List<ShowDTO> getAllShowsByMovieId(Long movieId);

    ShowResponse getAllShowsByTheaterId(int pageNumber, int pageSize, String sortBy, String sortDir,Long theaterId);

    ShowResponse getAllShowsByScreenId(int pageNumber, int pageSize, String sortBy, String sortDir, Long screenId);

    ShowDTO deleteShow(Long showId);

}
