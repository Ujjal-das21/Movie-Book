package com.moviebook.moviebook.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.model.ShowSeat;

import jakarta.persistence.LockModeType;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShow_ShowId(Long showId);

    Page<ShowSeat> findByShow_ShowId(Long showId, Pageable pageable);

    Page<ShowSeat> findByShow_ShowIdAndSeatStatus(Long showId, SeatStatus seatStatus, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM ShowSeat s WHERE s.show.showId = :showId AND s.showSeatId IN :seatIds")
    List<ShowSeat> findByShow_ShowIdAndShowSeatIdIn(Long showId, List<Long> seatIds);

}
