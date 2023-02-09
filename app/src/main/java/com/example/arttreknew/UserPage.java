package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPage extends AppCompatActivity {
    private BottomNavigationView bnv;
    // Initializing the ImageView
    ImageView rImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.user_page);

        // getting ImageView by its id
        rImage = findViewById(R.id.userpage_bgimage);

        // we will get the default FirebaseDatabase instance
        FirebaseDatabase firebaseDatabase
                = FirebaseDatabase.getInstance();

        // we will get a DatabaseReference for the database
        // root node
        DatabaseReference databaseReference
                = firebaseDatabase.getReference("uploads");

        // Here "image" is the child node value we are
        // getting child node data in the getImage variable
        DatabaseReference getImage
                = databaseReference.child("image");

        // Adding listener for a single change
        // in the data at this location.
        // this listener will triggered once
        // with the value of the data at the location
        getImage.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(
                            @NonNull DataSnapshot dataSnapshot)
                    {
                        // getting a DataSnapshot for the
                        // location at the specified relative
                        // path and getting in the link variable
                        String link = dataSnapshot.getValue(
                                String.class);

                        // loading that data into rImage
                        // variable which is ImageView
                        //Picasso.get().load(link).into(rImage);
                    }

                    // this will called when any problem
                    // occurs in getting data
                    @Override
                    public void onCancelled(
                            @NonNull DatabaseError databaseError)
                    {
                        // we are showing that error message in
                        // toast
                        Toast
                                .makeText(UserPage.this,
                                        "Error Loading Image",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                });
        bnv = findViewById(R.id.userpage_bottomNavigationView);
        //set the icon to active
        bnv.setSelectedItemId(R.id.botnav_ic_profile);
        /*bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.botnav_ic_home:
                        startActivity(new Intent(UserPage.this, HomePage.class));
                        finish();
                    case R.id.botnav_ic_map:
                        startActivity(new Intent(UserPage.this, MapFunction.class));
                        finish();
                    case R.id.botnav_ic_community:
                        startActivity(new Intent(UserPage.this, HomePage.class));
                        finish();
                }
                return false;

            }
        });
        View homebtn = findViewById(R.id.botnav_ic_home);*/
    }
}
