package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;

import com.google.android.material.button.MaterialButton;


import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.arttreknew.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomePage extends AppCompatActivity {

    private BottomNavigationView bnv;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private List<String> followingList;

    private ConstraintLayout searchbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.home_page);





        bnv = findViewById(R.id.hp_bottomNavigationView);
        searchbtn = findViewById(R.id.hp_searchview_container);


                bnv = findViewById(R.id.hp_bottomNavigationView);
                searchbtn = findViewById(R.id.hp_searchview_container);
            //   Bundle intent = getIntent().getExtras();
              //  if( intent != null ){
                //    String publisher = intent.getString("publisherid");

                  // SharedPreferences.Editor editor = getSharedPreferences("PREFS", MODE_PRIVATE).edit();
                    //editor.putString("profiled",publisher);
                    //editor.apply();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment()).commit();
               //}
                //else {
             //      getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment()).commit();
              // }
                searchbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomePage.this, SearchFragment.class));
                        finish();
                    }
                });
                bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.botnav_ic_map:
                                startActivity(new Intent(HomePage.this, MapFunction.class));
                                finish();
                                return true;
                            case R.id.botnav_ic_profile:
                                startActivity(new Intent(HomePage.this, UserPage.class));
                                finish();
                                return true;
                            case R.id.botnav_ic_chat:
                                startActivity(new Intent(HomePage.this, ChatLandingPage.class));
                                finish();
                                return true;

                        }
                        return false;
                    }
                });

                recyclerView = findViewById(R.id.recycler_view_home);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setReverseLayout(true);
                linearLayoutManager.setStackFromEnd(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                postList = new ArrayList<>();
                postAdapter = new PostAdapter(getApplicationContext(), postList);
                recyclerView.setAdapter(postAdapter);

                checkFollowing();

            }

            private void checkFollowing() {
                followingList = new ArrayList<>();

                DatabaseReference reference = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("follow")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "%"))
                        .child("following");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        followingList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            followingList.add(snapshot.getKey());
                        }
                        readPosts();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void readPosts() {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("post");
                //  DatabaseReference reference1
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        postList.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Post post = snapshot.getValue(Post.class);
                            for (String email : followingList) {
                                if (post.getPublisher().equals(email)) {
                                    postList.add(post);

                                }
                            }
                        }
                        postAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        };