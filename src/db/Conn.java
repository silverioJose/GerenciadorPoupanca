package db;
import java.io.File;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    public static Connection getConnection() throws SQLException, URISyntaxException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String jarPath = new File(Conn.class.getProtectionDomain()
            .getCodeSource().getLocation().toURI()).getParentFile().getParentFile().getAbsolutePath();
        String dbPath = jarPath + File.separator + "db" + File.separator + "base.db";
        new File(jarPath + File.separator + "db").mkdirs();
        System.out.println("Banco em: " + dbPath);
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        criarTabelas(conn);
        return conn;
    }
    private static void criarTabelas(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS contas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                saldo REAL NOT NULL DEFAULT 0.0,
                meta REAL NOT NULL,
                criacao TEXT NOT NULL
            );
        """;
        conn.createStatement().execute(sql);
        String sql2 = """
                CREATE TABLE IF NOT EXISTS transacoes (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    contaId INTEGER NOT NULL,
                    tipo TEXT NOT NULL,
                    valor REAL NOT NULL,
                    data TEXT NOT NULL,
                    FOREIGN KEY (contaId) REFERENCES contas(id)
                );
            """;
        conn.createStatement().execute(sql2);
        try {
            conn.createStatement().execute(
                "ALTER TABLE transacoes ADD COLUMN data TEXT DEFAULT ''"
            );
        } catch (SQLException e) {
        	
        }
    }
}