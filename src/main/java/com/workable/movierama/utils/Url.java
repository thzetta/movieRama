package com.workable.movierama.utils;

public final class Url {
	public final static String RT_IN_THEATERS = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	public final static String TMDB_NOW_PLAYING="https://api.themoviedb.org/3/movie/now_playing?api_key=186b266209c2da50f898b7977e2a44dd";
	public final static String RT_SEARCH="http://api.rottentomatoes.com/api/public/v1.0/movies.json?q={query}&apikey=qtqep7qydngcc7grk4r4hyd9";
	public final static String TMDB_SEARCH="https://api.themoviedb.org/3/movie/search?{query}&api_key=186b266209c2da50f898b7977e2a44dd"	;	
	final static String RT_REVIEWS = "http://api.rottentomatoes.com/api/public/v1.0/movies/{id}/reviews.json?apikey=qtqep7qydngcc7grk4r4hyd9";
	final static String TMDB_MOVIE= "https://api.themoviedb.org/3/movie/{id}?api_key=186b266209c2da50f898b7977e2a44dd&append_to_response=overview,reviews,credits";
}
