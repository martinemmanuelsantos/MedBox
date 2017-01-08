package com.martinemmanuelsantos.medbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CurrentDateTimeFragment extends Fragment {
    TextView textviewDayNumber;
    TextView textviewMonthYear;
    TextView textviewDay;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_date_time, container, false);

        textviewDayNumber = (TextView) view.findViewById(R.id.fragment_current_date_time_day_num);
        textviewMonthYear = (TextView) view.findViewById(R.id.fragment_current_date_time_month_year);
        textviewDay = (TextView) view.findViewById(R.id.fragment_current_date_time_day);

        updateDateTime();

        return view;
    }

    private void updateDateTime() {

        DateTimeHelper date = new DateTimeHelper();

        textviewDayNumber.setText(Integer.toString(date.day));
        textviewMonthYear.setText(date.monthFormatted + ", " + date.year);
        textviewDay.setText(date.dayFormatted + ", " + date.time);

    }

}
