package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class EmailVerificationSignUp extends AppCompatActivity {

   FirebaseAuth Auth;
    ImageButton VerifyEmail;
    ImageButton DoneVerifying;
    TextView VerifyNowMsg;
    String email;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
      setContentView(R.layout.emailverification_fromsignup);

        VerifyEmail = findViewById(R.id.imageButton35);
        VerifyNowMsg = findViewById(R.id.VerifyEmailNow);
        DoneVerifying = findViewById(R.id.imageButton12);
        Auth = FirebaseAuth.getInstance();
        email = Auth.getCurrentUser().getEmail();

        VerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send verification to email
                Auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EmailVerificationSignUp.this, "Verification email Sent.", Toast.LENGTH_SHORT).show();

                    }
                });

            }
            });
            DoneVerifying.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        startActivity(new Intent(getApplicationContext(), EmailVerificatioSuccessful.class));
                        finish();

            }
        });

        }
}