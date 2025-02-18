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

/**
 * Fragment that displays the home screen, including a list of assignments and a due date calendar.
 */
public class HomeFragment extends Fragment {

    private AssignmentAdapter assignmentAdapter;
    private AssignmentManager assignmentManager;
    private RecyclerView assignmentsRecyclerView;
    private CalendarView dueDateCalendar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout.
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the assignment manager.
        assignmentManager = AssignmentManager.getInstance(requireContext());

        // Find and initialize UI components.
        initializeViews(rootView);

        // Setup the RecyclerView with assignment data.
        setupRecyclerView();

        return rootView;
    }

    /**
     * Initializes UI components from the provided root view.
     *
     * @param rootView the inflated layout of the fragment.
     */
    private void initializeViews(View rootView) {
        assignmentsRecyclerView = rootView.findViewById(R.id.assignments_rv);
        dueDateCalendar = rootView.findViewById(R.id.due_date_cv);
    }

    /**
     * Configures the RecyclerView to display the list of assignments.
     * If the list is null or empty, it initializes an empty list.
     */
    private void setupRecyclerView() {
        // Retrieve assignments from the manager.
        ArrayList<Assignment> assignments = assignmentManager.getAssignments();
        if (assignments == null) {
            assignments = new ArrayList<>();
        }

        // Initialize the adapter with the assignment list.
        assignmentAdapter = new AssignmentAdapter(requireContext(), assignments);

        // Set the layout manager and adapter for the RecyclerView.
        assignmentsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        assignmentsRecyclerView.setAdapter(assignmentAdapter);

        // Optionally update the adapter data (if assignments may change).
        assignmentAdapter.updateData(assignmentManager.getAssignments());
    }
}
