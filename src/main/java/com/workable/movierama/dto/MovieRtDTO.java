package com.workable.movierama.dto;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.workable.movierama.model.Cast;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieRtDTO {
	
	private String id;
	private String title;
	private int year;
	@JsonProperty("synopsis")
	private String overview;
	@JsonProperty("abridged_cast")
	private List<Cast> cast;
	private ReviewsDTO reviews;
	
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

	public List<Cast> getCast() {
		return cast;
	}
	public void setCast(List<Cast> cast) {
		this.cast = cast;
	}
	public ReviewsDTO getReviews() {
		return reviews;
	}
	public void setReviews(ReviewsDTO reviews) {
		this.reviews = reviews;
	}

}
