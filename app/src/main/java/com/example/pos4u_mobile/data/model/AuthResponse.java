package com.example.pos4u_mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("token") // Jika backend menggunakan JWT Token
    private String token;

    @SerializedName("role") // Mengetahui apakah login sebagai 'kasir' atau 'admin'
    private String role;

    // Getter dan Setter
    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public String getRole() { return role; }
}