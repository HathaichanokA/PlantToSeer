package com.example.plant_app.approval_plants;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.plant_app.R;
import com.example.plant_app.approval_plants.adapter.PlantsApproveAdapter;
import com.example.plant_app.approval_plants.viewmodel.PlantListsApproveViewModel;
import com.example.plant_app.databinding.FragmentApprovePlantsBinding;
import com.example.plant_app.firebase.PlantBundle;
import com.example.plant_app.insert.InsertFruitFragment;
import com.google.firebase.events.Event;

import java.util.ArrayList;
import java.util.List;

public class PlantsListRequestFragment extends Fragment implements EventListner {
    private FragmentApprovePlantsBinding binding;
    private static final String TAG = "PlantsListRequestFragment";
    private PlantListsApproveViewModel plantListsApproveViewModel;
    private PlantsApproveAdapter plantsApproveAdapter = null;
    private List<PlantBundle> plantBundlesList = new ArrayList<>();

    public PlantsListRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApprovePlantsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initRecycleView();
    }

    private void initViewModel() {
        plantListsApproveViewModel = new PlantListsApproveViewModel();
        plantListsApproveViewModel.getAccountUser();
        plantListsApproveViewModel.getListPlantsRequest();
        observeable();
    }

    private void observeable() {
        plantListsApproveViewModel.getPlantsList().observe(getViewLifecycleOwner(), new Observer<List<PlantBundle>>() {
            @Override
            public void onChanged(List<PlantBundle> plantBundles) {
                if (plantBundles != null && !plantBundles.isEmpty()) {
                    plantBundlesList = plantBundles;
                    initRecycleView();
                }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initRecycleView() {
        binding.recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        plantsApproveAdapter = new PlantsApproveAdapter(plantBundlesList, this);
        binding.recycleview.setAdapter(plantsApproveAdapter);
        plantsApproveAdapter.notifyDataSetChanged();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClickItem(PlantBundle plantBundle) {
        replaceFragment(new DetailPlantsFragment(plantBundle));
    }
}