package com.moviebook.moviebook.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.moviebook.moviebook.config.constants.SeatPricing;
import com.moviebook.moviebook.config.constants.SeatStatus;
import com.moviebook.moviebook.config.constants.SeatType;
import com.moviebook.moviebook.exceptions.APIException;
import com.moviebook.moviebook.exceptions.ResourceNotFoundException;
import com.moviebook.moviebook.model.Movie;
import com.moviebook.moviebook.model.Screen;
import com.moviebook.moviebook.model.Seat;
import com.moviebook.moviebook.model.Show;
import com.moviebook.moviebook.model.ShowSeat;
import com.moviebook.moviebook.model.Theater;
import com.moviebook.moviebook.payload.MovieDTO;
import com.moviebook.moviebook.payload.MovieResponse;
import com.moviebook.moviebook.payload.ShowDTO;
import com.moviebook.moviebook.payload.ShowResponse;
import com.moviebook.moviebook.payload.TheaterDTO;
import com.moviebook.moviebook.repositories.MovieRepository;
import com.moviebook.moviebook.repositories.ScreenRepository;
import com.moviebook.moviebook.repositories.ShowRepository;
import com.moviebook.moviebook.repositories.ShowSeatRepository;
import com.moviebook.moviebook.repositories.TheaterRepository;

@Service
public class ShowServiceImpl implements ShowService {

        @Autowired
        MovieRepository movieRepository;
        @Autowired
        TheaterRepository theaterRepository;
        @Autowired
        ScreenRepository screenRepository;
        @Autowired
        ShowRepository showRepository;
        @Autowired
        ShowSeatRepository showSeatRepository;

        @Autowired
        ModelMapper modelMapper;

        @Override
        public ShowDTO createShow(Long theaterId, Long screenId, Long movieId, ShowDTO showDTO) {

                Movie movie = movieRepository.findById(movieId)
                                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));

                Theater theater = theaterRepository.findById(theaterId)
                                .orElseThrow(() -> (new ResourceNotFoundException("Theater", "theaterId", theaterId)));
                Screen screen = screenRepository.findById(screenId)
                                .orElseThrow(() -> new ResourceNotFoundException("Screen", "screenId", screenId));

                LocalDate showDate = showDTO.getShowDate();
                LocalTime startTime = showDTO.getStartTime();

                int bufferMinutes = 15;
                LocalTime endTime = startTime.plusMinutes(movie.getDuration() + bufferMinutes);

                // Fetch existing shows on same screen & date
                List<Show> existingShows = showRepository.findByScreenAndShowDate(screen, showDate);
                for (Show existing : existingShows) {
                        if (startTime.isBefore(existing.getEndTime()) && existing.getStartTime().isBefore(endTime)) {
                                throw new APIException("Overlapping show exists on this screen.");
                        }
                }
                Show show = new Show();
                show.setMovie(movie);
                show.setScreen(screen);
                show.setShowDate(showDate);
                show.setStartTime(startTime);
                show.setEndTime(endTime);
                show.setPricePerSeat(showDTO.getPricePerSeat());

                Show savedShow = showRepository.save(show);
                List<Seat> screenSeats = savedShow.getScreen().getSeats();

                List<ShowSeat> showSeats = new ArrayList<>();

                for (Seat seat : screenSeats) {
                        ShowSeat showSeat = new ShowSeat();
                        SeatType seatType = seat.getSeatType(); 
                        double price = SeatPricing.valueOf(seatType.name()).getPrice(); 
                        showSeat.setPrice(price); // Set price based on type
                        showSeat.setShow(savedShow);
                        showSeat.setSeat(seat);
                        showSeat.setSeatStatus(SeatStatus.AVAILABLE);
                        showSeats.add(showSeat);
                }

                showSeatRepository.saveAll(showSeats);
                return modelMapper.map(savedShow, ShowDTO.class);

        }

        @Override
        public ShowResponse getAllShows(int pageNumber, int pageSize, String sortBy, String sortDir) {

                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
                Page<Show> pageShows = showRepository.findAll(pageable);
                List<ShowDTO> showDTOs = pageShows.getContent()
                                .stream()
                                .map(show -> modelMapper.map(show, ShowDTO.class))
                                .toList();

                ShowResponse response = new ShowResponse();
                response.setContent(showDTOs);
                response.setPageNumber(pageShows.getNumber());
                response.setPageSize(pageShows.getSize());
                response.setTotalElements(pageShows.getTotalElements());
                response.setTotalPages(pageShows.getTotalPages());
                response.setLastPage(pageShows.isLast());

                return response;
        }

        @Override
        public List<ShowDTO> getAllShowsByMovieId(Long movieId) {
                Movie movie = movieRepository.findById(movieId)
                                .orElseThrow(() -> new ResourceNotFoundException("Movie", "movieId", movieId));
                List<Show> showList = showRepository.findByMovieMovieId(movieId);
                List<ShowDTO> showDTOsList = showList
                                .stream()
                                .map(show -> modelMapper.map(show, ShowDTO.class))
                                .toList();

                return showDTOsList;
        }

        @Override
        public ShowResponse getAllShowsByTheaterId(int pageNumber, int pageSize, String sortBy, String sortDir,
                        Long theaterId) {

                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
                Page<Show> pageShows = showRepository.findByScreen_Theater_TheaterId(theaterId, pageable);
                List<ShowDTO> showDTOs = pageShows.getContent()
                                .stream()
                                .map(show -> modelMapper.map(show, ShowDTO.class))
                                .toList();

                ShowResponse response = new ShowResponse();
                response.setContent(showDTOs);
                response.setPageNumber(pageShows.getNumber());
                response.setPageSize(pageShows.getSize());
                response.setTotalElements(pageShows.getTotalElements());
                response.setTotalPages(pageShows.getTotalPages());
                response.setLastPage(pageShows.isLast());

                return response;
        }

        @Override
        public ShowResponse getAllShowsByScreenId(int pageNumber, int pageSize, String sortBy, String sortDir,
                        Long screenId) {

                Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
                Page<Show> pageShows = showRepository.findByScreenScreenId(screenId, pageable);
                List<ShowDTO> showDTOs = pageShows.getContent()
                                .stream()
                                .map(show -> modelMapper.map(show, ShowDTO.class))
                                .toList();

                ShowResponse response = new ShowResponse();
                response.setContent(showDTOs);
                response.setPageNumber(pageShows.getNumber());
                response.setPageSize(pageShows.getSize());
                response.setTotalElements(pageShows.getTotalElements());
                response.setTotalPages(pageShows.getTotalPages());
                response.setLastPage(pageShows.isLast());

                return response;

        }

        @Override
        public ShowDTO deleteShow(Long showId) {

                Show deletedShow = showRepository.findById(showId)
                                .orElseThrow(() -> new ResourceNotFoundException("Show", "showId", showId));

                showRepository.delete(deletedShow);

                return modelMapper.map(deletedShow, ShowDTO.class);

        }

}
