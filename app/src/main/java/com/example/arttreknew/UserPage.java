package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Objects;

public class UserPage extends AppCompatActivity {
    private BottomNavigationView bnv;
    private Button mButtonUploadPic;

    private ImageView mBGImageView;

    private DatabaseReference mDatabaseRef;

    private final String currUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

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

        ShapeableImageView userpage_bgimage = findViewById(R.id.userpage_bgimage);
        TextView userpage_username = findViewById(R.id.userpage_username);
        TextView userpage_jobtitle = findViewById(R.id.userpage_jobtitle);

        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference("location").child("users").child(currUserEmail.replace(".", "%"));
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                if (data.get("fullname") != null) {
                    String dataFullname = (String) data.get("fullname");
                    userpage_username.setText(dataFullname, TextView.BufferType.EDITABLE);
                }
                if (data.get("imageURL") != null) {
                    String dataImageURL = (String) data.get("imageURL");
                    Glide.with(getApplicationContext()).load(dataImageURL).into(userpage_bgimage);
                }
                if (data.get("title") != null) {
                    String dataTitle = (String) data.get("title");
                    userpage_jobtitle.setText(dataTitle, TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
