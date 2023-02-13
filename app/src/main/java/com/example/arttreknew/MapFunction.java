package com.example.arttreknew;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapFunction extends AppCompatActivity implements OnMapReadyCallback {

    // current location
    boolean isPermissionGranted;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    // lat and long getter
    private LocationCallback mLocationCallback;
    // geocoder
    private Geocoder geocoder;

    // database
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://arttreknew-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;

    // pop up dialog
    private AlertDialog dialog;
    private EditText newcontactpopup_locationname, newcontactpopup_description;
    // image upload
    public ImageView imageView;
    public Uri imageUri;
    // search bar
    private EditText searchBar;
    private ImageButton searchButton;

    private BottomNavigationView bnv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.mapfunction);

        bnv = findViewById(R.id.hp_bottomNavigationView);
        bnv.setSelectedItemId(R.id.botnav_ic_map);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.botnav_ic_home:
                        startActivity(new Intent(MapFunction.this, HomePage.class));
                        finish();
                        return true;
                    case R.id.botnav_ic_profile:
                        startActivity(new Intent(MapFunction.this, UserPage.class));
                        finish();
                        return true;
                }
                return false;
            }
        });

        // current location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        checkMyPermission();
        if (isPermissionGranted) {
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
            if (supportMapFragment != null) {
                supportMapFragment.getMapAsync(this);
            }
        }

        // open pop up dialog
        ImageButton mButton = findViewById(R.id.addMarker);
        mButton.setOnClickListener(view -> createNewContactDialog());

        // search bar
        searchButton = findViewById(R.id.searchMarker);
        searchButton.setOnClickListener(view -> searchMethod());

        mRef = mDatabase.getReference().child("marker");
    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MapFunction.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGranted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // current location
        mGoogleMap = googleMap;
        mGoogleMap.setMyLocationEnabled(true);
        // geocoder
        geocoder = new Geocoder(this);

        // zoom to current location on start up
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                    }
                });

        // marker adder
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String description = snapshot.child("description").getValue(String.class);
                    String locationName = snapshot.child("location_name").getValue(String.class);
                    double latitude = snapshot.child("latitude").getValue(Double.class);
                    double longitude = snapshot.child("longitude").getValue(Double.class);

                    LatLng location = new LatLng(latitude, longitude);
                    mGoogleMap.addMarker(new MarkerOptions().position(location).title(locationName).snippet(description));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Error", databaseError.getMessage());
            }
        });
    }

    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    public void createNewContactDialog() {
        // pop up dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.form_dialog_fragment, null);

        // initialize input boxes and buttons
        newcontactpopup_locationname = contactPopupView.findViewById(R.id.location_name_input);
        newcontactpopup_description = contactPopupView.findViewById(R.id.description_input);
        imageView = contactPopupView.findViewById(R.id.firebaseImage);
        ImageButton newcontactpopup_upload = contactPopupView.findViewById(R.id.imageUpload);
        Button newcontactpopup_save = contactPopupView.findViewById(R.id.save);
        Button newcontactpopup_cancel = contactPopupView.findViewById(R.id.cancel);

        // create dialog
        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference("marker_image");

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

            // current location
            mFusedLocationClient.getLastLocation().addOnSuccessListener(MapFunction.this, location -> {
                if (location != null) {
                    final double latitude = location.getLatitude();
                    final double longitude = location.getLongitude();
                    if (mLocationCallback != null) {
                        mLocationCallback.onLocationResult(latitude, longitude);
                    }
                }
            });

            // lat and long getter
            mLocationCallback = (latitude, longitude) -> {
                // create hashmap
                HashMap<String, Object> locationHashmap = new HashMap<>();
                locationHashmap.put("location_name", locationName);
                locationHashmap.put("description", description);
                locationHashmap.put("latitude", latitude);
                locationHashmap.put("longitude", longitude);

                // initialize database
                mRef = mDatabase.getReference("marker");

                String key = mRef.push().getKey();
                locationHashmap.put("key", key);

                if (key != null) {
                    // save button
                    mRef.child(key).setValue(locationHashmap).addOnCompleteListener(task -> {
                        if (imageSelected.get() == true) {
                            uploadImage(key);
                        }
                        Toast.makeText(MapFunction.this, "Added", Toast.LENGTH_SHORT).show();
                        newcontactpopup_locationname.getText().clear();
                        newcontactpopup_description.getText().clear();
                        dialog.dismiss();
                    });
                }
            };
        });

        // dialog closer
        newcontactpopup_cancel.setOnClickListener(view -> dialog.dismiss());
    }

    // lat and long getter
    public interface LocationCallback {
        void onLocationResult(double latitude, double longitude);
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
                    StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://arttreknew.appspot.com/marker_image");
                    StorageReference imageRef = storageRef.child(fileName);

                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageURL = uri.toString();
                        // Do something with the image URL
                        DatabaseReference rootRef = mDatabase.getReference("marker");
                        DatabaseReference parentRef = rootRef.child(key);
                        DatabaseReference childRef = parentRef.child("image");

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

    public void searchMethod() {
        searchBar = findViewById(R.id.search_input);

        String searchInput = searchBar.getText().toString();

        if (searchInput.isEmpty()) {
            return;
        }

        try {
            List<Address> addresses = geocoder.getFromLocationName(searchInput, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}