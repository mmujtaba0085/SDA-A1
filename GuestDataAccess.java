import java.io.*;
import java.util.List;

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
                int guestID = Integer.parseInt(details[0]);
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
        return guest.guestID + "," + guest.Name + "," + guest.Email + "," + guest.phoneNumber + "," + guest.Address + "," + guest.GuestType + "\n";
    }
}
