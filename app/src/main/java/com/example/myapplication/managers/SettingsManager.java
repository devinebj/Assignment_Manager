package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.AppSettings;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;

import java.util.List;

public class SettingsManager {
    private static final String FILE_NAME = "app_settings.json";

    public static void saveSettings(Context context, int daysToNotify){
        List<Assignment> assignments = AssignmentManager.getInstance(context).getAssignments();
        List<Course> courses = CourseManager.getInstance(context).getCourses();

        AppSettings appSettings = new AppSettings(assignments, courses, daysToNotify);

        FileManager.saveToFile(context, FILE_NAME, appSettings);
    }

    public static void loadSettings(Context context){
        AppSettings settings = FileManager.loadFromFile(context, FILE_NAME, AppSettings.class);
    }
}
