package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ChatActionPage_D0 extends AppCompatActivity {

    private Button bt_previous;
    private Button bt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_action_page_d0);

        bt_previous = findViewById(R.id.chatactionp0_btn_previous);
        bt_send = findViewById(R.id.button_send);

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActionPage_D0.this, ChatLandingPage.class);
                startActivity(intent);
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActionPage_D0.this, ChatActionPage_D0_Next.class);
                startActivity(intent);
            }
        });
    }
}
