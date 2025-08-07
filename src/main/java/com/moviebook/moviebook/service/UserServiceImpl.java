package com.moviebook.moviebook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.config.constants.UserRole;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.User;
import com.moviebook.moviebook.payload.UserDTO;
import com.moviebook.moviebook.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User userFromDb = userRepository.findByEmail(userDTO.getEmail());

        if (userFromDb != null) {
            throw new APIException("User with the email " + userDTO.getEmail() + " already exists!!");
        }
        User user = modelMapper.map(userDTO, User.class);
        // set user role as User by default
        user.setRole(UserRole.USER);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {

        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        userRepository.delete(user);
        return modelMapper.map(user, UserDTO.class);

    }

    @Override
    public UserDTO updatedUser(Long userId, UserDTO userDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDTO.getName());
        if (!user.getEmail().equals(userDTO.getEmail())) {
            if (userRepository.findByEmail(userDTO.getEmail())!=null) {
                throw new APIException("Email already in use!");
            }
            user.setEmail(userDTO.getEmail());
        }
        user.setPhone(userDTO.getPhone());
        User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }

    @Override
    public UserDTO updateUserRole(Long userId) {
         User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
                user.setRole(UserRole.ADMIN);
                User updated = userRepository.save(user);
        return modelMapper.map(updated, UserDTO.class);
    }

}
