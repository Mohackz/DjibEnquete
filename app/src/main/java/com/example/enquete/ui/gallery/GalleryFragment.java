package com.example.enquete.ui.gallery;

import android.app.admin.SecurityLog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.enquete.EnqueteDetailsActivity;
import com.example.enquete.MainActivity;
import com.example.enquete.MyListAdapter;
import com.example.enquete.databinding.FragmentGalleryBinding;
import com.example.enquete.R;
import com.example.enquete.databinding.FragmentGalleryBinding;
import com.example.enquete.ui.slideshow.SlideshowFragment;

import java.util.ArrayList;

import models.Enquete;
import repositories.EnqueteRepositorie;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    ListView listViewEnquete;
    EnqueteRepositorie reposEnquetes;
    SharedPreferences preferences;
    ArrayList<Enquete> enquetes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listViewEnquete = root.findViewById(R.id.listViewEnquete);
        reposEnquetes = new EnqueteRepositorie(getContext());
        preferences = getActivity().getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);
        int idUser = preferences.getInt("id",0);
        enquetes = reposEnquetes.recupererEnquetes(idUser);

        listViewEnquete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getActivity(), EnqueteDetailsActivity.class);
                intent.putExtra("enquete",enquetes.get(position));
                startActivity(intent);
            }
        });


        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        int idUser = preferences.getInt("id",0);
        enquetes = reposEnquetes.recupererEnquetes(idUser);
        String[] titres = new String[enquetes.size()];
        String[] descriptions = new String[enquetes.size()];

        for(int i=0;i < enquetes.size();i++){
            Enquete e = enquetes.get(i);
            titres[i] = e.getTitre();
            descriptions[i] = e.getDescription();
        }

        MyListAdapter adapter = new MyListAdapter(getActivity(),titres,descriptions);
        listViewEnquete.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}