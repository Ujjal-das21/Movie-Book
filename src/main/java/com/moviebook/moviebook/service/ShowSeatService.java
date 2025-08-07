package com.moviebook.moviebook.service;

import java.util.List;
import com.moviebook.moviebook.payload.ShowSeatDTO;
import com.moviebook.moviebook.payload.ShowSeatResponse;

public interface ShowSeatService  {

    List<ShowSeatDTO> getAllShowSeatOfShow(Long showId);

    ShowSeatResponse getFilteredShowSeats(Long showId, String status, int pageNumber, int pageSize, String sortBy,
            String sortDir);

}
