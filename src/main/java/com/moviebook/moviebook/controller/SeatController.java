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

@RestController
@RequestMapping("/api")
public class SeatController {

    @Autowired
    SeatService seatService;

    @GetMapping("/public/seats/screen/{screenId}")
    public ResponseEntity<SeatResponse> getAllSeatsInScreen(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "seatId", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,@PathVariable Long screenId)
    {
         SeatResponse allSeatsByScrrenId=seatService.getAllSeatsByScreenId(pageNumber, pageSize, sortBy, sortDir,screenId);
         return new ResponseEntity<>(allSeatsByScrrenId, HttpStatus.OK);
    }

    @PutMapping("/admin/seats/{seatId}")
    
    public String updateSeat()
    {
        return "Seat updated successfully";
    }

    @DeleteMapping("/admin/seats/{seatId}")

    public String deleteSeat()
    {
        return "Seat deleted Successfully";
    }

    

}
