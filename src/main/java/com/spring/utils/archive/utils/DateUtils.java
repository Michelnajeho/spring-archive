package com.spring.utils.archive.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @apiNote Date utils
 * @author  Michelnajeho
 */
public class DateUtils {

    /**
     * Gets the date data in the form of the default pattern yyyy-MM-dd.
     *
     * @return 1994-07-30
     */
    public static String getToday() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }

    /**
     * Gets the date data in the form of the pattern.
     *
     * @return pattern
     */
    public static String getToday(String pattern) {

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date());
    }
}
