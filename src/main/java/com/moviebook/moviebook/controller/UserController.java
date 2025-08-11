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

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/auth/register")

    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")

    public ResponseEntity<JwtResponse> loginUser(@RequestBody JwtRequest jwtRequest) {
        JwtResponse jwtResponse = userService.login(jwtRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/auth/logout")

    public ResponseEntity<String> logoutUser(HttpServletRequest request) {
        userService.logout(request);
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/user/{userId}")
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
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId) {
        UserDTO deletedUser = userService.deleteUser(userId);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId) {
        UserDTO updatedUserRole = userService.updateUserRole(userId);
        return new ResponseEntity<>(updatedUserRole, HttpStatus.OK);
    }

}
