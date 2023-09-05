package com.alura.jdbc.DAO;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO {
    private final Connection con;
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
}
