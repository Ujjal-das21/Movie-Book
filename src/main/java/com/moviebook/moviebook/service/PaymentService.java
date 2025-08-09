package com.moviebook.moviebook.service;

import java.util.List;

import com.moviebook.moviebook.config.constants.PaymentStatus;
import com.moviebook.moviebook.payload.PaymentDTO;

public interface PaymentService {

    PaymentDTO createPayment(PaymentDTO paymentDTO);

    PaymentDTO getPaymentByBookingId(Long bookingId);

    List<PaymentDTO> getPaymentByUserId(Long userId);

    PaymentDTO getPaymentById(Long paymentId);

    PaymentDTO updatePayment(Long paymentId, PaymentStatus status);

}
