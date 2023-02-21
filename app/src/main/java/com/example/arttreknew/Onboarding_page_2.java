package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.Objects;

public class Onboarding_page_2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.onboarding_page2);

        ImageButton button5 = findViewById(R.id.imageButton5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_2.this, UserPage.class);
                startActivity(intent);
            }
        });

        ImageButton button6 = findViewById(R.id.imageButton6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_2.this, Onboarding_page_1.class);
                startActivity(intent);
            }
        });

        ImageButton button7 = findViewById(R.id.imageButton7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Onboarding_page_2.this, Onboarding_page_3.class);
                startActivity(intent);
            }
        });
    }
}