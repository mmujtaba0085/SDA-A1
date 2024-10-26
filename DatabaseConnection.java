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


    public static void addRoom(Room room) {

        String query1 = "SELECT COUNT(*) AS totalRooms FROM Rooms";
        String query2 = "INSERT INTO Rooms (roomNumber, roomType, basePrice, amenities, availability) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt1 = conn.prepareStatement(query1);
             PreparedStatement stmt = conn.prepareStatement(query2)) {
    
            // Execute query1 to get total room count
            ResultSet resultSet = stmt1.executeQuery();
            if (resultSet.next()) {
                int totalRooms = resultSet.getInt("totalRooms");
                // Pass the totalRooms to the room's TotalRoomInSystem method

                room.TotalRoomInSystem(totalRooms);
            }
    
            // Set parameters for query2 to insert the new room
            stmt.setInt(1, room.roomNumber);
            stmt.setString(2, room.roomType);
            stmt.setDouble(3, room.basePrice);
            stmt.setString(4, room.Amenities);
            stmt.setBoolean(5, room.isAvailable());
    
            // Execute the insert statement
            stmt.executeUpdate();
            System.out.println("Room added successfully.");
    
        } catch (SQLException e) {
            System.out.println("Error adding room to the database.");
            e.printStackTrace();
        }
    }
    
    public static void showAvailableRooms() {
        String query = "SELECT * FROM Rooms WHERE availability = true";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Available Rooms:");
            while (rs.next()) {
                System.out.printf("Room Number: %d, Type: %s, Price: %.2f, Amenities: %s%n",
                        rs.getInt("roomNumber"),
                        rs.getString("roomType"),
                        rs.getDouble("basePrice"),
                        rs.getString("amenities"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateRoomAvailability(int roomNumber, boolean availability) {
        String query = "UPDATE Rooms SET availability = ? WHERE roomNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, availability);
            stmt.setInt(2, roomNumber);
            stmt.executeUpdate();

            System.out.println("Room availability updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteRoom(int roomNumber) {
        String query = "DELETE FROM Rooms WHERE roomNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomNumber);
            stmt.executeUpdate();

            System.out.println("Room deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addGuest(String name, String email, String phoneNumber, String address, String guestType) {
        String query = "INSERT INTO Guests (guestID,namee, email, phoneNumber, address, guestType) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Guest.getTotalGuest());
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, phoneNumber);
            stmt.setString(5, address);
            stmt.setString(6, guestType);
            stmt.executeUpdate();

            System.out.println("Guest added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showAllGuests() {
        String query = "SELECT * FROM Guests";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Guests:");
            while (rs.next()) {
                System.out.printf("Guest ID: %d, Name: %s, Email: %s, Phone: %s, Address: %s, Type: %s%n",
                        rs.getInt("guestID"),
                        rs.getString("namee"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("guestType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

