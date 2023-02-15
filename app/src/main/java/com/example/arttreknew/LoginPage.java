package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        firebaseAuth = FirebaseAuth.getInstance();


         final EditText Email = findViewById(R.id.editTextTextEmailAddress);
         final EditText Password = findViewById(R.id.editTextTextPassword);
         final ImageButton Login = findViewById(R.id.imageButton11);
        final ImageButton SignUpNow = findViewById(R.id.imageButton17);

        SignUpNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(LoginPage.this, SignUpPage.class));
            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //extract / validate

                if(Email.getText().toString().isEmpty()){
                    Email.setError("Email is Missing.");
                    return;
                }
                if(Password.getText().toString().isEmpty()){
                    Password.setError("Password is Missing.");
                    return;
                }

                // data is valid
                // login user
                firebaseAuth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Login is successful

                        startActivity(new Intent(getApplicationContext(),HomePage.class));

                        startActivity(new Intent(getApplicationContext(), HomePage.class));

                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginPage.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
             }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext(),HomePage.class));

            startActivity(new Intent(getApplicationContext(), HomePage.class));

            finish();
        }
    }
}