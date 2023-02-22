package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class twofactorauthenticationsuccesful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twofactor_verificationsuccessful);
        ImageButton ProceedToHome;

        ProceedToHome = findViewById(R.id.imageButton28);
        ProceedToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = getIntent();
                String emailTxt = intent2.getStringExtra("emailTxt");
                String passwordTxt = intent2.getStringExtra("passwordTxt");
                String fullname = intent2.getStringExtra("fullname");


                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailTxt, passwordTxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Login is successful
                        Intent intent = new Intent(getApplicationContext(), Onboarding_page_1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);

                        //startActivity(new Intent(getApplicationContext(),EmailVerificationFromSignIn.class));

                        //  startActivity(new Intent(getApplicationContext(), HomePage.class));

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(twofactorauthenticationsuccesful.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                //open BackupMainActivity activity
              //  startActivity(new Intent(twofactorauthenticationsuccesful.this,Onboarding_page_1.class));

            }
        });
    }
}