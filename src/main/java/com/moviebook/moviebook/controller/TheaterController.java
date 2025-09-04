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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class TheaterController {

  @Autowired
  private TheaterService theaterService;

  @PostMapping("/admin/theater")

  @Operation(summary = "Create a new theater", description = "Adds a new theater with details like name, location, and capacity", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                                       // Admin
                                                                                                                                                                                       // only
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Theater created successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - JWT missing or invalid")
  })

  public ResponseEntity<TheaterDTO> createTheater(@RequestBody TheaterDTO theaterDTO) {
    TheaterDTO createdTheater = theaterService.createTheater(theaterDTO);
    return new ResponseEntity<>(createdTheater, HttpStatus.CREATED);
  }

  @PutMapping("/admin/theaters/{theaterId}")

  @Operation(summary = "Update theater", description = "Updates details of an existing theater by theaterId", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                   // Admin
                                                                                                                                                                   // only
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Theater updated successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "404", description = "Theater not found")
  })

  public ResponseEntity<TheaterDTO> updateTheater(@RequestBody TheaterDTO theaterDTO, @PathVariable Long theaterId) {
    TheaterDTO updatedTheater = theaterService.updateTheater(theaterDTO, theaterId);
    return new ResponseEntity<>(updatedTheater, HttpStatus.OK);
  }

  @DeleteMapping("/admin/theaters/{theaterId}")

  @Operation(summary = "Delete theater", description = "Deletes a theater from the system by theaterId", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                              // Admin
                                                                                                                                                              // only
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Theater deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Theater not found"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - JWT missing or invalid")
  })

  public ResponseEntity<TheaterDTO> deleteTheater(@PathVariable Long theaterId) {
    TheaterDTO deletedTheater = theaterService.deleteTheater(theaterId);
    return new ResponseEntity<>(deletedTheater, HttpStatus.OK);
  }

  @GetMapping("/public/theaters")

  @Operation(summary = "Get all theaters", description = "Fetches a list of all theaters in the system", security = @SecurityRequirement(name = "bearerAuth") // Can
                                                                                                                                                              // be
                                                                                                                                                              // open/public
                                                                                                                                                              // if
                                                                                                                                                              // you
                                                                                                                                                              // want
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of theaters fetched successfully"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
  })

  public ResponseEntity<TheaterResponse> getAllTheaters(
      @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
    TheaterResponse response = theaterService.getAllTheaters(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/public/theaters/{theaterId}")

  @Operation(summary = "Get theater by ID", description = "Fetches details of a theater using its unique ID", security = @SecurityRequirement(name = "bearerAuth"))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Theater details fetched successfully"),
      @ApiResponse(responseCode = "404", description = "Theater not found")
  })

  public ResponseEntity<TheaterDTO> getTheaterById(@PathVariable Long theaterId) {
    TheaterDTO createdTheater = theaterService.findTheaterById(theaterId);
    return new ResponseEntity<>(createdTheater, HttpStatus.CREATED);
  }

  @GetMapping("/public/theaters/search")

  @Operation(summary = "Search theaters", description = "Searches theaters based on name, city, or location", security = @SecurityRequirement(name = "bearerAuth") // or
                                                                                                                                                                   // public
                                                                                                                                                                   // if
                                                                                                                                                                   // needed
  )
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Theater search results fetched successfully"),
      @ApiResponse(responseCode = "404", description = "No matching theaters found")
  })

  public ResponseEntity<List<TheaterDTO>> searchTheater(@RequestParam("keyword") String keyword) {
    return ResponseEntity.ok(theaterService.searchTheatersByKeyword(keyword));
  }

}
