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

import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.payload.UserDTO;
import com.moviebook.moviebook.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/public/users")

    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO )
    {
         UserDTO createdUser=userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }

    @GetMapping("/public/users")
    public ResponseEntity<List<UserDTO>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/public/users/{userId}")

    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId)
    {
        UserDTO user=userService.getUserById(userId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/public/users/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId,@RequestBody UserDTO userDTO )
    {
        UserDTO updatedUser=userService.updatedUser(userId,userDTO);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Long userId)
    {
        UserDTO deletedUser=userService.deleteUser(userId);
        return new ResponseEntity<>(deletedUser,HttpStatus.OK);
    }

    @PutMapping("/admin/users/{userId}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long userId)
    {
        UserDTO updatedUserRole=userService.updateUserRole(userId);
        return new ResponseEntity<>(updatedUserRole,HttpStatus.OK);
    }

}
