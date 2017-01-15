package com.martinemmanuelsantos.medbox.database;

/**
 * Created by nutel on 1/14/2017.
 */

public class ScheduleTime {

    long timeID;
    String time;
    int doseCount;
    long scheduleID;

    public ScheduleTime() {

    }

    public int getDoseCount() {
        return doseCount;
    }

    public void setDoseCount(int doseCount) {
        this.doseCount = doseCount;
    }

    public long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeID() {
        return timeID;
    }

    public void setTimeID(long timeID) {
        this.timeID = timeID;
    }
}
