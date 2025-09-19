package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Cine_DB";
    private static final String USER = "root";
    private static final String PASS = "";

    // Constructor
    private DatabaseConnection(){}
    
    // Obtiene conexión
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
    // Testea la conexión
    public static boolean databaseTest() {
        try (Connection c = get()) {
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
