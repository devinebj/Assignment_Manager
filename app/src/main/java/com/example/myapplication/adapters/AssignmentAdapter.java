package com.example.myapplication.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.fragments.AddAssignmentFragment;
import com.example.myapplication.managers.AssignmentManager;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.utility.CalendarUtils;

import java.util.ArrayList;
import java.util.Locale;

public class AssignmentAdapter extends BaseAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder> {

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

        // ------------------
        // BASIC ASSIGNMENT DETAILS
        // ------------------
        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText(
                context.getString(R.string.assignment_cell_points_possible, assignment.getPointsPossible())
        );
        holder.gradeWeight.setText(
                context.getString(R.string.assignment_cell_grade_weight, (float) assignment.getGradeWeight())
        );

        holder.dueDate.setText(
                context.getString(
                        R.string.assignment_cell_due_date,
                        assignment.getDueDate() != null
                                ? CalendarUtils.formattedDate(assignment.getDueDate())
                                : "N/A"
                )
        );
        holder.dueTime.setText(
                context.getString(
                        R.string.assignment_cell_due_time,
                        assignment.getDueTime() != null
                                ? CalendarUtils.formattedTime(assignment.getDueTime())
                                : "N/A"
                )
        );

        // ------------------
        // REAL-TIME TIMER DISPLAY
        // ------------------
        long displayTime = assignment.getTimeSpent();
        if (assignment.isTimerRunning()) {
            // Add the time since the timer was last started
            long now = System.currentTimeMillis();
            displayTime += (now - assignment.getLastStartTime());
        }
        holder.timerTextView.setText("Time: " + formatTime(displayTime));

        // Enable/disable buttons based on timer state
        holder.startTimerButton.setEnabled(!assignment.isTimerRunning());
        holder.stopTimerButton.setEnabled(assignment.isTimerRunning());

        // START TIMER
        holder.startTimerButton.setOnClickListener(v -> {
            if (!assignment.isTimerRunning()) {
                assignment.startTimer();
                AssignmentManager.getInstance(context).saveAssignments();
                notifyItemChanged(position);
            }
        });

        // STOP TIMER
        holder.stopTimerButton.setOnClickListener(v -> {
            if (assignment.isTimerRunning()) {
                assignment.stopTimer();
                AssignmentManager.getInstance(context).saveAssignments();
                notifyItemChanged(position);
            }
        });

        // ------------------
        // COMPLETE BUTTON
        // ------------------
        holder.completeButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                removeItem(pos);
            }
        });

        // ------------------
        // EDIT BUTTON
        // ------------------
        holder.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("mode", "edit");
            bundle.putString("courseName", assignment.getCourse());
            bundle.putString("assignmentName", assignment.getName());
            bundle.putInt("pointsPossible", assignment.getPointsPossible());
            bundle.putInt("gradeWeight", assignment.getGradeWeight());
            bundle.putString("dueDate", CalendarUtils.toStorageDate(assignment.getDueDate()));
            bundle.putString("dueTime", CalendarUtils.toStorageTime(assignment.getDueTime()));

            AddAssignmentFragment fragment = new AddAssignmentFragment();
            fragment.setArguments(bundle);

            ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    protected void onItemRemoved(Assignment item) {
        AssignmentManager.getInstance(context).removeAssignment(item);
    }

    /**
     * Convert total milliseconds into HH:MM:SS format
     */
    private String formatTime(long timeMillis) {
        long totalSeconds = timeMillis / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        // Basic UI
        TextView assignmentName;
        TextView courseName;
        TextView pointsPossible;
        TextView gradeWeight;
        TextView dueDate;
        TextView dueTime;

        // Timer UI
        TextView timerTextView;
        Button startTimerButton;
        Button stopTimerButton;

        // Existing
        Button completeButton;
        Button editButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            pointsPossible = itemView.findViewById(R.id.cell_assignment_points_tv);
            gradeWeight = itemView.findViewById(R.id.cell_assignment_weight_tv);
            dueDate = itemView.findViewById(R.id.cell_assignment_due_date_tv);
            dueTime = itemView.findViewById(R.id.cell_assignment_due_time_tv);

            // Timer references
            timerTextView = itemView.findViewById(R.id.timer_text_view);
            startTimerButton = itemView.findViewById(R.id.start_timer_button);
            stopTimerButton = itemView.findViewById(R.id.stop_timer_button);

            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
            editButton = itemView.findViewById(R.id.cell_assignment_edit_button);
        }
    }
}
