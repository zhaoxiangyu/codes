package com.wutka.jox;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * JOXDateHandler handles date parsing and formatting.
 * Set system property com.oce.wutka.dateFormat to control default date
 * parsing.
 * @see java.text.SimpleDateFormat
 */
class JOXDateHandler {
    final static String DATE_FORMAT = "dd-MM-yyyy hh:mm:ss z";

    static DateFormat determineDateFormat() {
        String dateFormat = System.getProperty("com.oce.wutka.dateFormat");
        if (dateFormat!=null && !dateFormat.equals("")) {
            return new SimpleDateFormat(dateFormat);
        } else {
            // default format includes all necessary fields
            return new SimpleDateFormat(DATE_FORMAT);
        }
    }
}
