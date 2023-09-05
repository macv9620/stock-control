package com.alura.jdbc.DAO;

import com.alura.jdbc.model.Product;

import java.sql.*;

public class ProductDAO {
    private final Connection con;

    public ProductDAO(Connection con){
        this.con =  con;
    }
    public int guardar(Product producto) {
        int newProductId = 0;
        String name = producto.getName();
        String description = producto.getDescription();
        int quantity = producto.getQuantity();
        int maxQuantityPerTransaction = 20;

        try(con){
            con.setAutoCommit(false);
            do{
                String query = "INSERT INTO product (name, description, quantity)" +
                        "VALUES(?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                try{
                    if(quantity > maxQuantityPerTransaction){
                        newProductId = ejecutarGuardado(name, description, maxQuantityPerTransaction, ps);
                        quantity-=maxQuantityPerTransaction;
                    } else {
                        newProductId = ejecutarGuardado(name, description, quantity, ps);
                        quantity = 0;
                        con.commit();
                        System.out.println("Successful commit...");
                    }
                } catch (RuntimeException e){
                    e.printStackTrace();
                    con.rollback();
                    System.out.println("Rollback");
                }


            } while (quantity != 0);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return newProductId;
    }

    private int ejecutarGuardado(String name, String description, int quantity, PreparedStatement ps) throws SQLException{
        int newProductId = 0;

        ps.setString(1, name);
        ps.setString(2, description);
        ps.setInt(3, quantity);
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();

        while (generatedKeys.next()){
            newProductId = generatedKeys.getInt(1);
        }
        return newProductId;
    }
}
