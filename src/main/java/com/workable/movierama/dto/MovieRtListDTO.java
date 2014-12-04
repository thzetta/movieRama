package com.workable.movierama.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRtListDTO {
	private int total;
	private List<MovieRtDTO> movies;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<MovieRtDTO> getMovies() {
		return movies;
	}
	public void setMovies(List<MovieRtDTO> movies) {
		this.movies = movies;
	}


}
