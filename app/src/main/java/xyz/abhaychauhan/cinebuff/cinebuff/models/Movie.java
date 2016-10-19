package xyz.abhaychauhan.cinebuff.cinebuff.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mTitle;
    private int mId;
    private String mImage;
    private String mOverview;
    private String mReleaseDate;
    private Double mPopularity;
    private int mVoteCount;
    private float mVoteAverage;
    private String mPosterPath;

    /**
     * @param id
     * @param title
     * @param image
     */
    public Movie(int id, String title, String image, String overview, String releaseDate, Double popularity,
                 int voteCount, float voteAverage, String posterPath) {
        mId = id;
        mTitle = title;
        mImage = image;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mPopularity = popularity;
        mVoteCount = voteCount;
        mVoteAverage = voteAverage;
        mPosterPath = posterPath;
    }

    public Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mImage = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mPopularity = in.readDouble();
        mVoteCount = in.readInt();
        mVoteAverage = in.readFloat();
        mPosterPath = in.readString();
    }

    /**
     * Return the of the movie
     *
     * @return
     */
    public int getId() {
        return mId;
    }

    /**
     * Return the title of the movie
     *
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Return the poster image of the movie
     *
     * @return
     */
    public String getImage() {
        return mImage;
    }

    /**
     * Return the Overview of the movie
     *
     * @return
     */
    public String getOverView() {
        return mOverview;
    }

    /**
     * Return the release date of movie in String format
     *
     * @return
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Return the popularity of the movie
     *
     * @return
     */
    public Double getPopularity() {
        return mPopularity;
    }

    /**
     * Return the vote count of the movie
     *
     * @return
     */
    public int getVoteCount() {
        return mVoteCount;
    }

    /**
     * Return the vote average of the movie
     *
     * @return
     */
    public float getVoteAverage() {
        return mVoteAverage;
    }

    /**
     * Return the poster path of the movie
     *
     * @return
     */
    public String getPosterPath() {
        return mPosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeString(mImage);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeDouble(mPopularity);
        dest.writeInt(mVoteCount);
        dest.writeFloat(mVoteCount);
        dest.writeString(mPosterPath);
    }
}
