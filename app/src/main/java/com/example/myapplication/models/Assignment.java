package com.example.myapplication.models;

public class Assignment {
    private String course;
    private String assignmentName;
    private int gradeWeight;
    private String dueDate;

    public Assignment(String course, String assignmentName, int gradeWeight, String dueDate){
        this.course = course;
        this.assignmentName = assignmentName;
        this.gradeWeight = gradeWeight;
        this.dueDate = dueDate;
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

    public int getGradeWeight() {
        return gradeWeight;
    }

    public void setGradeWeight(int gradeWeight) {
        this.gradeWeight = gradeWeight;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
