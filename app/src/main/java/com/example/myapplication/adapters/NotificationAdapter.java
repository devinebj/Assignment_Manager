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
import com.google.firebase.crashlytics.buildtools.reloc.javax.annotation.Nonnull;

import java.text.Format;
import java.util.List;

public class NotificationAdapter extends BaseAdapter<Assignment, NotificationAdapter.NotificationViewHolder> {
    public NotificationAdapter(Context context, List<Assignment> assignments){
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
        Format formatter = new SimpleDateFormat("MM/dd/yyyy");
        String dueDateString = formatter.format(holder.dueDate);

        holder.assignmentName.setText(assignment.getName());
        holder.courseName.setText(assignment.getCourse());
        holder.pointsPossible.setText("Points: " + assignment.getPointsPossible());
        holder.gradeWeight.setText("Weight: " + assignment.getGradeWeight() + "%");
        holder.dueDate.setText("Due: " + dueDateString);
    }

    @Override
    protected void onItemRemoved(Assignment item){

    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentName, courseName, pointsPossible, gradeWeight, dueDate;

        public NotificationViewHolder(@NonNull View itemView){
            super(itemView);
            assignmentName = itemView.findViewById(R.id.tv_assignment_name);
            courseName = itemView.findViewById(R.id.tv_course_name);
            pointsPossible = itemView.findViewById(R.id.tv_points_possible);
            gradeWeight = itemView.findViewById(R.id.tv_grade_weight);
            dueDate = itemView.findViewById(R.id.tv_due_date);
        }
    }
}
