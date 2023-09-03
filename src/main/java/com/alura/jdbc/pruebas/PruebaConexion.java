package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
        System.out.println("Abriendo conexión...");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "Mpt412839");

        System.out.println("Cerrando la conexión...");

        con.close();
    }

}
