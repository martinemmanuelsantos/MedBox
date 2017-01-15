package com.martinemmanuelsantos.medbox.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.martinemmanuelsantos.medbox.utils.DateTimeUtils;
import com.martinemmanuelsantos.medbox.R;

import java.util.Calendar;

public class CurrentDateTimeFragment extends Fragment {
    TextView textViewDayNumber, textViewMonthYear, textViewDay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_date_time, container, false);

        textViewDayNumber = (TextView) view.findViewById(R.id.text_view_current_date_time_day_num);
        textViewMonthYear = (TextView) view.findViewById(R.id.text_view_current_date_time_month_year);
        textViewDay = (TextView) view.findViewById(R.id.text_view_current_date_time_day);

        updateDateTime();

        return view;
    }

    private void updateDateTime() {

        Calendar calendar = Calendar.getInstance();

        textViewDayNumber.setText(Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
        textViewMonthYear.setText(DateTimeUtils.getMonthOfYear(calendar) + ", " + Integer.toString(calendar.get(Calendar.YEAR)));
        textViewDay.setText(DateTimeUtils.getDayOfWeek(calendar) + ", " + DateTimeUtils.getTimeFormatted(calendar, DateTimeUtils.FORMAT_UI));

    }

}
