package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

/**
 * Created by abhay on 28/03/17.
 */

public class TvShow {

    private String posterPath;
    private String title;
    private int id;
    private double voteAverage;
    private String firstAirDate;

    /**
     * Constructor TvShow
     *
     * @param id
     * @param posterPath
     * @param title
     * @param voteAverage
     * @param firstAirDate
     */
    public TvShow(int id, String posterPath, String title, double voteAverage,
                  String firstAirDate) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.voteAverage = voteAverage;
        this.firstAirDate = firstAirDate;
    }

    public int getShowId() {
        return this.id;
    }

    public String getPosterPath() {
        return this.posterPath;
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

}
