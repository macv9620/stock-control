package com.alura.jdbc.controller;

import com.alura.jdbc.DAO.CategoryDAO;
import com.alura.jdbc.DAO.ProductDAO;
import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

	public ArrayList<Category> listar() {
        return newConnectionDAO().listar();
	}

    public ArrayList<Category> cargaReporte() {
        // TODO
        return newConnectionDAO().listarConProductos();
    }

    private CategoryDAO newConnectionDAO(){
        return new CategoryDAO(new DBConnection().getDBConnection());
    }

}
