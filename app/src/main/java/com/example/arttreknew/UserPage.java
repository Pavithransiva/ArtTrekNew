package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserPage extends AppCompatActivity {
    private BottomNavigationView bnv;
    private Button mButtonUploadPic;

    private ImageView mBGImageView;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.user_page);

        mButtonUploadPic = findViewById(R.id.userpage_button_upload_pic);
        mBGImageView = findViewById(R.id.userpage_bgimage);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile_pic");

        mButtonUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPage.this, EditProfilePage.class);
                startActivity(intent);
            }
        });

        MaterialButton settingsBtn = findViewById(R.id.userpage_ic_settings);
        settingsBtn.setOnClickListener(view -> {
            startActivity(new Intent(UserPage.this, SettingsPage.class));
            finish();
        });

        //Bottom Navigation Code
        bnv = findViewById(R.id.userpage_bottomNavigationView);
        //set the icon to active
        bnv.setSelectedItemId(R.id.botnav_ic_profile);

        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.botnav_ic_home:
                    startActivity(new Intent(UserPage.this, HomePage.class));
                    finish();
                    return true;
                case R.id.botnav_ic_map:
                    startActivity(new Intent(UserPage.this, MapFunction.class));
                    finish();
                    return true;
            }
            return false;

        });

        // ERROR - Retrive Image from the Database
        /*mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(
                        String.class);
                Picasso.with(UserPage.this).load(link).into(mBGImageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }); */
    }

}
