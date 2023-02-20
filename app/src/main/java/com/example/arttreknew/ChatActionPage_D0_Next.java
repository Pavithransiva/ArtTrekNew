package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class ChatActionPage_D0_Next extends AppCompatActivity {

    private Button bt_previous;
    private TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.chat_action_page_d0_next);

        bt_previous = findViewById(R.id.chatactionp0_btn_previous);
        tv_time = findViewById(R.id.chatactionp_ccr_datetime);

        bt_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatActionPage_D0_Next.this, ChatLandingPage.class);
                startActivity(intent);
            }
        });

        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        tv_time.setText(dateString);
    }
}
