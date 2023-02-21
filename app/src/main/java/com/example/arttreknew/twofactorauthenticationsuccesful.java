package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class twofactorauthenticationsuccesful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twofactor_verificationsuccessful);
        ImageButton ProceedToHome;

        ProceedToHome = findViewById(R.id.imageButton28);
        ProceedToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(twofactorauthenticationsuccesful.this,Onboarding_page_1.class));

            }
        });
    }
}