package com.example.myapplication.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Adapter for displaying assignment notifications in a RecyclerView.
 */
public class NotificationAdapter extends BaseAdapter<Assignment, NotificationAdapter.NotificationViewHolder> {

    // Static date formatter for displaying due dates in "MM/dd/yyyy" format.
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    /**
     * Constructor for the NotificationAdapter.
     *
     * @param context     the context for inflating layouts.
     * @param assignments the list of assignments to display.
     */
    public NotificationAdapter(Context context, ArrayList<Assignment> assignments) {
        super(context, assignments);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_notifications, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Assignment assignment = items.get(position);

        // Format the assignment's due date.
        String dueDateString = DATE_FORMAT.format(assignment.getDueDate());

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText("Points: " + assignment.getPointsPossible());
        holder.gradeWeight.setText("Weight: " + assignment.getGradeWeight() + "%");
        holder.dueDate.setText("Due: " + dueDateString);
    }

    @Override
    protected void onItemRemoved(Assignment item) {
        // Optionally handle item removal if needed.
    }

    /**
     * ViewHolder class for the NotificationAdapter.
     */
    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentName;
        TextView courseName;
        TextView pointsPossible;
        TextView gradeWeight;
        TextView dueDate;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.tv_assignment_name);
            courseName = itemView.findViewById(R.id.tv_course_name);
            pointsPossible = itemView.findViewById(R.id.tv_points_possible);
            gradeWeight = itemView.findViewById(R.id.tv_grade_weight);
            dueDate = itemView.findViewById(R.id.tv_due_date);
        }
    }
}
