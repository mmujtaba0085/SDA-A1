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
    
    public static List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE availability = true";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                String roomType = rs.getString("roomType");
                double basePrice = rs.getDouble("basePrice");
                String amenities = rs.getString("amenities");
                boolean availability = rs.getBoolean("availability");

                Room room = new Room(roomType, basePrice, amenities,roomNumber); // You can use subclasses (SingleRoom, DoubleRoom) if necessary
                //room.setRoomNumber(roomNumber);
                room.setAvailability(availability);
                availableRooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableRooms;
    }
}

