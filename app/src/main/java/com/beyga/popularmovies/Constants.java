package com.beyga.popularmovies;

public class Constants {

    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_PREFIX = "?api_key=";

    // Type here your API key from The Movie DB
    private static final String API_KEY = "";

    private static final String API_KEY_CODE = API_PREFIX + API_KEY;
    static final String POPULAR_MOVIES_URL = BASE_URL + "popular" + API_KEY_CODE;
    static final String TOP_RATED_MOVIES_URL = BASE_URL + "top_rated" + API_KEY_CODE;
}
