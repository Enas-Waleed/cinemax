package xyz.abhaychauhan.cinebuff.cinebuff.utils;

/**
 * Created by abhay on 13/10/16.
 */

public class Movie {

    private String mTitle;
    private int id;
    private String mImage;

    /**
     * @param id
     * @param title
     * @param image
     */
    public Movie(int id, String title, String image) {
        this.id = id;
        mTitle = title;
        mImage = image
    }

    /**
     * Return the of the movie
     *
     * @return
     */
    public int getId() {
        return this.id;
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

}
