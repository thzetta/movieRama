package com.workable.movierama.utils;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workable.movierama.dto.MovieTmdbDTO;
import com.workable.movierama.dto.MovieTmdbListDTO;
import com.workable.movierama.model.Movie;

public class TmdbJsonWrapper{
	private static final Logger logger = LoggerFactory.getLogger(TmdbJsonWrapper.class);
	
	public List<Movie> parseTMDB(String query){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ArrayList<Movie> movies = new ArrayList<Movie>();
		try{
		String result = restTemplate.getForObject(query, String.class);
		ObjectMapper mapper = new ObjectMapper();
		MovieTmdbListDTO movieList=new MovieTmdbListDTO();
		try {
			movieList = mapper.readValue(result, MovieTmdbListDTO.class);
				for (MovieTmdbDTO movie : movieList.getMovies()){
					String tmdb_movie = Url.TMDB_MOVIE.replace("{id}", movie.getId());
					String movieString = restTemplate.getForObject(tmdb_movie, String.class);		
					
					MovieTmdbDTO movieDTO = mapper.readValue(movieString, MovieTmdbDTO.class);
					movies.add(constructMovie(movieDTO));
				}				
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
		}catch (HttpClientErrorException ex){
			logger.warn("rest call failed");
		}
		return movies;
		
	/*	JSONObject jsonObject = new JSONObject(result);
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
		
		*/
		
	}
	

	
	private Movie constructMovie(MovieTmdbDTO movieDTO){
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setOverview(movieDTO.getOverview());
		movie.setReviews(movieDTO.getReviews().getTotalResults());
		movie.setCast(movieDTO.getCredits().getCast());
		movie.setYear(Utils.getYear(movieDTO.getReleaseDate()));
		movie.setTitle(movieDTO.getTitle());
		return movie;
	}
	
}