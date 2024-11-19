package com.example.mushroomhunting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mushroomhunting.R;
import com.example.mushroomhunting.db.DatabaseManager;
import com.example.mushroomhunting.dto.MushroomDto;

import java.util.List;

public class MushroomAdapter extends RecyclerView.Adapter<MushroomAdapter.MushroomViewHolder> {

    private final List<MushroomDto> mushroomList;
    private final DatabaseManager databaseManager;
    private final Context context;

    public MushroomAdapter(Context context, List<MushroomDto> mushroomList, DatabaseManager databaseManager) {
        this.context = context;
        this.mushroomList = mushroomList;
        this.databaseManager = databaseManager;
    }

    @NonNull
    @Override
    public MushroomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mushroom_item, parent, false);
        return new MushroomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MushroomViewHolder holder, int position) {
        MushroomDto mushroom = mushroomList.get(position);

        holder.mushroomType.setText("Type: " + mushroom.getMushroomType());
        holder.mushroomQuantity.setText("Quantity: " + mushroom.getMushroomQuantity());
        holder.mushroomLocation.setText("Location: " + mushroom.getMushroomLocation());

        // Delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            databaseManager.deleteMushroom(mushroom.getMushroomId());
            mushroomList.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return mushroomList.size();
    }

    public static class MushroomViewHolder extends RecyclerView.ViewHolder {
        TextView mushroomType, mushroomQuantity, mushroomLocation;
        Button deleteButton;

        public MushroomViewHolder(@NonNull View itemView) {
            super(itemView);

            mushroomType = itemView.findViewById(R.id.mushroom_type);
            mushroomQuantity = itemView.findViewById(R.id.mushroom_quantity);
            mushroomLocation = itemView.findViewById(R.id.mushroom_location);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
