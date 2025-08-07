package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.payload.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    UserDTO deleteUser(Long userId);

    UserDTO updatedUser(Long userId, UserDTO userDTO);

    UserDTO updateUserRole(Long userId);

}
