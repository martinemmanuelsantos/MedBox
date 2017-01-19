package com.martinemmanuelsantos.medbox.utils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Helper class for formatting dates
 */

public class DateTimeUtils {

    // Constants
    public static final int FORMAT_UI = 1;
    public static final int FORMAT_SQLITE = 2;

    // Time and date formats
    // TODO Make timeFormatUI a shared preference
    public static final SimpleDateFormat timeFormatUI = new SimpleDateFormat("h:mm a", Locale.US);             // UI Formatted time
    public static final SimpleDateFormat timeFormatSQLite = new SimpleDateFormat("HH:mm");                     // SQLite Formatted time
    public static final SimpleDateFormat dateFormatUI = new SimpleDateFormat("EEEE, MMMM dd, yyyy");                // SQLite Formatted date
    public static final SimpleDateFormat dateFormatSQLite = new SimpleDateFormat("yyyy-MM-dd");                // SQLite Formatted date


    public static String getDateUIFormatted(Calendar calendar, int format) {

        String dateFormatted;

        switch (format) {
            case FORMAT_UI:
                dateFormatted = dateFormatUI.format(calendar.getTime());
                break;
            case FORMAT_SQLITE:
                dateFormatted = dateFormatSQLite.format(calendar.getTime());
                break;
            default:
                dateFormatted = dateFormatSQLite.format(calendar.getTime());
                break;
        }

        return dateFormatted;

    }

    public static String getTimeFormatted(Calendar calendar, int format) {

        String timeFormatted;

        switch (format) {
            case FORMAT_UI:
                timeFormatted = timeFormatUI.format(calendar.getTime());
                break;
            case FORMAT_SQLITE:
                timeFormatted = timeFormatSQLite.format(calendar.getTime());
                break;
            default:
                timeFormatted = timeFormatSQLite.format(calendar.getTime());
                break;
        }

        return timeFormatted;

    }

    public static String convertToSqliteDate(String uiDate) {

        Date date = null;
        try {
            date = dateFormatUI.parse(uiDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatSQLite.format(date);

    }

    public static String convertToSqliteTime(String uiTime) {

        Date time = null;
        try {
            time= timeFormatUI.parse(uiTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeFormatSQLite.format(time);

    }

    public static String getDayOfWeek(Calendar calendar) {

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        return dayFormat.format(calendar.getTime());

    }

    public static String getMonthOfYear(Calendar calendar) {

        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
        return monthFormat.format(calendar.getTime());

    }

}
