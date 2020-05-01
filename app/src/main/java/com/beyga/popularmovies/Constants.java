package com.beyga.popularmovies;

public class Constants {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/?api_key=";
    // Type here your API key from The Movie DB
    public static final String API_KEY = "";
    public static final String POPULAR_MOVIES_URL = BASE_URL + "popular" + API_KEY;
    public static final String TOP_RATED_MOVIES_URL = BASE_URL + "top_rated" + API_KEY;
}
