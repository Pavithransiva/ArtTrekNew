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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class twoFactorauthenticationfromsignup extends AppCompatActivity {
    private final String currUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twofactorauthentication_fromsignup);

        final EditText inputMobile = findViewById(R.id.editTextPhone);
        ImageButton GetOtp = findViewById(R.id.imageButton41);
        FirebaseAuth Auth;
        FirebaseUser firebaseUser;
        DatabaseReference databaseReference;


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
                                Intent intent2 = getIntent();
                                String emailTxt = intent2.getStringExtra("emailTxt");
                                String passwordTxt = intent2.getStringExtra("passwordTxt");
                                String fullname = intent2.getStringExtra("fullname");
                                Intent intent = new Intent(getApplicationContext(), TwoFactorAuthentication.class);
                                intent.putExtra("emailTxt",emailTxt);
                                intent.putExtra("passwordTxt",passwordTxt);
                                intent.putExtra("fullname",fullname);
                                intent.putExtra("mobile", inputMobile.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);

                               // Intent intent = new Intent(getApplicationContext(), TwoFactorAuthentication.class);
                               // intent.putExtra("mobile", inputMobile.getText().toString());
                              // intent.putExtra("verificationId",verificationId);
                               // startActivity(intent);
                            }
                        }
                );

            }
        });
        ImageButton button40 = findViewById(R.id.imageButton40);
        button40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(twoFactorauthenticationfromsignup.this, EmailVerificatioSuccessful.class);
                startActivity(intent);
            }
        });




    }
}