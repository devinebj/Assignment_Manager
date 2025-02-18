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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HourAdapter extends BaseAdapter<HourEvent, HourAdapter.HourViewHolder> {

    public HourAdapter(Context context, ArrayList<HourEvent> hourEvents){
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
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        holder.timeTV.setText(formatter.format(time.getTime()));
    }

    private void setEvents(HourViewHolder holder, List<Event> events) {
        TextView[] eventTextViews = {holder.event1, holder.event2, holder.event3};

        for (TextView eventTextView: eventTextViews) {
            eventTextView.setVisibility(View.INVISIBLE);
        }

        for (int i = 0; i < Math.min(events.size(), 2); i++) {
            setEvent(eventTextViews[i], events.get(i));
        }

        if(events.size() > 2) {
            eventTextViews[2].setVisibility(View.VISIBLE);
            eventTextViews[2].setText((events.size() - 2) + " More Events");
        }
    }

    private void setEvent(TextView textView, Event event){
        textView.setText(event.getName());
        textView.setVisibility(View.VISIBLE);
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

