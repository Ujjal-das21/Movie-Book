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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")

public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/admin/movie")

    @Operation(summary = "Create a new movie", description = "Adds a new movie to the system", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                    // usually
                                                                                                                                                    // only
                                                                                                                                                    // admin
                                                                                                                                                    // can
                                                                                                                                                    // create
                                                                                                                                                    // movies
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "409", description = "Movie with same name already exists")
    })

    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO savedMovieDTO = movieService.createMovie(movieDTO);
        return new ResponseEntity<>(savedMovieDTO, HttpStatus.CREATED);
    }

    @PutMapping("/admin/movies/{movieId}")
    //
    @Operation(summary = "Update movie details", description = "Updates the details of an existing movie by its ID", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                          // since
                                                                                                                                                                          // usually
                                                                                                                                                                          // only
                                                                                                                                                                          // admin
                                                                                                                                                                          // updates
                                                                                                                                                                          // movies
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody MovieDTO updatedMovieDTO, @PathVariable Long movieId) {
        MovieDTO updatedMovie = movieService.updateMovie(updatedMovieDTO, movieId);
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);

    }

    @DeleteMapping("/admin/movies/{movieId}")

    // swagger-documenattion
    @Operation(summary = "Delete a movies by Id", description = "Deletes a movie and returns it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<MovieDTO> deleteMovie(@PathVariable Long movieId) {
        MovieDTO deleteMovie = movieService.deleteMovie(movieId);
        return new ResponseEntity<>(deleteMovie, HttpStatus.OK);

    }

    @GetMapping("/public/movies/{movieId}")

    @Operation(summary = "Get all movies by Id", description = "Fetches a movie by it's Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long movieId) {
        MovieDTO movieResult = movieService.getMovieById(movieId);

        return new ResponseEntity<>(movieResult, HttpStatus.OK);

    }

    @GetMapping("/public/movies")

    @Operation(summary = "Get all movies", description = "Fetches the list of all available movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<MovieResponse> getAllMovies(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "releaseDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        MovieResponse response = movieService.getAllMovies(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/public/movies/title")

    @Operation(summary = "Get all movies by Title", description = "Fetches the list of all available movies by Title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of movies fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<MovieResponse> getMovieByTitle(@RequestParam String title,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "releaseDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        MovieResponse response = movieService.getMoviesByTitle(title, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
