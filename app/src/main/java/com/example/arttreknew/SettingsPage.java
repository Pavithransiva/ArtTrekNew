package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.settings_page);

        final Button log_out = findViewById(R.id.button6);
        final Button Help = findViewById(R.id.button5);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SettingsPage.this,LoginPage.class));
                finish();
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SettingsPage.this,HelpSettings.class));
                finish();
            }
        });

        // for MapFunction testing purposes
        final Button testing = findViewById(R.id.button2);
        testing.setOnClickListener(view -> {
            startActivity(new Intent(SettingsPage.this,MapFunction.class));
            finish();
        });
    }
}