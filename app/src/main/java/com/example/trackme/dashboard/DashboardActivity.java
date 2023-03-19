package com.example.trackme.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import com.google.android.gms.location.LocationServices;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackme.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth userAuth;
    CollectionReference userReference;

    private FusedLocationProviderClient fusedLocationProvider;
    private Geocoder geocoder;

    Button btn_get_lokasi;
    TextView txt_lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        lokasi
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());

        txt_lokasi = findViewById(R.id.txt_location);

        btn_get_lokasi = findViewById(R.id.btn_send_lokasi);
        btn_get_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                Toast.makeText(DashboardActivity.this, "pencet", Toast.LENGTH_SHORT).show();
            }
        });

        db = FirebaseFirestore.getInstance();
        userAuth = FirebaseAuth.getInstance();
        userReference = db.collection("users");
        TextView username = findViewById(R.id.namekamu);
        userReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot documt : queryDocumentSnapshots) {
                String name = documt.getString("name");
                username.setText(name);
            }
        });

    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }
    }
}