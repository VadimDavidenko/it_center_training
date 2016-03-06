package taxi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by v.davidenko on 12.02.2016.
 */
public class Utils {
    public static final ThreadLocal<DateFormat> DATEFORMAT_COMMON = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            return sdf;
        }
    };

    public static Date stringToDate(String dateInString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
