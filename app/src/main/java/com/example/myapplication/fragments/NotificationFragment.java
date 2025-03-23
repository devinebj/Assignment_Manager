package com.example.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapters.NotificationAdapter;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    private AssignmentManager assignmentManager;
    private RecyclerView upcomingRecyclerView, pastDueRecyclerView;
    private TextView upcomingEmptyPlaceHolder, pastDueEmptyPlaceHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);
        assignmentManager = AssignmentManager.getInstance(requireContext());

        initializeViews(rootView);
        setupRecyclerViews();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAssignmentAdapter();
    }

    private void initializeViews(View rootView) {
        upcomingRecyclerView = rootView.findViewById(R.id.coming_up_rv);
        upcomingEmptyPlaceHolder = rootView.findViewById(R.id.coming_up_placeholder);
        pastDueRecyclerView = rootView.findViewById(R.id.past_due_rv);
        pastDueEmptyPlaceHolder = rootView.findViewById(R.id.past_due_placeholder);
    }

    private void setupRecyclerViews() {
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        pastDueRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void updateAssignmentAdapter() {
        List<Assignment> upcomingAssignments = assignmentManager.getUpcomingAssignments();
        List<Assignment> pastDueAssignments = assignmentManager.getPastDueAssignments();

        setupRecyclerView(upcomingRecyclerView, upcomingEmptyPlaceHolder, upcomingAssignments);
        setupRecyclerView(pastDueRecyclerView, pastDueEmptyPlaceHolder, pastDueAssignments);
    }

    private void setupRecyclerView(RecyclerView recyclerView, TextView placeHolder, List<Assignment> assignments) {
        if (assignments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            placeHolder.setVisibility(View.VISIBLE);
        } else {
            placeHolder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            NotificationAdapter adapter = new NotificationAdapter(requireContext(), new ArrayList<>(assignments));
            recyclerView.setAdapter(adapter);
        }
    }
}
