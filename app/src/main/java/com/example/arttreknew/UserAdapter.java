package com.example.arttreknew;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context mContext;
    private List<UserGetSet> mUsers;
    FirebaseUser firebaseUser;

    public UserAdapter(Context mContext, List<UserGetSet>mUsers){
        this.mContext = mContext;
        this.mUsers = mUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,viewGroup,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final UserGetSet userGetSet = mUsers.get(i);

        viewHolder.btn_follow.setVisibility(View.VISIBLE);

        viewHolder.fullname.setText(userGetSet.getFullname());
        viewHolder.Title.setText(userGetSet.getTitle());
        Glide.with(mContext).load(userGetSet.getImageurl()).into(viewHolder.image_profile);
        isFollowing(userGetSet.getEmail(),viewHolder.btn_follow);

        if (userGetSet.getEmail().equals(firebaseUser.getEmail())){
            viewHolder.btn_follow.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profiled", userGetSet.getEmail());
                editor.apply();

             //   ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment,
                //        new ProfileFragment()).commit();
            }
        });

        viewHolder.btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( viewHolder.btn_follow.getText().toString().equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getEmail())
                            .child("following").child(userGetSet.getEmail()).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(userGetSet.getEmail())
                            .child("followers").child(firebaseUser.getEmail()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(userGetSet.getEmail())
                            .child("following").child(userGetSet.getEmail()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(userGetSet.getEmail())
                            .child("followers").child(firebaseUser.getEmail()).removeValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView fullname;
        public TextView Title;
        public CircleImageView image_profile;
        public Button btn_follow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.fullname);
            Title = itemView.findViewById(R.id.title);
            image_profile = itemView.findViewById(R.id.post_image_profile);
            btn_follow = itemView.findViewById(R.id.btn_follow);

        }
    }

    private void isFollowing(final String email,final Button button){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getEmail()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(email).exists()){
                    button.setText("following");
                }else {
                    button.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
