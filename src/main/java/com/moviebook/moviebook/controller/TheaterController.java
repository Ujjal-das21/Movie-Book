package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.MovieResponse;
import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.payload.TheaterResponse;
import com.moviebook.moviebook.service.TheaterService;

@RestController
@RequestMapping("/api")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/admin/theater")

    public ResponseEntity<TheaterDTO> createTheater(@RequestBody TheaterDTO theaterDTO )
    {
        TheaterDTO createdTheater=theaterService.createTheater(theaterDTO);
        return new ResponseEntity<>(createdTheater,HttpStatus.CREATED);
    }

     @PutMapping("/admin/theaters/{theaterId}")

     public ResponseEntity<TheaterDTO> updateTheater(@RequestBody TheaterDTO theaterDTO,@PathVariable Long theaterId)
     {
       TheaterDTO updatedTheater=theaterService.updateTheater(theaterDTO, theaterId);
         return new ResponseEntity<>(updatedTheater,HttpStatus.CREATED);
     }

     @DeleteMapping("/admin/theaters/{theaterId}")

     public ResponseEntity<TheaterDTO> deleteTheater(@PathVariable Long theaterId)
     {
       TheaterDTO deletedTheater=theaterService.deleteTheater(theaterId);
         return new ResponseEntity<>(deletedTheater,HttpStatus.CREATED);
     }
  

    @GetMapping("/public/theaters")

    public ResponseEntity<TheaterResponse> getAllTheaters(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        TheaterResponse response=theaterService.getAllTheaters(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     @GetMapping("/public/theaters/{theaterId}")

     public ResponseEntity<TheaterDTO> getTheaterById(@PathVariable Long theaterId)
     {
        TheaterDTO createdTheater=theaterService.findTheaterById(theaterId);
        return new ResponseEntity<>(createdTheater,HttpStatus.CREATED);
     }

     @GetMapping("/public/theaters/search")

     public ResponseEntity<List<TheaterDTO>> searchTheater(@RequestParam("keyword") String keyword)
     {
       return ResponseEntity.ok(theaterService.searchTheatersByKeyword(keyword));
     }


}
