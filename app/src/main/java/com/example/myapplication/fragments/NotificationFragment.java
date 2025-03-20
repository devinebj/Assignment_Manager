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

/**
 * Fragment that displays notifications for assignments, including upcoming and past due items.
 */
public class NotificationFragment extends Fragment {

    private AssignmentManager assignmentManager;
    private RecyclerView upcomingRecyclerView;
    private RecyclerView pastDueRecyclerView;
    private TextView upcomingEmptyPlaceHolder;
    private TextView pastDueEmptyPlaceHolder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Initialize the assignment manager.
        assignmentManager = AssignmentManager.getInstance(requireContext());

        // Initialize UI components and setup recycler views.
        initializeViews(rootView);
        setupRecyclerViews();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setAssignmentAdapter();
    }

    /**
     * Finds and initializes UI components from the inflated view.
     *
     * @param rootView the root view of the fragment.
     */
    private void initializeViews(View rootView) {
        upcomingRecyclerView = rootView.findViewById(R.id.coming_up_rv);
        upcomingEmptyPlaceHolder = rootView.findViewById(R.id.coming_up_placeholder);
        pastDueRecyclerView = rootView.findViewById(R.id.past_due_rv);
        pastDueEmptyPlaceHolder = rootView.findViewById(R.id.past_due_placeholder);
    }

    /**
     * Retrieves assignments from the AssignmentManager and configures the recycler views.
     */
    private void setupRecyclerViews() {
        ArrayList<Assignment> upcomingAssignments = assignmentManager.getUpcomingAssignments();
        ArrayList<Assignment> pastDueAssignments = assignmentManager.getPastDueAssignments();

        setupRecyclerView(upcomingRecyclerView, upcomingEmptyPlaceHolder, upcomingAssignments);
        setupRecyclerView(pastDueRecyclerView, pastDueEmptyPlaceHolder, pastDueAssignments);
    }

    /**
     * Configures a recycler view to display assignments. If the list of assignments is empty,
     * the recycler view is hidden and a placeholder is shown.
     *
     * @param recyclerView the recycler view to configure.
     * @param placeHolder  the TextView to display when there are no assignments.
     * @param assignments  the list of assignments to display.
     */
    private void setupRecyclerView(RecyclerView recyclerView, TextView placeHolder, ArrayList<Assignment> assignments) {
        if (assignments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            placeHolder.setVisibility(View.VISIBLE);
        } else {
            placeHolder.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setAdapter(new NotificationAdapter(requireContext(), assignments));
        }
    }

    private void setAssignmentAdapter() {
        ArrayList<Assignment> upcomingAssignments = assignmentManager.getUpcomingAssignments();
        ArrayList<Assignment> pastDueAssignments = assignmentManager.getPastDueAssignments();

        setupRecyclerView(upcomingRecyclerView, upcomingEmptyPlaceHolder, upcomingAssignments);
        setupRecyclerView(pastDueRecyclerView, pastDueEmptyPlaceHolder, pastDueAssignments);
    }
}
