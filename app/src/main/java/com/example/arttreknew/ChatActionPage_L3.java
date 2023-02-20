package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class ChatActionPage_L3 extends AppCompatActivity {

    private Button bt_previous;
    private Button bt_send;
    private EditText et_chat;
    private TextView tv_chat;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_action_page_3);

        bt_previous = findViewById(R.id.chatactionp0_btn_previous);
        bt_send = findViewById(R.id.button_send);
        et_chat = findViewById(R.id.editText_chat);
        tv_chat = findViewById(R.id.chatactionp_ccr_text1);
        tv_time = findViewById(R.id.chatactionp_ccr_datetime1);

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActionPage_L3.this, ChatLandingPage.class);
                startActivity(intent);
            }
        });

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = et_chat.getText().toString();
                tv_chat.setText(message);

                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
                String dateString = sdf.format(date);
                tv_time.setText(dateString);
            }
        });
    }
}
