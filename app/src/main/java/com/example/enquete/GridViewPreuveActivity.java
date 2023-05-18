package com.example.enquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import models.Preuve;
import repositories.PreuveRepositorie;

public class GridViewPreuveActivity extends AppCompatActivity {
    private GridView gridView;
    private GridPreuveAdapter adapter;

    private List<Preuve> itemList;
    private PreuveRepositorie reposPreuves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view_preuve);
        gridView = (GridView) findViewById(R.id.gridView);
        reposPreuves = new PreuveRepositorie(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Preuve preuve = adapter.getItem(i);
                Intent intent = new Intent(GridViewPreuveActivity.this,FormPreuveActivity.class);
                intent.putExtra("id_preuve",preuve.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int idEnquet = getIntent().getIntExtra("id_enquete",0);
        itemList = reposPreuves.recupererPreuves(idEnquet);
        adapter = new GridPreuveAdapter(this,itemList);
        gridView.setAdapter(adapter);
    }
}