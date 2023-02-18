package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SettingsAccountPage extends AppCompatActivity {
    Button deleteAccount;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    TextView Email;
    private String currentEmail;
    DialogInterface dialogInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.settings_account);

        TextView Email = findViewById(R.id.Email);
        deleteAccount = findViewById(R.id.btnDeleteAccount);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        assert firebaseUser != null;
        Email.setText(firebaseUser.getEmail());

        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsAccountPage.this);
                dialog.setTitle("Are you sure?");
                dialog.setMessage("Deleting this account will results in completely removing your" + "account from the system and you wont be able to access the app.");
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                        firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    DeleteAccount(currentEmail);
                                    Toast.makeText(SettingsAccountPage.this,"Account Deleted", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(SettingsAccountPage.this, SignUpPage.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(SettingsAccountPage.this, task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });

        ImageButton button8 = findViewById(R.id.imageButton61);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsAccountPage.this, SettingsPage.class);
                startActivity(intent);
            }
        });

    }
    public void DeleteAccount(String currentEmail){
        FirebaseDatabase mDB = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/");
        //just user deletion
        mDB.getReference("location").child("users").child(currentEmail.replace(".", "%")).removeValue();
        //comment deletion
        mDB.getReference("Comments").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postkey: snapshot.getChildren()){
                    for (DataSnapshot commentkey : postkey.getChildren()){
                        if (Objects.equals(commentkey.child("publisher").getValue(String.class), currentEmail.replace(".", "%"))){
                            mDB.getReference("Comments").child(postkey.getKey()).child(commentkey.getKey()).removeValue();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //for like deletion
        mDB.getReference("Likes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postkey: snapshot.getChildren()){
                    if (postkey.child(currentEmail.replace(".","%")).exists()){
                        mDB.getReference("Likes").child(postkey.getKey()).child(currentEmail.replace(".","%")).removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //for follow deletion
        mDB.getReference("follow").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot Emailnode: snapshot.getChildren()){
                    if (Objects.equals(Emailnode.getKey(), currentEmail.replace(".", "%"))){
                        mDB.getReference("follow").child(Emailnode.getKey()).removeValue();
                    }
                    for(DataSnapshot node : Emailnode.getChildren()){
                        for (DataSnapshot fnode : node.getChildren()){
                            if(Objects.equals(fnode.getKey(), currentEmail.replace(".", "%"))){
                                mDB.getReference("follow").child(Emailnode.getKey()).child(node.getKey()).child(fnode.getKey()).removeValue();
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}