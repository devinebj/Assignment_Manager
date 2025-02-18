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

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for displaying a list of courses.
 */
public class CourseAdapter extends BaseAdapter<Course, CourseAdapter.CourseViewHolder> {

    /**
     * Constructor for CourseAdapter.
     *
     * @param context the context used for inflating views.
     * @param courses the list of courses to display.
     */
    public CourseAdapter(Context context, ArrayList<Course> courses) {
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

        // Set a click listener on the delete button to remove the course.
        holder.deleteButton.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                removeItem(pos);
            }
        });
    }

    @Override
    protected void onItemRemoved(Course item) {
        // Remove the course from the CourseManager.
        CourseManager.getInstance(context).removeCourse(item);
    }

    /**
     * ViewHolder class for CourseAdapter.
     */
    public static class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;
        private final Button deleteButton;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseName = itemView.findViewById(R.id.course_cell_tv);
            deleteButton = itemView.findViewById(R.id.course_cell_button);
        }
    }
}
