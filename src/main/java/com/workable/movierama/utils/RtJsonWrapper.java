package com.workable.movierama.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workable.movierama.dto.MovieRtDTO;
import com.workable.movierama.dto.MovieRtListDTO;
import com.workable.movierama.dto.MovieTmdbDTO;
import com.workable.movierama.model.Movie;

public class RtJsonWrapper {
	private static final Logger logger = LoggerFactory.getLogger(RtJsonWrapper.class);

	public List<Movie> parseRottenTomatoes(String query){
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<Movie> movies = new ArrayList<Movie>();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(query, String.class);
		ObjectMapper mapper = new ObjectMapper();
		MovieRtListDTO movieList = null;
		try {
			 movieList = mapper.readValue(result, MovieRtListDTO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for ( MovieRtDTO movieDTO : movieList.getMovies()){
			try{
				String reviews = Url.RT_REVIEWS.replace("{id}", movieDTO.getId());
				String res = restTemplate.getForObject(reviews, String.class);
				JSONObject obj = new JSONObject(res);
				movieDTO.setReviews(obj.getInt("total"));
			}catch(HttpClientErrorException ex){
				logger.info("Rest call denied");
			}
			
			movies.add(constructMovie(movieDTO));
		}
	/*	JSONObject jsonObject = new JSONObject(result);
		JSONArray array = (JSONArray)jsonObject.get("movies");
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		for (int i=0; i< array.length(); i++){
			try{
				Movie movie= new Movie();
				movie.setId(array.getJSONObject(i).getString("id"));
				movie.setTitle(array.getJSONObject(i).getString("title"));
				movie.setOverview(array.getJSONObject(i).getString("synopsis"));
				movie.setYear(array.getJSONObject(i).getInt("year"));
				JSONArray cast = (JSONArray) array.getJSONObject(i).get("abridged_cast");
				for (int j=0; j<cast.length();j++){
					movie.getActors().add(cast.getJSONObject(j).getString("name"));
				}
				movies.add(movie);
			}catch(JSONException ex){logger.info("Parsing failed");};
	}*/
		
		return movies;
		
	}


	
	private Movie constructMovie(MovieRtDTO movieDTO){
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setOverview(movieDTO.getOverview());
		movie.setReviews(movieDTO.getReviews());
		movie.setCast(movieDTO.getCast());
		movie.setYear(movieDTO.getYear());
		movie.setTitle(movieDTO.getTitle());
		return movie;
	}
}
