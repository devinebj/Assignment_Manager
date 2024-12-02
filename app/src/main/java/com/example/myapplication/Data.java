package com.example.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;

public class Data {
    private static Data instance = null;
    private static Context context;
    private List<Course> courses;

    private Data(Context context) {
        this.context = context.getApplicationContext();
        this.courses = FileManager.loadCoursesFromFile(this.context);
    }

    public static synchronized Data getInstance(Context context){
        if(instance == null) {
            instance = new Data(context);
        }

        return instance;
    }

    public static synchronized Data getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Data not initialized. Call getInstance(Context context) first");
        }

        return instance;
    }

    public List<Course> getCourses() {
        return FileManager.loadCoursesFromFile(context);
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
        FileManager.saveCoursesToFile(context, courses);
    }
}
