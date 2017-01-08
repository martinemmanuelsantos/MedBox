package com.martinemmanuelsantos.medbox;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MedicationScheduleActivity extends AppCompatActivity {

    private static EditText edittextTimes, edittextWeekdays, edittextMonthdays, edittextStartDate;
    static Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_schedule);

        createEditTexts();
    }

    private void createEditTexts() {

        edittextTimes = (EditText) findViewById((R.id.edit_text_medication_schedule_times));
        edittextWeekdays = (EditText) findViewById((R.id.edit_text_medication_schedule_weekdays));
        edittextMonthdays = (EditText) findViewById((R.id.edit_text_medication_schedule_monthdays));
        edittextStartDate = (EditText) findViewById((R.id.edit_text_medication_schedule_start_date));

        // Create date picker dialogue
        DateTimeHelper date = new DateTimeHelper(calendar);
        edittextStartDate.setText(date.getFormattedDate());
        edittextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
        }
        });

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    // Class for selecting the date
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use today's date as the default date
            DateTimeHelper date = new DateTimeHelper(calendar);
            return new DatePickerDialog(getActivity(), this, date.year, date.month, date.day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // Set the date to the selected date on the date picker dialog
            calendar.set(year, month, day);
            DateTimeHelper selectedDate = new DateTimeHelper(calendar);

            edittextStartDate.setText(selectedDate.getFormattedDate());
        }
    }

    // Class for selecting the time
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
            edittextStartDate.setText(String.format("%02d",hourOfDay) + ":" + String.format("%02d",minute));
        }

    }
}
