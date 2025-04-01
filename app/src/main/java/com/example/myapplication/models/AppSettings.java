package com.example.myapplication.models;

import java.util.ArrayList;
import java.util.List;

public class AppSettings {
    private List<Assignment> assignments;
    private List<Course> courses;
    private int daysToNotify;
    private String semesterEndDate;

    public AppSettings(String semesterEndDate, List<Assignment> assignments, List<Course> courses, int daysToNotify){
        this.semesterEndDate = semesterEndDate;
        this.assignments = assignments;
        this.courses = courses;
        this.daysToNotify = daysToNotify;
    }

    public AppSettings(){
        this.semesterEndDate = "";
        this.assignments = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.daysToNotify = 7;
    }

    public String getSemesterEndDate(){
        return semesterEndDate;
    }

    public void setSemesterEndDate(String semesterEndDate){
        this.semesterEndDate = semesterEndDate;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments){
        this.assignments = assignments;
    }

    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public List<Course> getCourses(){
        return courses;
    }

    public int getDaysToNotify() {
        return daysToNotify;
    }

    public void setDaysToNotify(int daysToNotify) {
        this.daysToNotify = daysToNotify;
    }
}
