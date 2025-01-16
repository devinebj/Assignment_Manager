package com.example.myapplication.managers;

import android.content.Context;

import com.example.myapplication.models.Assignment;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AssignmentManager {
    private static AssignmentManager instance = null;
    private static final String FILE_NAME = "assignments.json";
    private List<Assignment> assignments;
    private static Context context;

    private AssignmentManager(Context context){
        this.context = context.getApplicationContext();
        Type listType = new TypeToken<List<Assignment>>() {}.getType();
        this.assignments = FileManager.loadFromFile(this.context, FILE_NAME, listType);
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

    public void saveAssignments(){
        FileManager.saveToFile(context, FILE_NAME, assignments);
    }
}
