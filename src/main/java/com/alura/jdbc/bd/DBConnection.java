package com.alura.jdbc.bd;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private DataSource datasource;

    public DBConnection(){
        ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();
        pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_stock?useTimeZone=true&serverTimeZone=UTC");
        pooledDataSource.setUser("root");
        pooledDataSource.setPassword("Mpt412839");

        //Seteando máxima cantidad de conexiones
        pooledDataSource.setMaxPoolSize(10);

        this.datasource = pooledDataSource;
    }

    public Connection getDBConnection(){
        Connection con = null;

        try{
            System.out.println("Iniciando conexión");
            con = this.datasource.getConnection();
            //System.out.println("Conexión establecida");

        } catch (SQLException e){
            //System.out.println("Error en conexión");
            e.printStackTrace();
        }

        return con;
    }
}
