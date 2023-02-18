package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.arttreknew.Adapter.MyFotoAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserPage extends AppCompatActivity {
    private BottomNavigationView bnv;
    private Button mButtonUploadPic;

    private ImageView mBGImageView;

    private DatabaseReference mDatabaseRef;

    // pop up dialog
    private AlertDialog dialog;
    private EditText newcontactpopup_locationname, newcontactpopup_description;
    // image upload
    public ImageView imageView;
    public Uri imageUri;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;

    private final String currUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

    // post recycler view
    RecyclerView recyclerView;
    MyFotoAdapter myFotoAdapter;
    List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();

        setContentView(R.layout.user_page);

        mButtonUploadPic = findViewById(R.id.userpage_button_upload_pic);
        mBGImageView = findViewById(R.id.userpage_bgimage);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile_pic");

        mButtonUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserPage.this, EditProfilePage.class);
                startActivity(intent);
            }
        });

        MaterialButton settingsBtn = findViewById(R.id.userpage_ic_settings);
        settingsBtn.setOnClickListener(view -> {
            startActivity(new Intent(UserPage.this, SettingsPage.class));
            finish();
        });

        //Bottom Navigation Code
        bnv = findViewById(R.id.userpage_bottomNavigationView);
        //set the icon to active
        bnv.setSelectedItemId(R.id.botnav_ic_profile);

        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.botnav_ic_home:
                    startActivity(new Intent(UserPage.this, HomePage.class));
                    finish();
                    return true;
                case R.id.botnav_ic_map:
                    startActivity(new Intent(UserPage.this, MapFunction.class));
                    finish();
                    return true;
                case R.id.botnav_ic_chat:
                    startActivity(new Intent(UserPage.this, ChatLandingPage.class));
                    finish();
                    return true;
            }
            return false;

        });

        ShapeableImageView userpage_bgimage = findViewById(R.id.userpage_bgimage);
        TextView userpage_username = findViewById(R.id.userpage_username);
        TextView userpage_jobtitle = findViewById(R.id.userpage_jobtitle);

        DatabaseReference childRef = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("location").child("users").child(currUserEmail.replace(".", "%"));
        childRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getValue();
                if (data.get("fullname") != null) {
                    String dataFullname = (String) data.get("fullname");
                    userpage_username.setText(dataFullname, TextView.BufferType.EDITABLE);
                }
                if (data.get("imageURL") != null) {
                    String dataImageURL = (String) data.get("imageURL");
                    Glide.with(getApplicationContext()).load(dataImageURL).into(userpage_bgimage);
                }
                if (data.get("title") != null) {
                    String dataTitle = (String) data.get("title");
                    userpage_jobtitle.setText(dataTitle, TextView.BufferType.EDITABLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        MaterialButton uploadBtn = findViewById(R.id.userpage_button_upload_post);
        uploadBtn.setOnClickListener(view -> createNewContactDialog());

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

    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    public void createNewContactDialog() {
        // pop up dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.form_dialog_fragment_post, null);

        // initialize input boxes and buttons
        newcontactpopup_locationname = contactPopupView.findViewById(R.id.post_location_name_input);
        newcontactpopup_description = contactPopupView.findViewById(R.id.post_description_input);
        imageView = contactPopupView.findViewById(R.id.post_firebaseImage);
        ImageButton newcontactpopup_upload = contactPopupView.findViewById(R.id.post_imageUpload);
        Button newcontactpopup_save = contactPopupView.findViewById(R.id.post_save);
        Button newcontactpopup_cancel = contactPopupView.findViewById(R.id.post_cancel);

        // create dialog
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference("post_image");

        AtomicBoolean imageSelected;
        imageSelected = new AtomicBoolean(false);
        newcontactpopup_upload.setOnClickListener(view -> {
            imageSelected.set(true);
            selectImage();
        });

        // save location name, description, and current latitude & longitude
        newcontactpopup_save.setOnClickListener(view -> {
            String locationName = newcontactpopup_locationname.getText().toString();
            String description = newcontactpopup_description.getText().toString();

            // empty input boxes checker
            if (locationName.isEmpty()) {
                newcontactpopup_locationname.setError("Cannot be empty!");
                return;
            }
            if (description.isEmpty()) {
                newcontactpopup_description.setError("Cannot be empty!");
                return;
            }

                // create hashmap
                HashMap<String, Object> locationHashmap = new HashMap<>();
                locationHashmap.put("location_name", locationName);
                locationHashmap.put("description", description);
                locationHashmap.put("publisher", FirebaseAuth.getInstance().getCurrentUser().getEmail().replace(".", "%"));

                // initialize database
                mDatabase = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/");
                mRef = mDatabase.getReference("post");

                String key = mRef.push().getKey();
                locationHashmap.put("postid", key);

                if (key != null) {
                    // save button
                    mRef.child(key).setValue(locationHashmap).addOnCompleteListener(task -> {
                        if (imageSelected.get() == true) {
                            uploadImage(key);
                        }
                        Toast.makeText(UserPage.this, "Added", Toast.LENGTH_SHORT).show();
                        newcontactpopup_locationname.getText().clear();
                        newcontactpopup_description.getText().clear();
                        dialog.dismiss();
                    });
                }
        });

        // dialog closer
        newcontactpopup_cancel.setOnClickListener(view -> dialog.dismiss());
    }
    public void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadImage(String key) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();

        StorageReference riversRef = mStorageRef.child(fileName);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://arttreknew.appspot.com/post_image");
                    StorageReference imageRef = storageRef.child(fileName);

                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageURL = uri.toString();
                        // Do something with the image URL
                        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("post");
                        DatabaseReference parentRef = rootRef.child(key);
                        DatabaseReference childRef = parentRef.child("postimage");

                        childRef.setValue(imageURL)
                                .addOnSuccessListener(aVoid -> {
                                    // Data has been saved successfully
                                })
                                .addOnFailureListener(e -> {
                                    // Data could not be saved
                                });
                    }).addOnFailureListener(exception -> {
                        // Handle the error
                    });

                    pd.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to Upload", Toast.LENGTH_LONG).show();
                })
                .addOnProgressListener(snapshot -> {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    pd.setMessage("Percentage: " + (int) progressPercent + "%");
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
                    if (post.getPublisher().equals(currUserEmail.replace(".", "%"))) {
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
