package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class VerificationMethod extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_method);
        ImageButton button = findViewById(R.id.imageButton20);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationMethod.this, EmailVerificationSignUp.class);
                startActivity(intent);
            }
        });
        ImageButton button2 = findViewById(R.id.imageButton21);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerificationMethod.this, TwoFactorAuthentication.class);
                startActivity(intent);
            }
        });

    }
}