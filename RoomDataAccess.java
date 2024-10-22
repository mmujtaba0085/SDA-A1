import java.io.*;
import java.util.List;

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
        return room.roomType + "," + room.basePrice + "," + room.Amenities + "," + room.isAvailable() + "\n";
    }
}
