package com.example.pos4u_mobile.data.api;

import com.example.pos4u_mobile.data.model.auth.AuthRequest;
import com.example.pos4u_mobile.data.model.auth.RegisterRequest;
import com.example.pos4u_mobile.data.model.produk.ProductResponse;
import com.example.pos4u_mobile.data.model.auth.AuthResponse;
import com.example.pos4u_mobile.data.model.riwayat.DetailRiwayatResponse;
import com.example.pos4u_mobile.data.model.transaksi.TransactionRequest;
import com.example.pos4u_mobile.data.model.transaksi.TransactionResponse;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    // 1. AUTHENTICATION
    @POST("login")
    Call<AuthResponse> login(@Body AuthRequest request);

    @POST("register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    // 2. MODUL KASIR (API)
    // Menembak endpoint: kasir/products-api
    @GET("kasir/products-api")
    Call<List<ProductResponse>> getProducts(@Header("Authorization") String token);

    // Menembak endpoint: kasir/transaksi/process
    @POST("kasir/transaksi/process")
    Call<TransactionResponse> processTransaction(
            @Header("Authorization") String token,
            @Body TransactionRequest transaction
    );

    // Menembak endpoint: kasir/riwayat/detail-api/(:num)
    @GET("kasir/riwayat/detail-api/{id}")
    Call<DetailRiwayatResponse> getDetailRiwayat(
            @Header("Authorization") String token,
            @Path("id") int id
    );
}