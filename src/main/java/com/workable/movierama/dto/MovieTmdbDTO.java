package com.workable.movierama.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieTmdbDTO {
	
	private String id;
	@JsonProperty("original_title")
	private String title;
	@JsonProperty("release_date")
	private String releaseDate;
	private String overview;
	private CreditsDTO credits;
	private ReviewsDTO reviews;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOverview() {
		return overview;
	}
	public void setOverview(String overview) {
		this.overview = overview;
	}
	public CreditsDTO getCredits() {
		return credits;
	}
	public void setCredits(CreditsDTO credits) {
		this.credits = credits;
	}
	public ReviewsDTO getReviews() {
		return reviews;
	}
	public void setReviews(ReviewsDTO reviews) {
		this.reviews = reviews;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
