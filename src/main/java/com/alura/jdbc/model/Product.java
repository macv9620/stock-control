package com.alura.jdbc.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private int category_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category) {
        this.category_id = category;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
