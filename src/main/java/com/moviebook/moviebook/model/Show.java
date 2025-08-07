package com.moviebook.moviebook.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long showId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="movieId",nullable=false)
    private Movie movie;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="screenId",nullable=false)
    private Screen screen;

     private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;

    private Double pricePerSeat;

}
