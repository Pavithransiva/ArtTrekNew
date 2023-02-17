package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerificationFromSignIn extends AppCompatActivity {

    FirebaseAuth Auth;
    ImageButton VerifyEmail;
    ImageButton DoneVerifyingSignIn;

    TextView VerifyNowMsg;
    String email;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.email_verificationpagefromsignin);

        VerifyEmail = findViewById(R.id.imageButton23);
        DoneVerifyingSignIn = findViewById(R.id.imageDone);
        Auth = FirebaseAuth.getInstance();
        email = Auth.getCurrentUser().getEmail().replace(".", "%");

        VerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification to email
                Auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmailVerificationFromSignIn.this, "Verification email Sent.", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        DoneVerifyingSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), HomePage.class));
                finish();

            }
        });

    }
}