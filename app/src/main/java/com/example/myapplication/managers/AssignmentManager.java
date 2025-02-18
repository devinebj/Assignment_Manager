package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Singleton class that manages assignments.
 */
public class AssignmentManager {
    private static AssignmentManager instance;

    private final Context context;
    private ArrayList<Assignment> assignments;

    /**
     * Private constructor that initializes the AssignmentManager by loading assignments.
     *
     * @param context the application context.
     */
    private AssignmentManager(Context context) {
        this.context = context.getApplicationContext();
        this.assignments = SettingsManager.getInstance(context).loadAssignments();
    }

    /**
     * Returns the singleton instance of AssignmentManager, initializing it if necessary.
     *
     * @param context the context used for initialization.
     * @return the AssignmentManager instance.
     */
    public static synchronized AssignmentManager getInstance(Context context) {
        if (instance == null) {
            instance = new AssignmentManager(context);
        }
        return instance;
    }

    /**
     * Returns the singleton instance of AssignmentManager.
     * <p>
     * This method will throw an {@link IllegalStateException} if the manager has not been initialized
     * using {@link #getInstance(Context)} first.
     * </p>
     *
     * @return the AssignmentManager instance.
     * @throws IllegalStateException if the instance is not initialized.
     */
    public static synchronized AssignmentManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AssignmentManager is not initialized. Call getInstance(Context) first.");
        }
        return instance;
    }

    /**
     * Retrieves a copy of the current list of assignments.
     *
     * @return a new list containing all assignments.
     */
    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    /**
     * Adds the specified assignment if it is not null and not already present,
     * then saves the updated list.
     *
     * @param assignment the assignment to add.
     */
    public void addAssignment(Assignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
            saveAssignments();
        }
    }

    /**
     * Removes the specified assignment if it exists, then saves the updated list.
     *
     * @param assignment the assignment to remove.
     */
    public void removeAssignment(Assignment assignment) {
        if (assignment != null && assignments.contains(assignment)) {
            assignments.remove(assignment);
            saveAssignments();
        }
    }

    /**
     * Returns a list of assignments with due dates after the current date.
     *
     * @return a list of upcoming assignments.
     */
    public ArrayList<Assignment> getUpcomingAssignments() {
        Date now = new Date();
        ArrayList<Assignment> upcomingAssignmentList = new ArrayList<>(assignments.stream()
                .filter(assignment -> assignment.getDueDate().after(now))
                .collect(Collectors.toList()));

        return upcomingAssignmentList;
    }

    /**
     * Returns a list of assignments with due dates before the current date.
     *
     * @return a list of past due assignments.
     */
    public ArrayList<Assignment> getPastDueAssignments() {
        Date now = new Date();
        ArrayList<Assignment> pastAssignmentList = new ArrayList<>(assignments.stream()
                .filter(assignment -> assignment.getDueDate().before(now))
                .collect(Collectors.toList()));

        return pastAssignmentList;
    }

    /**
     * Persists the current list of assignments using the SettingsManager.
     */
    public void saveAssignments() {
        SettingsManager.getInstance(context).saveAssignments(assignments);
    }
}
