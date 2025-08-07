package com.moviebook.moviebook.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moviebook.moviebook.model.Movie;
import com.moviebook.moviebook.model.Show;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Movie findByTitle(String title);
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    

}
