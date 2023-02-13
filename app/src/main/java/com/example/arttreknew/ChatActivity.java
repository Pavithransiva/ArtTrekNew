package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Binder;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;

import com.example.arttreknew.databinding.AcitivtitychatMainBinding;
import com.example.arttreknew.databinding.ChatActionPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class ChatActivity extends AppCompatActivity {
    AcitivtitychatMainBinding binding;
    String receiverEmail;
    DatabaseReference mRefSender, mRefReceiver;
    String senderRoom,receiverRoom;
    MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AcitivtitychatMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverEmail = getIntent().getStringExtra("email");

        senderRoom = FirebaseAuth.getInstance().getCurrentUser().getEmail() + receiverEmail;
        receiverRoom = receiverEmail + FirebaseAuth.getInstance().getCurrentUser().getEmail();

        messageAdapter = new MessageAdapter(this);
        binding.recycler.setAdapter(messageAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));

        mRefSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        mRefReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);

        mRefSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageEd.getText().toString();
                if (message.trim().length() > 0) {
                    sendMessage(message);

                }
            }
        });


    }

    private void sendMessage(String message) {
        String messageId= UUID.randomUUID().toString();
        MessageModel messageModel=new MessageModel(messageId,FirebaseAuth.getInstance().getCurrentUser().getEmail(), message);

        messageAdapter.add(messageModel);

        mRefSender
                .child(messageId)
                .setValue(messageModel);
        mRefReceiver
                .child(messageId)
                .setValue(messageModel);




    }
}