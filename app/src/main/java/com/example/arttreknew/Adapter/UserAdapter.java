package com.example.arttreknew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arttreknew.ArtistPage;
import com.example.arttreknew.R;
import com.example.arttreknew.UserGetSet;
import com.example.arttreknew.UserPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

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
        UserGetSet userGetSet = mUsers.get(i);

        viewHolder.btn_follow.setVisibility(View.VISIBLE);

        viewHolder.fullname.setText(userGetSet.getFullname());
        viewHolder.Title.setText(userGetSet.getTitle());
        Glide.with(mContext).load(userGetSet.getImageURL()).into(viewHolder.image_profile);
        isFollowing(userGetSet.getEmail().replace(".","%"),viewHolder.btn_follow);

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
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getEmail().replace(".","%"))
                            .child("following").child(userGetSet.getEmail().replace(".","%")).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("follow").child(userGetSet.getEmail().replace(".","%"))
                            .child("followers").child(firebaseUser.getEmail().replace(".","%")).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("follow").child(firebaseUser.getEmail().replace(".","%"))
                            .child("following").child(userGetSet.getEmail().replace(".","%")).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("follow").child(userGetSet.getEmail().replace(".","%"))
                            .child("followers").child(firebaseUser.getEmail().replace(".","%")).removeValue();
                }
            }
        });

        viewHolder.fullname.setOnClickListener(view -> viewProfilePage(userGetSet.getEmail()));
        viewHolder.Title.setOnClickListener(view -> viewProfilePage(userGetSet.getEmail()));
        viewHolder.image_profile.setOnClickListener(view -> viewProfilePage(userGetSet.getEmail()));
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
        DatabaseReference reference = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference()
                .child("follow").child(firebaseUser.getEmail().replace(".","%")).child("following");
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

    private void viewProfilePage(String email) {
        if (!Objects.equals(email, FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
            Intent intent = new Intent(mContext, ArtistPage.class);
            intent.putExtra("email", email);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        } else {
            Intent intent = new Intent(mContext, UserPage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }
}
