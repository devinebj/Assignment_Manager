package com.example.myapplication.models;

import java.util.ArrayList;

public class AppSettings {
    private ArrayList<Assignment> assignments;
    private ArrayList<Course> courses;
    private int daysToNotify;

    public AppSettings(ArrayList<Assignment> assignments, ArrayList<Course> courses, int daysToNotify){
        this.assignments = assignments;
        this.courses = courses;
        this.daysToNotify = daysToNotify;
    }

    public AppSettings(){
        this.assignments = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.daysToNotify = 7;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments){
        this.assignments = assignments;
    }

    public void setCourses(ArrayList<Course> courses){
        this.courses = courses;
    }

    public ArrayList<Course> getCourses(){
        return courses;
    }

    public int getDaysToNotify() {
        return daysToNotify;
    }

    public void setDaysToNotify(int daysToNotify) {
        this.daysToNotify = daysToNotify;
    }
}
