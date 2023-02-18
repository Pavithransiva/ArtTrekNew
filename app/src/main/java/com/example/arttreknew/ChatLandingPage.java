package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.Objects;

public class ChatLandingPage extends AppCompatActivity {

    private BottomNavigationView bnv;
    private Button btn_new_message;
    private ConstraintLayout cl_user0, cl_user1, cl_user2, cl_user3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_landing_page);

        bnv = findViewById(R.id.chatLandingPage_bottomNavigationView);
        btn_new_message = findViewById(R.id.chatlandingPage_button_newmessage);
        cl_user0 = findViewById(R.id.cldp_container_user0);
        cl_user1 = findViewById(R.id.cldp_container_user1);
        cl_user2 = findViewById(R.id.cldp_container_user2);
        cl_user3 = findViewById(R.id.cldp_container_user3);

        bnv.setSelectedItemId(R.id.botnav_ic_chat);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.botnav_ic_home:
                    startActivity(new Intent(ChatLandingPage.this, HomePage.class));
                    finish();
                    return true;
                case R.id.botnav_ic_map:
                    startActivity(new Intent(ChatLandingPage.this, MapFunction.class));
                    finish();
                    return true;
                case R.id.botnav_ic_profile:
                    startActivity(new Intent(ChatLandingPage.this, UserPage.class));
                    finish();
                    return true;
            }
            return false;

        });

        btn_new_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatLandingPage.this, ChatNewMessagePage.class);
                startActivity(intent);
            }
        });

        cl_user0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatLandingPage.this, ChatActionPage_L0.class);
                startActivity(intent);
            }
        });

        cl_user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatLandingPage.this, ChatActionPage_L1.class);
                startActivity(intent);
            }
        });

        cl_user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatLandingPage.this, ChatActionPage_L2.class);
                startActivity(intent);
            }
        });

        cl_user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatLandingPage.this, ChatActionPage_L3.class);
                startActivity(intent);
            }
        });

    }
}