package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Objects;

public class SettingsHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.settings_help);
        final Button HelpCenter = findViewById(R.id.button10);
        final Button SupportRequests = findViewById(R.id.button11);
        final Button PrivacySecurity = findViewById(R.id.button12);
        final ImageButton backBtn = findViewById(R.id.imageButton62);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsHelp.this,SettingsPage.class);
                startActivity(intent);
            }
        });


        HelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/601151548549"));
                startActivity(browserIntent);

            }
        });
        SupportRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/bGA88C2x8EU38TXE9"));
                startActivity(browserIntent);

            }
        });
        PrivacySecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/bGA88C2x8EU38TXE9"));
                startActivity(browserIntent);

            }
        });
    }
}