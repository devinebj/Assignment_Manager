package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    ArrayList<String> assignments = new ArrayList<>();
    ArrayAdapter<String> assignmentsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container, false);
        ListView listView = rootView.findViewById(R.id.AssignmentList);
        CalendarView calendarView = rootView.findViewById(R.id.calendarView);
        rootView.findViewById(R.id.calendarView);

        for(int i = 0; i < 26; i++){
            assignments.add("Assignment " + ( i+1 ));
        }

        assignmentsAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, assignments);
        listView.setAdapter(assignmentsAdapter);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            assignmentsAdapter.add(selectedDate);
        });

        return rootView;
    }
}
