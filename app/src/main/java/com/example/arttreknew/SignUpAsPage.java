package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class SignUpAsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupas_page);
        ImageButton button = findViewById(R.id.imageButton47);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAsPage.this, InterestPage.class);
                startActivity(intent);
            }
        });
        ImageButton button2 = findViewById(R.id.imageButton46);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpAsPage.this, InterestPage.class);
                startActivity(intent);
            }
        });
    }
}