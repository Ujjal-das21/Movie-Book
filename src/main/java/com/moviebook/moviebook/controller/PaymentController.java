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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payments")
    @Operation(summary = "Create payment", description = "Creates a new payment record for a booking", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })

    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        PaymentDTO payment = paymentService.createPayment(paymentDTO);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @PutMapping("/payments/{paymentId}")

    @Operation(summary = "Update payment", description = "Updates an existing payment record by payment ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment update request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })

    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable Long paymentId, @RequestParam PaymentStatus status) {
        PaymentDTO updatedPayment = paymentService.updatePayment(paymentId, status);
        return new ResponseEntity<>(updatedPayment, HttpStatus.CREATED);
    }

    @GetMapping("/payments/{paymentId}")
    @Operation(summary = "Update payment", description = "Updates an existing payment record by payment ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment update request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "Payment not found")
    })
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long paymentId) {
        PaymentDTO payment = paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    @GetMapping("/payments/booking/{bookingId}")

    @Operation(summary = "Get payment by booking ID", description = "Fetches the payment details associated with a specific booking ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment details fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "No payment found for this booking ID")
    })

    public ResponseEntity<PaymentDTO> getPaymentByBookingId(@PathVariable Long bookingId) {
        PaymentDTO payment = paymentService.getPaymentByBookingId(bookingId);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @GetMapping("/payments/user/{userId}")

    @Operation(summary = "Get payments by user ID", description = "Fetches all payments made by a specific user", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User's payments fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
            @ApiResponse(responseCode = "404", description = "No payments found for this user")
    })

    public ResponseEntity<List<PaymentDTO>> getPaymentByUserId(@PathVariable Long userId) {
        List<PaymentDTO> payments = paymentService.getPaymentByUserId(userId);
        return new ResponseEntity<>(payments, HttpStatus.CREATED);
    }

}
