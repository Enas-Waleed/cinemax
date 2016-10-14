package xyz.abhaychauhan.cinebuff.cinebuff.models;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    private String mTitle;
    private int mId;
    private String mImage;

    /**
     * @param id
     * @param title
     * @param image
     */
    public Movie(int id, String title, String image) {
        mId = id;
        mTitle = title;
        mImage = image;
    }

    public Movie(Parcel in) {
        mId = in.readInt();
        mTitle = in.readString();
        mImage = in.readString();
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
    }
}
