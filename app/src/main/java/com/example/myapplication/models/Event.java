package com.example.myapplication.models;

import java.util.Calendar;
import java.util.Date;

public class Event {
    private final String name;
    private final Date date;
    private final Calendar time;

    public Event(String name, Date date, Calendar time){
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public Date getDate(){
        return date;
    }

    public Calendar getTime(){
        return time;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return name.equals(event.name) && date.equals(event.date) && time.equals(event.time);
    }

    @Override
    public String toString() {
        return "Event{name='" + name + "', date=" + date + ", time=" + time + '}';
    }
}