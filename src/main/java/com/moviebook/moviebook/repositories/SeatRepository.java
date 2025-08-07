package com.moviebook.moviebook.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.Seat;

public interface SeatRepository extends  JpaRepository<Seat, Long> {

    Page<Seat> findByScreen_ScreenId(Long screenId, Pageable pageable);

}
