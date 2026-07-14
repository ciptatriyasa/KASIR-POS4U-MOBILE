package com.example.pos4u_mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthRequest {
    @SerializedName("username") // sesuaikan dengan field input di login web Anda
    private String username;

    @SerializedName("password")
    private String password;

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter dan Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}