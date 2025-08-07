package com.moviebook.moviebook.payload;

import java.time.LocalDate;
import java.time.LocalTime;

import com.moviebook.moviebook.model.Movie;
import com.moviebook.moviebook.model.Screen;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDTO {

    private Long showId;
    private Long movieId;      
    private Long screenId; 
     private LocalDate showDate; 
    private LocalTime startTime;
    private LocalTime endTime;
    private Double pricePerSeat;
}
