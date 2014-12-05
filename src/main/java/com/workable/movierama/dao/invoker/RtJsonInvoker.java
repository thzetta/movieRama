package com.workable.movierama.dao.invoker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workable.movierama.dto.MovieRtDTO;
import com.workable.movierama.dto.MovieRtListDTO;
import com.workable.movierama.dto.ReviewsDTO;
import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.Url;

public class RtJsonInvoker {
	private static final Logger logger = LoggerFactory.getLogger(RtJsonInvoker.class);

	public List<Movie> getRTmovies(String query) throws RestClientException{
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<Movie> movies = new ArrayList<Movie>();
		//Rotten tomatoes'API content type is text/javascript so a string
		//converter is used to get it
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(query, String.class);
		ObjectMapper mapper = new ObjectMapper();
		MovieRtListDTO movieList = new MovieRtListDTO();
		try {
			 movieList = mapper.readValue(result, MovieRtListDTO.class);
		
			for ( MovieRtDTO movieDTO : movieList.getMovies()){
			try{
				//get the reviews count for each movie
					String reviews = Url.RT_REVIEWS.replace("{id}", movieDTO.getId());
					String res = restTemplate.getForObject(reviews, String.class);				
					movieDTO.setReviews(mapper.readValue(res, ReviewsDTO.class));
					movies.add(constructMovie(movieDTO));
			}catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}catch(RestClientException ex){
				logger.warn("Rest call failed");
			}
		}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
							
		return movies;
		
	}


	//convert DTO to Movie
	private Movie constructMovie(MovieRtDTO movieDTO){
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setOverview(movieDTO.getOverview());
		movie.setReviews(movieDTO.getReviews().getTotal());
		movie.setCast(movieDTO.getCast());
		movie.setYear(movieDTO.getYear());
		movie.setTitle(movieDTO.getTitle());
		return movie;
	}
}
