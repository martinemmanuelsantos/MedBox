package com.martinemmanuelsantos.medbox.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.martinemmanuelsantos.medbox.R;
import com.martinemmanuelsantos.medbox.database.MedBoxContract;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxDbHandler;
import com.martinemmanuelsantos.medbox.database.MedBoxProvider;

/**
 * Created by nutel on 1/18/2017.
 */

public class MedicationListActivity extends AppCompatActivity {

    /* UI Elements */
    TextView textViewDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);

        // Create UI elements
        createTextView();

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayMedicationData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //region Create UI elements and set listeners

    public void createTextView() {

        textViewDatabase = (TextView) findViewById(R.id.text_view_medicine_list_database);

    }

    //endregion

    public void displayMedicationData() {

        String[] projection = {
                MedicationEntry._ID,
                MedicationEntry.COLUMN_MEDICATIONS_NAME,
                /*MedicationEntry.COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE,
                MedicationEntry.COLUMN_MEDICATIONS_ICON,
                MedicationEntry.COLUMN_MEDICATIONS_DOSE_TYPE,
                MedicationEntry.COLUMN_MEDICATIONS_REMAINING_SUPPLY,
                MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE,
                MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE,
                MedicationEntry.COLUMN_MEDICATIONS_METHOD_TAKEN,
                MedicationEntry.COLUMN_MEDICATIONS_INSTRUCTION,
                MedicationEntry.COLUMN_MEDICATIONS_NOTES*/
        };

        Cursor cursor = getContentResolver().query(MedicationEntry.CONTENT_URI, projection, null, null, null);

        try {

            textViewDatabase.setText("The medications table contains " + cursor.getCount() + " medications.\n\n");
            textViewDatabase.append(MedicationEntry._ID + " - " +
                    MedicationEntry.COLUMN_MEDICATIONS_NAME + " - " + "\n");

            // Get the index of each column
            int columnIndexId = cursor.getColumnIndex(MedicationEntry._ID);
            int columnIndexName = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_NAME);
            int columnIndexPrescription = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE);
            int columnIndexIcon = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_ICON);
            int columnIndexDoseType = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_DOSE_TYPE);
            int columnIndexRemainingSupply = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_REMAINING_SUPPLY);
            int columnIndexLowSupplyWarning = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE);
            int columnIndexLowSupplyValue = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE);
            int columnIndexMethodTaken = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_METHOD_TAKEN);
            int columnIndexInstruction = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_INSTRUCTION);
            int columnIndexNotes = cursor.getColumnIndex(MedicationEntry.COLUMN_MEDICATIONS_NOTES);

            while (cursor.moveToNext()) {

                int currentId = cursor.getInt(columnIndexId);
                String currentName = cursor.getString(columnIndexName);

                textViewDatabase.append("\n" + currentId + " - " +
                        currentName);

            }

        } finally {

            cursor.close();

        }

    }

}
