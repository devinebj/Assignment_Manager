package com.example.myapplication.utility;

import android.icu.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtils{
    public static Calendar selectedDate = Calendar.getInstance();

    private static final SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat( "MMMM yyyy", Locale.US);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a", Locale.US);
    private static final SimpleDateFormat EDIT_TIME_FORMAT = new SimpleDateFormat("HH:mm a", Locale.US);

    public static String formattedMonth(Calendar date) { return MONTH_FORMAT.format(date.getTime()); }

    public static String formattedDate(Calendar date) { return DATE_FORMAT.format(date.getTime()); }

    public static String formattedDate(Date date) { return DATE_FORMAT.format(toCalendar(date).getTime()); }

    public static String formattedTime(Calendar time) {
        return TIME_FORMAT.format(time.getTime());
    }

    public static String formattedTime(Date time) { return TIME_FORMAT.format(toCalendar(time).getTime()); }

    public static String toStorageDate(Date date) { return DATE_FORMAT.format(toCalendar(date).getTime()); }

    public static String toStorageTime(Date time) { return EDIT_TIME_FORMAT.format(time.getTime()); }

    public static ArrayList<Calendar> daysInMonthArray() {
        ArrayList<Calendar> daysInMonthArray = new ArrayList<>();
        Calendar monthStart = (Calendar) selectedDate.clone();
        monthStart.set(Calendar.DAY_OF_MONTH, 1);

        int firstDayOfWeek = monthStart.get(Calendar.DAY_OF_WEEK) - 1;
        for(int i = 0; i < firstDayOfWeek; i++){
            daysInMonthArray.add(null);
        }

        int daysInMonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < daysInMonth; i++){
            Calendar day = (Calendar) monthStart.clone();
            day.add(Calendar.DAY_OF_MONTH, i);
            daysInMonthArray.add(day);
        }

        while (daysInMonthArray.size() % 7 != 0){
            daysInMonthArray.add(null);
        }

        return daysInMonthArray;
    }

    public static ArrayList<Calendar> daysInWeekArray(Calendar date) {
        ArrayList<Calendar> daysInWeek = new ArrayList<>();
        Calendar weekStart = (Calendar) date.clone();
        weekStart.set(Calendar.DAY_OF_WEEK, weekStart.getFirstDayOfWeek());

        for(int i = 0; i < 7; i++){
            Calendar day = (Calendar) weekStart.clone();
            day.add(Calendar.DAY_OF_MONTH, i);
            daysInWeek.add(day);
        }

        return daysInWeek;
    }

    public static boolean isSameDate(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }

    private static Calendar toCalendar(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}