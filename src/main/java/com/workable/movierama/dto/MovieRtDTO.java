package com.workable.movierama.dto;
import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.workable.movierama.model.Cast;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRtDTO {
	
	private String id;
	private String title;
	private int year;
	@JsonProperty("synopsis")
	private String overview;
	@JsonProperty("abridged_cast")
	private List<Cast> cast;
	private int reviews;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public int getReviews() {
		return reviews;
	}
	public void setReviews(int reviews) {
		this.reviews = reviews;
	}
	public List<Cast> getCast() {
		return cast;
	}
	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}

}
