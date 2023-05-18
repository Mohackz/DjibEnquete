package com.example.enquete;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import models.Suspect;
import models.Victime;
import repositories.SuspectRepositorie;
import repositories.VictimeRepositorie;

public class GridViewVictimeActivity extends AppCompatActivity {
    private GridView gridView;
    private GridVictimeAdapter adapter;

    private List<Victime> itemList;
    private VictimeRepositorie reposVictimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_victime);
        gridView = (GridView) findViewById(R.id.gridView);


        reposVictimes = new VictimeRepositorie(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        int idEnquet = getIntent().getIntExtra("id_enquete",0);
        itemList = reposVictimes.recupererVictimes(idEnquet);

        adapter = new GridVictimeAdapter(this,itemList);
        gridView.setAdapter(adapter);
    }
}