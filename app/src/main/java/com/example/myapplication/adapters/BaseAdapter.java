package com.example.myapplication.adapters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseAdapter is an abstract class that provides common functionality for RecyclerView adapters.
 *
 * @param <T>  the type of the data items.
 * @param <VH> the ViewHolder type extending RecyclerView.ViewHolder.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected final ArrayList<T> items;
    protected final Context context;

    /**
     * Constructs a new BaseAdapter.
     *
     * @param context the context used for inflating layouts.
     * @param items   the initial list of items.
     */
    public BaseAdapter(Context context, ArrayList<T> items) {
        this.context = context;
        this.items = new ArrayList<>(items);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     * Updates the adapter's data with a new list of items.
     *
     * @param newItems the new list of items.
     */
    public void updateData(@NonNull ArrayList<T> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    /**
     * Removes the item at the specified position from the adapter and notifies the change.
     *
     * @param position the position of the item to remove.
     */
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

    /**
     * Called when an item is removed from the adapter. Subclasses can override this method
     * to perform additional actions (e.g., updating persistent storage).
     *
     * @param item the item that was removed.
     */
    protected abstract void onItemRemoved(T item);
}
