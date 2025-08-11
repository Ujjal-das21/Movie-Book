package com.moviebook.moviebook.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.moviebook.moviebook.config.constants.UserRole;
import com.moviebook.moviebook.model.User;
import com.moviebook.moviebook.repositories.UserRepository;
import com.moviebook.moviebook.security.jwt.JwtTokenUtil;
import com.moviebook.moviebook.security.model.CustomUserDetails;

@Configuration
public class DataInitializer {
     @Autowired
    private AuthenticationManager authenticationManager;
     @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Bean
    public CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "ujjal@admin.com";

            User admin = userRepository.findByEmail(adminEmail);
            if (admin == null) {
                admin = new User();
                admin.setName("admin");
                admin.setEmail(adminEmail);
                admin.setPhone("8176217890");
                admin.setPassword(passwordEncoder.encode("root")); // password ko encode kar
                admin.setRole(UserRole.ADMIN);

                userRepository.save(admin);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                adminEmail,
                                "root"));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String token = jwtTokenUtil.generateToken(userDetails);
                System.out.println("✅ Admin user created: " + adminEmail);
            } else {
                System.out.println("ℹ Admin user already exists: " + adminEmail);
            }
        };
    }

}
