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

public class RtJsonWrapper {
	private static final Logger logger = LoggerFactory.getLogger(RtJsonWrapper.class);

	public List<Movie> parseRottenTomatoes(String query){
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(query, String.class);
		JSONObject jsonObject = new JSONObject(result);
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
	}
		
		return appendReviews(movies);
		
		
	}

	
	private List<Movie> appendReviews(List<Movie> movies){
		RestTemplate restTemplate = new RestTemplate();
		for (int i =0; i<movies.size(); i++){
			try{
				String reviews = Url.RT_REVIEWS.replace("{id}", movies.get(i).getId());
				String res = restTemplate.getForObject(reviews, String.class);
				JSONObject obj = new JSONObject(res);
				movies.get(i).setReviews(obj.getInt("total"));
			}catch(HttpClientErrorException ex){
				logger.info("Rest call denied");
			}
	
		}
		return movies;
	}
}
