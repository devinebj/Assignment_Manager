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
import com.example.myapplication.utility.CalendarUtils;

import java.util.List;
import java.util.Locale;

public class NotificationAdapter extends BaseAdapter<Assignment, NotificationAdapter.NotificationViewHolder> {

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
        String dueDateString = CalendarUtils.formattedDate(assignment.getDueDate());
        String dueTimeString = CalendarUtils.formattedTime(assignment.getDueTime());

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText(context.getString(R.string.assignment_cell_points_possible, assignment.getPointsPossible()));
        holder.gradeWeight.setText(context.getString(R.string.assignment_cell_grade_weight, (float)assignment.getGradeWeight()));
        holder.dueDate.setText(context.getString(R.string.assignment_cell_due_date, dueDateString));
        holder.dueTime.setText(context.getString(R.string.assignment_cell_due_time, dueTimeString));


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
               bundle.putString("dueDate", dueDateString);
           }

           if(assignment.getDueTime() != null) {
               bundle.putString("dueTime", dueTimeString);
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
