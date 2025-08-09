package com.moviebook.moviebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebook.moviebook.config.constants.PaymentStatus;
import com.moviebook.moviebook.payload.PaymentDTO;
import com.moviebook.moviebook.service.PaymentService;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payments")

    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO)
    {
        PaymentDTO payment=paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }
    @PutMapping("/payments/{paymentId}")

    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long paymentId,@RequestParam PaymentStatus status)
    {
        PaymentDTO updatedPayment=paymentService.updatePayment(paymentId,status);
        return new ResponseEntity<>(updatedPayment, HttpStatus.CREATED);
    }
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId)
    {
        PaymentDTO payment=paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/payments/booking/{bookingId}")

    public ResponseEntity<PaymentDTO> getPaymentByBookingId(@PathVariable Long bookingId)
    {
       PaymentDTO payment=paymentService.getPaymentByBookingId(bookingId);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/payments/user/{userId}")

    public ResponseEntity<List<PaymentDTO>> getPaymentByUserId(@PathVariable Long userId)
    {
         List<PaymentDTO> payments=paymentService.getPaymentByUserId(userId);
        return new ResponseEntity<>(payments, HttpStatus.CREATED);
    }

}
