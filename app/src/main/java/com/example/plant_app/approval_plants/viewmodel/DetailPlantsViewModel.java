package com.example.plant_app.approval_plants.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.plant_app.firebase.Plant;
import com.example.plant_app.firebase.PlantBundle;
import com.example.plant_app.insert.KeyInsert;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailPlantsViewModel extends ViewModel {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference, ref;
    private FirebaseFirestore db;
    private String userId;

    @SuppressLint("StaticFieldLeak")
    private Context context;
    private String userStatus;

    public void setContext(Context _context) {
        this.context = _context;
    }

    public void getAccountUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = firebaseUser.getUid();
    }

    private MutableLiveData<List<PlantBundle>> plantsList;

    public LiveData<List<PlantBundle>> getPlantsList() {
        if (plantsList == null) {
            plantsList = new MutableLiveData<List<PlantBundle>>();
        }
        return plantsList;
    }

    private MutableLiveData<Plant> plants;

    public LiveData<Plant> getPlants() {
        if (plants == null) {
            plants = new MutableLiveData<Plant>();
        }
        return plants;
    }

    public void onApprovePlant(PlantBundle plantBundle) {
        if (userStatus.equals("user")) {
            Toast.makeText(context, "Approve by Admin only!", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> plant = new HashMap<>();
        plant.put(KeyInsert.KEY_NAME, plantBundle.getName());
        plant.put(KeyInsert.KEY_SCIENCE_NAME, plantBundle.getScienceName());
        plant.put(KeyInsert.KEY_FAMILIES, plantBundle.getFamilies());
        plant.put(KeyInsert.KEY_FAMILY_DESC, plantBundle.getFamilyDescription());
        plant.put(KeyInsert.KEY_BOTANICAL_HABIT, plantBundle.getBotanicalHabit());
        plant.put(KeyInsert.KEY_DESCRIPTION, plantBundle.getDescription());
        plant.put(KeyInsert.KEY_SEASON, plantBundle.getSeason());
        plant.put(KeyInsert.KEY_VITAMIN, plantBundle.getVitamin());
        plant.put(KeyInsert.KEY_MINERAL, plantBundle.getMineral());
        plant.put(KeyInsert.KEY_HARVEST_TIME, plantBundle.getHarvestTime());
        plant.put(KeyInsert.KEY_TREATMENTS, plantBundle.getTreatments());
        plant.put(KeyInsert.KEY_PLANTING, plantBundle.getPlanting());
        plant.put(KeyInsert.KEY_SOIL, plantBundle.getSoil());
        plant.put(KeyInsert.KEY_SOIL_PH, plantBundle.getSoilPH());
        plant.put(KeyInsert.KEY_SUN_EXPOSURE, plantBundle.getSunExposure());
        plant.put(KeyInsert.KEY_WATER, plantBundle.getWater());
        plant.put(KeyInsert.KEY_TEMPERATURE, plantBundle.getTemperature());
        plant.put(KeyInsert.KEY_HUMIDITY, plantBundle.getHumidity());
        plant.put(KeyInsert.KEY_FERTILIZER, plantBundle.getFertilizer());
        plant.put(KeyInsert.TYPE, plantBundle.getType());
        plant.put(KeyInsert.OWNER, plantBundle.getOwner());

        db.collection(userId).document(plantBundle.getName()).set(plant)
                .addOnSuccessListener(unused -> Toast.makeText(context, "Insert " + plantBundle.getCreateType() + " successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                });

        db.collection(plantBundle.getCreateType()).document(plantBundle.getName()).set(plant)
                .addOnSuccessListener(unused -> Toast.makeText(context, "Insert " + plantBundle.getCreateType() + " successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                });
    }

    public void setUserStatus(String _userStatus){
        this.userStatus = _userStatus;
    }
}