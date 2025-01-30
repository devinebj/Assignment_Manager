package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.Assignment;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

public class AssignmentManager {
    private static AssignmentManager instance = null;
    private List<Assignment> assignments;
    private static Context context;

    private AssignmentManager(Context context){
        this.context = context.getApplicationContext();
        this.assignments = SettingsManager.getInstance(context).loadAssignments();
    }

    public static synchronized AssignmentManager getInstance(Context context){
        if(instance == null){
            instance = new AssignmentManager(context);
        }

        return instance;
    }

    public static synchronized AssignmentManager getInstance(){
        if(instance == null) {
            throw new IllegalStateException("Data not initialized. Call getInstance(Context context) first");
        }

         return instance;
    }

    public List<Assignment> getAssignments() {
        return new ArrayList<>(assignments);
    }

    public void addAssignment(Assignment assignment){
        if(assignment != null && !assignments.contains(assignment)){
            assignments.add(assignment);
            saveAssignments();
        }
    }

    public void removeAssignment(Assignment assignment){
        if(assignment != null && assignments.contains(assignment)){
            assignments.remove(assignment);
            saveAssignments();
        }
    }

    public List<Assignment> getUpcomingAssignments() {
        Date now = new Date();
        return assignments.stream()
                .filter(assignment -> assignment.getDueDate().after(now))
                .collect(Collectors.toList());
    }

    public List<Assignment> getPastDueAssignments() {
        Date now = new Date();
        return assignments.stream()
                .filter(assignment -> assignment.getDueDate().before(now))
                .collect(Collectors.toList());
    }

    public void saveAssignments(){
        SettingsManager.getInstance(context).saveAssignments(assignments);
    }
}
