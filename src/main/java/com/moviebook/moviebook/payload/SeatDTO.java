package com.moviebook.moviebook.payload;

import com.moviebook.moviebook.config.constants.SeatType;
import com.moviebook.moviebook.model.Screen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatDTO {

    private Long seatId;
    private Long seatNumber;
    private SeatType seatType; 
     private Long screenId;

}
