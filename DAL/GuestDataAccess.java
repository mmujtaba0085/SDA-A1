package DAL;

import Connection.DatabaseConnection;
import java.io.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import BLL.Guest;
import BLL.GuestManagement;


public class GuestDataAccess {
    
    private static final String GUESTS_FILE = "Guests.csv";

    // Save guest details to CSV file
    public static void saveGuests(List<Guest> guests) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GUESTS_FILE))) {
            for (Guest guest : guests) {
                writer.write(guestToCSV(guest));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load guest details from CSV file
    public static void loadGuests(GuestManagement guestManagement) {
        File file = new File(GUESTS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(GUESTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String name = details[1];
                String email = details[2];
                String phone = details[3];
                String address = details[4];
                String guestType = details[5];

                switch (guestType) {
                    case "Regular":
                        guestManagement.addRegular(name, email, phone, address);
                        break;
                    case "Frequent":
                        guestManagement.addFrequent(name, email, phone, address);
                        break;
                    case "Corporate":
                        guestManagement.addCorporate(name, email, phone, address);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper function to convert guest object to CSV format
    private static String guestToCSV(Guest guest) {
        return guest.getID() + "," + guest.getName() + "," + guest.getEmail() + "," + guest.getPhNumber() + "," + guest.getAddress() + "," + guest.getGType() + "\n";
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
    public static void updateGuestTotalFee(int guestID, double totalFee) {
        String query = "UPDATE Guests SET totalFee = ? WHERE guestID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, totalFee);
            stmt.setInt(2, guestID);
            stmt.executeUpdate();

            System.out.println("Guest total fee updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteGuest(int guestID) {
        String query = "DELETE FROM Guests WHERE guestID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, guestID);
            stmt.executeUpdate();

            System.out.println("Guest deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
