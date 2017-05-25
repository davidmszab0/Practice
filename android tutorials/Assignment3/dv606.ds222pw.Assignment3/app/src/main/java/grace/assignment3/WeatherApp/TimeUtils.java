package grace.assignment3.WeatherApp;

/**
 * Created by root on 2016-09-07.
 */
/**
 * TimeUtils.java
 * Created: May 9, 2010
 * Jonas Lundberg, LnU
 */

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeUtils {

    public static Date getDate(String date_string) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        try {
            date = format.parse(date_string);
            //System.out.println(date_string);
            //System.out.println(date);
            //System.out.println(getYYMMDD(date)+" "+getHHMM(date));
        }
        catch (Exception e) {
            System.out.println("Time string: "+date_string);
            e.printStackTrace();
        }
        return date;
    }

    public static String getYYMMDD(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getHHMM(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }
}
