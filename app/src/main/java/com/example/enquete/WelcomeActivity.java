package com.example.enquete;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button btn_connexion;
    Button btn_inscription;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        btn_connexion = findViewById(R.id.btn_connexion);
        btn_inscription=findViewById(R.id.btn_inscription);
        preferences = getSharedPreferences(MainActivity.PREFERENCE, Context.MODE_PRIVATE);



        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_inscription.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Intent intent= new Intent(WelcomeActivity.this,inscriptionActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(LoginActivity.isUserLogin(preferences)){
            Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }
}