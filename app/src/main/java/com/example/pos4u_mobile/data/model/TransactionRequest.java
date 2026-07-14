package com.example.pos4u_mobile.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TransactionRequest {
    @SerializedName("total_harga")
    private double totalHarga;

    @SerializedName("bayar")
    private double bayar;

    @SerializedName("kembalian")
    private double kembalian;

    @SerializedName("items")
    private List<ItemOrder> items;

    public TransactionRequest(double totalHarga, double bayar, double kembalian, List<ItemOrder> items) {
        this.totalHarga = totalHarga;
        this.bayar = bayar;
        this.kembalian = kembalian;
        this.items = items;
    }

    // Kelas internal untuk format item yang dibeli
    public static class ItemOrder {
        @SerializedName("produk_id")
        private int produkId;

        @SerializedName("qty")
        private int qty;

        @SerializedName("harga_satuan")
        private double hargaSatuan;

        public ItemOrder(int produkId, int qty, double hargaSatuan) {
            this.produkId = produkId;
            this.qty = qty;
            this.hargaSatuan = hargaSatuan;
        }
    }
}