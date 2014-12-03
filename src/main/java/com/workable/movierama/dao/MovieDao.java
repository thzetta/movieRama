package com.workable.movierama.dao;

import java.util.List;

import com.workable.movierama.model.Movie;

public interface MovieDao {
	
	public List<Movie> parseRottenTomatoes(String query);
	
	public List<Movie> parseTMDB(String query);
	
	public List<Movie> mergeMovies(List<Movie> rtMovies, List<Movie> tmdbMovies);

}
