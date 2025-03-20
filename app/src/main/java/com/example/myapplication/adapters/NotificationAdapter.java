package com.example.myapplication.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
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
        View view = LayoutInflater.from(context).inflate(R.layout.cell_assignment, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Assignment assignment = items.get(position);

        // Format the assignment's due date.
        String dueDateString = DATE_FORMAT.format(assignment.getDueDate());

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText(context.getString(R.string.notification_points_possible, assignment.getPointsPossible()));
        holder.gradeWeight.setText(context.getString(R.string.notification_grade_weight, (float)assignment.getGradeWeight()));
        holder.dueDate.setText(context.getString(R.string.notification_due_date, assignment.getDueDate()));

        holder.completeButton.setOnClickListener(v -> {
           int pos = holder.getAdapterPosition();

           if(pos != RecyclerView.NO_POSITION) {
               removeItem(pos);
           }
        });
    }

    @Override
    protected void onItemRemoved(Assignment item) {
        AssignmentManager.getInstance(context).removeAssignment(item);
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
        Button completeButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            pointsPossible = itemView.findViewById(R.id.cell_assignment_points_tv);
            gradeWeight = itemView.findViewById(R.id.cell_assignment_weight_tv);
            dueDate = itemView.findViewById(R.id.cell_assignment_due_date_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
        }
    }
}
