package com.martinemmanuelsantos.medbox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by nutel on 1/8/2017.
 */

public class DateTimeHelper {

    int day, month, year;
    String time, dayFormatted, monthFormatted;

    DateTimeHelper() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        time = timeFormat.format(c.getTime());
        dayFormatted = dayFormat.format(c.getTime());
        monthFormatted = monthFormat.format(c.getTime());
    }

    DateTimeHelper(Calendar calendar) {
        calendar.getInstance();
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM", Locale.US);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.US);

        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        time = timeFormat.format(calendar.getTime());

        dayFormatted = dayFormat.format(calendar.getTime());
        monthFormatted = monthFormat.format(calendar.getTime());
    }

    public String getFormattedDate() {

        return dayFormatted + ", " + monthFormatted + " " + day + ", " + year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayFormatted() {
        return dayFormatted;
    }

    public void setDayFormatted(String dayFormatted) {
        this.dayFormatted = dayFormatted;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthFormatted() {
        return monthFormatted;
    }

    public void setMonthFormatted(String monthFormatted) {
        this.monthFormatted = monthFormatted;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
