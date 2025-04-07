package com.example.myapplication.models;

import java.util.Date;

public class Assignment {
    private String course;
    private String assignmentName;
    private int pointsPossible;
    private int gradeWeight;
    private Date dueDate;
    private Date dueTime;

    // New fields for the timer feature
    private long timeSpent;       // Total time in milliseconds
    private boolean timerRunning; // Is the timer running right now?
    private long lastStartTime;   // Timestamp of when the timer was last started

    public Assignment(String course, String assignmentName, int pointsPossible,
                      int gradeWeight, Date dueDate, Date dueTime) {
        this.course = course;
        this.assignmentName = assignmentName;
        this.pointsPossible = pointsPossible;
        this.gradeWeight = gradeWeight;
        this.dueDate = dueDate;
        this.dueTime = dueTime;

        // Initialize timer fields
        this.timeSpent = 0;
        this.timerRunning = false;
        this.lastStartTime = 0;
    }

    // ---------------------
    // Existing getters/setters
    // ---------------------
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

    public int getPointsPossible() {
        return pointsPossible;
    }
    public void setPointsPossible(int pointsPossible) {
        this.pointsPossible = pointsPossible;
    }

    public int getGradeWeight() {
        return gradeWeight;
    }
    public void setGradeWeight(int gradeWeight) {
        this.gradeWeight = gradeWeight;
    }

    public Date getDueDate() {
        return dueDate;
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueTime() {
        return dueTime;
    }
    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    // ---------------------
    // New timer fields' getters/setters
    // ---------------------
    public long getTimeSpent() {
        return timeSpent;
    }
    public void setTimeSpent(long timeSpent) {
        this.timeSpent = timeSpent;
    }

    public boolean isTimerRunning() {
        return timerRunning;
    }
    public void setTimerRunning(boolean timerRunning) {
        this.timerRunning = timerRunning;
    }

    public long getLastStartTime() {
        return lastStartTime;
    }
    public void setLastStartTime(long lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    // ---------------------
    // New convenience methods for starting/stopping the timer
    // ---------------------
    public void startTimer() {
        if (!timerRunning) {
            this.lastStartTime = System.currentTimeMillis();
            this.timerRunning = true;
        }
    }

    public void stopTimer() {
        if (timerRunning) {
            long now = System.currentTimeMillis();
            this.timeSpent += (now - lastStartTime);
            this.timerRunning = false;
        }
    }
}
