package com.moviebook.moviebook.payload;

import java.time.LocalDateTime;
import java.util.List;

import com.moviebook.moviebook.config.constants.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {

     private Long bookingId;
    private Long userId;
    private Long showId;
    private List<Long> showSeatIds; // Seats that user selected
    private Double totalPrice;
    private LocalDateTime bookingTime;
    private BookingStatus bookingStatus;

}
