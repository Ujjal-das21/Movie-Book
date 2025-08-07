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

import com.moviebook.moviebook.payload.ShowDTO;
import com.moviebook.moviebook.payload.ShowResponse;
import com.moviebook.moviebook.service.ShowService;

@RestController
@RequestMapping("/api")
public class ShowController {

    @Autowired
    private ShowService showService;
    @PostMapping("/admin/shows/theater/{theaterId}/screen/{screenId}/movie/{movieId}")

    public ResponseEntity<ShowDTO> createShow(@PathVariable Long theaterId,@PathVariable Long screenId,@PathVariable Long movieId,@RequestBody ShowDTO showDTO)
    {
        ShowDTO createdShow=showService.createShow(theaterId,screenId,movieId,showDTO);
         return new ResponseEntity<>(createdShow,HttpStatus.CREATED);
    }
    @DeleteMapping("/admin/shows/{showId}")

    public ResponseEntity<ShowDTO> deleteShow(@PathVariable Long showId)
    {
        ShowDTO deletedShow=showService.deleteShow(showId);
        return new ResponseEntity<>(deletedShow,HttpStatus.OK); 
    }

    @PutMapping("/admin/shows/{showId}")

    public String updateShow()
    {
        return "Show updated";
    }


    @GetMapping("/public/shows")

    public ResponseEntity<ShowResponse> getAllShows(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        ShowResponse allShows=showService.getAllShows(pageNumber, pageSize, sortBy, sortDir);
         return new ResponseEntity<>(allShows, HttpStatus.OK);
    }

    @GetMapping("/public/shows/movie/{movieId}")

    public ResponseEntity<List<ShowDTO>> getAllShowForMovie(@PathVariable Long movieId)
    {
        List<ShowDTO> showByMovieId=showService.getAllShowsByMovieId(movieId);
        return new ResponseEntity<>(showByMovieId, HttpStatus.OK);
    }

    @GetMapping("/public/shows/theater/{theaterId}")

    public ResponseEntity<ShowResponse> getAllShowsInTheater(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
    @PathVariable Long theaterId)
    {
        ShowResponse allShowsByTheater=showService.getAllShowsByTheaterId(pageNumber, pageSize, sortBy, sortDir,theaterId);
         return new ResponseEntity<>(allShowsByTheater, HttpStatus.OK);
    }

    @GetMapping("/public/shows/screen/{screenId}")

    public ResponseEntity<ShowResponse> getAllShowsInScreen(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
    @PathVariable Long screenId)
    {
        ShowResponse allShowsByScreenId=showService.getAllShowsByScreenId(pageNumber, pageSize, sortBy, sortDir,screenId);
         return new ResponseEntity<>(allShowsByScreenId, HttpStatus.OK);
    }


}
