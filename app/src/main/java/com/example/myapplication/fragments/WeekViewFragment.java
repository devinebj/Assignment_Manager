package com.example.myapplication.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.AssignmentAdapter;
import com.example.myapplication.adapters.CalendarAdapter;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.utility.CalendarUtils;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;

public class WeekViewFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView, assignmentRecyclerView;
    private Button prevWeekBtn, nextWeekBtn, addAssignmentBtn, dayViewBtn, monthViewBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weekly, container, false);
        initializeViews(rootView);
        setWeekView();
        setupListeners();
        return rootView;
    }

    private void initializeViews(View view) {
        monthYearText = view.findViewById(R.id.monthYearTV);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        assignmentRecyclerView = view.findViewById(R.id.week_assignments_rv);

        prevWeekBtn = view.findViewById(R.id.prevWeekBtn);
        nextWeekBtn = view.findViewById(R.id.nextWeekBtn);
        addAssignmentBtn = view.findViewById(R.id.new_assignment_button);
        dayViewBtn = view.findViewById(R.id.day_view_button);
        monthViewBtn = view.findViewById(R.id.month_view_button);
    }

    private void setupListeners() {
       prevWeekBtn.setOnClickListener(v -> previousWeekAction());
       nextWeekBtn.setOnClickListener(v -> nextWeekAction());
       addAssignmentBtn.setOnClickListener(v -> switchFragment(new AddAssignmentFragment()));
       dayViewBtn.setOnClickListener(v -> switchFragment(new DailyViewFragment()));
       monthViewBtn.setOnClickListener(v -> switchFragment(new HomeFragment()));
    }

    private void switchFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setWeekView() {
        monthYearText.setText(CalendarUtils.formattedMonth(CalendarUtils.selectedDate));

        ArrayList<Calendar> weekDays = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);
        calendarRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        CalendarAdapter calendarAdapter = new CalendarAdapter(requireContext(), weekDays, this);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setWeeklyAssignmentsAdapter();
    }

    private void setWeeklyAssignmentsAdapter() {
        AssignmentManager assignmentManager = AssignmentManager.getInstance(requireContext());
        ArrayList<Assignment> weeklyAssignments = new ArrayList<>();

        Calendar weekStart = (Calendar) CalendarUtils.selectedDate.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, weekStart.getFirstDayOfWeek());
        Calendar weekEnd = (Calendar) weekStart.clone();
        weekEnd.add(Calendar.DAY_OF_MONTH, 6);

        for (Assignment  assignment : assignmentManager.getAssignments()){
            Calendar dueDate = Calendar.getInstance();
            dueDate.setTime(assignment.getDueDate());

            if(!dueDate.before(weekStart) && !dueDate.after(weekEnd)) {
                weeklyAssignments.add(assignment);
            }
        }

        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        AssignmentAdapter assignmentAdapter = new AssignmentAdapter(requireContext(), weeklyAssignments);
        assignmentRecyclerView.setAdapter(assignmentAdapter);
    }

    private void previousWeekAction() {
        CalendarUtils.selectedDate.add(Calendar.WEEK_OF_YEAR, -1);
        setWeekView();
    }

    private void nextWeekAction() {
        CalendarUtils.selectedDate.add(Calendar.WEEK_OF_YEAR, 1);
        setWeekView();
    }

    @Override
    public void onItemClick(int position, Calendar date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }
}
