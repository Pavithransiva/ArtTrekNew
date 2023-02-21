package com.example.arttreknew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignUpPage extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
     FirebaseAuth Auth;


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

        final ImageButton sign_in = findViewById(R.id.imageButton14);
        final ImageButton back = findViewById(R.id.imageButton2);






        mDatabase = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mRef = mDatabase.getReference("location");
        Auth = FirebaseAuth.getInstance();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(SignUpPage.this,LoginPage.class));

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //open BackupMainActivity activity
                startActivity(new Intent(SignUpPage.this,LoginPage.class));
                finish();
            }
        });

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
                    Toast.makeText(SignUpPage.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                } else if (passwordTxt.length() < 6) {
                    Toast.makeText(SignUpPage.this, "Passwords must be at least 6 characters long!", Toast.LENGTH_SHORT).show();
                }

                // check if passwords are matching with each other
                // if not matching with each other then show a toast message
                else if (!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(SignUpPage.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();

                } else {

                    mRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 String Txt = emailTxt.replace(".", "%");



                                // check if email is not registered before
                                if(snapshot.hasChild(Txt)){
                                    Toast.makeText(SignUpPage.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                            }
                                else{
                                    Auth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            startActivity(new Intent(getApplicationContext(),EmailVerificationSignUp.class));
                                            finish();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SignUpPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    //sending data to firebase Realtime Database.
                                    // we are using email as unique identity of every user.
                                    //so all the other details of user comes under email
                                    mRef.child("users").child(Txt).child("fullname").setValue(nameTxt);
                                    mRef.child("users").child(Txt).child("email").setValue(emailTxt);
                                    mRef.child("users").child(Txt).child("password").setValue(passwordTxt);

                                    // save email to memory
                                    MemoryData.saveData(emailTxt,SignUpPage.this);

                                    // save name to memory
                                    MemoryData.saveData(nameTxt,SignUpPage.this);


                                    // show a success message then finish the activity
                                    Toast.makeText(SignUpPage.this, "User Registered Successfully.", Toast.LENGTH_SHORT).show();

                                    sign_up.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {


                                            startActivity(new Intent(SignUpPage.this,EmailVerificationSignUp.class));


                                        }
                                    });
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