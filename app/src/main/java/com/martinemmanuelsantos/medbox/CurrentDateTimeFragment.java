package com.martinemmanuelsantos.medbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        DateTimeHelper date = new DateTimeHelper();

        textViewDayNumber.setText(Integer.toString(date.day));
        textViewMonthYear.setText(date.monthFormatted + ", " + date.year);
        textViewDay.setText(date.dayFormatted + ", " + date.time);

    }

}
