package com.moviebook.moviebook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.Screen;

public interface ScreenRepository extends  JpaRepository<Screen, Long> {

    Screen findByNameAndTheater_TheaterId(String name, Long theaterId);

}
