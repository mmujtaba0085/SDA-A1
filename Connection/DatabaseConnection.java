package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement; 
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;
public class DatabaseConnection {
    // MySQL connection URL (assuming MySQL is on localhost and default port 3306)
    private static final String URL = "jdbc:mysql://localhost:3306/HotelManagement";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "icu321@"; // Replace with your MySQL password

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load MySQL JDBC driver
            // Class.forName("com.mysql.cj.jdbc.Driver"); // Optional in modern JDBC
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connection established successfully.");
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}