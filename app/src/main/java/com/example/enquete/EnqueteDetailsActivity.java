package com.example.enquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import models.Enquete;

public class EnqueteDetailsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    Enquete enquete;
    TextView textViewTitre, textViewLieu, textViewDate, textViewEtat, textViewDescription,ajoutSuspectActionText, ajoutVictimeActionText, ajoutPreuveActionText;
    FloatingActionButton mAjoutSuspect, mAjoutVictime, mAjoutPreuve;
    ExtendedFloatingActionButton mAddFab;
    Button voirPreuves, voirVictimes, voirSuspects;
    boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enquete_details);
        textViewTitre = (TextView) findViewById(R.id.textViewTitre);
        textViewLieu = (TextView) findViewById(R.id.textViewLieu);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewEtat = (TextView) findViewById(R.id.textViewEtat);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        voirPreuves = (Button) findViewById(R.id.btnVoirPreuves);
        voirSuspects = (Button) findViewById(R.id.btnVoirSuspects);
        voirVictimes = (Button) findViewById(R.id.btnVoirVictimes);

                preferences = getSharedPreferences(MainActivity.PREFERENCE,MODE_PRIVATE);
        Intent intent = getIntent();
        enquete = (Enquete) intent.getSerializableExtra("enquete");

        textViewTitre.setText(enquete.getTitre());
        textViewLieu.setText(enquete.getLieu());
        textViewDate.setText(enquete.getDate());
        textViewEtat.setText(enquete.getEtat());
        textViewDescription.setText(enquete.getDescription());

        mAddFab = (ExtendedFloatingActionButton) findViewById(R.id.add_fab);

        mAjoutSuspect = (FloatingActionButton) findViewById(R.id.add_suspect);
        mAjoutVictime = (FloatingActionButton) findViewById(R.id.add_victime);
        mAjoutPreuve = (FloatingActionButton) findViewById(R.id.add_preuve);

        ajoutSuspectActionText = findViewById(R.id.add_suspect_action_text);
        ajoutVictimeActionText = findViewById(R.id.add_victime_action_text);
        ajoutPreuveActionText = findViewById(R.id.add_preuve_action_text);

        mAjoutSuspect.setVisibility(View.GONE);
        mAjoutVictime.setVisibility(View.GONE);
        mAjoutPreuve.setVisibility(View.GONE);
        ajoutSuspectActionText.setVisibility(View.GONE);
        ajoutVictimeActionText.setVisibility(View.GONE);
        ajoutPreuveActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;
        mAddFab.shrink();


        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {
                            mAjoutSuspect.show();
                            mAjoutVictime.show();
                            mAjoutPreuve.show();
                            ajoutSuspectActionText.setVisibility(View.VISIBLE);
                            ajoutVictimeActionText.setVisibility(View.VISIBLE);
                            ajoutPreuveActionText.setVisibility(View.VISIBLE);

                            mAddFab.extend();

                            isAllFabsVisible = true;
                        } else {
                            mAjoutVictime.hide();
                            mAjoutSuspect.hide();
                            mAjoutPreuve.hide();
                            ajoutVictimeActionText.setVisibility(View.GONE);
                            ajoutSuspectActionText.setVisibility(View.GONE);
                            ajoutPreuveActionText.setVisibility(View.GONE);

                            mAddFab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                });

        mAjoutVictime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,FormVictimeActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });

        mAjoutPreuve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,FormPreuveActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });

        mAjoutSuspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,FormSuspectActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });

        voirPreuves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,GridViewPreuveActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });

        voirSuspects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,GridViewSuspectActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });

        voirVictimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnqueteDetailsActivity.this,GridViewVictimeActivity.class);
                intent.putExtra("id_enquete",enquete.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(EnqueteDetailsActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
}