package com.example.trackme.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trackme.R;
import com.example.trackme.dashboard.DashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    EditText nama;
    Button daftarakun;
//    untuk authentikasi data dan penyimpanan data
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        nama = findViewById(R.id.nama);
        daftarakun = findViewById(R.id.daftar);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        ref = firebaseFirestore.collection("users").document();
        daftarakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Map<String, Object> register_sukses = new HashMap<>();
                                    register_sukses.put("name", nama.getText().toString());
                                    register_sukses.put("email", email.getText().toString());
                                    register_sukses.put("password", password.getText().toString());


//                                    masukan ke db firestore
                                    firebaseFirestore.collection("users").add(register_sukses).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(RegisterActivity.this, "Buat akun berhasil", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Buat akun gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

}