package com.example.myapplication.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CalendarAdapter;
import com.example.myapplication.managers.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearTV;
    private RecyclerView calendarRecyclerView;
    private Button prevMonthBtn, nextMonthBtn;
    private Calendar currentCalendar;
    private CalendarAdapter calendarAdapter;
    private ArrayList<Calendar> daysInMonthList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(rootView);
        setupCalendar();
        setupListeners();
        return rootView;
    }

    private void initViews(View view) {
        monthYearTV = view.findViewById(R.id.monthYearTV);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        prevMonthBtn = view.findViewById(R.id.prevMonthBtn);
        nextMonthBtn = view.findViewById(R.id.nextMonthBtn);
    }

    private void setupCalendar() {
        currentCalendar = Calendar.getInstance();
        updateMonthYearDisplay();
        daysInMonthList = CalendarUtils.daysInMonthArray();

        calendarRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        calendarAdapter = new CalendarAdapter(getContext(), daysInMonthList, this);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void setupListeners() {
        prevMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, -1);
            updateMonthYearDisplay();
            updateCalendar();
        });

        nextMonthBtn.setOnClickListener(v -> {
            currentCalendar.add(Calendar.MONTH, 1);
            updateMonthYearDisplay();
            updateCalendar();
        });
    }

    private void updateMonthYearDisplay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        monthYearTV.setText(simpleDateFormat.format(currentCalendar.getTime()));
    }

    private void updateCalendar() {
        CalendarUtils.selectedDate = (Calendar) currentCalendar.clone();
        daysInMonthList.clear();
        daysInMonthList.addAll(CalendarUtils.daysInMonthArray());
        calendarAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, Calendar date) {
        CalendarUtils.selectedDate = date;
        updateCalendar();
    }
}
