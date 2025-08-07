package com.moviebook.moviebook.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moviebook.moviebook.model.Theater;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {

Optional<Theater> findByNameAndLocation(String name, String location);

@Query("SELECT t FROM Theater t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(t.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Theater> searchByNameOrLocation(@Param("keyword") String keyword);

}
