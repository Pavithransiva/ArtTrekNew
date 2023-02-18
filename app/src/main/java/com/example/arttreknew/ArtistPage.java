package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arttreknew.Adapter.MyFotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ArtistPage extends AppCompatActivity {
    private BottomNavigationView bnv;
    String currViewEmail;

    // post recycler view
    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    List<Post> postList;

    private final String currUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.artist_page);

        //Bottom Navigation Code
        bnv = findViewById(R.id.artistpage_bottomNavigationView);
        //set the icon to active
        bnv.setSelectedItemId(R.id.botnav_ic_home);

        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.botnav_ic_home:
                    startActivity(new Intent(ArtistPage.this, HomePage.class));
                    finish();
                    return true;
                case R.id.botnav_ic_map:
                    startActivity(new Intent(ArtistPage.this, MapFunction.class));
                    finish();
                    return true;
                case R.id.botnav_ic_chat:
                    startActivity(new Intent(ArtistPage.this, ChatLandingPage.class));
                    finish();
                    return true;
                case R.id.botnav_ic_profile:
                    startActivity(new Intent(ArtistPage.this, UserPage.class));
                    finish();
                    return true;
            }
            return false;

        });

        Intent intent = getIntent();
        currViewEmail = intent.getStringExtra("email");
        ShapeableImageView artistpage_bgimage = findViewById(R.id.artistpage_bgimage);
        TextView artistpage_username = findViewById(R.id.artistp_username);
        TextView artistpage_jobtitle = findViewById(R.id.artistp_jobtitle);

        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference("location").child("users").child(currViewEmail.replace(".", "%"));
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                if (data.get("fullname") != null) {
                    String dataFullname = (String) data.get("fullname");
                    artistpage_username.setText(dataFullname, TextView.BufferType.EDITABLE);
                }
                if (data.get("imageURL") != null) {
                    String dataImageURL = (String) data.get("imageURL");
                    Glide.with(getApplicationContext()).load(dataImageURL).into(artistpage_bgimage);
                }
                if (data.get("title") != null) {
                    String dataTitle = (String) data.get("title");
                    artistpage_jobtitle.setText(dataTitle, TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        TextView artistp_follower_number = findViewById(R.id.artistp_follower_number);
        TextView artistp_following_number = findViewById(R.id.artistp_following_number);
        Button followBtn = findViewById(R.id.artistp_button_follow);

        followCounter(artistp_follower_number, artistp_following_number, followBtn);

        followBtn.setOnClickListener(view -> {
            if (followBtn.getText().toString().equals("Follow")){
                FirebaseDatabase.getInstance().getReference().child("follow").child(currUserEmail.replace(".","%"))
                        .child("following").child(currViewEmail.replace(".","%")).setValue(true);
                FirebaseDatabase.getInstance().getReference().child("follow").child(currViewEmail.replace(".","%"))
                        .child("followers").child(currUserEmail.replace(".","%")).setValue(true);
            } else {
                FirebaseDatabase.getInstance().getReference().child("follow").child(currUserEmail.replace(".","%"))
                        .child("following").child(currViewEmail.replace(".","%")).removeValue();
                FirebaseDatabase.getInstance().getReference().child("follow").child(currViewEmail.replace(".","%"))
                        .child("followers").child(currUserEmail.replace(".","%")).removeValue();
                followBtn.setText("Follow", TextView.BufferType.EDITABLE);
            }
            followCounter(artistp_follower_number, artistp_following_number, followBtn);
        });

        // post recycler view
        recyclerView = findViewById(R.id.foto_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        myFotoAdapter = new MyFotoAdapter(getApplicationContext(), postList);
        recyclerView.setAdapter(myFotoAdapter);
        myFotos();
    }

    public void followCounter(TextView artistp_follower_number, TextView artistp_following_number, Button followBtn) {
        DatabaseReference childRef2 = FirebaseDatabase.getInstance().getReference("follow").child(currViewEmail.replace(".", "%"));
        childRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String countToString;

                long followerCount = snapshot.child("followers").getChildrenCount();
                countToString = Long.toString(followerCount);
                artistp_follower_number.setText(countToString, TextView.BufferType.EDITABLE);

                long followingCount = snapshot.child("following").getChildrenCount();
                countToString = Long.toString(followingCount);
                artistp_following_number.setText(countToString, TextView.BufferType.EDITABLE);

                for (DataSnapshot dataSnapshot : snapshot.child("followers").getChildren()) {
                    if (Objects.equals(dataSnapshot.getKey(), currUserEmail.replace(".", "%"))) {
                        if (Objects.equals(dataSnapshot.getValue(), true)) {
                            Log.i("Output: ", dataSnapshot.toString());
                            followBtn.setText("Following", TextView.BufferType.EDITABLE);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // post recycler view
    private void myFotos() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(currViewEmail.replace(".", "%"))) {
                        postList.add(post);
                    }
                }
                Collections.reverse(postList);
                myFotoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}