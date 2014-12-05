package com.workable.movierama.dao.invoker;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.dto.MovieTmdbDTO;
import com.workable.movierama.dto.MovieTmdbListDTO;
import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.Url;
import com.workable.movierama.utils.Utils;

public class TmdbJsonInvoker{
	private static final Logger logger = LoggerFactory.getLogger(TmdbJsonInvoker.class);
	
	public List<Movie> getTMDBMovies(String query) throws RestClientException{
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ArrayList<Movie> movies = new ArrayList<Movie>();

			MovieTmdbListDTO movieList = new MovieTmdbListDTO();			
			 movieList = restTemplate.getForObject(query, MovieTmdbListDTO.class);

			 //go through the list to get more info about a movie (overview,cast,reviews)
				for (MovieTmdbDTO movie : movieList.getMovies()){
					MovieTmdbDTO movieDTO = new MovieTmdbDTO();
					String tmdb_movie = Url.TMDB_MOVIE.replace("{id}", movie.getId());
					try{
						movieDTO = restTemplate.getForObject(tmdb_movie, MovieTmdbDTO.class);
					}catch (RestClientException ex){
						logger.warn("Rest call failed");
					}
					movies.add(constructMovie(movieDTO));
				}				
		
		return movies;

		
	}
	

	//convert DTO to Movie
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