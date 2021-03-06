package com.commit451.adapterlayout.sample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.commit451.adapterlayout.AdapterLayout;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Adapter for the recyclerview, which holds cheeses
 */
public class CheeseAdapter extends RecyclerView.Adapter<CheeseViewHolder> {

    private Listener mListener;
    private ArrayList<Cheese> mValues;

    public CheeseAdapter(Listener listener) {
        mListener = listener;
        mValues = new ArrayList<>();
    }

    public void setData(Collection<Cheese> cheeses) {
        mValues.addAll(cheeses);
        notifyDataSetChanged();
    }

    public void add(Cheese cheese) {
        mValues.add(cheese);
        notifyItemInserted(mValues.size()-1);
    }

    public void removeLast() {
        if (!mValues.isEmpty()) {
            int removeIndex = mValues.size() - 1;
            mValues.remove(removeIndex);
            notifyItemRemoved(removeIndex);
        }
    }

    public void changeMiddle() {
        if (!mValues.isEmpty()) {
            int index = mValues.size()/2;
            mValues.get(index).setName("Swiss");
            notifyItemChanged(index);
        }
    }

    public void changeAll() {
        if (!mValues.isEmpty()) {
            for (Cheese cheese : mValues) {
                cheese.setName("Swiss");
            }
            notifyItemRangeChanged(0, mValues.size());
        }
    }

    public void clear() {
        if (!mValues.isEmpty()) {
            int size = mValues.size();
            mValues.clear();
            notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public CheeseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final CheeseViewHolder holder = CheeseViewHolder.inflate(parent);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the place of holder.getAdapterPosition()
                int position = AdapterLayout.getAdapterPosition(holder);
                Cheese cheese = getItemAt(position);
                mListener.onItemClicked(cheese);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final CheeseViewHolder holder, int position) {
        Cheese cheese = getItemAt(position);
        holder.bind(cheese);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    private Cheese getItemAt(int position) {
        return mValues.get(position);
    }

    public interface Listener {
        void onItemClicked(Cheese cheese);
    }
}
