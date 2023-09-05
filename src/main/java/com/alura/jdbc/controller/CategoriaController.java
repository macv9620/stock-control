package com.alura.jdbc.controller;

import com.alura.jdbc.DAO.CategoryDAO;
import com.alura.jdbc.DAO.ProductDAO;
import com.alura.jdbc.bd.DBConnection;
import com.alura.jdbc.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

	public ArrayList<Category> listar() {
		ArrayList<Category> categories = null;
        categories = newConnectionDAO().listar();
		return categories;
	}

    public List<?> cargaReporte() {
        // TODO
        return new ArrayList<>();
    }

    private CategoryDAO newConnectionDAO(){
        return new CategoryDAO(new DBConnection().getDBConnection());
    }

}
