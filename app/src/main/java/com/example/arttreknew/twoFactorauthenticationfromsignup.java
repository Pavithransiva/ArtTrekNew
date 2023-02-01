package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class twoFactorauthenticationfromsignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twofactorauthentication_fromsignup);

        final EditText inputMobile = findViewById(R.id.editTextPhone);
        ImageButton GetOtp = findViewById(R.id.imageButton41);
        FirebaseAuth Auth;


        final ProgressBar progressBar ;
        FirebaseAuth app = FirebaseAuth.getInstance();

        GetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()){
                    Toast.makeText(twoFactorauthenticationfromsignup.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhoneAuthProvider.getInstance(app).verifyPhoneNumber(
                        "+60" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        twoFactorauthenticationfromsignup.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                            Toast.makeText(twoFactorauthenticationfromsignup.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                Intent intent = new Intent(getApplicationContext(), TwoFactorAuthentication.class);
                                intent.putExtra("mobile", inputMobile.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );

            }
        });




    }
}