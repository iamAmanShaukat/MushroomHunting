package com.example.mushroomhunting.util;

import androidx.recyclerview.widget.DiffUtil;

import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class TripDiffCallback extends DiffUtil.Callback {
    private final List<TripDto> oldList;
    private final List<TripDto> newList;

    public TripDiffCallback(List<TripDto> oldList, List<TripDto> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getTripName().equals(newList.get(newItemPosition).getTripName());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}