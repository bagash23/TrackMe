package com.example.trackme.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.github.muddz.styleabletoast.StyleableToast;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email;
    EditText password;
    Button button;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        button = findViewById(R.id.btn_login);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage("Sabar Cuy...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                    StyleableToast.makeText(LoginActivity.this, "Berhasil Buat Akun", R.style.ToastBerhasil).show();

                                } else {
                                    progressDialog.dismiss();
                                    StyleableToast.makeText(LoginActivity.this, "Gagal Buat Akun", R.style.ToastGagal).show();
                                    button.setEnabled(true);
                                }
                            }
                        });
                    }
                });
    }
}