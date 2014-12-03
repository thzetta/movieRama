package com.workable.movierama.service;

import java.util.List;

import com.workable.movierama.model.Movie;

public interface MovieService {

	List<Movie> findAllInTheaters();

	List<Movie> search(String query);
}
