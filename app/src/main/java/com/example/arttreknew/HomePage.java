package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        bnv = findViewById(R.id.hp_bottomNavigationView);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.botnav_ic_profile:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        finish();
                    case R.id.botnav_ic_home:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        finish();
                    case R.id.botnav_ic_map:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        finish();
                    case R.id.botnav_ic_community:
                        startActivity(new Intent(getApplicationContext(), UserPage.class));
                        finish();
                }
                return false;

            }
        });
    }
}