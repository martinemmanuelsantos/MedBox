package com.martinemmanuelsantos.medbox.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Helper class for formatting dates
 */

public class DateTimeUtils {

    public static final int FORMAT_UI = 1;
    public static final int FORMAT_SQLITE = 2;

    public static String getDateUIFormatted(Calendar calendar, int format) {

        SimpleDateFormat formatSQLite = new SimpleDateFormat("yyyy-MM-dd");

        String dateFormatted;

        switch (format) {
            case FORMAT_UI:
                dateFormatted = getDayOfWeek(calendar) + ", " + getMonthOfYear(calendar) + " " + calendar.get(Calendar.DAY_OF_MONTH) + ", " + calendar.get(Calendar.YEAR);
                break;
            case FORMAT_SQLITE:
                dateFormatted = formatSQLite.format(calendar.getTime());
                break;
            default:
                dateFormatted = formatSQLite.format(calendar.getTime());
                break;
        }

        return dateFormatted;

    }

    public static String getTimeFormatted(Calendar calendar, int format) {

        SimpleDateFormat formatUI = new SimpleDateFormat("h:mm a", Locale.US);             // UI Format
        SimpleDateFormat formatSQLite = new SimpleDateFormat("HH:mm");                     // SQLite Format

        String timeFormatted;

        switch (format) {
            case FORMAT_UI:
                timeFormatted = formatUI.format(calendar.getTime());
                break;
            case FORMAT_SQLITE:
                timeFormatted = formatSQLite.format(calendar.getTime());
                break;
            default:
                timeFormatted = formatSQLite.format(calendar.getTime());
                break;
        }

        return timeFormatted;

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
