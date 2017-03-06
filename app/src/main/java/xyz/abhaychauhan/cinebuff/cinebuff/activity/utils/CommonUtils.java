package xyz.abhaychauhan.cinebuff.cinebuff.activity.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CommonUtils {

    /**
     * Return the movie formatted date in String format e.g Jan 22, 2017
     *
     * @param date
     * @return
     */
    public static String getFormattedDate(String date) {
        // TODO : (fix) Application crashing because of some extra Y
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, YYYY");
        return simpleDateFormat.format(date);
    }

    /**
     * Return the movie formatted time in String format e.g 2h 22 min
     *
     * @param time
     * @return
     */
    public static String getFormattedMovieTime(int time) {
        int hours = time / 60;
        int minutes = time % 60;
        return hours + "h " + minutes + " min";
    }

    /**
     * Return votes in K format e.g 10K
     *
     * @param votesCount
     * @return
     */
    public static String getFormattedVotes(Integer votesCount) {
        if (votesCount > 1000000000) {
            return votesCount % 1000 + "K";
        }
        return Integer.toString(votesCount);
    }

    /**
     * Convert the list data into comma separated string and return the string
     *
     * @param list
     * @return
     */
    public static String getFormattedString(ArrayList<String> list) {
        String text = "";
        if (list != null) {
            for (int index = 0; index < list.size(); index++) {
                text += list.get(index);
                if (index != list.size() - 1) {
                    text += ", ";
                }
            }
        } else {
            text += "Not found";
        }
        return text;
    }

}
