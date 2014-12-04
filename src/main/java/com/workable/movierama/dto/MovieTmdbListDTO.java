package com.workable.movierama.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieTmdbListDTO {
	
	@JsonProperty("results")
	private List<MovieTmdbDTO> movies;

	public List<MovieTmdbDTO> getMovies() {
		return movies;
	}

	public void setMovies(List<MovieTmdbDTO> movies) {
		this.movies = movies;
	}

}
