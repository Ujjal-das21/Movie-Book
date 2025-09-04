package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.BookingDTO;
import com.moviebook.moviebook.security.model.CustomUserDetails;
import com.moviebook.moviebook.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings")

    @Operation(summary = "Create booking", description = "Creates a new booking for a movie show", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                        // user
                                                                                                                                                        // login
                                                                                                                                                        // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid booking request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Show or user not found")
    })
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO, Authentication authentication) {
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();

        if (!loggedInUser.getUserId().equals(bookingDTO.getUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        BookingDTO booking = bookingService.createBooking(bookingDTO);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/bookings/{bookingId}")

    @Operation(summary = "Get booking details", description = "Fetches the details of a specific booking by its ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking details fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })

    public ResponseEntity<BookingDTO> getBookingDetails(@PathVariable Long bookingId) {
        BookingDTO booking = bookingService.getBookingById(bookingId);
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/bookings/user/{userId}")

    @Operation(summary = "Get bookings by user", description = "Fetches all bookings made by a specific user", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's bookings fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "No bookings found for this user")
    })

    public ResponseEntity<List<BookingDTO>> getBookingByUser(@PathVariable Long userId) {
        List<BookingDTO> bookingsByUser = bookingService.getBookingByUser(userId);

        return new ResponseEntity<>(bookingsByUser, HttpStatus.OK);
    }

    @DeleteMapping("/bookings/{bookingId}")

    @Operation(summary = "Cancel booking", description = "Cancels an existing booking by its ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - you cannot cancel another user's booking"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })

    public ResponseEntity<BookingDTO> cancelBooking(@PathVariable Long bookingId, Authentication authentication) {
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();
        Long userId = bookingService.getBookingById(bookingId).getUserId();

        if (!loggedInUser.getUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        BookingDTO canceledBooking = bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(canceledBooking, HttpStatus.OK);
    }
}
