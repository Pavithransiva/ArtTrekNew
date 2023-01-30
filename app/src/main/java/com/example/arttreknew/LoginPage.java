package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        final EditText Email = findViewById(R.id.editTextTextEmailAddress);
        final EditText Password = findViewById(R.id.editTextTextPassword);
        final ImageButton Login = findViewById(R.id.imageButton11);
        final ImageButton SignUpNow = findViewById(R.id.imageButton17);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                    final String emailTxt = Email.getText().toString();
                    final String PasswordTxt = Password.getText().toString();

                    if(emailTxt.isEmpty()|| PasswordTxt.isEmpty()){
                Toast.makeText(LoginPage.this, "Please enter your email or password", Toast.LENGTH_SHORT).show();
                    }
             }
        });
                SignUpNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //open BackupMainActivity activity
                        startActivity(new Intent(LoginPage.this,BackupMainActivity.class));
                    }
                });

    }
}