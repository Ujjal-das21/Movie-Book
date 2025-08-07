package com.moviebook.moviebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.User;

public interface UserRepository extends  JpaRepository<User, Long> {

    User findByEmail(String email);

}
