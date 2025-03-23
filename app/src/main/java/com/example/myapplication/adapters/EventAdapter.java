package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.managers.CalendarUtils;
import com.example.myapplication.models.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter<Event, EventAdapter.EventViewHolder> {
    public EventAdapter(Context context, List<Event> events) {
        super(context, events);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.cell_event, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = items.get(position);
        String eventTitle = String.format("%s %s", event.getName(), CalendarUtils.formattedTime(event.getTime()));
        holder.eventCellTV.setText(eventTitle);
    }

    @Override
    protected void onItemRemoved(Event item) {
        // No additional actions needed when an event is removed.
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventCellTV;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventCellTV = itemView.findViewById(R.id.eventCellTV);
        }
    }
}