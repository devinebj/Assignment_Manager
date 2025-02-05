package com.example.myapplication.managers;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;

import com.example.myapplication.models.Course;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    private static CourseManager instance = null;
    private List<Course> courses;
    private static Context context;

    private CourseManager(Context context) {
        this.context = context.getApplicationContext();
        this.courses = SettingsManager.getInstance(context).loadCourses();
    }

    public static synchronized CourseManager getInstance(Context context){
        if(instance == null) {
            instance = new CourseManager(context);
        }

        return instance;
    }

    public static synchronized CourseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Data not initialized. Call getInstance(Context context) first");
        }

        return instance;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course){
        if (course != null && !courses.contains(course)){
            courses.add(course);
            saveCourses();
        }
    }

    public void removeCourse(Course course){
        if (course != null && courses.contains(course)){
            courses.remove(course);
            saveCourses();
        }
    }

    public void saveCourses(){
        SettingsManager.getInstance(context).saveCourses(courses);
    }
}
