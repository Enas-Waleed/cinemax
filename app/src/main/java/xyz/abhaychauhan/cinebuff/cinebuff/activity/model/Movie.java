package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;


public class Movie {

    private int id;
    private String title;
    private String overview;
    private int votesCount;
    private double voteAverage;
    private String posterPath;

    public Movie(int id, String title, String overview, int votesCount, double voteAverage,
                 String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.votesCount = votesCount;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

    public int getId(){
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOverview() {
        return this.overview;
    }

    public int getVotesCount() {
        return this.votesCount;
    }

    public double getVoteAverage() {
        return this.voteAverage;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

}
