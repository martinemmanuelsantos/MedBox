package com.martinemmanuelsantos.medbox.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedBoxDatabaseHandler extends SQLiteOpenHelper {

    /* Database properties */
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "medbox.db";

    /* Medication table and column names */
    public static final String TABLE_MEDICATIONS = "medications";
    public static final String COLUMN_MEDICATIONS_ID = "medication_id";
    public static final String COLUMN_MEDICATIONS_NAME = "medication_name";
    public static final String COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE = "needs_prescription";
    public static final String COLUMN_MEDICATIONS_ICON = "icon";
    public static final String COLUMN_MEDICATIONS_DOSE_TYPE = "dose_type";
    public static final String COLUMN_MEDICATIONS_REMAINING_SUPPLY = "remaining_supply";
    public static final String COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE = "toggle_low_supply_warning";
    public static final String COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE = "low_supply_value";
    public static final String COLUMN_MEDICATIONS_METHOD_TAKEN = "method_taken";
    public static final String COLUMN_MEDICATIONS_INSTRUCTION = "insutruction";
    public static final String COLUMN_MEDICATIONS_NOTES = "notes";

    /* Medicine MedicationSchedule table and column names */
    public static final String TABLE_SCHEDULES = "schedule";
    public static final String COLUMN_SCHEDULES_ID = "schedule_id";
    public static final String COLUMN_SCHEDULES_START_DATE = "start_date";
    public static final String COLUMN_SCHEDULES_INDEFINITE = "use_indefinitely";
    public static final String COLUMN_SCHEDULES_END_DATE = "end_date";
    public static final String COLUMN_SCHEDULES_INTERVAL = "interval";
    public static final String COLUMN_SCHEDULES_ALERT_TOGGLE = "enable_alerts";
    public static final String COLUMN_SCHEDULES_SNOOZE_TOGGLE = "enable_snooze";
    public static final String COLUMN_SCHEDULES_MEDICATION_ID = "fk_medication_id";

    /* ScheduleTime table and column names */
    public static final String TABLE_TIMES = "time";
    public static final String COLUMN_TIMES_ID = "time_id";
    public static final String COLUMN_TIMES_TIME = "time";
    public static final String COLUMN_TIMES_DOSE_COUNT = "dose_count";
    public static final String COLUMN_TIMES_SCHEDULE_ID = "fk_dose_count";


    public MedBoxDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
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
                "CREATE TABLE " + TABLE_MEDICATIONS + " (" +
                        COLUMN_MEDICATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_MEDICATIONS_NAME + " TEXT," +
                        COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE + " INTEGER DEFAULT 0," +
                        COLUMN_MEDICATIONS_ICON + " BLOB," +
                        COLUMN_MEDICATIONS_DOSE_TYPE + " TEXT," +
                        COLUMN_MEDICATIONS_REMAINING_SUPPLY + " INTEGER," +
                        COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE + " INTEGER DEFAULT 0," +
                        COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE + " INTEGER," +
                        COLUMN_MEDICATIONS_METHOD_TAKEN + " INTEGER," +
                        COLUMN_MEDICATIONS_INSTRUCTION + " INTEGER," +
                        COLUMN_MEDICATIONS_NOTES + " TEXT" +
                        ")"
        );

        // Create the schedules table
        db.execSQL(
                "CREATE TABLE " + TABLE_SCHEDULES + " (" +
                        COLUMN_SCHEDULES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_SCHEDULES_START_DATE + " TEXT," +
                        COLUMN_SCHEDULES_INDEFINITE + " INTEGER DEFAULT 0," +
                        COLUMN_SCHEDULES_END_DATE + " TEXT," +
                        COLUMN_SCHEDULES_INTERVAL + " INTEGER," +
                        COLUMN_SCHEDULES_ALERT_TOGGLE + " INTEGER DEFAULT 0," +
                        COLUMN_SCHEDULES_SNOOZE_TOGGLE + " INTEGER DEFAULT 0," +
                        COLUMN_SCHEDULES_MEDICATION_ID + " INTEGER REFERENCES " + TABLE_MEDICATIONS + "(" + COLUMN_MEDICATIONS_ID + ")" +
                        ")"
        );

        // Create the times table
        db.execSQL(
                "CREATE TABLE " + TABLE_TIMES + " (" +
                        COLUMN_TIMES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TIMES_TIME + " TEXT," +
                        COLUMN_TIMES_DOSE_COUNT + " INTEGER," +
                        COLUMN_TIMES_SCHEDULE_ID + " INTEGER REFERENCES " + TABLE_SCHEDULES + "(" + COLUMN_SCHEDULES_ID + ")" +
                        ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMES);
        onCreate(db);

    }

    public void addRowMedication(Medication medication) {



    }

    public void addRowSchedule(MedicationSchedule schedule) {



    }

    public void addRowTime(ScheduleTime Time) {



    }

}
