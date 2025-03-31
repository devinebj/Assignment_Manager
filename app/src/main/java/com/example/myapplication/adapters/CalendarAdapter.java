package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.utility.CalendarUtils;

import java.util.Calendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final Context context;
    private List<Calendar> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(Context context, List<Calendar> days, OnItemListener onItemListener) {
        this.context = context;
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_calendar, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        Calendar date = days.get(position);
        if (date != null) {
            // Set day of month text.
            holder.dayOfMonth.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));

            // Highlight the selected date.
            if (CalendarUtils.isSameDate(date, CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.parentView.setBackgroundColor(Color.TRANSPARENT);
            }

            // Set text color based on whether the date is in the current month.
            if (date.get(Calendar.MONTH) == CalendarUtils.selectedDate.get(Calendar.MONTH)) {
                holder.dayOfMonth.setTextColor(Color.BLACK);
            } else {
                holder.dayOfMonth.setTextColor(Color.LTGRAY);
            }

            // Set click listener directly here.
            holder.itemView.setOnClickListener(v -> onItemListener.onItemClick(position, date));
        } else {
            holder.dayOfMonth.setText("");
            holder.parentView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void updateCalendar(List<Calendar> newDays) {
        this.days = newDays;
        notifyDataSetChanged();
    }

    public interface OnItemListener {
        void onItemClick(int position, Calendar date);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfMonth;
        View parentView;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            parentView = itemView;
        }
    }
}
