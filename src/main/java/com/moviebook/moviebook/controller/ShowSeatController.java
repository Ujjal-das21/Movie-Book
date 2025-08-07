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

@RestController
@RequestMapping("/api")
public class ShowSeatController {

    @Autowired
    ShowSeatService showSeatService;

    @GetMapping("/public/show/{showId}")

    public ResponseEntity<ShowSeatResponse> getAllShowSeatOfShow(@PathVariable Long showId,
    @RequestParam(value = "status", required = false) String status,
    @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "showSeatId") String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir)
    {
        ShowSeatResponse response =showSeatService.getFilteredShowSeats(showId, status, pageNumber, pageSize, sortBy, sortDir);
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
