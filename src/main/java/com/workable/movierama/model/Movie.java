package com.workable.movierama.model;
import java.util.List;

public class Movie {
	
	private String id;
	private String title;
	private int year;
	private String overview;
	private List<Cast> cast;
	private int reviews;
	
	public Movie(){
		this.id="Not found";
		this.title="Not found";
		this.year=0;
		this.overview="Not found";
		this.reviews=0;		
	}
	
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
