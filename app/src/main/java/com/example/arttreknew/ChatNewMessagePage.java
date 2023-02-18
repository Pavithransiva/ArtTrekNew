package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class ChatNewMessagePage extends AppCompatActivity {

    private TextView tv_cancel;
    private ConstraintLayout cl_user0, cl_user1, cl_user2, cl_user3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_newmessage_page);

        tv_cancel = findViewById(R.id.cnmp_cancel);
        cl_user0 = findViewById(R.id.cnmp_container_user0);
        cl_user1 = findViewById(R.id.cnmp_container_user1);
        cl_user2 = findViewById(R.id.cnmp_container_user2);
        cl_user3 = findViewById(R.id.cnmp_container_user3);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatNewMessagePage.this, ChatLandingPage.class);
                startActivity(intent);
            }
        });

        cl_user0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatNewMessagePage.this, ChatActionPage_D0.class);
                startActivity(intent);
            }
        });

        cl_user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatNewMessagePage.this, ChatActionPage_D1.class);
                startActivity(intent);
            }
        });

        cl_user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatNewMessagePage.this, ChatActionPage_D2.class);
                startActivity(intent);
            }
        });

        cl_user3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatNewMessagePage.this, ChatActionPage_D3.class);
                startActivity(intent);
            }
        });

    }
}
