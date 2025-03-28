package com.example.myapplication.adapters;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.models.Event;
import com.example.myapplication.models.HourEvent;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HourAdapter extends BaseAdapter<HourEvent, HourAdapter.HourViewHolder> {

    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm", Locale.US);

    public HourAdapter(Context context, List<HourEvent> hourEvents){
        super(context, hourEvents);
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.cell_hour, parent, false);
        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        HourEvent hourEvent = items.get(position);
        setHour(holder, hourEvent.getTime());
        setEvents(holder, hourEvent.events);
    }

    private void setHour(HourViewHolder holder, Calendar time){
        holder.timeTV.setText(HOUR_FORMAT.format(time.getTime()));
    }

    private void setEvents(HourViewHolder holder, List<Event> events) {
        TextView[] eventViews = {holder.event1, holder.event2, holder.event3};

        // Hide all event views initially
        for (TextView tv : eventViews) {
            tv.setVisibility(View.INVISIBLE);
        }

        // Display the first two events (if available).
        int eventsToShow = Math.min(events.size(), 2);
        for(int i = 0; i < eventsToShow; i++) {
            eventViews[i].setText(events.get(i).getName());
            eventViews[i].setVisibility(View.VISIBLE);
        }

        // If more events exist, show count on the third view.
        if(events.size() > 2) {
            eventViews[2].setText((events.size() - 2) + " More Events");
            eventViews[2].setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onItemRemoved(HourEvent item){

    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        public TextView timeTV, event1, event2, event3;

        public HourViewHolder(@NonNull View itemView){
            super(itemView);
            timeTV = itemView.findViewById(R.id.timeTV);
            event1 = itemView.findViewById(R.id.event1);
            event2 = itemView.findViewById(R.id.event2);
            event3 = itemView.findViewById(R.id.event3);
        }
    }
}
