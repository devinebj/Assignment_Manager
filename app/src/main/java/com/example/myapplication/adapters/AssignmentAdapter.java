package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;

import java.util.ArrayList;

/**
 * Adapter for displaying and managing a list of assignments.
 */
public class AssignmentAdapter extends BaseAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder> {

    /**
     * Constructor for AssignmentAdapter.
     *
     * @param context     the context for inflating views.
     * @param assignments the list of assignments to display.
     */
    public AssignmentAdapter(Context context, ArrayList<Assignment> assignments) {
        super(context, assignments);
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment assignment = items.get(position);

        // Populate views with assignment data
        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());

        // Handle complete button click to remove assignment
        holder.completeButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                removeItem(pos);
            }
        });
    }

    /**
     * Handles item removal from persistent storage.
     *
     * @param item the assignment being removed.
     */
    @Override
    protected void onItemRemoved(Assignment item) {
        AssignmentManager.getInstance(context).removeAssignment(item);
    }

    /**
     * ViewHolder for assignment items.
     */
    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assignmentName;
        private final TextView courseName;
        private final Button completeButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
        }
    }
}
