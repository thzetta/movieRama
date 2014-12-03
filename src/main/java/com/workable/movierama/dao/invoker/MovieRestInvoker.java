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
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.dao.MovieDao;
import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.RtJsonWrapper;
import com.workable.movierama.utils.TmdbJsonWrapper;
import com.workable.movierama.utils.Utils;

public class MovieRestInvoker implements MovieDao{

	private static final Logger logger = LoggerFactory.getLogger(MovieRestInvoker.class);
	final static String RT_REVIEWS = "http://api.rottentomatoes.com/api/public/v1.0/movies/{id}/reviews.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	final static String TMDB_MOVIE= "https://api.themoviedb.org/3/movie/{id}?api_key=186b266209c2da50f898b7977e2a44dd&append_to_response=overview,reviews,credits";

	@Override
	public List<Movie> parseRottenTomatoes(String query) {
		RtJsonWrapper rtJsonWrapper = new RtJsonWrapper();
		return rtJsonWrapper.parseRottenTomatoes(query);
	}

	@Override
	public List<Movie> parseTMDB(String query) {
		TmdbJsonWrapper tmdbJsonWrapper = new TmdbJsonWrapper();
		return tmdbJsonWrapper.parseTMDB(query);
	}

	@Override
	public List<Movie> mergeMovies(List<Movie> rtMovies, List<Movie> tmdbMovies) {
		Map<String, Movie> mergedMovies= new HashMap<String, Movie>();
		for (Movie movie : rtMovies){
			mergedMovies.put(movie.getTitle()+movie.getYear(), movie);
		}
		for (int i=0; i<tmdbMovies.size(); i++){
			Movie movie = mergedMovies.get(tmdbMovies.get(i).getTitle());
			if(movie == null){
				mergedMovies.put(tmdbMovies.get(i).getTitle()+tmdbMovies.get(i).getYear(), tmdbMovies.get(i));
			}else{
				int reviews = mergedMovies.get(tmdbMovies.get(i).getTitle()).getReviews() + tmdbMovies.get(i).getReviews();
				mergedMovies.get(tmdbMovies.get(i).getTitle()).setReviews(reviews);
				if (tmdbMovies.get(i).getOverview().length() >  mergedMovies.get(tmdbMovies.get(i).getTitle()).getOverview().length()){
					mergedMovies.get(tmdbMovies.get(i).getTitle()).setOverview(tmdbMovies.get(i).getOverview());
				}
			}
			
		}
		return new ArrayList<Movie>(mergedMovies.values());
		
	}
	
	

}
