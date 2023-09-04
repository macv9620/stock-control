package com.alura.jdbc.controller;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id) {
		int affectedRows = 0;
		try (Connection con = DBConnection.getDBConnection()) {
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
		try(Connection con = DBConnection.getDBConnection()) {
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
		try(Connection con = DBConnection.getDBConnection()){
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

    public int guardar(Product producto) {
		int newProductId = 0;
		String name = producto.getName();
		String description = producto.getDescription();
		int quantity = producto.getQuantity();
		int maxQuantityPerTransaction = 20;

		try(Connection con = DBConnection.getDBConnection()){
			con.setAutoCommit(false);
			do{
				String query = "INSERT INTO product (name, description, quantity)" +
						"VALUES(?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
					//ERROR INDUCIDO
				/*
					if (quantity < 40) {
						throw new  RuntimeException("Error generado");
					}*/

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
