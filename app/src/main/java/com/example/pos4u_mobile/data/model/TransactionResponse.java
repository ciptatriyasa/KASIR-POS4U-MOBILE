package com.example.pos4u_mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class TransactionResponse {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("invoice_number")
    private String invoiceNumber;

    public boolean isStatus() { return status; }
    public String getMessage() { return message; }
    public String getInvoiceNumber() { return invoiceNumber; }
}