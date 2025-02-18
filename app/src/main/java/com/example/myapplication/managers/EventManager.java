package com.example.myapplication.managers;

import com.example.myapplication.models.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager {
    private final List<Event> events = new ArrayList<>();

    public void addEvent(String name, Date date, Calendar time){
        Event event = new Event(name, date, time);
        events.add(event);
    }

    public List<Event> getEventsForDate(Date date){
        return events.stream()
                .filter(event -> event.getDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Event> getEventsForDateAndTime(Date date, Calendar time){
        return events.stream()
                .filter(event -> event.getDate().equals(date) &&
                        event.getTime().get(Calendar.HOUR_OF_DAY) == time.get(Calendar.HOUR_OF_DAY))
                .collect(Collectors.toList());
    }

    public void removeEvent(Event event){
        events.remove(event);
    }

    public List<Event> getAllEvents() {
        return new ArrayList<>(events);
    }

    public boolean eventExists(String name, Date date, Calendar time){
        return events.stream()
                .anyMatch(event -> event.getName().equals(name) && event.getDate().equals(date) && event.getTime().equals(time));
    }

    public void clearAllEvents() {
        events.clear();
    }
}
