package com.example.arttreknew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arttreknew.Comment;
import com.example.arttreknew.CommentsActivity;
import com.example.arttreknew.HomePage;
import com.example.arttreknew.Post;
import com.example.arttreknew.PostAdapter;
import com.example.arttreknew.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context mContext;
    private List<Comment> mComment;
    private String postid;
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile;
        public TextView username, commentShow;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            image_profile = itemView.findViewById(R.id.image_profile);
            username = itemView.findViewById(R.id.username);
            commentShow = itemView.findViewById(R.id.commentShow);
        }
    }

private FirebaseUser firebaseUser;

    public CommentAdapter(Context mContext, List<Comment> mComment,String postid) {
        this.mContext = mContext;
        this.mComment = mComment;
        this.postid = postid;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, viewGroup, false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Comment listcomment = mComment.get(i);


        viewHolder.commentShow.setText(listcomment.getComments());
        getUserInfo(viewHolder.image_profile,viewHolder.username,viewHolder.commentShow, listcomment.getPublisher(), postid, i);

        viewHolder.commentShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomePage.class);
                intent.putExtra("publisherid", listcomment.getPublisher());
                mContext.startActivity(intent);
            }
        });

        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HomePage.class);
                intent.putExtra("publisherid", listcomment.getPublisher());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }


    private void getUserInfo(ImageView imageView, TextView username,TextView commentShow, String publisherid, String postid, int i ){
            DatabaseReference reference = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Comments")
                .child(postid);
        //Log.println("putang inang mo ", "kakakkakaak");
      //  DatabaseReference childRef = reference.child("users").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //must ask nich
             //Glide.with(mContext).load(comment.getImageURL()).into(imageView);
               int x = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(i == x ){
                    Log.i("SnapshotGenerated", snapshot +"please work");
                    Comment comment = snapshot.getValue(Comment.class);
                    commentShow.setText(comment.getComments());
                    username.setText(comment.getPublisher().replace("%com", ".com"));
                    }
                x++;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
