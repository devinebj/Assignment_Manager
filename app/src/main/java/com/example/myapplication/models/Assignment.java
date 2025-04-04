package com.example.myapplication.models;

import java.util.Date;

public class Assignment {
    private String course;
    private String assignmentName;
    private int pointsPossible;
    private int gradeWeight;
    private Date dueDate;
    private Date dueTime;

    public Assignment(String course, String assignmentName, int pointsPossible, int gradeWeight, Date dueDate, Date dueTime){
        this.course = course;
        this.assignmentName = assignmentName;
        this.pointsPossible = pointsPossible;
        this.gradeWeight = gradeWeight;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return assignmentName;
    }
    public void setName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getPointsPossible() { return pointsPossible; }
    public void setPointsPossible(int pointsPossible) { this.pointsPossible = pointsPossible; }

    public int getGradeWeight() {
        return gradeWeight;
    }
    public void setGradeWeight(int gradeWeight) { this.gradeWeight = gradeWeight; }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueTime() { return dueTime; }
    public void setDueTime(Date dueTime) { this.dueTime = dueTime; }
}
