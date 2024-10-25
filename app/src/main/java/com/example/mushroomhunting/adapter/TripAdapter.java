package com.example.mushroomhunting.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.activity.TripDetailsActivity;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<TripDto> tripList;
    private Context context;

    public TripAdapter(Context context, List<TripDto> tripList) {
        this.context = context;
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        TripDto trip = tripList.get(position);
        holder.tripName.setText(trip.getTripName());
        holder.tripDate.setText(trip.getTripDate());
        holder.tripLocation.setText(trip.getTripLocation());

        // Set click listener for trip item to open TripDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TripDetailsActivity.class);
            intent.putExtra("tripId", trip.getTripId());
            context.startActivity(intent);
        });

        // Set click listener for delete icon with custom confirmation dialog
        holder.deleteIcon.setOnClickListener(v -> {
            // Inflate the custom dialog view
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_delete, null);

            // Initialize buttons in the custom layout
            Button confirmButton = dialogView.findViewById(R.id.btn_confirm);
            Button cancelButton = dialogView.findViewById(R.id.btn_cancel);

            // Create and show the AlertDialog
            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setView(dialogView)
                    .create();

            // Set button actions
            confirmButton.setOnClickListener(confirmView -> {
                deleteTrip(trip.getTripId());
                tripList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tripList.size());
                dialog.dismiss();
            });

            cancelButton.setOnClickListener(cancelView -> dialog.dismiss());

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        TextView tripName, tripDate, tripLocation;
        ImageView deleteIcon;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name);
            tripDate = itemView.findViewById(R.id.trip_date);
            tripLocation = itemView.findViewById(R.id.trip_location);
            deleteIcon = itemView.findViewById(R.id.delete_icon); // Reference to delete icon in XML
        }
    }

    // Stub for deleteTrip method; implement actual deletion logic as needed
    private void deleteTrip(String tripId) {
        Log.i("Delete","Delete item with tripId: "+tripId);
    }
}