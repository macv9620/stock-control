package com.alura.jdbc.DAO;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Category;
import com.alura.jdbc.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDAO {
    private final Connection con;
    private ArrayList<Category> categories = new ArrayList<>();
    private ArrayList<Product> products = new ArrayList<>();
    public CategoryDAO(Connection con){
        this.con = con;
    }

    public ArrayList<Category> listar(){
        ArrayList<Category> categories = new ArrayList<>();
        try(con) {
            String query = "SELECT * FROM category";
            try(PreparedStatement ps = con.prepareStatement(query)){
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()){
                        Category category = new Category();
                        category.setId(rs.getInt(1));
                        category.setName(rs.getString(2));
                        categories.add(category);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

    public ArrayList<Category> listarConProductos(){

        try(con) {
            System.out.println("Ejecutando query listarConProductos");
            String query =
                    "SELECT p.id, p.name, p.description, p.quantity, p.category_id, c.name AS category_name FROM product p " +
                    "JOIN category c ON p.category_id = c.id";
            try(PreparedStatement ps = con.prepareStatement(query)){
                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()){
                            Category category = new Category();
                            category.setId(rs.getInt("category_id"));
                            category.setName(rs.getString("category_name"));
                            if (categories.isEmpty()){
                                categories.add(category);
                            } else{
                                boolean categoryAlreadyExists =
                                        categories.stream().noneMatch(c -> category.getId() == c.getId());
                                if (categoryAlreadyExists){
                                    categories.add(category);
                                }
                            }

                            Product product = new Product();
                            product.setId(rs.getInt("id"));
                            product.setName(rs.getString("name"));
                            product.setDescription(rs.getString("description"));
                            product.setQuantity(rs.getInt("quantity"));
                            product.setCategory_id(rs.getInt("category_id"));
                            products.add(product);
                    }

                    //System.out.println(categories.size());
/*
                    products.forEach( p -> {
                        categories.forEach(c ->{
                            if (p.getCategory_id() == c.getId()){
                                System.out.println(c.getName());
                                c.addProduct(p);
                            }
                        });
                    });*/
                    for (Category c : categories){
                            //System.out.println("holi" + c.getName());
                        for (Product p : products){
                        //System.out.println("hola " + p.getName());
                            if (p.getCategory_id() == c.getId()){
                                //System.out.println(p.getCategory_id() == c.getId());
                                //System.out.println("hey" + c.getName());
                                c.addProduct(p);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return categories;
    }

}
