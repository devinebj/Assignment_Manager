package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.AppSettings;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private static SettingsManager instance;
    private final Context context;
    private AppSettings appSettings;

    private SettingsManager(Context context) {
        this.context = context.getApplicationContext();
        this.appSettings = FileManager.loadFromFile(context);
    }

    public static synchronized SettingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsManager(context);
        }
        return instance;
    }

    private void saveSettings() {
        FileManager.saveToFile(context, appSettings);
    }

    public int loadDaysToNotify() {
        return appSettings.getDaysToNotify();
    }

    public void saveDaysToNotify(int days) {
        appSettings.setDaysToNotify(days);
        saveSettings();
    }

    public List<Course> loadCourses() {
        return appSettings.getCourses();
    }

    public void saveCourses(List<Course> courses) {
        appSettings.setCourses(courses);
        saveSettings();
    }

    public List<Assignment> loadAssignments() {
        return appSettings.getAssignments();
    }

    public void saveAssignments(List<Assignment> assignments) {
        appSettings.setAssignments(assignments);
        saveSettings();
    }
}
