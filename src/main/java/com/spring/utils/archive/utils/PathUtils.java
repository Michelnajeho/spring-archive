package com.spring.utils.archive.utils;

import java.nio.file.Paths;

/**
 * @apiNote Path utils
 * @author  Michelnajeho
 */
public class PathUtils {

    /**
     * Gets the file upload path in the current date format.
     *
     * @return 1994\07\30
     */
    public static String getDateFormatPath() {

        String[] directory = DateUtils.getToday().split("-");
        return Paths.get(directory[0], directory[1], directory[2]).toString();
    }
}
