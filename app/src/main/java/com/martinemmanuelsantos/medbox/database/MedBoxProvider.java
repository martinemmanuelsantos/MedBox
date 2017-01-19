package com.martinemmanuelsantos.medbox.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationScheduleEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.ScheduleTimesEntry;
import com.martinemmanuelsantos.medbox.models.MedicationSchedule;

public class MedBoxProvider extends ContentProvider {

    /* URI matcher code for the content URI for the each table */
    private static final int MEDICATIONS = 100;
    private static final int MEDICATION_ID = 101;
    private static final int SCHEDULES = 102;
    private static final int SCHEDULE_ID = 103;
    private static final int TIMES = 104;
    private static final int TIME_ID = 105;

    /* UriMatcher object to match a content to any of the corresponding URI matcher codes listed
     * above. The input pased into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from the class.
    static {
        // Each of the below represents the URI patterns that the provider should recognize

        // content://com.martinemmanuelsantos.medbox/medications
        // Used for querying the medications table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "medications", MEDICATIONS);

        // content://com.martinemmanuelsantos.medbox/medications/#
        // Used for querying row # from the medications table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "medications/#", MEDICATION_ID);

        // content://com.martinemmanuelsantos.medbox/schedules
        // Used for querying the schedules table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "schedules", SCHEDULES);

        // content://com.martinemmanuelsantos.medbox/schedules/#
        // Used for querying row # from the schedules table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "schedules/#", TIME_ID);

        // content://com.martinemmanuelsantos.medbox/times
        // Used for querying the times table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "times", TIMES);

        // content://com.martinemmanuelsantos.medbox/times/#
        // Used for querying row # from the times table
        sUriMatcher.addURI(MedBoxContract.CONTENT_AUTHORITY, "times/#", TIME_ID);

        // TODO: Add more URI patterns here...
    }

    /* Database Handler */
    MedBoxDbHandler medBoxDB;
    /* Log Tag */
    public static final String LOG_TAG = MedBoxProvider.class.getSimpleName();


    @Override
    public boolean onCreate() {
        medBoxDB = new MedBoxDbHandler(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        // Get writeable database
        SQLiteDatabase db = medBoxDB.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {

            case MEDICATIONS:

                cursor = db.query(MedicationEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            case MEDICATION_ID:

                selection = MedicationEntry._ID +"=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(MedicationEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            case SCHEDULES:

                cursor = db.query(MedicationScheduleEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            case SCHEDULE_ID:

                selection = MedicationScheduleEntry._ID +"=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(MedicationScheduleEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            case TIMES:

                cursor = db.query(ScheduleTimesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            case TIME_ID:

                selection = ScheduleTimesEntry._ID +"=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = db.query(ScheduleTimesEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);

                break;

            // TODO: Add more cases here

            default:
                throw new IllegalArgumentException(" Cannot query unknown URI " + uri);

        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {

            case MEDICATIONS:
                return MedicationEntry.CONTENT_LIST_TYPE;

            case MEDICATION_ID:
                return MedicationEntry.CONTENT_ITEM_TYPE;

            case SCHEDULES:
                return MedicationScheduleEntry.CONTENT_LIST_TYPE;

            case SCHEDULE_ID:
                return MedicationScheduleEntry.CONTENT_ITEM_TYPE;

            case TIMES:
                return ScheduleTimesEntry.CONTENT_LIST_TYPE;

            case TIME_ID:
                return ScheduleTimesEntry.CONTENT_ITEM_TYPE;

            // TODO: Add more cases here

            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);

        }

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final int match = sUriMatcher.match(uri);
        switch (match) {

            case MEDICATIONS:
                return insertMedication(uri, contentValues);

            case SCHEDULES:
                return insertSchedule(uri, contentValues);

            case TIMES:
                return insertTime(uri, contentValues);

            // TODO: Add more cases here

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);

        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        // Get writeable database
        SQLiteDatabase database = medBoxDB.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case MEDICATIONS:
                // Delete all rows that match the selection and selection args
                return database.delete(MedicationEntry.TABLE_NAME, selection, selectionArgs);

            case MEDICATION_ID:
                // Delete a single row given by the ID in the URI
                selection = MedicationEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(MedicationEntry.TABLE_NAME, selection, selectionArgs);

            case SCHEDULES:
                // Delete all rows that match the selection and selection args
                return database.delete(ScheduleTimesEntry.TABLE_NAME, selection, selectionArgs);

            case SCHEDULE_ID:
                // Delete a single row given by the ID in the URI
                selection = ScheduleTimesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(ScheduleTimesEntry.TABLE_NAME, selection, selectionArgs);

            case TIMES:
                // Delete all rows that match the selection and selection args
                return database.delete(ScheduleTimesEntry.TABLE_NAME, selection, selectionArgs);

            case TIME_ID:
                // Delete a single row given by the ID in the URI
                selection = ScheduleTimesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(ScheduleTimesEntry.TABLE_NAME, selection, selectionArgs);

            // TODO: Add more cases here...

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);

        }

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {

        final int match = sUriMatcher.match(uri);
        switch (match) {

            case MEDICATIONS:
                return updateMedication(uri, contentValues, selection, selectionArgs);

            case MEDICATION_ID:
                // For the MEDICATION_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MedicationEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateMedication(uri, contentValues, selection, selectionArgs);

            case SCHEDULES:
                return updateSchedule(uri, contentValues, selection, selectionArgs);

            case SCHEDULE_ID:
                // For the SCHEDULE_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MedicationScheduleEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateSchedule(uri, contentValues, selection, selectionArgs);

            case TIMES:
                return updateTime(uri, contentValues, selection, selectionArgs);

            case TIME_ID:
                // For the TIME_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = ScheduleTimesEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateTime(uri, contentValues, selection, selectionArgs);

            // TODO: Add more cases here

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);

        }

    }

    //region Insert Methods

    //region TODO: Implement sanity checks for each value
    /*if (values.containsKey(<VALUE NAME>)) {
        <TYPE> name = values.getAs<VALUE>(<VALUE NAME>);
        if (name == null) {
          throw new IllegalArgumentException("<ERROR MESSAGE>");
        }
    }*/
    //endregion

    /**
     * Insert a medication into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertMedication(Uri uri, ContentValues values) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // Insert the row into the medications table with the given content values
        long id = db.insert(MedicationEntry.TABLE_NAME, null, values);

        // The insertion query will return -1 if there was an error inserting the row
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Insert a medication into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertSchedule(Uri uri, ContentValues values) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // Insert the row into the medications table with the given content values
        long id = db.insert(MedicationScheduleEntry.TABLE_NAME, null, values);

        // The insertion query will return -1 if there was an error inserting the row
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    /**
     * Insert a medication into the database with the given content values. Return the new content URI
     * for that specific row in the database.
     */
    private Uri insertTime(Uri uri, ContentValues values) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // Insert the row into the medications table with the given content values
        long id = db.insert(ScheduleTimesEntry.TABLE_NAME, null, values);

        // The insertion query will return -1 if there was an error inserting the row
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Once we know the ID of the new row in the table,
        // return the new URI with the ID appended to the end of it
        return ContentUris.withAppendedId(uri, id);
    }

    //endregion

    //region Update Methods

    //region TODO: Implement sanity checks for each value
    /*if (values.containsKey(<VALUE NAME>)) {
        <TYPE> name = values.getAs<VALUE>(<VALUE NAME>);
        if (name == null) {
          throw new IllegalArgumentException("<ERROR MESSAGE>");
        }
    }*/
    //endregion

    /**
     * Update medications in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more medications).
     * Return the number of rows that were successfully updated.
     */
    private int updateMedication(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Update the rows the medications table with the given content values
        return db.update(MedicationEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    /**
     * Update schedules in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more schedules).
     * Return the number of rows that were successfully updated.
     */
    private int updateSchedule(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Update the rows the medications table with the given content values
        return db.update(MedicationEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    /**
     * Update times in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more times).
     * Return the number of rows that were successfully updated.
     */
    private int updateTime(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        // Get a writable database
        SQLiteDatabase db = medBoxDB.getWritableDatabase();

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Update the rows the medications table with the given content values
        return db.update(MedicationEntry.TABLE_NAME, values, selection, selectionArgs);

    }

    //endregion

    //region Delete Methods

    //endregion

    //region Get Type Methods

    //endregion

}
