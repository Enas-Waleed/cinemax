package xyz.abhaychauhan.cinebuff.cinebuff.activity.model;

import java.util.ArrayList;

public class MovieDetail {

    private String backdropPath;
    private ArrayList<String> genres;
    private int id;
    private String title;
    private String synopsis;
    private String posterPath;
    private String releaseDate;
    private int runtime;
    private String status;
    private float rating;
    private int vote;
    private String tagline;

    public MovieDetail(String backdropPath, ArrayList<String> genres, int id, String title,
                       String synopsis, String posterPath, String releaseDate, int runtime,
                       String status, float rating, int vote, String tagline) {
        this.backdropPath = backdropPath;
        this.genres = genres;
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.runtime = runtime;
        this.status = status;
        this.rating = rating;
        this.vote = vote;
        this.tagline = tagline;
    }

    public String getBackdropPath() {
        return this.backdropPath;
    }

    public ArrayList<String> getGenres() {
        return this.genres;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSynopsis() {
        return this.synopsis;
    }

    public String getPosterPath() {
        return this.posterPath;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public int getRuntime() {
        return this.runtime;
    }

    public String getStatus() {
        return this.status;
    }

    public float getRating() {
        return this.rating;
    }

    public int getVote() {
        return this.vote;
    }

    public String getTagline() {
        return this.tagline;
    }

}
