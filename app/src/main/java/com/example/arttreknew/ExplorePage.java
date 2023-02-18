package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class ExplorePage extends AppCompatActivity {

    private BottomNavigationView bnv;
    private ImageView imgview0, imgview1, imgview2, imgview3;
    private Button homebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.explore_page);

        bnv = findViewById(R.id.explorePage_bottomNavigationView);
        imgview0 = findViewById(R.id.epglc_imageView_0);
        imgview1 = findViewById(R.id.epglc_imageView_1);
        imgview2 = findViewById(R.id.epglc_imageView_2);
        imgview3 = findViewById(R.id.epglc_imageView_3);
        homebtn = findViewById(R.id.explorePage_button_home);

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.botnav_ic_home:
                        startActivity(new Intent(ExplorePage.this, HomePage.class));
                        finish();
                        return true;
                    case R.id.botnav_ic_map:
                        startActivity(new Intent(ExplorePage.this, MapFunction.class));
                        finish();
                        return true;
                    case R.id.botnav_ic_profile:
                        startActivity(new Intent(ExplorePage.this, UserPage.class));
                        finish();
                        return true;
                    case R.id.botnav_ic_chat:
                        startActivity(new Intent(ExplorePage.this, ChatLandingPage.class));
                        finish();
                        return true;
                }
                return false;
            }
        });

        imgview0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorePage.this, ImageDetailsPage_0.class);
                startActivity(intent);
            }
        });

        imgview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorePage.this, ImageDetailsPage_1.class);
                startActivity(intent);
            }
        });

        imgview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorePage.this, ImageDetailsPage_2.class);
                startActivity(intent);
            }
        });

        imgview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorePage.this, ImageDetailsPage_3.class);
                startActivity(intent);
            }
        });

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExplorePage.this, HomePage.class);
                startActivity(intent);
            }
        });

    }
}