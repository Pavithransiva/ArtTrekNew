package com.example.arttreknew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class BackupMainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.signup_page);

        final EditText name = findViewById(R.id.editTextTextPersonName2);
        final EditText email = findViewById(R.id.editTextTextEmailAddress3);
        final EditText password = findViewById(R.id.editTextTextPassword4);
        final EditText conPassword = findViewById(R.id.editTextTextPassword5);
        final ImageButton sign_up = findViewById(R.id.imageButton13);

        mDatabase = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mRef = mDatabase.getReference("location");

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get data from EditTexts into String variables
                final String nameTxt = name.getText().toString();
                 final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                // check if user fill all the fields before sending data to firebase
                if (nameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(BackupMainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                // check if passwords are matching with each other
                // if not matching with each other then show a toast message
                else if (!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(BackupMainActivity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();

                } else {

                    mRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                             String Txt = emailTxt.replace(".", "%");


                            // check if email is not registered before
                            if(snapshot.hasChild(Txt)){
                                Toast.makeText(BackupMainActivity.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                        }
                            else{

                                //sending data to firebase Realtime Database.
                                // we are using email as unique identity of every user.
                                //so all the other details of user comes under email
                                mRef.child("users").child(Txt).child("fullname").setValue(nameTxt);
                                mRef.child("users").child(Txt).child("email").setValue(emailTxt);
                                mRef.child("users").child(Txt).child("password").setValue(passwordTxt);

                                // show a success message then finish the activity
                                Toast.makeText(BackupMainActivity.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });





                }
            }
        });

    }


}