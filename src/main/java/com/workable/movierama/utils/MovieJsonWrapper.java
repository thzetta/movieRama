package com.workable.movierama.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.model.Movie;

public class MovieJsonWrapper {
	
	
	final static String IN_THEATERS = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	final static String REVIEWS = "http://api.rottentomatoes.com/api/public/v1.0/movies/{id}/reviews.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	public List<Movie> parseRottenTomatoes(){
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(IN_THEATERS, String.class);
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
			}catch(JSONException ex)	{};
	}
		
	/*	for (int i =0; i<movies.size(); i++){
			String reviews = REVIEWS.replace("{id}", movies.get(i).getId());
			String res = restTemplate.getForObject(reviews, String.class);
			JSONObject obj = new JSONObject(res);
			movies.get(i).setReviews(obj.getInt("total"));


		}*/
		return movies;
	}

}
