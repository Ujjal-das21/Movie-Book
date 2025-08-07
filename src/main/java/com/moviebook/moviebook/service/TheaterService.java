package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.payload.TheaterResponse;

public interface TheaterService {

    TheaterDTO createTheater(TheaterDTO theaterDTO);
     TheaterDTO updateTheater(TheaterDTO theaterDTO,Long theaterId);
     TheaterDTO deleteTheater(Long theaterId);
     TheaterResponse getAllTheaters(int pageNumber, int pageSize, String sortBy, String sortDir);
     TheaterDTO findTheaterById(Long theaterId);
     List<TheaterDTO> searchTheatersByKeyword(String keyword);


}
