package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.arttreknew.databinding.ChatLandingPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivityChat extends AppCompatActivity {

   ChatLandingPageBinding binding;
    DatabaseReference mRef;
    UserAdapterChat userAdapterChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userAdapterChat = new UserAdapterChat(this);
        binding.recycler.setAdapter(userAdapterChat);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));


        mRef = FirebaseDatabase.getInstance().getReference("users");

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userAdapterChat.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String email = dataSnapshot.getKey();
                    if (!email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        UserModelChat userModelChat =dataSnapshot.child(email).getValue(UserModelChat.class);
                        userAdapterChat.add(userModelChat);



                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}