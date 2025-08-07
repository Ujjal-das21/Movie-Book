package com.moviebook.moviebook.payload;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long movieId;
    private String title;
    private String description;
    private String genre;
    private Integer duration;
    private LocalDate releaseDate;

}
