package com.example.plant_app.approval_plants.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plant_app.R;
import com.example.plant_app.approval_plants.EventListner;
import com.example.plant_app.approval_plants.PlantsListRequestFragment;
import com.example.plant_app.databinding.ItemPlantRequestBinding;
import com.example.plant_app.firebase.PlantBundle;
import com.example.plant_app.firebase.PlantListView;

import java.util.ArrayList;
import java.util.List;

public class PlantsApproveAdapter extends RecyclerView.Adapter<PlantsApproveAdapter.MyViewHolder> {
    private List<PlantBundle> plantListsss;
    private EventListner listner;

    class MyViewHolder extends RecyclerView.ViewHolder {
        ItemPlantRequestBinding binding = null;

        public MyViewHolder(@NonNull ItemPlantRequestBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void onBind(PlantBundle plant) {
            binding.tvTitlePlant.setText(plant.getName());
            binding.tvDescPlant.setText(plant.getDescription());
            binding.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClickItem(plant);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemPlantRequestBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(plantListsss.get(position));
    }

    @Override
    public int getItemCount() {
        return plantListsss.size();
    }

    public PlantsApproveAdapter(List<PlantBundle> plantList, EventListner _listner) {
        this.plantListsss = plantList;
        this.listner = _listner;
    }
}
