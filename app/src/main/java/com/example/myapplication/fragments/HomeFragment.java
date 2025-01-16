package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AssignmentAdapter;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    AssignmentAdapter assignmentAdapter;
    AssignmentManager assignmentManager;
    RecyclerView assignmentsRecyclerView;
    CalendarView dueDateCalendar;
    View rootView;
    List<Assignment> assignments;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        assignmentManager = AssignmentManager.getInstance(requireContext());
        assignments = assignmentManager.getAssignments();
        
        initializeViews();
        setupRecyclerView();

        return rootView;
    }

    private void initializeViews() {
        assignmentsRecyclerView = rootView.findViewById(R.id.assignments_rv);
        dueDateCalendar = rootView.findViewById(R.id.due_date_cv);
    }

    private void setupRecyclerView() {
        if(assignments == null) {
            assignments = new ArrayList<>();
        }

        assignmentAdapter = new AssignmentAdapter(requireContext(), assignments);
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        assignmentsRecyclerView.setAdapter(assignmentAdapter);

        assignmentAdapter.updateData(assignmentManager.getAssignments());
    }
}
