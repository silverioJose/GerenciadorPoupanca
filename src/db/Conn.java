package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {

    public static Connection getConnection() throws SQLException {
        try {
            
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            
            System.err.println("Erro: Driver do SQLite não encontrado no Classpath!");
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:sqlite:base.db");
    }
}