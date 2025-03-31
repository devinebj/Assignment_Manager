package com.example.myapplication.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final List<T> items;
    protected final Context context;

    public BaseAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateData(@NonNull List<T> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < items.size()) {
            T removedItem = items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, items.size() - position);
            onItemRemoved(removedItem);
        }
    }

    protected abstract void onItemRemoved(T item);
}
