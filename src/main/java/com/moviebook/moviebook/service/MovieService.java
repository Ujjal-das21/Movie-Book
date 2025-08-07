package com.moviebook.moviebook.service;

import com.moviebook.moviebook.payload.MovieDTO;
import com.moviebook.moviebook.payload.MovieResponse;

public interface MovieService {

    MovieDTO createMovie(MovieDTO movieDTO);
    MovieDTO deleteMovie(Long movieId);
    MovieDTO updateMovie(MovieDTO movieDTO,Long movieId);
    MovieDTO getMovieById(Long movieId);
    MovieResponse getAllMovies(int pageNumber, int pageSize, String sortBy, String sortDir);
    MovieResponse getMoviesByTitle(String title, int pageNumber, int pageSize, String sortBy, String sortDir);

}
