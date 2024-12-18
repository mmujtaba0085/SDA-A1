package DAL;

import java.io.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BLL.*;
import Connection.DatabaseConnection;
public class RoomDataAccess {
    
    private static final String ROOMS_FILE = "Rooms.csv";

    // Save room details to CSV file
    public static void saveRooms(List<SingleRoom> singleRooms, List<DoubleRoom> doubleRooms, List<Suite> suites) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOMS_FILE))) {
            for (SingleRoom room : singleRooms) {
                writer.write(roomToCSV(room));
            }
            for (DoubleRoom room : doubleRooms) {
                writer.write(roomToCSV(room));
            }
            for (Suite room : suites) {
                writer.write(roomToCSV(room));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load room details from CSV file
    public static void loadRooms(RoomManagement roomManagement) {
        File file = new File(ROOMS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String type = details[0];
                double price = Double.parseDouble(details[1]);
                String amenities = details[2];
                boolean availability = Boolean.parseBoolean(details[3]);

                switch (type) {
                    case "Single":
                        SingleRoom singleRoom = new SingleRoom(price, amenities);
                        singleRoom.setAvailability(availability);
                        roomManagement.singleRooms.add(singleRoom);
                        break;
                    case "Double":
                        DoubleRoom doubleRoom = new DoubleRoom(price, amenities);
                        doubleRoom.setAvailability(availability);
                        roomManagement.doubleRooms.add(doubleRoom);
                        break;
                    case "Suite":
                        Suite suite = new Suite(price, amenities);
                        suite.setAvailability(availability);
                        roomManagement.suites.add(suite);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function to convert room object to CSV format
    private static String roomToCSV(Room room) {
        return room.getRType() + "," + room.getBasePrice() + "," + room.getAmenities() + "," + room.isAvailable() + "\n";
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
            stmt.setInt(1, room.getRoomNum());
            stmt.setString(2, room.getRType());
            stmt.setDouble(3, room.getBasePrice());
            stmt.setString(4, room.getAmenities());
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
    public static void addBooking(int guestID, int roomNumber, int nightsStaying, double additionalCharges, double totalCost) {
        String query = "INSERT INTO Bookings (guestID, roomNumber, nights, additionalCharges, totalCost) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, guestID);
            stmt.setInt(2, roomNumber);
            stmt.setInt(3, nightsStaying);
            stmt.setDouble(4, additionalCharges);
            stmt.setDouble(5, totalCost);
            stmt.executeUpdate();

            System.out.println("Booking added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding booking.");
            e.printStackTrace();
        }
    }
    public static double getRoomPrice(int roomNumber) {
        String query = "SELECT basePrice FROM Rooms WHERE roomNumber = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, roomNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("basePrice");
            } else {
                System.out.println("Room number not found.");
                return -1; // or throw an exception or return another appropriate value
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving room price.");
            e.printStackTrace();
            return -1; // or throw an exception or return another appropriate value
        }
    }
    // New method to get the first available room type's ID and set its availability to 0
    public static Integer getFirstAvailableRoomType(String roomType) {
        String selectQuery = "SELECT roomNumber FROM Rooms WHERE roomType = ? AND availability = true LIMIT 1";
        String updateQuery = "UPDATE Rooms SET availability = false WHERE roomNumber = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
             
            // Set the room type parameter
            selectStmt.setString(1, roomType);
            ResultSet rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                int roomNumber = rs.getInt("roomNumber");
                
                // Update availability to false
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setInt(1, roomNumber);
                    updateStmt.executeUpdate();
                }
                
                return roomNumber; // Return the roomNumber
            } else {
                System.out.println("No available rooms of type: " + roomType);
                return null; // Or return another appropriate value indicating no rooms are available
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving or updating room availability.");
            e.printStackTrace();
            return null; // Or return another appropriate value indicating an error occurred
        }
    }
}
