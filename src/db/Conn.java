package db;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        new File("db").mkdirs();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:db/base.db");
        criarTabelas(conn);
        return conn;
    }

    private static void criarTabelas(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS contas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                saldo REAL NOT NULL DEFAULT 0.0,
                meta REAL NOT NULL
            );
        """;
        conn.createStatement().execute(sql);
        
        String sql2 = """
        	    CREATE TABLE IF NOT EXISTS transacoes (
        	        id INTEGER PRIMARY KEY AUTOINCREMENT,
        	        conta_id INTEGER NOT NULL,
        	        tipo TEXT NOT NULL,
        	        valor REAL NOT NULL,
        	        data TEXT NOT NULL,
        	        FOREIGN KEY (conta_id) REFERENCES contas(id)
        	    );
        	""";
        conn.createStatement().execute(sql2);
    }
}