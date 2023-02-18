package com.example.arttreknew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapterChat extends RecyclerView.Adapter<UserAdapterChat.MyViewHolder> {

    private  Context context;
    private static List<UserModelChat> userModelsListChat;

    public UserAdapterChat(Context context){
        this.context = context;
        userModelsListChat =new ArrayList<>();
    }
    public void add(UserModelChat userModelChat){
        userModelsListChat.add(userModelChat);
        notifyDataSetChanged();
    }

    public void clear(){
        userModelsListChat.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_landing_page,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserModelChat userModelChat = userModelsListChat.get(position);
        holder.fullname.setText(userModelChat.getFullname());
        holder.email.setText(userModelChat.getEmail());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ChatActivity.class);
                intent.putExtra("email", userModelChat.getEmail());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return userModelsListChat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView fullname,email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
