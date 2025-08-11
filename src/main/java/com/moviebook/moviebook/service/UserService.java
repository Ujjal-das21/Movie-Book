package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.CreateUserDTO;
import com.moviebook.moviebook.payload.UserDTO;
import com.moviebook.moviebook.security.model.JwtRequest;
import com.moviebook.moviebook.security.model.JwtResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    UserDTO createUser(CreateUserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    UserDTO deleteUser(Long userId);

    UserDTO updatedUser(Long userId, UserDTO userDTO);

    UserDTO updateUserRole(Long userId);

    JwtResponse login(JwtRequest jwtRequest);

    void logout(HttpServletRequest request);

}
