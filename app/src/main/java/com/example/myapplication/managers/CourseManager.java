package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.Course;

import java.util.ArrayList;

/**
 * A singleton class that manages the list of courses.
 */
public class CourseManager {
    private static CourseManager instance;
    private final Context context;
    private ArrayList<Course> courses;

    /**
     * Private constructor that initializes courses from the SettingsManager.
     *
     * @param context the application context.
     */
    private CourseManager(Context context) {
        this.context = context.getApplicationContext();
        this.courses = SettingsManager.getInstance(context).loadCourses();
    }

    /**
     * Returns the singleton instance of CourseManager, initializing it if necessary.
     *
     * @param context a Context used to initialize the manager.
     * @return the singleton instance of CourseManager.
     */
    public static synchronized CourseManager getInstance(Context context) {
        if (instance == null) {
            instance = new CourseManager(context);
        }
        return instance;
    }

    /**
     * Returns the singleton instance of CourseManager.
     * <p>
     * This method will throw an IllegalStateException if the manager has not been initialized
     * by calling {@link #getInstance(Context)} first.
     * </p>
     *
     * @return the singleton instance of CourseManager.
     * @throws IllegalStateException if the instance has not been initialized.
     */
    public static synchronized CourseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("CourseManager is not initialized. Call getInstance(Context context) first.");
        }
        return instance;
    }

    /**
     * Retrieves the list of courses.
     *
     * @return the list of courses.
     */
    public ArrayList<Course> getCourses() {
        return courses;
    }

    /**
     * Adds a course to the list if it is not null and not already present,
     * then saves the updated list.
     *
     * @param course the course to add.
     */
    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            saveCourses();
        }
    }

    /**
     * Removes the specified course from the list if it exists, then saves the updated list.
     *
     * @param course the course to remove.
     */
    public void removeCourse(Course course) {
        if (course != null && courses.contains(course)) {
            courses.remove(course);
            saveCourses();
        }
    }

    /**
     * Persists the current list of courses using the SettingsManager.
     */
    public void saveCourses() {
        SettingsManager.getInstance(context).saveCourses(courses);
    }
}
