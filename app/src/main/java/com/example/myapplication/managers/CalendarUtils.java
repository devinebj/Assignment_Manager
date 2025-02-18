package com.example.myapplication.managers;

import android.icu.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class CalendarUtils{
    public static Calendar selectedDate = Calendar.getInstance();

    public static String formattedDate(Calendar date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return formatter.format(date.getTime());
    }

    public static String formattedTime(Calendar time) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
        return formatter.format(time.getTime());
    }

    public static ArrayList<Calendar> daysInMonthArray() {
        ArrayList<Calendar> daysInMonthArray = new ArrayList<>();
        Calendar monthStart = (Calendar) selectedDate.clone();
        monthStart.set(Calendar.DAY_OF_MONTH, 1);

        int daysInMonth = monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i = 0; i < daysInMonth; i++){
            Calendar day = (Calendar) monthStart.clone();
            day.add(Calendar.DAY_OF_MONTH, 1);
            daysInMonthArray.add(day);
        }

        return daysInMonthArray;
    }

    public static boolean isSameDate(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR) &&
                date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH) &&
                date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }
}