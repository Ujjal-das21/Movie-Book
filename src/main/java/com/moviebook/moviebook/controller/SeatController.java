package com.moviebook.moviebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.SeatResponse;
import com.moviebook.moviebook.payload.ShowResponse;
import com.moviebook.moviebook.service.SeatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class SeatController {

    @Autowired
    SeatService seatService;

    @GetMapping("/public/seats/screen/{screenId}")

    @Operation(summary = "Get all seats in a screen", description = "Fetches all the seats (booked and available) for a given screen by screenId", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seats fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Screen not found or no seats available")
    })
    public ResponseEntity<SeatResponse> getAllSeatsInScreen(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "seatId", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable Long screenId) {
        SeatResponse allSeatsByScrrenId = seatService.getAllSeatsByScreenId(pageNumber, pageSize, sortBy, sortDir,
                screenId);
        return new ResponseEntity<>(allSeatsByScrrenId, HttpStatus.OK);
    }

    @PutMapping("/admin/seats/{seatId}")

    @Operation(summary = "Seat management endpoints (planned)", description = "This controller will handle seat-related operations like  update seat, delete seat, and get seats by screen. Scheduled for next version.")

    public String updateSeat() {
        return "Seat updated successfully";
    }

    @DeleteMapping("/admin/seats/{seatId}")

    @Operation(summary = "Seat management endpoints (planned)", description = "This controller will handle seat-related operations like  update seat, delete seat, and get seats by screen. Scheduled for next version.")

    public String deleteSeat() {
        return "Seat deleted Successfully";
    }

}
