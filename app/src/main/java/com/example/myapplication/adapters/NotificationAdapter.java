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

import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends BaseAdapter<Assignment, NotificationAdapter.NotificationViewHolder> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);

    public NotificationAdapter(Context context, List<Assignment> assignments) {
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
        String dueDateString = DATE_FORMAT.format(assignment.getDueDate());

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText(context.getString(R.string.notification_points_possible, assignment.getPointsPossible()));
        holder.gradeWeight.setText(context.getString(R.string.notification_grade_weight, (float)assignment.getGradeWeight()));
        holder.dueDate.setText(context.getString(R.string.notification_due_date, assignment.getDueDate()));

        //Complete Button
        holder.completeButton.setOnClickListener(v -> {
           int pos = holder.getAdapterPosition();

           if(pos != RecyclerView.NO_POSITION) {
               removeItem(pos);
           }
        });

        //Edit Button
        holder.editButton.setOnClickListener(v -> {
           Bundle bundle = new Bundle();
           bundle.putString("mode", "edit");
           bundle.putString("courseName", assignment.getCourse());
           bundle.putString("assignmentName", assignment.getName());
           bundle.putInt("pointsPossible", assignment.getPointsPossible());
           bundle.putInt("gradeWeight", assignment.getGradeWeight());

           if(assignment.getDueDate() != null){
               bundle.putString("dueDate", DATE_FORMAT.format(assignment.getDueDate()));
           }

           if(assignment.getDueTime() != null) {
               bundle.putString("dueTime", TIME_FORMAT.format(assignment.getDueTime()));
           }

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

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentName;
        TextView courseName;
        TextView pointsPossible;
        TextView gradeWeight;
        TextView dueDate;
        TextView dueTime;
        Button completeButton;
        Button editButton;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            pointsPossible = itemView.findViewById(R.id.cell_assignment_points_tv);
            gradeWeight = itemView.findViewById(R.id.cell_assignment_weight_tv);
            dueDate = itemView.findViewById(R.id.cell_assignment_due_date_tv);
            dueTime = itemView.findViewById(R.id.cell_assignment_due_time_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
            editButton = itemView.findViewById(R.id.cell_assignment_edit_button);
        }
    }
}
