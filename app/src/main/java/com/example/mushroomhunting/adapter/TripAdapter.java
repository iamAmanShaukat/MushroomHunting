package com.example.mushroomhunting.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.activity.EditTripActivity;
import com.example.mushroomhunting.activity.TripDetailsActivity;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.TripDto;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private final List<TripDto> tripList;
    private final Context context;
    private final Runnable onSelectionChange; // Callback for selection change

    public TripAdapter(Context context, List<TripDto> tripList, Runnable onSelectionChange) {
        this.context = context;
        this.tripList = tripList;
        this.onSelectionChange = onSelectionChange;
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

        // Bind data
        holder.tripName.setText(trip.getTripName());
        holder.tripDate.setText(trip.getTripDate());
        holder.tripLocation.setText(trip.getTripLocation());

        // View Trip Details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TripDetailsActivity.class);
            intent.putExtra("tripId", trip.getTripId());
            context.startActivity(intent);
        });

        // Edit Trip
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditTripActivity.class);
            intent.putExtra("tripId", trip.getTripId());
            context.startActivity(intent);
        });

        // Delete Trip
        holder.deleteIcon.setOnClickListener(v -> showDeleteConfirmationDialog(trip, position));

        // Individual checkbox state
        holder.individualCheckbox.setOnCheckedChangeListener(null); // Reset listener
        holder.individualCheckbox.setChecked(trip.isSelected());
        holder.individualCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            trip.setSelected(isChecked);
            onSelectionChange.run(); // Notify activity about selection change
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    // Select or deselect all trips
    public void selectAllTrips(boolean isChecked) {
        for (TripDto trip : tripList) {
            trip.setSelected(isChecked);
        }
        notifyDataSetChanged();
    }

    private void showDeleteConfirmationDialog(TripDto trip, int position) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_delete, null);

        Button confirmButton = dialogView.findViewById(R.id.btn_confirm);
        Button cancelButton = dialogView.findViewById(R.id.btn_cancel);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        confirmButton.setOnClickListener(confirmView -> {
            deleteTrip(trip.getTripId());
            tripList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tripList.size());
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(cancelView -> dialog.dismiss());
        dialog.show();
    }

    private void deleteTrip(String tripId) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.deleteTrip(tripId);
        Log.i("Delete", "Deleted trip with ID: " + tripId);
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tripName, tripDate, tripLocation;
        Button editButton;
        ImageView deleteIcon;
        CheckBox individualCheckbox;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name);
            tripDate = itemView.findViewById(R.id.trip_date);
            tripLocation = itemView.findViewById(R.id.trip_location);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
            individualCheckbox = itemView.findViewById(R.id.individual_checkbox);
        }
    }
}
