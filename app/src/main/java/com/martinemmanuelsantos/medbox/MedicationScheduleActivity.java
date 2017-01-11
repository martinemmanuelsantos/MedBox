package com.martinemmanuelsantos.medbox;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Calendar;

public class MedicationScheduleActivity extends AppCompatActivity {

    /* Constants */
    public static final int ONE_TIME_USE = 1;
    public static final int DAILY = 2;
    public static final int WEEKLY = 3;
    public static final int MONTHLY = 4;

    /* UI Elements */
    private static EditText editTextWeekdays, editTextDaysOfMonth, editTextStartDate;
    private Spinner spinnerInterval, spinnerDuration;
    private Switch switchAlert, switchSnooze;
    private LinearLayout parentViewDoseTimes;
    private Button buttonAddDose, buttonAddMedication;

    /* Calendar with initial time set to current time */
    static Calendar calendar = Calendar.getInstance();

    /* ArrayLists to keep values of multi-select alert dialogs */
    ArrayList<Integer> selectedWeekdays, selectedMonthdays;

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
        editTextWeekdays = (EditText) findViewById(R.id.edit_text_medication_schedule_weekdays);
        editTextDaysOfMonth = (EditText) findViewById(R.id.edit_text_medication_schedule_monthdays);

        // Create date picker dialogue
        DateTimeHelper date = new DateTimeHelper(calendar);
        editTextStartDate.setText(date.getFormattedDate());
        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        editTextWeekdays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDaysOfTheWeekCheckboxAlertDialog();
            }
        });

        editTextDaysOfMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDaysOfTheMonthCheckboxAlertDialog();
            }
        });

    }

    private void createSpinners() {

        spinnerInterval = (Spinner) findViewById(R.id.spinner_medication_schedule_interval);
        spinnerDuration = (Spinner) findViewById(R.id.spinner_medication_schedule_duration);

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

        switchAlert = (Switch) findViewById(R.id.switch_medication_schedule_alert);
        switchSnooze = (Switch) findViewById(R.id.switch_medication_schedule_snooze);

    }

    private void createLinearLayouts() {

        parentViewDoseTimes = (LinearLayout) findViewById(R.id.linear_layout_add_times_rows);
        addRowDoseTimes();
        View firstRow = parentViewDoseTimes.getChildAt(0);
        firstRow.findViewById(R.id.image_button_add_times_delete_row).setVisibility(View.INVISIBLE);

    }

    private void createButtons() {

        buttonAddDose = (Button) findViewById(R.id.button_add_medication_add_dose);
        buttonAddDose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRowDoseTimes();
            }
        });

    }

    //endregion

    //region Custom dialogs

    public void showDatePickerDialog(final View v) {
        /*DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");*/
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(MedicationScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Set the date to the selected date on the date picker dialog
                calendar.set(year, month, day);
                DateTimeHelper currentDate = new DateTimeHelper(calendar);

                EditText editTextDate = (EditText) v.findViewById(R.id.edit_text_medication_schedule_start_date);
                editTextDate.setText(currentDate.getFormattedDate());

            }
        }, year, month, day);
        datePickerDialog.show();

    }

    public void showTimePickerDialog(final View v) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(MedicationScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                EditText editTextTime = (EditText) v.findViewById(R.id.edit_text_add_times_dose_time);
                String formattedTime = DateTimeHelper.getTimeFormatted(hourOfDay, minute);
                editTextTime.setText(formattedTime);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void showDaysOfTheWeekCheckboxAlertDialog(){

        selectedWeekdays = new ArrayList<Integer>();

        AlertDialog.Builder builder = new AlertDialog.Builder(MedicationScheduleActivity.this);

        // Set the dialog title
        builder.setTitle("Select day(s)")

                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // R.array.days were set in the resources res/values/strings.xml
                .setMultiChoiceItems(R.array.days_of_the_week, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            selectedWeekdays.add(which);
                        }

                        else if (selectedWeekdays.contains(which)) {
                            // else if the item is already in the array, remove it
                            selectedWeekdays.remove(Integer.valueOf(which));
                        }
                    }

                })

                // Set the action buttons
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // user clicked OK, so save the selectedItems results somewhere
                        // here we are trying to retrieve the selected items indices
                        String selectedIndex = "";
                        for(Integer i : selectedWeekdays){
                            selectedIndex += i + ", ";
                        }

                        Toast.makeText(getApplicationContext(), "Selected index: " + selectedIndex, Toast.LENGTH_LONG);

                    }
                })

                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();

    }

    public void showDaysOfTheMonthCheckboxAlertDialog(){

        selectedMonthdays = new ArrayList<Integer>();

        AlertDialog.Builder builder = new AlertDialog.Builder(MedicationScheduleActivity.this);

        // Set the dialog title
        builder.setTitle("Select day(s)")

                // specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive call backs when items are selected
                // R.array.days were set in the resources res/values/strings.xml
                .setMultiChoiceItems(R.array.days_of_the_month, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items
                            selectedWeekdays.add(which);
                        }

                        else if (selectedWeekdays.contains(which)) {
                            // else if the item is already in the array, remove it
                            selectedWeekdays.remove(Integer.valueOf(which));
                        }
                    }

                })

                // Set the action buttons
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // user clicked OK, so save the selectedItems results somewhere
                        // here we are trying to retrieve the selected items indices
                        String selectedIndex = "";
                        for(Integer i : selectedWeekdays){
                            selectedIndex += i + ", ";
                        }

                        Toast.makeText(getApplicationContext(), "Selected index: " + selectedIndex, Toast.LENGTH_LONG);

                    }
                })

                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();

    }

    //endregion

    //region showAddTimesDialog Helper methods

    private void addRowDoseTimes() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.row_add_doses, null);
        final ImageButton imageButtonDeleteRow = (ImageButton) rowView.findViewById(R.id.image_button_add_times_delete_row);
        final ImageButton imageButtonSubtract = (ImageButton) rowView.findViewById(R.id.image_button_add_times_subtract);
        final ImageButton imageButtonAdd = (ImageButton) rowView.findViewById(R.id.image_button_add_times_add);
        final EditText editTextDoseTime = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_time);
        final EditText editTextDoseCount = (EditText) rowView.findViewById(R.id.edit_text_add_times_dose_count);

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

        editTextDoseTime.setText(new DateTimeHelper(calendar).getTime());
        editTextDoseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(view);
            }
        });

        parentViewDoseTimes.addView(rowView, parentViewDoseTimes.getChildCount() - 1);
    }

    public void onAddRowDoseTimeClicked(View view){
        addRowDoseTimes();
    }

    public void onDeleteDoseTimeClicked(View view) {
        parentViewDoseTimes.removeView((View) view.getParent());
    }

    public void incDoseCount (View view) {

    }

    public void decDoseCount (View view) {

    }

    //endregion

}
