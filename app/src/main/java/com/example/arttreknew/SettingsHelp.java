package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsHelp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.settings_help);
        super.onCreate(savedInstanceState);
        final Button HelpCenter = findViewById(R.id.button10);
        final Button SupportRequests = findViewById(R.id.button11);
        final Button PrivacySecurity = findViewById(R.id.button12);


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