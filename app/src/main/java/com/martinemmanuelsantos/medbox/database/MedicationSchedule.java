package com.martinemmanuelsantos.medbox.database;

/**
 * Created by nutel on 1/14/2017.
 */

public class MedicationSchedule {

    long scheduleID;
    String startDate;
    boolean isIndefinite;
    String endDate;
    boolean[] weekdays;
    boolean[] daysOfMonth;
    long interval;
    boolean isAlertsEnabled;
    boolean isSnoozeEnabled;
    long medicationID;

    public MedicationSchedule() {

    }

    //region Getters and Setters

    public boolean[] getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(boolean[] daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public boolean[] getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(boolean[] weekdays) {
        this.weekdays = weekdays;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public boolean isAlertsEnabled() {
        return isAlertsEnabled;
    }

    public void setAlertsEnabled(boolean alertsEnabled) {
        isAlertsEnabled = alertsEnabled;
    }

    public boolean isSnoozeEnabled() {
        return isSnoozeEnabled;
    }

    public void setSnoozeEnabled(boolean snoozeEnabled) {
        isSnoozeEnabled = snoozeEnabled;
    }

    public long getMedicationID() {
        return medicationID;
    }

    public void setMedicationID(long medicationID) {
        this.medicationID = medicationID;
    }

    public long getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public boolean isIndefinite() {
        return isIndefinite;
    }

    public void setIndefinite(boolean indefinite) {
        this.isIndefinite = indefinite;
    }

    //endregion
}
