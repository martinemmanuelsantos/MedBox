package com.martinemmanuelsantos.medbox.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nutel on 1/16/2017.
 */

public final class MedBoxContract {

    /*
     * The content authority for the MedBox contract, which is the package name of the app.
     * This is the name of the entire content provider.
     */
    public static final String CONTENT_AUTHORITY = "com.martinemmanuelsantos.medbox";

    /*
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /*
     * Possible path (appended to base content URI for possible URI's)
     */
    public static final String PATH_MEDICAITONS = "medications";
    public static final String PATH_SCHEDULES = "schedules";
    public static final String PATH_TIMES = "times";

    /**
     * Inner class that defines constant values for the medications database table.
     * Each entry in the table represents a single medication.
     */
    public static final class MedicationEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEDICAITONS);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of medications.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDICAITONS;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single medication.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEDICAITONS;

        public static final String TABLE_NAME = "medications";

        public static final String _ID = BaseColumns._ID;
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

    }

    /**
     * Inner class that defines constant values for the schedules database table.
     * Each entry in the table represents a single medication.
     */
    public static final class MedicationScheduleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SCHEDULES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of schedules.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single schedule.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCHEDULES;

        public static final String TABLE_NAME = "schedules";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_SCHEDULES_START_DATE = "start_date";
        public static final String COLUMN_SCHEDULES_INDEFINITE = "use_indefinitely";
        public static final String COLUMN_SCHEDULES_END_DATE = "end_date";
        public static final String COLUMN_SCHEDULES_INTERVAL = "interval";
        public static final String COLUMN_SCHEDULES_ALERT_TOGGLE = "enable_alerts";
        public static final String COLUMN_SCHEDULES_SNOOZE_TOGGLE = "enable_snooze";
        public static final String COLUMN_SCHEDULES_MEDICATION_ID = "fk_medication_id";

    }

    /**
     * Inner class that defines constant values for the times database table.
     * Each entry in the table represents a single medication.
     */
    public static final class ScheduleTimesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TIMES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of times.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIMES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single time.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TIMES;

        public static final String TABLE_NAME = "times";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TIMES_TIME = "time";
        public static final String COLUMN_TIMES_DOSE_COUNT = "dose_count";
        public static final String COLUMN_TIMES_SCHEDULE_ID = "fk_schedule_id";

    }

}
