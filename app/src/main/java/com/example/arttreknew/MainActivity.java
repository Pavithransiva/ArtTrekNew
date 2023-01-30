package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //create object of DatabaseReference class to access firebase's Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        final EditText name = findViewById(R.id.editTextTextPersonName2);
        final EditText email = findViewById(R.id.editTextTextEmailAddress3);
        final EditText password = findViewById(R.id.editTextTextPassword4);
        final EditText conPassword = findViewById(R.id.editTextTextPassword5);
        final ImageButton sign_up = findViewById(R.id.imageButton13);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get data from EditTexts into String variables
                final String nameTxt = name.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                // check if user fill all the fields before sending data to firebase
                if(nameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // check if passwords are matching with each other
                // if not matching with each other then show a toast message
                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(MainActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();

            }
                else{

        });

            }
        });


    }

}