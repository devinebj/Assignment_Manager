package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.AppSettings;
import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Course;

import java.util.ArrayList;

/**
 * Singleton class that manages application settings.
 * It provides methods to load and save settings such as the number of days
 * to notify, courses, and assignments using the FileManager.
 */
public class SettingsManager {

    private static SettingsManager instance;

    private final Context context;
    private AppSettings appSettings;

    /**
     * Private constructor that initializes the app settings from a file.
     *
     * @param context the application context
     */
    private SettingsManager(Context context) {
        // Use the application context to avoid leaking activity context.
        this.context = context.getApplicationContext();
        this.appSettings = FileManager.loadFromFile(context);
    }

    /**
     * Returns the singleton instance of SettingsManager.
     *
     * @param context the context used for initialization
     * @return the singleton instance of SettingsManager
     */
    public static synchronized SettingsManager getInstance(Context context) {
        if (instance == null) {
            instance = new SettingsManager(context);
        }
        return instance;
    }

    /**
     * Persists the current app settings to file.
     */
    private void saveSettings() {
        FileManager.saveToFile(context, appSettings);
    }

    /**
     * Loads the number of days to notify from the app settings.
     *
     * @return the number of days to notify
     */
    public int loadDaysToNotify() {
        return appSettings.getDaysToNotify();
    }

    /**
     * Saves the number of days to notify to the app settings.
     *
     * @param days the number of days to notify
     */
    public void saveDaysToNotify(int days) {
        appSettings.setDaysToNotify(days);
        saveSettings();
    }

    /**
     * Loads the list of courses from the app settings.
     *
     * @return a list of courses
     */
    public ArrayList<Course> loadCourses() {
        return appSettings.getCourses();
    }

    /**
     * Saves the list of courses to the app settings.
     *
     * @param courses the list of courses to save
     */
    public void saveCourses(ArrayList<Course> courses) {
        appSettings.setCourses(courses);
        saveSettings();
    }

    /**
     * Loads the list of assignments from the app settings.
     *
     * @return a list of assignments
     */
    public ArrayList<Assignment> loadAssignments() {
        return appSettings.getAssignments();
    }

    /**
     * Saves the list of assignments to the app settings.
     *
     * @param assignments the list of assignments to save
     */
    public void saveAssignments(ArrayList<Assignment> assignments) {
        appSettings.setAssignments(assignments);
        saveSettings();
    }
}
