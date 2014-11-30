package com.workable.movierama.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.MovieJsonWrapper;

public class MovieServiceImpl implements MovieService{

	@Override
	@Cacheable("movies")
	public List<Movie> findAllInTheaters() {
		MovieJsonWrapper wrapper= new MovieJsonWrapper();
		return wrapper.parseRottenTomatoes();
	}

}
