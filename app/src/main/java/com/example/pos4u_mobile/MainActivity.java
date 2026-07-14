package com.example.pos4u_mobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pos4u_mobile.data.api.ApiClient;
import com.example.pos4u_mobile.data.model.transaksi.CartItem;
import com.example.pos4u_mobile.data.model.produk.ProductResponse;
import com.example.pos4u_mobile.databinding.ActivityMainBinding;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<CartItem> keranjangBelanja = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private String token;
    private double totalHargaSeluruhnya = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inisialisasi View Binding dengan benar
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("POS4U_SESSION", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        String namaKasir = sharedPreferences.getString("username", "Kasir POS4U");

        binding.tvNamaKasir.setText("Kasir: " + namaKasir);

        setupRecyclerViews();
        muatDataProdukDariWeb();

        binding.btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keranjangBelanja.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Keranjang masih kosong!", Toast.LENGTH_SHORT).show();
                    return;
                }
                prosesPembayaranKeServer();
            }
        });
    }

    private void setupRecyclerViews() {
        // Deteksi apakah panel kanan (untuk tablet) ada di layout xml saat ini
        int jumlahKolom = (findViewById(R.id.panelKeranjang) != null) ? 3 : 2;
        binding.rvProduk.setLayoutManager(new GridLayoutManager(this, jumlahKolom));
        binding.rvKeranjang.setLayoutManager(new LinearLayoutManager(this));
    }

    private void muatDataProdukDariWeb() {
        // enqueue sekarang sinkron dengan tipe data ProductResponse dari package data.model
        ApiClient.getClient().getProducts(token).enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductResponse>> call, @NonNull Response<List<ProductResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductResponse> produkList = response.body();
                    // Kode penghubung ke adapter nantinya ditaruh di sini
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mengambil data barang server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductResponse>> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung ke Laragon: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tambahKeKeranjang(ProductResponse produk) {
        boolean barangSudahAda = false;
        for (CartItem item : keranjangBelanja) {
            if (item.getProduct().getId() == produk.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                barangSudahAda = true;
                break;
            }
        }

        if (!barangSudahAda) {
            keranjangBelanja.add(new CartItem(produk, 1));
        }

        hitungUlangTotalBelanja();
    }

    private void hitungUlangTotalBelanja() {
        totalHargaSeluruhnya = 0;
        for (CartItem item : keranjangBelanja) {
            totalHargaSeluruhnya += item.getSubtotal();
        }

        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
        binding.tvTotalHarga.setText(rupiahFormat.format(totalHargaSeluruhnya));
    }

    private void prosesPembayaranKeServer() {
        Toast.makeText(this, "Mengirim data transaksi ke POS4U Web...", Toast.LENGTH_SHORT).show();
    }
}