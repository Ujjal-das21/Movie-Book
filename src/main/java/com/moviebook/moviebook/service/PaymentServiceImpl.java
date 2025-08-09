package com.moviebook.moviebook.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.config.constants.BookingStatus;
import com.moviebook.moviebook.config.constants.PaymentStatus;
import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Booking;
import com.moviebook.moviebook.model.Payment;
import com.moviebook.moviebook.model.User;
import com.moviebook.moviebook.payload.PaymentDTO;
import com.moviebook.moviebook.repositories.BookingRepository;
import com.moviebook.moviebook.repositories.PaymentRepository;
import com.moviebook.moviebook.repositories.ShowSeatRepository;
import com.moviebook.moviebook.repositories.UserRepository;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PaymentDTO createPayment(PaymentDTO paymentDTO) {

        Long bookingId = paymentDTO.getBookingId();
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

        // Check if already paid
        if (paymentRepository.existsByBooking_BookingId(bookingId)) {
            throw new APIException("Payment already exists for this booking!");
        }

        // Verify amount
        if (!booking.getTotalPrice().equals(paymentDTO.getAmount())) {
            throw new APIException("Payment amount mismatch!");
        }

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING); // Simulate
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // Return DTO
        return modelMapper.map(savedPayment, PaymentDTO.class);
    }

    @Override
    public PaymentDTO getPaymentByBookingId(Long bookingId) {

        Payment payment = paymentRepository.findByBooking_BookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "bookingId", bookingId));
        return modelMapper.map(payment, PaymentDTO.class);

    }

    @Override
    public List<PaymentDTO> getPaymentByUserId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        List<Booking> userBookings = bookingRepository.findByUser_UserId(userId);
        if (userBookings.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> bookingIds = userBookings.stream()
                .map(Booking::getBookingId)
                .toList();
        List<Payment> payments = paymentRepository.findByBooking_BookingIdIn(bookingIds);
        List<PaymentDTO> paymentDTOs = payments.stream()
                .map(p -> modelMapper.map(p, PaymentDTO.class))
                .toList();
        return paymentDTOs;
    }

    @Override
    public PaymentDTO getPaymentById(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));

        return modelMapper.map(payment, PaymentDTO.class);
    }

    @Override
    public PaymentDTO updatePayment(Long paymentId, PaymentStatus status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "paymentId", paymentId));
        Booking booking = payment.getBooking();

        payment.setPaymentStatus(status);

        if (status == PaymentStatus.SUCCESS) {
            booking.setBookingStatus(BookingStatus.CONFIRMED);
        } else if (status == PaymentStatus.FAILED) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            booking.getBookedSeats().forEach(seat -> {
                seat.setSeatStatus(SeatStatus.AVAILABLE);
                seat.setBooking(null);
            });
            showSeatRepository.saveAll(booking.getBookedSeats());

        } else {
            throw new APIException("Error in Payment");
        }
        bookingRepository.save(booking);
        Payment savedPayment = paymentRepository.save(payment);

        
        return modelMapper.map(savedPayment, PaymentDTO.class);

    }

}
