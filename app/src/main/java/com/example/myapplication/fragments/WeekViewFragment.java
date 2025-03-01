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
import com.example.myapplication.adapters.CalendarAdapter;
import com.example.myapplication.managers.CalendarUtils;
import com.example.myapplication.managers.EventManager;
import com.example.myapplication.models.Event;
import com.example.myapplication.adapters.EventAdapter;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WeekViewFragment extends Fragment implements CalendarAdapter.OnItemListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView, eventRecyclerView;
    private Button prevWeekBtn, nextWeekBtn, newEventBtn, dayViewBtn, monthViewBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly, container, false);
        initWidgets(view);
        setWeekView();
        setupListeners();
        return view;
    }

    private void initWidgets(View view) {
        monthYearText = view.findViewById(R.id.monthYearTV);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerView);
        eventRecyclerView = view.findViewById(R.id.eventRecyclerView);

        prevWeekBtn = view.findViewById(R.id.prevWeekBtn);
        nextWeekBtn = view.findViewById(R.id.nextWeekBtn);
        newEventBtn = view.findViewById(R.id.newEventBtn);
        dayViewBtn = view.findViewById(R.id.day_view_button);
        monthViewBtn = view.findViewById(R.id.month_view_button);
    }

    private void setupListeners() {
       prevWeekBtn.setOnClickListener(v -> previousWeekAction());
       nextWeekBtn.setOnClickListener(v -> nextWeekAction());
       newEventBtn.setOnClickListener(v -> newEventAction());
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
        monthYearText.setText(CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        ArrayList<Calendar> weekDays = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate);

        calendarRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 7));
        CalendarAdapter calendarAdapter = new CalendarAdapter(requireContext(), weekDays, this);
        calendarRecyclerView.setAdapter(calendarAdapter);

        setEventAdapter();
    }

    private void setEventAdapter() {
        EventManager eventManager = new EventManager();
        Date selectedDate = CalendarUtils.selectedDate.getTime();

        ArrayList<Event> dailyEvents = new ArrayList<>(eventManager.getEventsForDate(selectedDate));

        eventRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        EventAdapter eventAdapter = new EventAdapter(requireContext(), dailyEvents);
        eventRecyclerView.setAdapter(eventAdapter);
    }

    private void previousWeekAction() {
        CalendarUtils.selectedDate.add(Calendar.WEEK_OF_YEAR, -1);
        setWeekView();
    }

    private void nextWeekAction() {
        CalendarUtils.selectedDate.add(Calendar.WEEK_OF_YEAR, 1);
        setWeekView();
    }

    private void newEventAction(){
        // TODO: Implement event creation logic
    }

    @Override
    public void onItemClick(int position, Calendar date) {
        CalendarUtils.selectedDate = date;
        setWeekView();
    }
}
