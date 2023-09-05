package com.alura.jdbc.controller;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Product;
import com.alura.jdbc.DAO.ProductDAO;

import java.sql.*;
import java.util.ArrayList;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id) {
		int affectedRows = 0;
		DBConnection dbConnection = new DBConnection();
		try (Connection con = dbConnection.getDBConnection()) {
			String query = "UPDATE product SET name = ?, description = ? WHERE id = ? ";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, nombre);
			ps.setString(2, descripcion);
			ps.setInt(3, id);
			affectedRows = ps.executeUpdate();

		}catch (SQLException e){
			e.printStackTrace();
		}
		return affectedRows;
	}

	public int eliminar(Integer id) {
		// TODO
		int affectedRows = 0;
		DBConnection dbConnection = new DBConnection();
		try(Connection con = dbConnection.getDBConnection()) {
			String query = "DELETE FROM product WHERE id = ?";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			affectedRows = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return affectedRows;
	}

	public ArrayList<Product> listar() {
		ArrayList<Product> products = new ArrayList<>();
		DBConnection dbConnection = new DBConnection();

		try(Connection con = dbConnection.getDBConnection()){
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

	//IMPLEMENTACIÓN DE PATRÓN DAO-------------------------

    public int guardar(Product producto) {
		int newProductId = 0;
		ProductDAO productDAO =
				new ProductDAO(new DBConnection().getDBConnection());
		newProductId = productDAO.guardar(producto);
		return  newProductId;
	}


}
