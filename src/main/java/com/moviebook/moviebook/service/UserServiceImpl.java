package com.moviebook.moviebook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.config.constants.UserRole;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.User;
import com.moviebook.moviebook.payload.CreateUserDTO;
import com.moviebook.moviebook.payload.UserDTO;
import com.moviebook.moviebook.repositories.UserRepository;
import com.moviebook.moviebook.security.jwt.JwtTokenUtil;
import com.moviebook.moviebook.security.model.CustomUserDetails;
import com.moviebook.moviebook.security.model.JwtRequest;
import com.moviebook.moviebook.security.model.JwtResponse;
import com.moviebook.moviebook.security.service.CustomUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDTO createUser(CreateUserDTO userDTO) {

        User userFromDb = userRepository.findByEmail(userDTO.getEmail());

        if (userFromDb != null) {
            throw new APIException("User with the email " + userDTO.getEmail() + " already exists!!");
        }
        User user = modelMapper.map(userDTO, User.class);
        // set user role as User by default
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

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

    @Override
    public JwtResponse login(JwtRequest jwtRequest) {
        
         Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getEmail(),
                        jwtRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
         String token = jwtTokenUtil.generateToken(userDetails);
         
        return new JwtResponse(token);
    }

    @Override
    public void logout(HttpServletRequest request) {
        
        // JWT stateless hota hai, so logout ka kaam mostly client pe hota hai
        // Agar blacklist maintain karni hai toh DB me store karke expiry tak reject karna padega
        SecurityContextHolder.clearContext();
    }

}
