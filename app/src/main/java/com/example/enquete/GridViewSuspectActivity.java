package com.example.enquete;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import models.Preuve;
import models.Suspect;
import repositories.PreuveRepositorie;
import repositories.SuspectRepositorie;

public class GridViewSuspectActivity extends AppCompatActivity {
    private GridView gridView;
    private GridSuspectAdapter adapter;

    private List<Suspect> itemList;
    private SuspectRepositorie reposSuspects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_suspect);
        gridView = (GridView) findViewById(R.id.gridView);


        reposSuspects = new SuspectRepositorie(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Suspect suspect = adapter.getItem(i);
                Intent intent = new Intent(GridViewSuspectActivity.this,FormSuspectActivity.class);
                intent.putExtra("id_suspect",suspect.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int idEnquet = getIntent().getIntExtra("id_enquete",0);
        itemList = reposSuspects.recupererSuspects(idEnquet);

        adapter = new GridSuspectAdapter(this,itemList);
        gridView.setAdapter(adapter);
    }
}