package com.moviebook.moviebook.service;

import com.moviebook.moviebook.payload.SeatResponse;

public interface SeatService {

    SeatResponse getAllSeatsByScreenId(int pageNumber, int pageSize, String sortBy, String sortDir, Long screenId);

}
