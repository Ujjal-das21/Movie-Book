package com.moviebook.moviebook.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.model.ShowSeat;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShow_ShowId(Long showId);

    Page<ShowSeat> findByShow_ShowId(Long showId, Pageable pageable);

    Page<ShowSeat> findByShow_ShowIdAndSeatStatus(Long showId, SeatStatus seatStatus, Pageable pageable);

}
