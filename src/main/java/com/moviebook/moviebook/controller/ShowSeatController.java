package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.SeatResponse;
import com.moviebook.moviebook.payload.ShowSeatDTO;
import com.moviebook.moviebook.payload.ShowSeatResponse;
import com.moviebook.moviebook.service.ShowSeatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class ShowSeatController {

    @Autowired
    ShowSeatService showSeatService;

    @GetMapping("/public/show/{showId}")

    @Operation(summary = "Get all show seats of a show", description = "Fetches all seats (booked and available) for a specific show by showId", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show seats fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Show not found or no seats available")
    })

    public ResponseEntity<ShowSeatResponse> getAllShowSeatOfShow(@PathVariable Long showId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "showSeatId") String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        ShowSeatResponse response = showSeatService.getFilteredShowSeats(showId, status, pageNumber, pageSize, sortBy,
                sortDir);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
