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

import java.util.List;

public class AssignmentAdapter extends BaseAdapter<Assignment, AssignmentAdapter.AssignmentViewHolder> {

    public AssignmentAdapter(Context context, List<Assignment> assignments) {
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

        holder.completeButton.setOnClickListener(v -> removeItem(holder.getAdapterPosition()));
    }

    @Override
    protected void onItemRemoved(Assignment item) {
        AssignmentManager.getInstance(context).removeAssignment(item);
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentName;
        TextView courseName;
        Button completeButton;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.cell_assignment_name_tv);
            courseName = itemView.findViewById(R.id.cell_assignment_course_tv);
            completeButton = itemView.findViewById(R.id.cell_assignment_complete_button);
        }
    }
}

