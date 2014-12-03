package com.workable.movierama.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.workable.movierama.model.Movie;

public class TmdbJsonWrapper{
	private static final Logger logger = LoggerFactory.getLogger(TmdbJsonWrapper.class);
	
	public List<Movie> parseTMDB(String query){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try{
		String result = restTemplate.getForObject(query, String.class);
		JSONObject jsonObject = new JSONObject(result);
		JSONArray array = (JSONArray)jsonObject.get("results");

		for (int i=0; i< array.length(); i++){
			try{
				Movie movie= new Movie();
				movie.setId(String.valueOf(array.getJSONObject(i).getInt("id")));
				movie.setTitle(array.getJSONObject(i).getString("original_title"));
				movie.setYear(Utils.getYear(array.getJSONObject(i).getString("release_date")));
				movies.add(appendMovieInfo(movie));
				}catch(JSONException ex){
					logger.info("Parsing failed");
			};
		}
		}catch(JSONException ex){
			logger.info("Parsing failed");
		}catch(HttpClientErrorException ex){
			logger.info("Rest call failed");
		}
		return movies;
	}
	
	
	private Movie appendMovieInfo(Movie movie){
		RestTemplate restTemplate = new RestTemplate();
		String tmdb_movie = Url.TMDB_MOVIE.replace("{id}", movie.getId());
		String movieString = restTemplate.getForObject(tmdb_movie, String.class);		
		JSONObject movieObject = new JSONObject(movieString);
		movie.setOverview(movieObject.getString("overview"));
		JSONObject reviewsObject = (JSONObject) movieObject.get("reviews");
		movie.setReviews(reviewsObject.getInt("total_results"));
		JSONObject creditsObject = (JSONObject) movieObject.get("credits");
		JSONArray castArray = (JSONArray)creditsObject.get("cast");
		for (int j=0; j < 5; j++){
			movie.getActors().add(castArray.getJSONObject(j).getString("name"));
		}
		return movie;
		
	}
	
}