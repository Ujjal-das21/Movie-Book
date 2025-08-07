package com.moviebook.moviebook.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebook.moviebook.model.Screen;
import com.moviebook.moviebook.model.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findByScreenAndShowDate(Screen screen, LocalDate showDate);

    List<Show> findByMovieMovieId(Long movieId);

    Page<Show> findByScreen_Theater_TheaterId(Long theaterId, Pageable pageable);

    Page<Show> findByScreenScreenId(Long screenId, Pageable pageable);

}
