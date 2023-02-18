package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.settings_page);

        ImageButton button = findViewById(R.id.settingslandingpage_button_previous);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, UserPage.class);
                startActivity(intent);
            }
        });

        Button button88 = findViewById(R.id.settingpage_button_noti);
        button88.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, SettingsNotificationPage.class);
                startActivity(intent);
            }
        });

        Button button5 = findViewById(R.id.settingpage_button_privacy);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, SettingsPrivacyPage.class);
                startActivity(intent);
            }
        });

        Button button6 = findViewById(R.id.settingpage_button_security);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, SettingsSecurityPage.class);
                startActivity(intent);
            }
        });

        Button button7 = findViewById(R.id.settingpage_button_account);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsPage.this, SettingsAccountPage.class);
                startActivity(intent);

            }
        });

        final Button log_out = findViewById(R.id.settingpage_button_logout);
        final Button Help = findViewById(R.id.settingpage_button_help);

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
                startActivity(new Intent(SettingsPage.this, SettingsHelp.class));
                finish();
            }
        });
    }
}