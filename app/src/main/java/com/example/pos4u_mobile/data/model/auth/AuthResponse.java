package com.example.pos4u_mobile.data.model.auth;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("role")
    private String role;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public String getToken() { return token; }
    public String getRole() { return role; }
}