package com.alura.jdbc.pruebas;

import com.alura.jdbc.bd.DBConnection;

public class PruebasPool {
    public static void main(String[] args) {
        for (int i = 0; i<= 10; i++){
            DBConnection dbConnection = new DBConnection();
            dbConnection.getDBConnection();
            //System.out.println("Abriendo conexión " + i);
        }
    }
}
