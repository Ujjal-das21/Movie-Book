package com.moviebook.moviebook.payload;

import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.config.constants.SeatType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDTO {

     private Long showSeatId;
      private SeatDTO seat; // optionally, or just seatNumber
    private Long showRefId;     
     private Long seatId;
    private SeatType seatType;
    private Double price;
    private SeatStatus seatStatus;
}
