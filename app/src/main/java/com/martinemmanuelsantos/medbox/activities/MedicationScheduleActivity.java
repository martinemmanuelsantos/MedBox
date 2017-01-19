package com.martinemmanuelsantos.medbox.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.MedicationScheduleEntry;
import com.martinemmanuelsantos.medbox.database.MedBoxContract.ScheduleTimesEntry;
import com.martinemmanuelsantos.medbox.models.Medication;
import com.martinemmanuelsantos.medbox.models.MedicationSchedule;
import com.martinemmanuelsantos.medbox.models.ScheduleTime;
import com.martinemmanuelsantos.medbox.utils.DateTimeUtils;
import com.martinemmanuelsantos.medbox.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MedicationScheduleActivity extends AppCompatActivity {

    /* Intent Variables */
    Medication medication;

    /* UI Elements */
    private EditText editTextStartDate, editTextEndDate, editTextWeekdays, editTextDaysOfMonth;
    private Spinner spinnerInterval;
    private Switch switchIndefinite, switchAlerts, switchSnooze;
    private LinearLayout parentViewDoseTimes;
    private Button buttonAddDose, buttonAddMedication;

    /* Calendar with initial time set to current time */
    static Calendar calendar = Calendar.getInstance();

    /* ArrayLists to keep values of multi-select alert dialogs */
    boolean[] checkedWeekdays, checkedDaysOfTheMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_schedule);

        // Retrieve Medication object
        initializeExtras();

        // Create UI elements
        createEditTexts();
        createSpinners();
        createSwitches();
        createLinearLayouts();
        createButtons();

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

    //region Intent

    private void initializeExtras() {

        // Retrieve Medication object
        Intent intent = getIntent();
        medication = (Medication) intent.getSerializableExtra("medicationObject");

    }

    //endregion

    //region Create UI elements and set listeners

    private void createEditTexts() {

        editTextStartDate = (EditText) findViewById(R.id.edit_text_medication_schedule_start_date);
        editTextEndDate = (EditText) findViewById(R.id.edit_text_medication_schedule_end_date);
        editTextWeekdays = (EditText) findViewById(R.id.edit_text_medication_schedule_weekdays);
        editTextDaysOfMonth = (EditText) findViewById(R.id.edit_text_medication_schedule_monthdays);

        // Show date picker dialog
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextStartDate);
            }
        });

        // Show date picker dialog
        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextEndDate);
            }
        });

        // Show checkboxes for each day of the week
        checkedWeekdays = new boolean[getResources().getStringArray(R.array.days_of_the_week).length];
        editTextWeekdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckboxDialog(checkedWeekdays, R.array.days_of_the_week, editTextWeekdays);

            }
        });

        // Show checkboxes for each day of the month
        checkedDaysOfTheMonth = new boolean[getResources().getStringArray(R.array.days_of_the_month).length];
        editTextDaysOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCheckboxDialog(checkedDaysOfTheMonth, R.array.days_of_the_month, editTextDaysOfMonth);
            }
        });

    }

    private void createSpinners() {

        spinnerInterval = (Spinner) findViewById(R.id.spinner_medication_schedule_interval);

        // Set the visibility of the appropriate EditTexts according to the value fo the spinner
        spinnerInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                switch (position) {
                    case 0:
                        findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 1:
                        findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.VISIBLE);
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 2:
                        findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.VISIBLE);
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.VISIBLE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 3:
                        findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.VISIBLE);
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void createSwitches() {

        switchIndefinite = (Switch) findViewById(R.id.switch_medication_schedule_indefinite);
        switchAlerts = (Switch) findViewById(R.id.switch_medication_schedule_alert);
        switchSnooze = (Switch) findViewById(R.id.switch_medication_schedule_snooze);

        // Show the end date EditText if the user does not want to take the medication indefinitely
        switchIndefinite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (switchIndefinite.isChecked()) {
                    findViewById(R.id.edit_text_medication_schedule_end_date).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.edit_text_medication_schedule_end_date).setVisibility(View.VISIBLE);
                }
            }

        });

    }

    private void createLinearLayouts() {

        // Set up the parent layout to hold each row (a row will contain the dose and time of the medication)
        // Add the first row, then hide it's delete button so at least one row is always visible
        parentViewDoseTimes = (LinearLayout) findViewById(R.id.linear_layout_add_times_rows);
        addRowDoseTimes();
        View firstRow = parentViewDoseTimes.getChildAt(0);
        firstRow.findViewById(R.id.image_button_add_times_delete_row).setVisibility(View.INVISIBLE);

    }

    private void createButtons() {

        buttonAddDose = (Button) findViewById(R.id.button_add_medication_add_dose);
        buttonAddMedication = (Button) findViewById(R.id.button_medication_schedule_add_medication);

        // Add a row to the dose times parent view
        buttonAddDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddRowDoseTimeClicked(view);
            }
        });

        buttonAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MedicationSchedule schedule = buildMedicationSchedule();
                ArrayList<ScheduleTime> times = buildScheduleTimes();

                if (schedule == null) { return; }
                if (times == null) { return; }

                // Insert the Medication row first and get the row ID as a long
                // Then use the row ID as the foreign key ID for the MedicationSchedule object.
                // Again, get the MedicationSchedule row ID as a long and use it as the foreign key
                // for ALL ScheduleTime objects
                Uri medicationUri = insertMedication(medication);
                long medicationId = ContentUris.parseId(medicationUri);
                schedule.setMedicationID(medicationId);

                Uri scheduleUri = insertSchedule(schedule);
                long scheduleId = ContentUris.parseId(scheduleUri);

                for (int i = 0; i < times.size(); i++) {
                    times.get(i).setScheduleID(scheduleId);
                    Uri timeUri = insertTime(times.get(i));
                    long timeId = ContentUris.parseId(timeUri);
                    times.get(i).setTimeID(timeId);
                }

                StringBuilder temp = new StringBuilder();
                temp.append("Added new medication with ID: ");
                temp.append(medicationId + "\n");
                temp.append("with the following schedule ID: ");
                temp.append(scheduleId + "\n");
                temp.append("and the following time IDs: \n");
                for (int i = 0; i < times.size(); i++) {
                    temp.append(times.get(i).getTimeID() + " ");
                }

                Toast.makeText(getApplicationContext(), temp.toString(), Toast.LENGTH_LONG).show();

                // Go back to the MainActivity
                Intent intent = new Intent(MedicationScheduleActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

    //endregion

    //region Custom dialogs

    /**
     * Custom dialog for allowing the user to enter in a date. The date entered by the user
     * is passed and sets the value of the parentEditText
     * @param parentEditText
     */
    public void showDatePickerDialog(final EditText parentEditText) {

        // Initial date values
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create the DatePickerDialog
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(MedicationScheduleActivity.this,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Set the text of parentEditText to the selected date on the date picker dialog
                calendar.set(year, month, day, 0, 0, 0);
                parentEditText.setText(DateTimeUtils.getDateUIFormatted(calendar, DateTimeUtils.FORMAT_UI));

            }
        }, year, month, day);
        datePickerDialog.show();

    }

    /**
     * Custom dialog for allowing the user to enter in a time. The time entered by the user
     * is passed and sets the value of the parentEditText
     * @param parentEditText
     */
    public void showTimePickerDialog(final EditText parentEditText) {

        // Ensure the time values are set to current time
        final Calendar currentCalendar = Calendar.getInstance();
        int hour = currentCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = currentCalendar.get(Calendar.MINUTE);

        // Create the TimePickerDialog
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(MedicationScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                currentCalendar.set(0, 0, 0, hourOfDay, minute);
                // Set the text of parentEditText to the selected time on the date picker dialog
                parentEditText.setText(DateTimeUtils.getTimeFormatted(currentCalendar, DateTimeUtils.FORMAT_UI));
            }
        }, hour, minute, false);
        timePickerDialog.show();

    }

    /**
     * Custom dialog for allowing select 0 to many checkboxes. The selected checkboxes are passed
     * as a string and set the value of parentEditText
     * @param parentEditText
     */
    public void showCheckboxDialog(final boolean[] checkedItems, final int arrayResource, final EditText parentEditText) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MedicationScheduleActivity.this);

        // Make a temporary copy of the current state of the checkboxes
        final boolean[] tempCheckedItems = new boolean[checkedItems.length];
        System.arraycopy(checkedItems, 0, tempCheckedItems, 0, checkedItems.length);

        // Create the checkboxes and set their value to tempCheckedItems
        builder.setMultiChoiceItems(arrayResource, tempCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                tempCheckedItems[which] = isChecked;
            }
        });

        // Set the logic of the OK button
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Make a copy of all the items in the arrayResource
                List<String> allItems = Arrays.asList(getResources().getStringArray(arrayResource));
                // List to hold selected items
                List<String> selectedItems = new ArrayList<String>();

                // Iterate through each item and add it to the selected items list if it is checked
                for(int i = 0; i < tempCheckedItems.length; i++){
                    if (tempCheckedItems[i]) {
                        selectedItems.add(allItems.get(i));
                    }
                }

                // List as a formatted string
                String selectedItemsString = TextUtils.join(", ", selectedItems);

                // Set the text of the specified parentEditText
                parentEditText.setText(selectedItemsString);

                // The OK button has been pressed, so save the state of the checkboxes
                System.arraycopy(tempCheckedItems, 0, checkedItems, 0, tempCheckedItems.length);

            }
        });

        // Discard the selected checkbox items
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // removes the AlertDialog in the screen
            }
        });

        builder.show();

    }

    //endregion

    //region MedicationSchedule Builder

    private MedicationSchedule buildMedicationSchedule() {

        MedicationSchedule schedule = new MedicationSchedule();

        // Start Date
        String startDate = editTextStartDate.getText().toString();
        // Check if the EditText is empty
        if (startDate.matches("")) {

            // Highlight the color of the empty EditText, notify the user and exit onClick
            editTextStartDate.getBackground()
                    .setColorFilter(getResources()
                    .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
            Toast.makeText(getApplicationContext(), "Please enter a start date", Toast.LENGTH_LONG).show();
            return null;

        } else {

            // Save start date and return EditText highlight to original state
            schedule.setStartDate(DateTimeUtils.convertToSqliteDate(startDate));
            editTextStartDate.getBackground().setColorFilter(null);

        }

        // Is Indefinite
        schedule.setIndefinite(switchIndefinite.isChecked());

        // Intervals
        String selectedInterval = spinnerInterval.getSelectedItem().toString();

        // End Date
        // Only proceed if the
        if (editTextEndDate.isShown()) {
                String endDate = editTextEndDate.getText().toString();
                // Check if the EditText is empty
                if (endDate.matches("")) {

                    // Highlight the color of the empty EditText, notify the user and exit onClick
                    editTextEndDate.getBackground()
                            .setColorFilter(getResources()
                            .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(getApplicationContext(), "Please enter a end date", Toast.LENGTH_LONG).show();
                    return null;

                } else {

                    // Save end date and return EditText highlight to original state
                    schedule.setStartDate(DateTimeUtils.convertToSqliteDate(endDate));
                    editTextEndDate.getBackground().setColorFilter(null);

                }
        }

        // Weekdays
        // Only check if spinner is set to weekly
        if (editTextWeekdays.isShown()) {
            String weekdays = editTextWeekdays.getText().toString();
            // Check if the EditText is empty
            if (weekdays.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextWeekdays.getBackground()
                        .setColorFilter(getResources()
                        .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please enter at least one weekday", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save weekdays and return EditText highlight to original state
                schedule.setWeekdays(checkedWeekdays);
                editTextEndDate.getBackground().setColorFilter(null);

            }
        }

        // Monthdays
        // Only check if spinner is set to monthly
        if (editTextDaysOfMonth.isShown()) {
            String daysOfMonth = editTextDaysOfMonth.getText().toString();
            // Check if the EditText is empty
            if (daysOfMonth.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                editTextDaysOfMonth.getBackground()
                        .setColorFilter(getResources()
                        .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please enter at least one day of the month", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save weekdays and return EditText highlight to original state
                schedule.setDaysOfMonth(checkedDaysOfTheMonth);
                editTextEndDate.getBackground().setColorFilter(null);

            }
        }

        // Interval
        schedule.setInterval(spinnerInterval.getSelectedItemId());

        // Is Alerts Enabled
        schedule.setAlertsEnabled(switchAlerts.isChecked());

        // Is Snooze Enabled
        schedule.setSnoozeEnabled(switchSnooze.isChecked());

        return schedule;

    }

    //endregion

    //region ScheduleTime Builder

    private ArrayList<ScheduleTime> buildScheduleTimes() {

        ArrayList<ScheduleTime> scheduleTimes = new ArrayList<ScheduleTime>();

        for (int i = 0; i < parentViewDoseTimes.getChildCount(); i++) {

            ScheduleTime time = new ScheduleTime();
            View rowView = parentViewDoseTimes.getChildAt(i);

            // Time
            EditText childDoseTime = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_time);
            String doseTime = childDoseTime.getText().toString();
            // Check if the EditText is empty
            if (doseTime.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                childDoseTime.getBackground()
                        .setColorFilter(getResources()
                        .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please fill in all dose time entries", Toast.LENGTH_LONG).show();
                return null;

            } else {

                // Save weekdays and return EditText highlight to original state
                time.setTime(DateTimeUtils.convertToSqliteTime(doseTime));
                childDoseTime.getBackground().setColorFilter(null);

            }

            // Dose Count
            EditText childDoseCount = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_count);
            String doseCount = childDoseCount.getText().toString();
            // Check if the EditText is empty
            if (doseCount.matches("")) {

                // Highlight the color of the empty EditText, notify the user and exit onClick
                childDoseCount.getBackground()
                        .setColorFilter(getResources()
                        .getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                Toast.makeText(getApplicationContext(), "Please fill in all dose count entries", Toast.LENGTH_LONG).show();
                return null;

            } else {

                int doseCountNum = Integer.parseInt(doseCount);
                if (doseCountNum < 1) {

                    // Highlight the color of the empty EditText, notify the user and exit onClick
                    childDoseCount.getBackground().setColorFilter(getResources().getColor(R.color.colorInvalid), PorterDuff.Mode.SRC_ATOP);
                    Toast.makeText(getApplicationContext(), "All dose counts must be at least 1", Toast.LENGTH_LONG).show();
                    return null;

                } else {

                    // Save weekdays and return EditText highlight to original state
                    time.setDoseCount(Integer.parseInt(doseCount));
                    childDoseCount.getBackground().setColorFilter(null);


                }

            }

            scheduleTimes.add(time);

        }

        return scheduleTimes;

    }

    //endregion

    //region showAddTimesDialog Helper methods

    // Logic for the row_add_doses layout
    private void addRowDoseTimes() {

        // Create the view and inflate it with appropriate xml layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_add_doses, null);

        // Create elements of the layout
        final ImageButton imageButtonDeleteRow = (ImageButton) rowView.findViewById(R.id.image_button_add_times_delete_row);
        final ImageButton imageButtonSubtract = (ImageButton) rowView.findViewById(R.id.image_button_add_times_subtract);
        final ImageButton imageButtonAdd = (ImageButton) rowView.findViewById(R.id.image_button_add_times_add);
        final EditText editTextDoseTime = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_time);
        final EditText editTextDoseCount = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_count);

        // Remove any highlights on the EditTexts
        editTextDoseTime.getBackground().setColorFilter(null);
        editTextDoseCount.getBackground().setColorFilter(null);

        // Decrease the dose count
        imageButtonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editTextDoseCount.getText().toString().trim();

                // If the EditText is not empty
                if(!(text.isEmpty() || text.length() == 0 || text.equals("")))
                {
                    int doseCount = Integer.parseInt(text);
                    if (doseCount >= 2) doseCount--;
                    editTextDoseCount.setText(String.valueOf(doseCount));
                }

            }
        });

        // Increment the dose count
        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = editTextDoseCount.getText().toString().trim();

                // If the EditText is not empty
                if(!(text.isEmpty() || text.length() == 0 || text.equals("")))
                {
                    int doseCount = Integer.parseInt(text);
                    doseCount++;
                    editTextDoseCount.setText(String.valueOf(doseCount));
                }

            }
        });

        // Show a TimePickerDialog when the time EditView is selected
        editTextDoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(editTextDoseTime);
            }
        });

        // Add the row at the END of the parent view
        parentViewDoseTimes.addView(rowView, parentViewDoseTimes.getChildCount());

    }

    // Add a row
    public void onAddRowDoseTimeClicked(View view){
        addRowDoseTimes();
    }

    // Remove row and all it's data
    public void onDeleteDoseTimeClicked(View view) {
        parentViewDoseTimes.removeView((View) view.getParent());
    }

    //endregion

    //region Database Helper Methods

    private Uri insertMedication(Medication medication) {

        ContentValues values = new ContentValues();

        values.put(MedicationEntry.COLUMN_MEDICATIONS_NAME, medication.getName());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_PRESCRIPTION_TOGGLE, medication.isPrescription());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_ICON, medication.getIcon());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_DOSE_TYPE, medication.getDoseType());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_REMAINING_SUPPLY, medication.getRemainingSupply());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_WARNING_TOGGLE, medication.isLowSupplyWarning());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_LOW_SUPPLY_VALUE, medication.getLowSupplyValue());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_METHOD_TAKEN, medication.getMethodTaken());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_INSTRUCTION, medication.getInstruction());
        values.put(MedicationEntry.COLUMN_MEDICATIONS_NOTES, medication.getNotes());

        // TODO: Error handling for null Uri

        return getContentResolver().insert(MedicationEntry.CONTENT_URI, values);

    }

    private Uri insertSchedule(MedicationSchedule schedule) {

        ContentValues values = new ContentValues();

        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_START_DATE, schedule.getStartDate());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_INDEFINITE, schedule.isIndefinite());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_END_DATE, schedule.getEndDate());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_INTERVAL, schedule.getInterval());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_ALERT_TOGGLE, schedule.isAlertsEnabled());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_SNOOZE_TOGGLE, schedule.isSnoozeEnabled());
        values.put(MedicationScheduleEntry.COLUMN_SCHEDULES_MEDICATION_ID, schedule.getMedicationID());

        // TODO: Error handling for null Uri

        return getContentResolver().insert(MedicationScheduleEntry.CONTENT_URI, values);

    }

    private Uri insertTime(ScheduleTime time) {

        ContentValues values = new ContentValues();

        values.put(ScheduleTimesEntry.COLUMN_TIMES_TIME, time.getDoseTime());
        values.put(ScheduleTimesEntry.COLUMN_TIMES_DOSE_COUNT, time.getDoseCount());
        values.put(ScheduleTimesEntry.COLUMN_TIMES_SCHEDULE_ID, time.getScheduleID());

        // TODO: Error handling for null Uri

        return getContentResolver().insert(ScheduleTimesEntry.CONTENT_URI, values);

    }

    //endregion
}
