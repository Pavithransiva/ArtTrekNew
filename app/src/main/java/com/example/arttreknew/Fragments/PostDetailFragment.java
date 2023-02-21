package com.example.arttreknew.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.example.arttreknew.ArtistPage;
import com.example.arttreknew.HomePage;
import com.example.arttreknew.MapFunction;
import com.example.arttreknew.Post;
import com.example.arttreknew.PostAdapter;
import com.example.arttreknew.R;
import com.example.arttreknew.UserPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class PostDetailFragment extends AppCompatActivity {
    String postid;
    String publisher;
    String from;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.fragment_post_detail);

        // View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        // SharedPreferences preferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        // postid = preferences.getString("postid", "none");
        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        publisher = intent.getStringExtra("publisher");
        if (intent.getStringExtra("from") != null) {
            from = intent.getStringExtra("from");
        } else {
            from = "no";
        }
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getApplicationContext(), postList, from);
        recyclerView.setAdapter(postAdapter);

        readPost();

        ImageButton backBtn = findViewById(R.id.post_back_btn);
        backBtn.setOnClickListener(view -> {
            if (from.equals("user")) {
                if (Objects.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "%"), publisher)) {
                    startActivity(new Intent(PostDetailFragment.this, UserPage.class));
                    finish();
                } else {
                    Intent intent2 = new Intent(PostDetailFragment.this, ArtistPage.class);
                    intent2.putExtra("email", publisher);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    PostDetailFragment.this.startActivity(intent2);
                }
            } else {
                startActivity(new Intent(PostDetailFragment.this, HomePage.class));
                finish();
            }
        });
    }


    private void readPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("post")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                Post post = dataSnapshot.getValue(Post.class);
                postList.add(post);

                postAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}