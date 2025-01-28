package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;

import java.util.List;

public class NotificationFragment extends Fragment {
    AssignmentManager assignmentManager;
    RecyclerView upcomingRecyclerView;
    RecyclerView pastDueRecyclerView;
    TextView upcomingEmptyPlaceHolder;
    TextView pastDueEmptyPlaceHolder;
    View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        assignmentManager = AssignmentManager.getInstance(requireContext());

        initializeViews();
        setupRecyclerViews();

        return rootView;
    }

    private void initializeViews() {
        upcomingRecyclerView = rootView.findViewById(R.id.coming_up_rv);
        upcomingEmptyPlaceHolder = rootView.findViewById(R.id.coming_up_placeholder);
        pastDueRecyclerView = rootView.findViewById(R.id.past_due_rv);
        pastDueEmptyPlaceHolder = rootView.findViewById(R.id.past_due_placeholder);
    }

    private void setupRecyclerViews() {
        List<Assignment> upcomingAssignments = assignmentManager.getUpcomingAssignments();
        List<Assignment> pastDueAssignments = assignmentManager.getPastDueAssignments();

        togglePlaceholderVisibility(upcomingRecyclerView, upcomingEmptyPlaceHolder, upcomingAssignments);
        togglePlaceholderVisibility(pastDueRecyclerView, pastDueEmptyPlaceHolder, pastDueAssignments);
    }

    private void togglePlaceholderVisibility(RecyclerView recyclerView, TextView placeholder, List<Assignment> assignments) {
        if (assignments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            placeholder.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.GONE);
        }
    }
}
