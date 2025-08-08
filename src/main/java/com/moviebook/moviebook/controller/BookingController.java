package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.BookingDTO;
import com.moviebook.moviebook.service.BookingService;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO )
    {
        BookingDTO booking=bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(booking,HttpStatus.CREATED);
    }

    @GetMapping("/bookings/{bookingId}")

    public  ResponseEntity<BookingDTO> getBookingDetails(@PathVariable Long bookingId)
    {
        BookingDTO booking=bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking,HttpStatus.OK);
    }

    @GetMapping("/bookings/user/{userId}")

    public ResponseEntity<List<BookingDTO>> getBookingByUser(@PathVariable Long userId)
    {
        List<BookingDTO>bookingsByUser=bookingService.getBookingByUser(userId);

        return new ResponseEntity<>(bookingsByUser,HttpStatus.OK);
    }

    @DeleteMapping("/bookings/{bookingId}")

    public  ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long bookingId)
    {
         BookingDTO canceledBooking=bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(canceledBooking,HttpStatus.OK);
    }
}
