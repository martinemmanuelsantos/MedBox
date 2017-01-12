package com.martinemmanuelsantos.medbox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MedicationScheduleActivity extends AppCompatActivity {

    /* UI Elements */
    private static EditText editTextStartDate, editTextEndDate, editTextWeekdays, editTextDaysOfMonth;
    private Spinner spinnerInterval;
    private Switch switchIndefinite, switchAlert, switchSnooze;
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

        createEditTexts();
        createSpinners();
        createSwitches();
        createLinearLayouts();
        createButtons();

    }

    //region Create UI elements and set listeners

    private void createEditTexts() {

        editTextStartDate = (EditText) findViewById(R.id.edit_text_medication_schedule_start_date);
        editTextEndDate = (EditText) findViewById(R.id.edit_text_medication_schedule_end_date);
        editTextWeekdays = (EditText) findViewById(R.id.edit_text_medication_schedule_weekdays);
        editTextDaysOfMonth = (EditText) findViewById(R.id.edit_text_medication_schedule_monthdays);

        // Use the current time as the default date/time value
        DateTimeHelper date = new DateTimeHelper(calendar);

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
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 1:
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.GONE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 2:
                        findViewById(R.id.linear_layout_medication_schedule_weekdays).setVisibility(View.VISIBLE);
                        findViewById(R.id.linear_layout_medication_schedule_monthdays).setVisibility(View.GONE);
                        break;
                    case 3:
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
        switchAlert = (Switch) findViewById(R.id.switch_medication_schedule_alert);
        switchSnooze = (Switch) findViewById(R.id.switch_medication_schedule_snooze);

        // Show the end date EditText if the user does not want to take the medication indefinitely
        switchIndefinite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchIndefinite.isChecked()) {
                    findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.linear_layout_medication_schedule_end_date).setVisibility(View.VISIBLE);
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

                // TODO: Write data from this activity into database then move on to next activity

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
        datePickerDialog = new DatePickerDialog(MedicationScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Set the text of parentEditText to the selected date on the date picker dialog
                calendar.set(year, month, day);
                DateTimeHelper currentDate = new DateTimeHelper(calendar);
                parentEditText.setText(currentDate.getFormattedDate());

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

        // Initial time values (set to current time)
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        // Create the TimePickerDialog
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(MedicationScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                String formattedTime = DateTimeHelper.getTimeFormatted(hourOfDay, minute);
                // Set the text of parentEditText to the selected time on the date picker dialog
                parentEditText.setText(formattedTime);
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
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // removes the AlertDialog in the screen
            }
        });

        builder.show();

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
        parentViewDoseTimes.addView(rowView, parentViewDoseTimes.getChildCount() - 1);
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

}
