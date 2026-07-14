package com.example.pos4u_mobile.data.model;

import com.google.gson.annotations.SerializedName;

public class ProductResponse {
    @SerializedName("id")
    private int id;

    @SerializedName("kode_produk")
    private String kodeProduk;

    @SerializedName("nama_produk")
    private String namaProduk;

    @SerializedName("harga_jual")
    private double hargaJual;

    @SerializedName("stok")
    private int stok;

    @SerializedName("gambar") // Menyimpan nama file gambar untuk ditampilkan via Glide/Picasso
    private String gambar;

    // Getter dan Setter
    public int getId() { return id; }
    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public double getHargaJual() { return hargaJual; }
    public int getStok() { return stok; }
    public String getGambar() { return gambar; }
}