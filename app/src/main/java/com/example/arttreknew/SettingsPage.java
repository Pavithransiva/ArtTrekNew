package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.settings_page);

        final Button log_out = findViewById(R.id.button6);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(SettingsPage.this,LoginPage.class));

            }
        });
    }
}