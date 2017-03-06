package xyz.abhaychauhan.cinebuff.cinebuff.activity.utils;

/**
 * This is a helper class to get urls
 */
public class TmdbUrl {

    public static final String TMDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String POPULAR_MOVIES_URL = TMDB_BASE_URL + "popular";
    public static final String TOP_RATED_URL = TMDB_BASE_URL + "top_rated";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    public static final String MOVIE_TRAILER_URL = TMDB_BASE_URL + "%s" + "/videos";

    public static final String API_KEY_PARAM = "api_key";
    public static final String PAGE_PARAM = "page";
    public static final String LANGUAGE_PARAM = "language";
    // TODO (1) Place api key here
    public static final String API_KEY = "661beb6fe6cbb63c894b5a47c9275675";
    public static final String LANGUAGE = "en-US";

}
