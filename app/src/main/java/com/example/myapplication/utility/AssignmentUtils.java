package com.example.myapplication.utility;

import com.example.myapplication.models.Assignment;
import com.example.myapplication.models.Event;

import java.util.Calendar;

public class AssignmentUtils {

    public static Event convertToEvent(Assignment assignment) {
        Calendar dueCalendar = Calendar.getInstance();
        dueCalendar.setTime(assignment.getDueTime());
        Calendar dateOnly = Calendar.getInstance();
        dateOnly.setTime(assignment.getDueDate());

        dueCalendar.set(Calendar.YEAR, dateOnly.get(Calendar.YEAR));
        dueCalendar.set(Calendar.MONTH, dateOnly.get(Calendar.MONTH));
        dueCalendar.set(Calendar.DAY_OF_MONTH, dateOnly.get(Calendar.DAY_OF_MONTH));

        return new Event(assignment.getName(), assignment.getDueDate(), dueCalendar);
    }
}
