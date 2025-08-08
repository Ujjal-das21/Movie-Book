package com.moviebook.moviebook.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUser_UserId(Long userId);

}
