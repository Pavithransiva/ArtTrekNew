package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ChatActionPage_L0 extends AppCompatActivity {

    private Button bt_previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_action_page_0);

        bt_previous = findViewById(R.id.chatactionp0_btn_previous);

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActionPage_L0.this, ChatLandingPage.class);
                startActivity(intent);
            }
        });
    }
}
