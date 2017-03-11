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
    public static final String MOVIE_SIMILAR_URL = TMDB_BASE_URL + "%s" + "/similar";
    public static final String MOVIE_CAST_URL = TMDB_BASE_URL + "%s" + "/credits";
    public static final String MOVIE_REVIEW_URL = TMDB_BASE_URL + "%s" + "/reviews";

    public static final String API_KEY_PARAM = "api_key";
    public static final String PAGE_PARAM = "page";
    public static final String LANGUAGE_PARAM = "language";

    // TODO : Place your API key here
    public static final String API_KEY = "YOUR_API_KEY_HERE";
    public static final String LANGUAGE = "en-US";

    public static final String YOUTUBE_VIDEO_URL = "http://www.youtube.com/watch?v=%s";
    public static final String YOUTUBE_THUMB_URL = "http://img.youtube.com/vi/%s/mqdefault.jpg";

}
