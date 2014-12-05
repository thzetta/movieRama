package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;



import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workable.movierama.dao.MovieDao;
import com.workable.movierama.dto.MovieRtListDTO;
import com.workable.movierama.model.Movie;
import com.workable.movierama.service.MovieService;
import com.workable.movierama.service.MovieServiceImpl;
import com.workable.movierama.utils.Url;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/test-context.xml")
public class MovieTests {

    @Autowired
    private MovieService movieService;
    @Autowired MovieDao movieDao;
    @Autowired
    private CacheManager cacheManager;
    
    @Before
    public void clearCache(){
    	cacheManager.getCache("movies").clear();
    }
    
    @Test
    public void testCache() {    	
       ArrayList<Movie> movies = (ArrayList<Movie>)movieService.search("Frozen");
       ArrayList<Movie> movies2 = (ArrayList<Movie>)movieService.search("Frozen");
       assertEquals(movies, movies2);
    }
    
   
    
    @Test
    public void testMovieDao(){
    	assertNotNull(movieDao);
    	List<Movie> movies = movieDao.getRTmovies(Url.RT_IN_THEATERS);
    	List<Movie> movies2 = movieDao.getTMDBmovies(Url.RT_IN_THEATERS);
    	assertNotNull(movies);
    	assertNotNull(movies2);
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
