package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPage extends AppCompatActivity {
    EditText Email;
    ImageButton Proceed;
    ImageButton buttonBck;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        Email = findViewById(R.id.editTextTextEmailAddress2);
        Proceed = findViewById(R.id.imageButton29);
        buttonBck = findViewById(R.id.imageButton30);

        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e= Email.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordPage.this, "Email Sent", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ForgotPasswordPage.this, "Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        buttonBck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordPage.this, LoginPage.class);
                startActivity(intent);
            }
        });
    }
}