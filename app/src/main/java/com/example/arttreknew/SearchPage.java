package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextClock;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchPage extends AppCompatActivity {

    private EditText SearchBar;
    private ImageButton SearchButton;
    private TextView CancelButton;
    private RecyclerView RecyclerView;
    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.search_page);
        super.onCreate(savedInstanceState);
            String name, email;
        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
        SearchBar = findViewById(R.id.searchviewp_searchview);
                SearchButton = findViewById(R.id.imageButton15);
        CancelButton= findViewById(R.id.searchviewp_cancel);
                RecyclerView= findViewById(R.id.recycler_view);
        

    }




}