package com.example.pos4u_mobile.data.model;

public class CartItem {
    private ProductResponse product;
    private int quantity;

    public CartItem(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Hitung subtotal belanjaan item ini
    public double getSubtotal() {
        return product.getHargaJual() * quantity;
    }
}