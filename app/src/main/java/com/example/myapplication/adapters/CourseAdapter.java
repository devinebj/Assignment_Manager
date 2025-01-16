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
import com.example.myapplication.managers.CourseManager;
import com.example.myapplication.models.Course;

import java.util.List;

public class CourseAdapter extends BaseAdapter<Course, CourseAdapter.CourseViewHolder> {

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, courses);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_course, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course = items.get(position);
        holder.courseName.setText(course.getName());

        holder.deleteButton.setOnClickListener(v -> removeItem(holder.getAdapterPosition()));
    }

    @Override
    protected void onItemRemoved(Course item) {
        CourseManager.getInstance(context).removeCourse(item);
    }

    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView courseName;
        Button deleteButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_cell_tv);
            deleteButton = itemView.findViewById(R.id.course_cell_button);
        }
    }
}
