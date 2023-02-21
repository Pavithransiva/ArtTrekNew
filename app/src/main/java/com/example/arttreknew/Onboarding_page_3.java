package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.Objects;

public class Onboarding_page_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.onboarding_page3);

        ImageButton button8 = findViewById(R.id.imageButton8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_3.this, UserPage.class);
                startActivity(intent);
            }
        });

        ImageButton button9 = findViewById(R.id.imageButton9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_3.this, Onboarding_page_2.class);
                startActivity(intent);
            }
        });

        ImageButton button10 = findViewById(R.id.imageButton10);
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_3.this, HomePage.class);
                startActivity(intent);
            }
        });
    }
}