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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapters.AssignmentAdapter;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.managers.CalendarUtils;
import com.example.myapplication.managers.EventManager;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Event;
import com.example.myapplication.adapters.HourAdapter;
import com.example.myapplication.models.HourEvent;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DailyViewFragment extends Fragment {
    private Button weekViewBtn, monthViewBtn, addEventBtn, prevDayBtn, nextDayBtn;
    private TextView monthDayText, dayOfWeekTV;
    private RecyclerView assignmentRecyclerView, hourlyRecyclerView;
    private EventManager eventManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        eventManager = new EventManager();
        initWidgets(view);
        setupListeners();
        setupRecyclerViews();
        setDayView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAssignmentAdapter();
    }

    private void initWidgets(View view) {
        addEventBtn = view.findViewById(R.id.add_event_button);
        weekViewBtn = view.findViewById(R.id.week_view_button);
        monthViewBtn = view.findViewById(R.id.month_view_button);
        prevDayBtn = view.findViewById(R.id.prevDayBtn);
        nextDayBtn = view.findViewById(R.id.nextDayBtn);
        monthDayText = view.findViewById(R.id.monthDayText);
        dayOfWeekTV = view.findViewById(R.id.dayOfWeekTV);
        assignmentRecyclerView = view.findViewById(R.id.assignments_view_rv);
        hourlyRecyclerView = view.findViewById(R.id.hourly_view_rv);
    }

    private void setupListeners(){
        weekViewBtn.setOnClickListener(v -> switchFragment(new WeekViewFragment()));
        monthViewBtn.setOnClickListener(v -> switchFragment(new HomeFragment()));
        addEventBtn.setOnClickListener(v -> switchFragment(new AddAssignmentFragment()));

        prevDayBtn.setOnClickListener(v -> {
            CalendarUtils.selectedDate.add(Calendar.DAY_OF_MONTH, -1);
            setDayView();
        });

        nextDayBtn.setOnClickListener(v -> {
            CalendarUtils.selectedDate.add(Calendar.DAY_OF_MONTH, 1);
            setDayView();
        });
    }

    private void setupRecyclerViews() {
        hourlyRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        assignmentRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void switchFragment(Fragment fragment){
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setDayView() {
        monthDayText.setText(CalendarUtils.formattedMonth(CalendarUtils.selectedDate));
        String dayOfWeek = CalendarUtils.selectedDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
        setAssignmentAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(requireContext(), hourEventList());
        hourlyRecyclerView.setAdapter(hourAdapter);
    }


    private void setAssignmentAdapter() {
        ArrayList<Assignment> assignmentsToday = getAssignmentForSelectedDay();
        AssignmentAdapter assignmentAdapter = new AssignmentAdapter(requireContext(), assignmentsToday);
        assignmentRecyclerView.setAdapter(assignmentAdapter);
    }

    private ArrayList<Assignment> getAssignmentForSelectedDay() {
        ArrayList<Assignment> assignmentsDueToday = new ArrayList<>();
        AssignmentManager assignmentManager = AssignmentManager.getInstance(requireContext());
        for(Assignment assignment : assignmentManager.getAssignments()){
            Calendar assignmentCal = Calendar.getInstance();
            assignmentCal.setTime(assignment.getDueDate());

            if(assignmentCal.get(Calendar.YEAR) == CalendarUtils.selectedDate.get(Calendar.YEAR) &&
               assignmentCal.get(Calendar.DAY_OF_YEAR) == CalendarUtils.selectedDate.get(Calendar.DAY_OF_YEAR)) {
                    assignmentsDueToday.add(assignment);
            }
        }

        return assignmentsDueToday;
    }

    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> list = new ArrayList<>();

        for(int hour = 0; hour < 24; hour++){
            Calendar time = (Calendar) CalendarUtils.selectedDate.clone();
            time.set(Calendar.HOUR_OF_DAY, hour);
            time.set(Calendar.MINUTE, 0);
            time.set(Calendar.SECOND, 0);

            ArrayList<Event> events = new ArrayList<>(eventManager.getEventsForDateAndTime(CalendarUtils.selectedDate.getTime(), time));
            list.add(new HourEvent(time, events));
        }

        return list;
    }
}
