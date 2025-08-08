package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.BookingDTO;

public interface BookingService {

    BookingDTO createBooking(BookingDTO bookingDTO);

    BookingDTO getBookingById(Long bookingId);

    List<BookingDTO> getBookingByUser(Long userId);

    BookingDTO cancelBooking(Long bookingId);

    

}
