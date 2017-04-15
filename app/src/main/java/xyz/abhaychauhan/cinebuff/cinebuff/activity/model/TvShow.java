package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

/**
 * Created by abhay on 28/03/17.
 */

public class TvShow {

    private int id;
    private String posterPath;
    private String backdropPath;
    private String title;
    private double voteAverage;
    private String firstAirDate;
    private String overview;
    private int voteCount;

    /**
     * Constructor TvShow
     *
     * @param id
     * @param posterPath
     * @param title
     * @param voteAverage
     * @param firstAirDate
     */
    public TvShow(int id, String posterPath, String backdropPath, String title, double voteAverage,
                  String firstAirDate, String overview, int voteCount) {
        this.id = id;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.firstAirDate = firstAirDate;
        this.overview = overview;
        this.voteCount = voteCount;
    }

    public int getShowId() {
        return this.id;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public String getTitle() {
        return this.title;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public String getFirstAirDate() {
        return this.firstAirDate;
    }

    public String getOverview() {
        return this.overview;
    }

    public int getVoteCount() {
        return this.voteCount;
    }

}
