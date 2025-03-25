package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.Assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AssignmentManager {
    private static AssignmentManager instance;
    private final Context context;
    private List<Assignment> assignments;

    private AssignmentManager(Context context) {
        this.context = context.getApplicationContext();
        this.assignments = SettingsManager.getInstance(context).loadAssignments();
    }

    public static synchronized AssignmentManager getInstance(Context context) {
        if (instance == null) {
            instance = new AssignmentManager(context);
        }
        return instance;
    }

    public static synchronized AssignmentManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("AssignmentManager is not initialized. Call getInstance(Context) first.");
        }
        return instance;
    }

    public ArrayList<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public void addAssignment(Assignment assignment) {
        if (assignment != null && !assignments.contains(assignment)) {
            assignments.add(assignment);
            saveAssignments();
        }
    }

    public void removeAssignment(Assignment assignment) {
        if (assignment != null && assignments.contains(assignment)) {
            assignments.remove(assignment);
            saveAssignments();
        }
    }

    public void updateAssignment(String oldAssignmentName, Assignment updatedAssignment) {
        for(int i = 0; i < assignments.size(); i++){
            Assignment current = assignments.get(i);

            if(current.getName().equals(oldAssignmentName)) {
                assignments.set(i, updatedAssignment);
                saveAssignments();
                return;
            }
        }
    }

    private ArrayList<Assignment> filterAssignments(Predicate<Assignment> predicate){
        return assignments.stream()
                .filter(predicate)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Assignment> getUpcomingAssignments() {
        Date now = new Date();
        return filterAssignments(assignment -> assignment.getDueDate().after(now));
    }

    public ArrayList<Assignment> getPastDueAssignments() {
        Date now = new Date();
        return filterAssignments(assignment -> assignment.getDueDate().before(now));
    }

    public void saveAssignments() {
        SettingsManager.getInstance(context).saveAssignments(assignments);
    }
}
