package com.moviebook.moviebook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.Booking;
import com.moviebook.moviebook.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByBooking_BookingId(Long bookingId);

    Optional<Payment> findByBooking_BookingId(Long bookingId);

    List<Payment> findByBooking_BookingIdIn(List<Long> bookingIds);


}
