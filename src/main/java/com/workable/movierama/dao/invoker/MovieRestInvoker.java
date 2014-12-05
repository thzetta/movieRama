package com.workable.movierama.dao.invoker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.dao.MovieDao;
import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.Utils;

public class MovieRestInvoker implements MovieDao{

	private static final Logger logger = LoggerFactory.getLogger(MovieRestInvoker.class);

	@Override
	public List<Movie> getRTmovies(String query) {
		RtJsonInvoker rtJsonInvoker = new RtJsonInvoker();
		List<Movie> movies= new ArrayList<Movie>();
		try{
			movies = rtJsonInvoker.getRTmovies(query);
		}catch(RestClientException ex){
			logger.warn("Rest call failed");
		}
		return movies;
	}

	@Override
	public List<Movie> getTMDBmovies(String query) {
		TmdbJsonInvoker tmdbJsonInvoker = new TmdbJsonInvoker();
		List<Movie> movies= new ArrayList<Movie>();
		try{
			movies =  tmdbJsonInvoker.getTMDBMovies(query);
		}catch(RestClientException ex){
			logger.warn("Rest call failed");
		}
		return movies;
	}

	@Override
	public List<Movie> mergeMovies(List<Movie> rtMovies, List<Movie> tmdbMovies) {
		Map<String, Movie> mergedMovies= new HashMap<String, Movie>();
		//get movies from rotten tomatoes first and add them to the map
		//key is movie title + movie year and value is the movie object
		for (Movie movie : rtMovies){
			String key = movie.getTitle().trim().toLowerCase() + movie.getYear();
			mergedMovies.put(key, movie);
		}
		//go through movies from the movie database
		for (int i=0; i<tmdbMovies.size(); i++){
			String key = tmdbMovies.get(i).getTitle().trim().toLowerCase() + tmdbMovies.get(i).getYear();
			//if the movie key doesn't exist then add the movie to the map
			Movie movie = mergedMovies.get(key);
			if(movie == null){
				mergedMovies.put(key, tmdbMovies.get(i));
			}else{
				//else sum reviews of the two movies and update the object in the map
				int reviews = mergedMovies.get(key).getReviews() + tmdbMovies.get(i).getReviews();
				mergedMovies.get(key).setReviews(reviews);
				//keep in the loop movie's overview if it's longer
				if (tmdbMovies.get(i).getOverview().length() >  mergedMovies.get(key).getOverview().length()){
					mergedMovies.get(key).setOverview(tmdbMovies.get(i).getOverview());
				}
			}
			
		}
		return new ArrayList<Movie>(mergedMovies.values());
		
	}
	
	

}
