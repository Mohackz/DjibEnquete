package com.example.enquete;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.enquete.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import repositories.EnqueteRepositorie;
import repositories.PreuveRepositorie;
import repositories.SuspectRepositorie;
import repositories.VictimeRepositorie;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static final String PREFERENCE = "MyPreference";
    EnqueteRepositorie reposEnquetes;
    FloatingActionButton mAjoutSuspect, mAjoutVictime, mAjoutEnquete;
    ExtendedFloatingActionButton mAddFab;
    TextView ajoutSuspectActionText, ajoutVictimeActionText, ajoutEnqueteActionText;
    SharedPreferences preferences;
    TextView textViewNombreEnquetes, textViewNombreSuspects, textViewNombreVictimes, textViewNombrePreuves;

    boolean isAllFabsVisible;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setNavigationItemSelectedListener(this);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        preferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        reposEnquetes = new EnqueteRepositorie(this);

        textViewNombreEnquetes = (TextView) findViewById(R.id.textViewNombreEnquetes);
        textViewNombreVictimes = (TextView) findViewById(R.id.textViewNombreVictimes);
        textViewNombrePreuves= (TextView) findViewById(R.id.textViewNombrePreuves);
        textViewNombreSuspects= (TextView) findViewById(R.id.textViewNombreSuspects);


        mAddFab = (ExtendedFloatingActionButton) findViewById(R.id.add_fab);
        mAjoutEnquete = (FloatingActionButton) findViewById(R.id.add_enquete);

        ajoutSuspectActionText = findViewById(R.id.add_suspect_action_text);
        ajoutVictimeActionText = findViewById(R.id.add_victime_action_text);
        ajoutEnqueteActionText = findViewById(R.id.add_enquete_action_text);

        mAjoutEnquete.setVisibility(View.GONE);
        ajoutEnqueteActionText.setVisibility(View.GONE);

        isAllFabsVisible = false;
        mAddFab.shrink();


        mAddFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {
                            mAjoutEnquete.show();
                            ajoutEnqueteActionText.setVisibility(View.VISIBLE);

                            mAddFab.extend();

                            isAllFabsVisible = true;
                        } else {
                            mAjoutEnquete.hide();
                            ajoutEnqueteActionText.setVisibility(View.GONE);

                            mAddFab.shrink();

                            isAllFabsVisible = false;
                        }
                    }
                });

        mAjoutEnquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FormEnqueteActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("DJIB_ENQUETE","Item id: " + Integer.toString(item.getItemId()));
        if(item.getItemId() == R.id.deco) {
            Toast.makeText(this,"Deconnexion...",Toast.LENGTH_LONG).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}