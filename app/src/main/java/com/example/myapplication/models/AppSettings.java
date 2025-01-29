package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {
    private List<Assignment> assignments;
    private List<Course> courses;
    private int daysToNotify;

    public AppSettings(List<Assignment> assignments, List<Course> courses, int daysToNotify){
        this.assignments = assignments;
        this.courses = courses;
        this.daysToNotify = daysToNotify;
    }

    public AppSettings(){
        this.assignments = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.daysToNotify = 7;
    }

    public List<Assignment> setAssignments() {
        return assignments;
    }

    public void getAssignments(List<Assignment> assignments){
        this.assignments = assignments;
    }

    public List<Course> setCourses(){
        return courses;
    }

    public void getCourses(List<Course> courses){
        this.courses = courses;
    }

    public int setDaysToNotify() {
        return daysToNotify;
    }

    public void getDaysToNotify(int daysToNotify) {
        this.daysToNotify = daysToNotify;
    }
}
