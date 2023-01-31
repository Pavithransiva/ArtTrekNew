package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class EmailVerificationSignUp extends AppCompatActivity {

   FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
      setContentView(R.layout.emailverification_fromsignup);

       ImageButton Verify = findViewById(R.id.imageButton35);
        Auth = FirebaseAuth.getInstance();

      // if(!Auth.getCurrentuser().isEmailVerified()){
           Verify.setVisibility(View.VISIBLE);
       }}



     //  Verify.setOnClickListener(new View.OnClickListener() {
         // @Override
      //     public void onClick(View view) {
                //send email link to new registered users
         //     Auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            //        @Override
            ///       public void onComplete(@NonNull Task<Void> task) {
               //        if (task.isSuccessful()){
                  //        Toast.makeText(EmailVerificationSignUp.this, "Check your email to verify.", Toast.LENGTH_SHORT).show();
                 //       }


               //    }
             //  });

        //   }
   // });
//}}