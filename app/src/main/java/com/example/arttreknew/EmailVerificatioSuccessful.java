package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EmailVerificatioSuccessful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verificationsuccess_fromsignup);

        ImageButton Yes = (findViewById(R.id.imageButton39));
        ImageButton No = (findViewById(R.id.imageButton38));

        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent2 = getIntent();
                String emailTxt = intent2.getStringExtra("emailTxt");
                String passwordTxt = intent2.getStringExtra("passwordTxt");
                String fullname = intent2.getStringExtra("fullname");
                Intent intent = new Intent(getApplicationContext(), twoFactorauthenticationfromsignup.class);
                intent.putExtra("emailTxt",emailTxt);
                intent.putExtra("passwordTxt",passwordTxt);
                intent.putExtra("fullname",fullname);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);

                //open BackupMainActivity activity
              // startActivity(new Intent(EmailVerificatioSuccessful.this, twoFactorauthenticationfromsignup.class));
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(EmailVerificatioSuccessful.this, Onboarding_page_1.class));
            }
        });
    }
}