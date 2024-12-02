package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> implements Data.IObserver {
    private List<Course> courses;
    private Context context;
    private OnCourseClickListener listener;

    // Constructor
    public CourseAdapter(Context context, List<Course> courses, OnCourseClickListener listener) {
        this.context = context;
        this.courses = courses;
        this.listener = listener;

        Data.getInstance().registerObserver(this);
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_cell, parent, false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (position < courses.size()) {
            Course course = courses.get(position);
            holder.courseName.setText(course.getName());

            holder.deleteButton.setOnClickListener(v -> {
                if(RecyclerView.NO_POSITION != position){
                    removeItem(position);
                }
            });

            holder.itemView.setOnClickListener(v -> listener.onCourseClick(course));
        } else {
            Log.e("CourseAdapter", "Invalid position in onBindViewHolder" + position);
        }
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public void updateData(List<Course> newCourses){
        this.courses.clear();
        this.courses.addAll(newCourses);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        if (position >= 0 && position < courses.size()) {
            courses.remove(position);
            notifyItemRemoved(position);

            List<Course> sharedCourses = Data.getInstance().getCourses();
            if(position < sharedCourses.size()) {
                sharedCourses.remove(position);
                Data.getInstance().notifyRecyclerDataChanged();
            }
        } else{
            Log.e("CourseAdapter", "Invalid position: " + position + ", List size: " + courses.size());
        }
    }

    @Override
    public void onRecyclerDataChanged(List<Course> courses) {
        updateData(courses);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        Data.getInstance().unregisterObserver(this);
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

    public interface OnCourseClickListener {
        void onCourseClick(Course course);
    }
}
