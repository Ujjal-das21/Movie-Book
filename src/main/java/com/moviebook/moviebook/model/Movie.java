package com.moviebook.moviebook.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="movies")
@NoArgsConstructor
@AllArgsConstructor

public class Movie {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long movieId;
    
    private String title;

     private String description;

      private String genre;

       private Integer duration;

       private LocalDate releaseDate;



}
// movie_id (PK)

// title

// description

// genre

// duration (in minutes)

// release_date

// language