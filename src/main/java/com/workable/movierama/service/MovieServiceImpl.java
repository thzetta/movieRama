package com.workable.movierama.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.workable.movierama.dao.MovieDao;
import com.workable.movierama.model.Movie;
import com.workable.movierama.utils.Url;

public class MovieServiceImpl implements MovieService{

	@Autowired
	MovieDao movieDao;
	
	
	@Override
	@Cacheable("movies")
	public List<Movie> findAllInTheaters() {
		return movieDao.mergeMovies(movieDao.parseRottenTomatoes(Url.RT_IN_THEATERS), movieDao.parseTMDB(Url.TMDB_NOW_PLAYING));
	}

	@Override
	@Cacheable("movies")
	public List<Movie> search(String query) {	
		String rt_url = Url.RT_SEARCH.replace("{query}", query);
		String tmdb_url = Url.TMDB_SEARCH.replace("{query}", query);
		return movieDao.mergeMovies(movieDao.parseRottenTomatoes(rt_url), movieDao.parseTMDB(tmdb_url));
	}

}
