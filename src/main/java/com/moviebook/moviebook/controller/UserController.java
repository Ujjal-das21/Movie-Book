package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.payload.CreateUserDTO;
import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.payload.UserDTO;
import com.moviebook.moviebook.security.model.CustomUserDetails;
import com.moviebook.moviebook.security.model.JwtRequest;
import com.moviebook.moviebook.security.model.JwtResponse;
import com.moviebook.moviebook.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/auth/register")

    @Operation(summary = "Get user details", description = "Fetch details of a user by their unique user ID", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                   // usually
                                                                                                                                                                   // only
                                                                                                                                                                   // admin
                                                                                                                                                                   // or
                                                                                                                                                                   // the
                                                                                                                                                                   // user
                                                                                                                                                                   // himself
                                                                                                                                                                   // can
                                                                                                                                                                   // view
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - You are not allowed to access this resource"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")

    @Operation(summary = "User login", description = "Authenticates a user with email and password, and returns a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid login request (missing or bad data)"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid email or password")
    })

    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = userService.login(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/auth/logout")

    @Operation(summary = "User logout", description = "Logs out the currently authenticated user by invalidating the session or JWT token", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                                                 // logout
                                                                                                                                                                                                 // ke
                                                                                                                                                                                                 // liye
                                                                                                                                                                                                 // token
                                                                                                                                                                                                 // chahiye
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid")
    })

    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @GetMapping("/admin/users")

    @Operation(summary = "Get all users", description = "Fetches a list of all registered users (admin-only endpoint)", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                             // admin
                                                                                                                                                                             // role
                                                                                                                                                                             // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can access this endpoint")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")

    @Operation(summary = "Get user by ID", description = "Fetches a user’s details using their unique ID", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                // user
                                                                                                                                                                // or
                                                                                                                                                                // admin
                                                                                                                                                                // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and details returned"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - you cannot access another user's details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })

    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")

    @Operation(summary = "Update user details", description = "Updates the details of an existing user by their ID", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                          // user
                                                                                                                                                                          // himself
                                                                                                                                                                          // or
                                                                                                                                                                          // admin
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - you cannot update another user's details"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO,
            Authentication authentication) {
        CustomUserDetails loggedInUser = (CustomUserDetails) authentication.getPrincipal();

        if (!loggedInUser.getUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        UserDTO updatedUser = userService.updatedUser(userId, userDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")

    @Operation(summary = "Delete user", description = "Deletes a user by their ID (admin-only endpoint)", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                               // admin
                                                                                                                                                               // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can delete users"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        UserDTO deletedUser = userService.deleteUser(userId);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}/role")

    @Operation(summary = "Update user role", description = "Updates the role of a user (admin-only endpoint)", security = @SecurityRequirement(name = "bearerAuth") // 🔒
                                                                                                                                                                    // admin
                                                                                                                                                                    // required
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid role specified"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only admins can update roles"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId) {
        UserDTO updatedUserRole = userService.updateUserRole(userId);
        return new ResponseEntity<>(updatedUserRole, HttpStatus.OK);
    }

}
