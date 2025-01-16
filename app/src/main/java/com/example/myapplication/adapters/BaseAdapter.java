package com.example.myapplication.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<T> items;
    protected Context context;

    public BaseAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(List<T> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            T item = items.get(position);
            onItemRemoved(item);
            items.remove(position);
            notifyItemRemoved(position);
        } else {
            Log.e("BaseAdapter", "Invalid position: " + position + ", List Size: " + items.size());
        }
    }

    protected abstract void onItemRemoved(T item);
}
