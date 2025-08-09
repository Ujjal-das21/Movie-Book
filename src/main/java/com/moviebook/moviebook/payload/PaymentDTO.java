package com.moviebook.moviebook.payload;

import java.time.LocalDateTime;

import com.moviebook.moviebook.config.constants.PaymentMethod;
import com.moviebook.moviebook.config.constants.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private LocalDateTime paymentDate;

}
