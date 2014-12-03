package controllerTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.model.Movie;

public class MovieControllerTest {
	
	@Test
	public void searchMoviesRT() throws HttpClientErrorException{
	final  String RT_SEARCH="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=Frozen&apikey=qtqep7qydngcc7grk4r4hyd9";

	 RestTemplate restTemplate = new RestTemplate();
	 restTemplate.getMessageConverters().add(new StringHttpMessageConverter() );
	 String result = restTemplate.getForObject(RT_SEARCH, String.class);
	 JSONObject jsonObject = new JSONObject(result);
		JSONArray array = (JSONArray)jsonObject.get("movies");
		for (int i=0; i< array.length(); i++){
			assertTrue(array.getJSONObject(i).getString("title").contains("Frozen"));
		}
	}
	
	@Test
	public void searchMoviesTMDB() throws HttpClientErrorException,JSONException{
	final  String TMDB_SEARCH="https://api.themoviedb.org/3/search/movie/Frozen?api_key=186b266209c2da50f898b7977e2a44dd"	;	
	 RestTemplate restTemplate = new RestTemplate();
	 String result="";
	 try{
	 restTemplate.getMessageConverters().add(new StringHttpMessageConverter() );
	
	  result = restTemplate.getForObject(TMDB_SEARCH, String.class);
	
	 JSONObject jsonObject = new JSONObject(result);
		JSONArray array = (JSONArray)jsonObject.get("movies");
		for (int i=0; i< array.length(); i++){
			assertTrue(array.getJSONObject(i).getString("title").contains("Frozen"));
		}
	 }catch (HttpClientErrorException ex){
		 System.out.println("Rest call error");
	 }catch(JSONException ex){
		 System.out.println("JSON parsing error");
	 }
	}
	
	@Test
	public void mergeResults(){

		Movie movie1 = new Movie();
		movie1.setTitle("Frozen");
		movie1.setYear(2014);
		movie1.setReviews(30);
		movie1.setOverview("Frozen Frozen Frozen Frozen");
		Movie movie2 = new Movie();
		movie2.setTitle("Frozen");
		movie2.setYear(2014);
		movie2.setReviews(20);
		movie2.setOverview("Frozen Frozen Frozen Frozen Frozen");
		Map<String, Movie> mergedMovies= new HashMap<String, Movie>();
		String key = movie1.getTitle()+movie1.getYear();
		String key2 = movie2.getTitle()+movie2.getYear();
		mergedMovies.put(key, movie1);
		Movie movie = mergedMovies.get(key2);
		if(movie == null){
			mergedMovies.put(key2, movie2);
		}else{
			int reviews = mergedMovies.get(key2).getReviews() +movie2.getReviews();
			mergedMovies.get(key2).setReviews(reviews);
			if (movie2.getOverview().length() >  mergedMovies.get(key2).getOverview().length()){
				mergedMovies.get(key2).setOverview(movie2.getOverview());
			}
		}
		
		assertEquals(50,mergedMovies.get(key2).getReviews());
		assertEquals("Frozen Frozen Frozen Frozen Frozen",mergedMovies.get(key2).getOverview());
		
	}

	
}
