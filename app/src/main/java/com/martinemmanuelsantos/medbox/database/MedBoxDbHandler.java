package com.martinemmanuelsantos.medbox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.martinemmanuelsantos.medbox.models.Medication;
import com.martinemmanuelsantos.medbox.models.MedicationSchedule;
import com.martinemmanuelsantos.medbox.models.ScheduleTime;

import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationScheduleEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.ScheduleTimesEntry;

public class MedBoxDbHandler extends SQLiteOpenHelper {

    /* Database properties */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medbox.db";

    public MedBoxDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Enable foreign keys
        db.execSQL("PRAGMA foreign_keys=ON");

        // Create the medications table
        db.execSQL(
                "CREATE TABLE " + MedicationEntry.TABLE_NAME + " (" +
                        MedicationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MedicationEntry.COLUMN_MEDICATIONS_NAME + " TEXT," +
                        MedicationEntry.COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE + " INTEGER DEFAULT 0," +
                        MedicationEntry.COLUMN_MEDICATIONS_ICON + " INTEGER," +
                        MedicationEntry.COLUMN_MEDICATIONS_DOSE_TYPE + " TEXT," +
                        MedicationEntry.COLUMN_MEDICATIONS_REMAINING_SUPPLY + " INTEGER," +
                        MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE + " INTEGER DEFAULT 0," +
                        MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE + " INTEGER," +
                        MedicationEntry.COLUMN_MEDICATIONS_METHOD_TAKEN + " INTEGER," +
                        MedicationEntry.COLUMN_MEDICATIONS_INSTRUCTION + " INTEGER," +
                        MedicationEntry.COLUMN_MEDICATIONS_NOTES + " TEXT" +
                        ");"
        );

        // Create the schedules table
        db.execSQL(
                "CREATE TABLE " + MedicationScheduleEntry.TABLE_NAME + " (" +
                        MedicationScheduleEntry._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_START_DATE + " TEXT," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_INDEFINITE + " INTEGER DEFAULT 0," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_END_DATE + " TEXT," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_INTERVAL + " INTEGER," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_ALERT_TOGGLE + " INTEGER DEFAULT 0," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_SNOOZE_TOGGLE + " INTEGER DEFAULT 0," +
                        MedicationScheduleEntry.COLUMN_SCHEDULES_MEDICATION_ID + " INTEGER REFERENCES " + MedicationEntry.TABLE_NAME + "(" + MedicationEntry._ID + ")" +
                        ");"
        );

        // Create the times table
        db.execSQL(
                "CREATE TABLE " + ScheduleTimesEntry.TABLE_NAME + " (" +
                        ScheduleTimesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ScheduleTimesEntry.COLUMN_TIMES_TIME + " TEXT," +
                        ScheduleTimesEntry.COLUMN_TIMES_DOSE_COUNT + " INTEGER," +
                        ScheduleTimesEntry.COLUMN_TIMES_SCHEDULE_ID + " INTEGER REFERENCES " + MedicationScheduleEntry.TABLE_NAME + "(" + MedicationScheduleEntry._ID + ")" +
                        ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + MedicationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MedicationScheduleEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScheduleTimesEntry.TABLE_NAME);
        onCreate(db);

    }

}
