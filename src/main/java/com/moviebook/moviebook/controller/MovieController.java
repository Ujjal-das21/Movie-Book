package com.moviebook.moviebook.controller;

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

import com.moviebook.moviebook.payload.MovieDTO;
import com.moviebook.moviebook.payload.MovieResponse;
import com.moviebook.moviebook.service.MovieService;

@RestController
@RequestMapping("/api")

public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/admin/movie")

    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO)
    {
        MovieDTO savedMovieDTO=movieService.createMovie(movieDTO);
       return new ResponseEntity<>(savedMovieDTO,HttpStatus.CREATED);
    }

    @PutMapping("/admin/movies/{movieId}")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO updatedMovieDTO,@PathVariable Long movieId)
    {
        MovieDTO updatedMovie=movieService.updateMovie(updatedMovieDTO, movieId);
        return new ResponseEntity<>(updatedMovie,HttpStatus.OK);

    }
    @DeleteMapping("/admin/movies/{movieId}")
    public ResponseEntity<MovieDTO> deleteMovie(@PathVariable Long movieId)
    {
        MovieDTO deleteMovie=movieService.deleteMovie(movieId);
       return new ResponseEntity<>(deleteMovie,HttpStatus.OK);
        
    }

    @GetMapping("/public/movies/{movieId}")

    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long movieId)
    {
         MovieDTO movieResult=movieService.getMovieById(movieId);

          return new ResponseEntity<>(movieResult,HttpStatus.OK);

    }

    @GetMapping("/public/movies")

    public ResponseEntity<MovieResponse> getAllMovies(@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "releaseDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        MovieResponse response = movieService.getAllMovies(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/movies/title")

   public ResponseEntity<MovieResponse> getMovieByTitle( @RequestParam String title,
    @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "releaseDate", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {
        MovieResponse response = movieService.getMoviesByTitle(title, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
