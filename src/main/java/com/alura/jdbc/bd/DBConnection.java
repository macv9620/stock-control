package com.alura.jdbc.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getDBConnection(){
        Connection con = null;

        try{
            System.out.println("Iniciando conexión");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC",
                    "root",
                    "Mpt412839"
            );
            System.out.println("Conexión establecida");

        } catch (SQLException e){
            System.out.println("Error en conexión");
            e.printStackTrace();
        }

        return con;
    }
}
