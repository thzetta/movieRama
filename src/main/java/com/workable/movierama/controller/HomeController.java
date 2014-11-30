package com.workable.movierama.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.workable.movierama.model.Movie;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		String url = "https://api.themoviedb.org/3/discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22&api_key=186b266209c2da50f898b7977e2a44dd";
		String url2="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=qtqep7qydngcc7grk4r4hyd9";
		RestTemplate restTemplate = new RestTemplate();
		//MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		//List<MediaType> list = new ArrayList<MediaType>();
		//list.add(new MediaType("text","javascript"));
		//list.add(new MediaType("application","json"));
		//converter.setSupportedMediaTypes(list);
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		String result = restTemplate.getForObject(url2, String.class);
		JSONObject jsonObject = new JSONObject(result);
		JSONArray array = (JSONArray)jsonObject.get("movies");
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		for (int i=0; i< array.length(); i++){
			Movie movie= new Movie();
			movie.setTitle(array.getJSONObject(i).getString("title"));
			movie.setOverview(array.getJSONObject(i).getString("synopsis"));
			movie.setYear(array.getJSONObject(i).getInt("year"));
			movies.add(movie);
		}
		model.addAttribute("movies", movies);
		
				
		
		
		return "home";
	}

	
}
