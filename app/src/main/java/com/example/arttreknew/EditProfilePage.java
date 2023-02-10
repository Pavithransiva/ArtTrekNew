package com.example.arttreknew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EditProfilePage extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.edit_profile_page);

        mButtonChooseImage = findViewById(R.id.editprofilepage_button_choose_image);
        mButtonUpload = findViewById(R.id.editprofilepage_button_upload_image);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.editprofilepage_image_view);
        mProgressBar = findViewById(R.id.editprofilepage_progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("profile_pic");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("profile_pic");

        mButtonChooseImage.setOnClickListener(v -> openFileChooser());

        mButtonUpload.setOnClickListener(v -> {
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(EditProfilePage.this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        });

        MaterialButton backBtn = findViewById(R.id.editprofilepage_ic_previous);
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(EditProfilePage.this, UserPage.class));
            finish();
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            mImageView.setImageURI(mImageUri);
        }
    }

    //Get File Extension function PNG/JPG
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            String fileName = formatter.format(now);

            StorageReference fileReference = mStorageRef.child(fileName
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        Handler handler = new Handler();
                        handler.postDelayed(() -> mProgressBar.setProgress(0), 500);

                        Toast.makeText(EditProfilePage.this, "Upload successful", Toast.LENGTH_LONG).show();
                        Upload upload = new Upload(mEditTextFileName.getText().toString().trim(),
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EditProfilePage.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}

