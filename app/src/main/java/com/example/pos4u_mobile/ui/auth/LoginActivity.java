package com.example.pos4u_mobile.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pos4u_mobile.MainActivity;
import com.example.pos4u_mobile.data.api.ApiClient;
import com.example.pos4u_mobile.data.model.auth.AuthRequest;
import com.example.pos4u_mobile.data.model.auth.AuthResponse;
import com.example.pos4u_mobile.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup shared preferences untuk session kasir
        sharedPreferences = getSharedPreferences("POS4U_SESSION", Context.MODE_PRIVATE);

        // Jika kasir sudah login sebelumnya dan session masih aktif, langsung lempar ke dashboard
        if (sharedPreferences.getString("token", null) != null) {
            pindahKeDashboard();
        }

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameOrEmail = binding.etUsername.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();

                if (usernameOrEmail.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username/Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Jalankan fungsi tembak API ke Laragon
                eksekusiLoginKeAPI(usernameOrEmail, password);
            }
        });
    }

    private void eksekusiLoginKeAPI(String username, String password) {
        // Beri efek loading pada tombol agar kasir tidak menekan berkali-kali
        binding.btnLogin.setEnabled(false);
        binding.btnLogin.setText("Memverifikasi Akun...");

        AuthRequest request = new AuthRequest(username, password);

        ApiClient.getClient().login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(@NonNull Call<AuthResponse> call, @NonNull Response<AuthResponse> response) {
                binding.btnLogin.setEnabled(true);
                binding.btnLogin.setText("Masuk Kasir");

                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();

                    if (authResponse.isStatus()) { // Jika API web merespons true
                        // Simpan data kasir dan token ke penyimpanan lokal smartphone/tablet
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", "Bearer " + authResponse.getToken());
                        editor.putString("username", username);
                        editor.putString("role", authResponse.getRole());
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Selamat Datang di POS4U!", Toast.LENGTH_SHORT).show();
                        pindahKeDashboard();
                    } else {
                        // Jika server merespons balik namun kredensial salah (misal password keliru)
                        Toast.makeText(LoginActivity.this, authResponse.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Gagal: Username atau Password salah!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AuthResponse> call, @NonNull Throwable t) {
                binding.btnLogin.setEnabled(true);
                binding.btnLogin.setText("Masuk Kasir");
                // Menampilkan error jika jaringan ke IP Laragon PC terputus
                Toast.makeText(LoginActivity.this, "Gagal terhubung ke server Laragon: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pindahKeDashboard() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Hancurkan LoginActivity agar saat kasir menekan tombol back, mereka tidak kembali ke form login
    }
}