package com.example.plant_app.approval_plants.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.plant_app.firebase.Plant;
import com.example.plant_app.firebase.PlantBundle;
import com.example.plant_app.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlantListsApproveViewModel extends ViewModel {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference, ref;
    private FirebaseFirestore db;
    private String userId;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    private MutableLiveData<List<PlantBundle>> plantsList;

    public LiveData<List<PlantBundle>> getPlantsList() {
        if (plantsList == null) {
            plantsList = new MutableLiveData<List<PlantBundle>>();
        }
        return plantsList;
    }

    public void getAccountUser() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        userId = firebaseUser.getUid();
    }


    public void addPlants(List<PlantBundle> list) {
        plantsList.setValue(list);
    }

    public void getListPlantsRequest() {
        db.collection("CREATE").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<PlantBundle> arr = new ArrayList<PlantBundle>();
                        for (QueryDocumentSnapshot plants : queryDocumentSnapshots) {
//                            PlantBundle plant = plants.toObject(PlantBundle.class);
                            PlantBundle plantBundle = new PlantBundle(
                                    Objects.requireNonNull(plants.getData().get("name")).toString(),
                                    Objects.requireNonNull(plants.getData().get("scienceName")).toString(),
                                    Objects.requireNonNull(plants.getData().get("families")).toString(),
                                    Objects.requireNonNull(plants.getData().get("familyDescription")).toString(),
                                    Objects.requireNonNull(plants.getData().get("description")).toString(),
                                    Objects.requireNonNull(plants.getData().get("season")).toString(),
                                    Objects.requireNonNull(plants.getData().get("vitamin")).toString(),
                                    Objects.requireNonNull(plants.getData().get("mineral")).toString(),
                                    Objects.requireNonNull(plants.getData().get("harvestTime")).toString(),
                                    Objects.requireNonNull(plants.getData().get("treatments")).toString(),
                                    Objects.requireNonNull(plants.getData().get("planting")).toString(),
                                    Objects.requireNonNull(plants.getData().get("soil")).toString(),
                                    Objects.requireNonNull(plants.getData().get("soilPH")).toString(),
                                    Objects.requireNonNull(plants.getData().get("sunExposure")).toString(),
                                    Objects.requireNonNull(plants.getData().get("water")).toString(),
                                    Objects.requireNonNull(plants.getData().get("temperature")).toString(),
                                    Objects.requireNonNull(plants.getData().get("humidity")).toString(),
                                    Objects.requireNonNull(plants.getData().get("fertilizer")).toString(),
                                    Objects.requireNonNull(plants.getData().get("botanicalHabit")).toString(),
                                    Objects.requireNonNull(plants.getData().get("type")).toString(),
                                    Objects.requireNonNull(plants.getData().get("owner")).toString(),
                                    Objects.requireNonNull(plants.getData().get("createType")).toString()
                            );
                            arr.add(plantBundle);
                        }
                        addPlants(arr);
                    }
                }).addOnFailureListener(e -> Toast
                .makeText(context, Html.fromHtml("<font color='#FE0000' ><b>Cannot find plant!</b></font>"), Toast.LENGTH_SHORT)
                .show());

    }

    public void setContext(Context _context) {
        this.context = _context;
    }
}