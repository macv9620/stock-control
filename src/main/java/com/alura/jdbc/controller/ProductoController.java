package com.alura.jdbc.controller;

import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Product;
import com.alura.jdbc.DAO.ProductDAO;

import java.sql.*;
import java.util.ArrayList;

public class ProductoController {


	//SI SE CREA LA CONEXIÓN COMO UNA VARIABLE DE LA INSTANCIA CUALQUIER MÉTODO QUE LA
	//USE CERRARÁ LA CONEXIÓN Y DENTRO DE ESA INSTANCIA NO SE PODRÁ OCUPAR DE NUEVO
	//LA CONEXIÓN, OPTO POR CREAR CADA VEZ LA CONEXIÓN EN UN MÉTODO

	//private ProductDAO productDAO;
	/*public ProductoController(){
		productDAO = new ProductDAO(new DBConnection().getDBConnection());
	}*/



	public int modificar(Product product) {
		int affectedRows = 0;
		affectedRows = newConnectionDAO().modificar(product);
		return affectedRows;
	}

	public int eliminar(Integer id) {
		// TODO
		int affectedRows = 0;
		affectedRows = newConnectionDAO().eliminar(id);
		return affectedRows;
	}

	public ArrayList<Product> listar() {
		ArrayList<Product> products;
		products = newConnectionDAO().listar();
		return products;
	}

    public int guardar(Product producto) {
		int newProductId = 0;
		newProductId = newConnectionDAO().guardar(producto);
		return  newProductId;
	}

	private ProductDAO newConnectionDAO(){
		return new ProductDAO(new DBConnection().getDBConnection());
	}


}
