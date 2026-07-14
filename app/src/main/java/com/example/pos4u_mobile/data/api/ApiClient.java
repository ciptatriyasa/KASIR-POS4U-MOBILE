package com.example.pos4u_mobile.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // Sesuaikan URL dengan IP lokal komputer / domain hosting POS4U Anda
    private static final String BASE_URL = "http://192.168.1.67/programkasir/public/";
    private static Retrofit retrofit = null;

    public static ApiService getClient() {
        if (retrofit == null) {
            // Tambahkan interceptor untuk melihat log request/response API di Logcat
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}