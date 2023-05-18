package com.example.enquete.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.enquete.MainActivity;
import com.example.enquete.MyListAdapter;
import com.example.enquete.R;
import com.example.enquete.databinding.FragmentHomeBinding;

import java.util.ArrayList;

import models.Enquete;
import repositories.EnqueteRepositorie;
import repositories.PreuveRepositorie;
import repositories.SuspectRepositorie;
import repositories.VictimeRepositorie;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    SharedPreferences preferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        preferences = getActivity().getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EnqueteRepositorie repos = new EnqueteRepositorie(getActivity().getApplicationContext());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        int idUSer  = preferences.getInt("id",0);
        int countEnquetes = new EnqueteRepositorie(getActivity()).getCount(idUSer);
        int countPreuves = new PreuveRepositorie(getActivity()).getCount();
        int countVictimes = new VictimeRepositorie(getActivity()).getCount();
        int countSuspects = new SuspectRepositorie(getActivity()).getCount();
        View root = binding.getRoot();
        TextView textViewNombreEnquetes = root.findViewById(R.id.textViewNombreEnquetes);
        TextView textViewNombreVictimes = root.findViewById(R.id.textViewNombreVictimes);
        TextView textViewNombrePreuves = root.findViewById(R.id.textViewNombrePreuves);
        TextView textViewNombreSuspects = root.findViewById(R.id.textViewNombreSuspects);


        textViewNombreEnquetes.setText(Integer.toString(countEnquetes));
        textViewNombreVictimes.setText(Integer.toString(countVictimes));
        textViewNombrePreuves.setText(Integer.toString(countPreuves));
        textViewNombreSuspects.setText(Integer.toString(countSuspects));
    }
}