package com.example.myapplication.models;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

public class HourEvent
{
    public ArrayList<Event> events;
    Calendar time;

    public HourEvent(Calendar time, ArrayList<Event> events)
    {
        this.time = time;
        this.events = events;
    }

    public Calendar getTime()
    {
        return time;
    }

    public void setTime(Calendar time)
    {
        this.time = time;
    }

    public ArrayList<Event> getEvents()
    {
        return events;
    }

    public void setEvents(ArrayList<Event> events)
    {
        this.events = events;
    }
}