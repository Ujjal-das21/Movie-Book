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
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.ScreenDTO;
import com.moviebook.moviebook.service.ScreenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class ScreenController {

    @Autowired
    ScreenService screenService;

    @PostMapping("/admin/theaters/{theaterId}/screens")
    @Operation(summary = "Create screen", description = "Adds a new screen to a specific theater", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                        // admin
                                                                                                                                                        // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Screen created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid screen request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Theater not found")
    })

    public ResponseEntity<ScreenDTO> createScreen(@PathVariable Long theaterId, @RequestBody ScreenDTO screenDTO) {
        ScreenDTO createdScreen = screenService.createScreen(theaterId, screenDTO);
        return new ResponseEntity<>(createdScreen, HttpStatus.CREATED);
    }

    @PutMapping("/admin/screens/{screenId}")
    @Operation(summary = "Update screen", description = "Updates the details of an existing screen", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                          // admin
                                                                                                                                                          // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid update request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })

    public ResponseEntity<ScreenDTO> updateScreen(@PathVariable Long screenId, @RequestBody ScreenDTO screenDTO) {
        ScreenDTO updatedScreen = screenService.updateScreen(screenId, screenDTO);
        return new ResponseEntity<>(updatedScreen, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/screens/{screenId}")
    @Operation(summary = "Delete screen", description = "Deletes a screen by its ID", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                           // admin
                                                                                                                                           // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can delete screens"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })

    public ResponseEntity<ScreenDTO> deleteScreen(@PathVariable Long screenId) {
        ScreenDTO deletedScreen = screenService.deleteScreen(screenId);
        return new ResponseEntity<>(deletedScreen, HttpStatus.OK);
    }

    @GetMapping("/public/theaters/{theaterId}/screens")
    @Operation(summary = "Get screens by theater ID", description = "Fetches all screens associated with a specific theater", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screens fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "No screens found for this theater")
    })

    public ResponseEntity<List<ScreenDTO>> getScreenByTheaterId(@PathVariable Long theaterId) {
        List<ScreenDTO> screenList = screenService.getScreenByTheaterId(theaterId);
        return new ResponseEntity<>(screenList, HttpStatus.OK);
    }

    @GetMapping("/public/screens/{screenId}")

    @Operation(summary = "Get screen by ID", description = "Fetches details of a specific screen using its ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen details fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Screen not found")
    })

    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable Long screenId) {
        ScreenDTO searchedScreen = screenService.findScreenById(screenId);
        return new ResponseEntity<>(searchedScreen, HttpStatus.OK);
    }

}
