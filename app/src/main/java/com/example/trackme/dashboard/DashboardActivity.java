package com.example.trackme.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import com.example.trackme.auth.LoginActivity;
import com.example.trackme.utiils.AdapterLokasi;
import com.example.trackme.utiils.ListLokasiPengguna;
import com.google.android.gms.location.LocationServices;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackme.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

import io.github.muddz.styleabletoast.StyleableToast;


public class DashboardActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    FirebaseAuth userAuth;
    CollectionReference userReference;
    private CollectionReference lokasiReference;

    private FusedLocationProviderClient fusedLocationProvider;
    private Geocoder geocoder;

    Button btn_get_lokasi;
    RecyclerView recyclerView;
    Button btn_lokasi;

    ProgressDialog progressDialog;

    private final static  int  REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

//        lokasi
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this, Locale.getDefault());


        btn_get_lokasi = findViewById(R.id.btn_send_lokasi);
        btn_get_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
                StyleableToast.makeText(DashboardActivity.this, "Berhasil Mengirimkan Lokasi", R.style.ToastInformasi).show();
            }
        });

        db = FirebaseFirestore.getInstance();
        userAuth = FirebaseAuth.getInstance();
        userReference = db.collection("users");
        lokasiReference = db.collection("locations");
        TextView username = findViewById(R.id.namekamu);
//        pemanggilan data user
//        userReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
//            for (QueryDocumentSnapshot documt : queryDocumentSnapshots) {
//                String name = documt.getString("name");
//                username.setText(name);
//            }
//        });




        recyclerView = findViewById(R.id.rc_link);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        pemanggilan data lokasi
        lokasiReference.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<ListLokasiPengguna> listlokasi = new ArrayList<>();
            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                ListLokasiPengguna lokasi = new ListLokasiPengguna(documentSnapshot.getString("namePengirim"), documentSnapshot.getString("uri"));
                listlokasi.add(lokasi);
                System.out.println("show list");

            }
            AdapterLokasi adapterLokasi = new AdapterLokasi(listlokasi);
            recyclerView.setAdapter(adapterLokasi);
        });


    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 10) {
//            if (ActivityCompat.checkSelfPermission(this,
//                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                    ActivityCompat.checkSelfPermission(this,
//                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
//
//                Toast.makeText(getApplicationContext(), "izin lokasi tidak di aktifkan", Toast.LENGTH_SHORT).show();
//
//            }else {
//                getLocation();
//            }
//        }
//    }
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
//
//        } else {
//            fusedLocationProvider.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    Map<String, Object> dataLocation = new HashMap<>();
//                    System.out.println(location.getLatitude());
//                    String uri = "geo:" + location.getLatitude() + "," + location.getLongitude() + "?q=" + location.getLatitude() + "," + location.getLongitude();
//                    dataLocation.put("namePengirim", userAuth.getCurrentUser().getEmail());
//                    dataLocation.put("uri", uri);
//
//                    try {
//                        db.collection("locations").add(dataLocation);
//                        System.out.println("Data saved successfully.");
//                    } catch (Exception e) {
//                        System.err.println("Error saving data: " + e.getMessage());
//                    }
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(DashboardActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//    }


    private void getLocation(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED){

            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Memuat Ulang...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            fusedLocationProvider.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                    progressDialog.dismiss();
                                    Map<String, Object> data = new HashMap<>();
                                    String uri = "geo:" + location.getLatitude() + "," + location.getLongitude() + "?q=" + location.getLatitude() + "," + location.getLongitude();
                                    data.put("namePengirim", userAuth.getCurrentUser().getEmail());
                                    data.put("uri", uri);
                                    db.collection("locations").add(data);
                                    updateView();
                            }else {
                                Log.d("GEO ", location.getLatitude() + "," + location.getLongitude());
                            }
                        }
                    });
        } else {
            askPermission();
        }
    }

    private void updateView(){
        FirebaseFirestore dbs = FirebaseFirestore.getInstance();
        CollectionReference tambahMasukRef = dbs.collection("locations");

        tambahMasukRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<ListLokasiPengguna> listlokasi1 = new ArrayList<>();
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    ListLokasiPengguna lokasi1 = new ListLokasiPengguna(documentSnapshot.getString("namePengirim"), documentSnapshot.getString("uri"));
                    listlokasi1.add(lokasi1);
                    System.out.println("show list");

                }
                StyleableToast.makeText(DashboardActivity.this, "Berhasil Update Data Lokasi", R.style.ToastInformasi).show();
                AdapterLokasi adapterLokasi = new AdapterLokasi(listlokasi1);
                recyclerView.setAdapter(adapterLokasi);
            }
        });

    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this, new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}