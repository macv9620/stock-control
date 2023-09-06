package com.alura.jdbc.DAO;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Product;

import java.sql.*;
import java.util.ArrayList;

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
        int maxQuantityPerTransaction = 100;

        try(con){
            con.setAutoCommit(false);
            do{
                String query = "INSERT INTO product (name, description, quantity, category_id)" +
                        "VALUES(?, ?, ?, ?)"; //Modificar ejecutarGuardado
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                try{
                    if(quantity > maxQuantityPerTransaction){
                        newProductId = ejecutarGuardado(name, description, maxQuantityPerTransaction, producto.getCategory_id(), ps);
                        quantity-=maxQuantityPerTransaction;
                    } else {
                        newProductId = ejecutarGuardado(name, description, quantity, producto.getCategory_id(), ps);
                        quantity = 0;
                        con.commit();
                        //System.out.println("Successful commit...");
                    }
                } catch (RuntimeException e){
                    e.printStackTrace();
                    con.rollback();
                    //System.out.println("Rollback");
                }


            } while (quantity != 0);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return newProductId;
    }

    private int ejecutarGuardado(String name, String description, int quantity, int product_id, PreparedStatement ps) throws SQLException{
        int newProductId = 0;

        ps.setString(1, name);
        ps.setString(2, description);
        ps.setInt(3, quantity);
        ps.setInt(4, product_id);
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();

        while (generatedKeys.next()){
            newProductId = generatedKeys.getInt(1);
        }
        return newProductId;
    }

    public ArrayList<Product> listar() {
        ArrayList<Product> products = new ArrayList<>();
        try(con){
            String query = "SELECT * FROM product";
            try(PreparedStatement preparedStatement = con.prepareStatement(query)){
                try(ResultSet result = preparedStatement.executeQuery()) {
                    while (result.next()){

                        Product product = new Product();
                        product.setId(result.getInt("id"));
                        product.setName(result.getString("name"));
                        product.setDescription(result.getString("description"));
                        product.setQuantity(result.getInt("quantity"));

                        products.add(product);
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    public int eliminar(Integer id) {
        // TODO
        int affectedRows = 0;
        try(this.con) {
            String query = "DELETE FROM product WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            affectedRows = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return affectedRows;
    }

    public int modificar(Product product) {
        int affectedRows = 0;
        try (this.con) {
            String query = "UPDATE product SET name = ?, description = ? WHERE id = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getId());
            affectedRows = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return affectedRows;
    }
}
