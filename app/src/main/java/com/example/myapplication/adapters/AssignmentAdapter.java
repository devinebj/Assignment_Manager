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

public class AssignmentAdapter extends BaseAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder> {

    private static final SimpleDateFormat DISPLAY_DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private static final SimpleDateFormat EDIT_DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    private static final SimpleDateFormat DISPLAY_TIME_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static final SimpleDateFormat EDIT_TIME_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

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

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText(context.getString(R.string.notification_points_possible));
        holder.gradeWeight.setText(context.getString(R.string.notification_grade_weight));

        if(assignment.getDueDate() != null) {
            holder.dueDate.setText("Due: " + DISPLAY_DATE_FORMAT.format(assignment.getDueDate()));
        } else {
            holder.dueDate.setText("Due: N/A");
        }

        if(assignment.getDueTime() != null) {
            holder.dueTime.setText("Time: " + DISPLAY_TIME_FORMAT.format(assignment.getDueTime()));
        } else {
            holder.dueTime.setText("Time: N/A");
        }


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
           bundle.putString("dueDate", EDIT_DATE_FORMAT.format(assignment.getDueDate()));
           bundle.putString("dueTime", EDIT_TIME_FORMAT.format(assignment.getDueTime()));

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

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentName;
        TextView courseName;
        TextView dueDate;
        TextView dueTime;
        TextView gradeWeight;
        TextView pointsPossible;
        Button completeButton;
        Button editButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            dueDate = itemView.findViewById(R.id.cell_assignment_due_date_tv);
            dueTime  = itemView.findViewById(R.id.cell_assignment_due_time_tv);
            gradeWeight = itemView.findViewById(R.id.cell_assignment_weight_tv);
            pointsPossible = itemView.findViewById(R.id.cell_assignment_points_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
            editButton = itemView.findViewById(R.id.cell_assignment_edit_button);
        }
    }
}
