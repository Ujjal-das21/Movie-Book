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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/admin/shows/theater/{theaterId}/screen/{screenId}/movie/{movieId}")

    @Operation(summary = "Create a new show", description = "Adds a new show for a given movie, theater, and screen", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                           // Admin
                                                                                                                                                                           // only
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<ShowDTO> createShow(@PathVariable Long theaterId, @PathVariable Long screenId,
            @PathVariable Long movieId, @RequestBody ShowDTO showDTO) {
        ShowDTO createdShow = showService.createShow(theaterId, screenId, movieId, showDTO);
        return new ResponseEntity<>(createdShow, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/shows/{showId}")

    @Operation(summary = "Delete a show", description = "Deletes an existing show by showId", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                   // Admin
                                                                                                                                                   // only
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<ShowDTO> deleteShow(@PathVariable Long showId) {
        ShowDTO deletedShow = showService.deleteShow(showId);
        return new ResponseEntity<>(deletedShow, HttpStatus.OK);
    }

    @PutMapping("/admin/shows/{showId}")

    @Operation(summary = "Update show (planned)", description = "This endpoint will allow updating show details (like timings, price) in version 2", security = @SecurityRequirement(name = "bearerAuth"))

    public String updateShow() {
        return "Show updated";
    }

    @GetMapping("/public/shows")

    @Operation(summary = "Get all shows", description = "Fetches a list of all shows available in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No shows found")
    })

    public ResponseEntity<ShowResponse> getAllShows(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        ShowResponse allShows = showService.getAllShows(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allShows, HttpStatus.OK);
    }

    @GetMapping("/public/shows/movie/{movieId}")
    @Operation(summary = "Get all shows for a movie", description = "Fetches all shows scheduled for a given movie by movieId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows for movie fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No shows found for the given movie")
    })

    public ResponseEntity<List<ShowDTO>> getAllShowForMovie(@PathVariable Long movieId) {
        List<ShowDTO> showByMovieId = showService.getAllShowsByMovieId(movieId);
        return new ResponseEntity<>(showByMovieId, HttpStatus.OK);
    }

    @GetMapping("/public/shows/theater/{theaterId}")

    @Operation(summary = "Get all shows in a theater", description = "Fetches all shows running in a particular theater by theaterId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows for theater fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No shows found in this theater")
    })

    public ResponseEntity<ShowResponse> getAllShowsInTheater(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable Long theaterId) {
        ShowResponse allShowsByTheater = showService.getAllShowsByTheaterId(pageNumber, pageSize, sortBy, sortDir,
                theaterId);
        return new ResponseEntity<>(allShowsByTheater, HttpStatus.OK);
    }

    @GetMapping("/public/shows/screen/{screenId}")

    @Operation(summary = "Get all shows in a screen", description = "Fetches all shows scheduled for a particular screen by screenId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows for screen fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No shows found in this screen")
    })

    public ResponseEntity<ShowResponse> getAllShowsInScreen(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "showDate", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
            @PathVariable Long screenId) {
        ShowResponse allShowsByScreenId = showService.getAllShowsByScreenId(pageNumber, pageSize, sortBy, sortDir,
                screenId);
        return new ResponseEntity<>(allShowsByScreenId, HttpStatus.OK);
    }

}
