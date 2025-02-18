package com.example.myapplication.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.managers.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends BaseAdapter<Calendar, CalendarAdapter.CalendarViewHolder> {
    private final OnItemListener onItemListener;

    public CalendarAdapter(Context context, ArrayList<Calendar> days, OnItemListener onItemListener) {
        super(context, days);
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.cell_calendar, parent);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (items.size() > 15) // month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, items);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final Calendar date = items.get(position);
        holder.dayOfMonth.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));

        if (CalendarUtils.isSameDate(date, CalendarUtils.selectedDate)) {
            holder.parentView.setBackgroundColor(Color.LTGRAY);
        }

        if (date.get(Calendar.MONTH) == CalendarUtils.selectedDate.get(Calendar.MONTH)) {
            holder.dayOfMonth.setTextColor(Color.BLACK);
        } else {
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
        }
    }

    @Override
    protected void onItemRemoved(Calendar item) {
        // Handle removal
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
            if (position != RecyclerView.NO_POSITION) {
                Calendar date = days.get(position);
                onItemListener.onItemClick(position, date);
            }
        }
    }
}
