package com.example.myapplication.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.fragments.AddAssignmentFragment;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.Locale;

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

        //Format due date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String dueDateStr = "Due: " + dateFormat.format(assignment.getDueDate());
        holder.dueDate.setText(dueDateStr);

        //Set points and weight
        holder.pointsPossible.setText(context.getString(R.string.notification_points_possible));
        holder.gradeWeight.setText(context.getString(R.string.notification_grade_weight));

        // Handle complete button click to remove assignment
        holder.completeButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                removeItem(pos);
            }
        });

        holder.editButton.setOnClickListener(v -> {
           Bundle bundle = new Bundle();
           bundle.putString("mode", "edit");
           bundle.putString("courseName", assignment.getCourse());
           bundle.putString("assignmentName", assignment.getName());
           bundle.putInt("pointsPossible", assignment.getPointsPossible());
           bundle.putInt("gradeWeight", assignment.getGradeWeight());
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
           bundle.putString("dueDate", simpleDateFormat.format(assignment.getDueDate()));

           AddAssignmentFragment fragment = new AddAssignmentFragment();
           fragment.setArguments(bundle);

            ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
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
        private final TextView dueDate;
        private final TextView gradeWeight;
        private final TextView pointsPossible;
        private final Button completeButton;
        private final Button editButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            gradeWeight = itemView.findViewById(R.id.cell_assignment_weight_tv);
            pointsPossible = itemView.findViewById(R.id.cell_assignment_points_tv);
            dueDate = itemView.findViewById(R.id.cell_assignment_due_date_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
            editButton = itemView.findViewById(R.id.cell_assignment_edit_button);
        }
    }
}
