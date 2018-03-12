package com.thinkwage.minutesbundle.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.thinkwage.minutesbundle.R;

/**
 * Created by ICE on 2017/7/17.
 */

public class MinutesAdapter extends  RecyclerView.Adapter {

    public MinutesAdapter(String type) {
        super();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (0 == viewType) {
            viewHolder = new HeadViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minutes_head,null));
        } else {
            viewHolder =  new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_minutes,null));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.itemView.setLayoutParams(layoutParams);
        switch (getItemViewType(position)) {
            case 0:
                break;
            case 1:
                ItemViewHolder iHolder = (ItemViewHolder) holder;
                if (position > 1) {
                    iHolder.itemTiele.setVisibility(View.GONE);
                } else {
                    iHolder.itemTiele.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {

        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 0 : 1;
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {

        public HeadViewHolder(View itemView) {

            super(itemView);
        }
    }
    static class ItemViewHolder extends RecyclerView.ViewHolder {

        public final TextView itemTiele;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemTiele = (TextView) itemView.findViewById(R.id.item_title);
        }
    }
}
