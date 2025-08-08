package com.moviebook.moviebook.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviebook.moviebook.config.constants.BookingStatus;
import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Booking;
import com.moviebook.moviebook.model.Show;
import com.moviebook.moviebook.model.ShowSeat;
import com.moviebook.moviebook.model.User;
import com.moviebook.moviebook.payload.BookingDTO;
import com.moviebook.moviebook.repositories.BookingRepository;
import com.moviebook.moviebook.repositories.ShowRepository;
import com.moviebook.moviebook.repositories.ShowSeatRepository;
import com.moviebook.moviebook.repositories.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ShowRepository showRepository;

    @Autowired
    ShowSeatRepository showSeatRepository;

    @Transactional
    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {

        User user = userRepository.findById(bookingDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Show", "showId", bookingDTO.getShowId()));

        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException("Show", "showId", bookingDTO.getShowId()));
        Booking booking = modelMapper.map(bookingDTO, Booking.class);
        List<Long> seatIds = bookingDTO.getShowSeatIds();

        List<ShowSeat> seats = showSeatRepository.findByShow_ShowIdAndShowSeatIdIn(bookingDTO.getShowId(), seatIds);
        if (seats.size() != seatIds.size()) {
            throw new APIException("Some seats not found for this show!");
        }

        for (ShowSeat seat : seats) {
            if (seat.getSeatStatus() == SeatStatus.BOOKED) {
                throw new APIException("Seat " + seat.getShowSeatId() + " already booked!");
            }
        }

        for (ShowSeat seat : seats) {
            seat.setSeatStatus(SeatStatus.BOOKED);
            seat.setBooking(booking);
        }
        // Step 3: Set booking details
        booking.setUser(user);
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookedSeats(seats);
        double totalPrice = seats.stream().mapToDouble(ShowSeat::getPrice).sum();
        booking.setTotalPrice(totalPrice);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        // Step 4: Save booking
        Booking savedBooking = bookingRepository.save(booking);

        // Step 5: Return DTO
        BookingDTO completedBooking = modelMapper.map(savedBooking, BookingDTO.class);
        completedBooking.setShowSeatIds(seatIds);

        return completedBooking;

    }

    @Override
    public BookingDTO getBookingById(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));
        BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        bookingDTO.setShowSeatIds(
                booking.getBookedSeats()
                        .stream()
                        .map(ShowSeat::getShowSeatId)
                        .toList());

        return bookingDTO;
    }

    @Override
    public List<BookingDTO> getBookingByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        List<Booking> userBookings = bookingRepository.findByUser_UserId(userId);
        List<BookingDTO> userBookingDTOs = userBookings.stream()
                .map(booking -> {
                    BookingDTO bookingDTO = modelMapper.map(booking, BookingDTO.class);
        bookingDTO.setShowSeatIds(
            booking.getBookedSeats()
                   .stream()
                   .map(ShowSeat::getShowSeatId)
                   .toList()
        );
        return bookingDTO;
                }).toList();
        return userBookingDTOs;

    }

    // will implement refund in v2
    @Override
    @Transactional
    public BookingDTO cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "bookingId", bookingId));

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new APIException("Booking with BookingId " + bookingId + " is already Canceled!!");
        }
        List<ShowSeat> seats = booking.getBookedSeats();
        for (ShowSeat seat : seats) {
            if (seat.getSeatStatus() == SeatStatus.AVAILABLE) {
                throw new APIException("Seat " + seat.getShowSeatId() + " already canceled!");
            }
            seat.setSeatStatus(SeatStatus.AVAILABLE);
            seat.setBooking(null);
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setCancelledAt(LocalDateTime.now());
        showSeatRepository.saveAll(seats);

        Booking canceledBooking = bookingRepository.save(booking);
        BookingDTO canceledBookingDTO = modelMapper.map(canceledBooking, BookingDTO.class);
        canceledBookingDTO.setShowSeatIds(
                seats.stream().map(ShowSeat::getShowSeatId).toList());

        return canceledBookingDTO;
    }

}
