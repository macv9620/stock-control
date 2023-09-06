package com.alura.jdbc.model;

import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private ArrayList<Product> products = new ArrayList<>();

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
