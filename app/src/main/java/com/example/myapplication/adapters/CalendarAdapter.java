package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.managers.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {
    private final Context context;
    private ArrayList<Calendar> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(Context context, ArrayList<Calendar> days, OnItemListener onItemListener) {
        this.context = context;
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_calendar, parent, false);

        if(context == null){
            throw new IllegalStateException("Context is null in CalendarAdapter");
        }

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final Calendar date = days.get(position);

        if (date != null) {
            holder.dayOfMonth.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));
            Log.d("CalendarAdapter", "Date set: " + date.getTime());

            if (CalendarUtils.isSameDate(date, CalendarUtils.selectedDate)) {
                holder.parentView.setBackgroundColor(Color.LTGRAY);
            } else {
                holder.parentView.setBackgroundColor(Color.TRANSPARENT);
            }

            if (date.get(Calendar.MONTH) == CalendarUtils.selectedDate.get(Calendar.MONTH)) {
                holder.dayOfMonth.setTextColor(Color.BLACK);
            } else {
                holder.dayOfMonth.setTextColor(Color.LTGRAY);
            }
        } else {
            holder.dayOfMonth.setText("");
            holder.parentView.setBackgroundColor(Color.TRANSPARENT);
            Log.d("CalendarAdapter", "Null date at position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public void updateCalendar(ArrayList<Calendar> newDays){
        this.days = newDays;
        notifyDataSetChanged();
    }

    public interface OnItemListener {
        void onItemClick(int position, Calendar date);
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dayOfMonth;
        public View parentView;
        private final OnItemListener onItemListener;
        private final ArrayList<Calendar> days;

        public CalendarViewHolder(@NonNull View itemView, OnItemListener onItemListener, ArrayList<Calendar> days) {
            super(itemView);
            this.onItemListener = onItemListener;
            this.days = days;
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            parentView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && days.get(position) != null) {
                Calendar date = days.get(position);
                onItemListener.onItemClick(position, date);
            }
        }
    }
}
