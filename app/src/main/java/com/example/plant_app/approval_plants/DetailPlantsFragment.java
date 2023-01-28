package com.example.plant_app.approval_plants;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plant_app.approval_plants.viewmodel.DetailPlantsViewModel;
import com.example.plant_app.databinding.FragmentDetailPlantsBinding;
import com.example.plant_app.firebase.Plant;
import com.example.plant_app.firebase.PlantBundle;
import com.example.plant_app.firebase.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPlantsFragment extends Fragment {
    private static final String TAG = "ApprovalFragment";

    private PlantBundle plantBundle = null;
    private Plant plant = null;
    private FragmentDetailPlantsBinding binding;
    private DetailPlantsViewModel viewModel;

    public DetailPlantsFragment(PlantBundle _plant) {
        this.plantBundle = _plant;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDetailPlantsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initObserveable();
        initView();
    }

    private void initView() {
        binding.title.setText("Details: " +plantBundle.getName());
    }

    private void initViewModel() {
        viewModel = new DetailPlantsViewModel();
        binding.setListner(this);
        binding.setVm(viewModel);
        binding.setPlant(plantBundle);
        viewModel.getAccountUser();
        viewModel.setContext(requireContext());
        initUser();
    }

    private void  onApproveData(PlantBundle plantBundle){

    }

    private void initObserveable() {
    }

    public void onClickApprove() {
        viewModel.onApprovePlant(plantBundle);
    }
    private void initUser() {
        String id = FirebaseAuth.getInstance().getUid();
        DocumentReference db = FirebaseFirestore.getInstance().collection("User").document(id);
        db.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null) {
                            User user = document.toObject(User.class);
                            if(user!=null) {
                                viewModel.setUserStatus(user.getStatus());
                            }
                        }
                    }
                });
    }

}