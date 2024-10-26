import BLL.*;
import DAL.*;
//import Connection.DatabaseConnection;
import java.util.Scanner;

public class MainMenu {
    static RoomManagement roomManagement = new RoomManagement();
    static GuestManagement guestManagement = new GuestManagement();
    static Scanner scanner = new Scanner(System.in);
    static boolean useDatabase = false;  // Set this based on user input at the start
    public static void main(String[] args) {
        boolean running = true;
        
        // Ask the user to choose between file or database storage at the start
        System.out.println("Choose data storage option (1: File, 2: Database): ");
        int storageOption = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (storageOption == 2) {
            useDatabase = true;
            System.out.println("Database storage selected.");
        } else {
            System.out.println("File storage selected.");
            // Load rooms from file on program start
            RoomDataAccess.loadRooms(roomManagement);
            // Load guests from file on program start
            GuestDataAccess.loadGuests(guestManagement);
        }


        

        while (running) {
            System.out.println("------------------------------------------------------------------");
            System.out.println("Hotel Management System");
            System.out.println("1. Add Room");
            System.out.println("2. Add Guest");
            System.out.println("3. Book a Room");
            System.out.println("4. Show Guests Staying");
            System.out.println("5. Show All Guest");
            System.out.println("6. Show Available Rooms");
            System.out.println("7. Show Available Rooms By Price");
            System.out.println("8. Remove Guest");
            System.out.println("9. Show Guest History");
            System.out.println("10. Exit");
            System.out.println("------------------------------------------------------------------");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    addRoom();
                    break;
                case 2:
                    addGuest();
                    break;
                case 3:
                    bookRoom();
                    break;
                case 4:
                    guestManagement.ShowinHotelGuest();
                    break;
                case 5:
                    if(useDatabase)
                    {
                        GuestDataAccess.showAllGuests();
                        break;
                    }
                    guestManagement.GuestIdName();
                    break;
                case 6:
                    showAvailableRooms();
                    break;
                case 7:
                    System.out.println("Enter Max Price Range: ");
                    double price = scanner.nextDouble();
                    if(price<0){
                        break;
                    }
                    roomManagement.Price_Range_Room(price);
                    break;
                case 8:
                    System.out.println("Provide Guest ID: ");
                    int id=scanner.nextInt();
                    if(useDatabase)
                    {
                        GuestDataAccess.RemoveGuest(id);
                        break;
                    }
                    guestManagement.RemoveinHotelGuest(guestManagement.findGuestByID(id));
                    break;
                case 9:
                    System.out.println("Provide Guest ID:");
                    int iD=scanner.nextInt();
                    guestManagement.findGuestByID(iD).PrintInfo();
                    break;
                case 10:
                    running = false;
                    // Save rooms to file before exit
                    RoomDataAccess.saveRooms(roomManagement.singleRooms, roomManagement.doubleRooms, roomManagement.suites);
                    // Save guests to file before exit
                    GuestDataAccess.saveGuests(guestManagement.TotalGuest);
                    System.out.println("Data saved. Exiting...");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
            System.out.println("------------------------------------------------------------------");
        }
    }
    
    private static void addRoom() {
        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();
        System.out.print("Enter base price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter amenities (e.g., Wi-Fi, AC): ");
        String amenities = scanner.nextLine();
       
        Room room;
        switch (roomType.toLowerCase()) {
            case "single":
                room = new SingleRoom(price, amenities);
                roomManagement.singleRooms.add((SingleRoom) room);
                break;
            case "double":
                room = new DoubleRoom(price, amenities);
                roomManagement.doubleRooms.add((DoubleRoom) room);
                break;
            case "suite":
                room = new Suite(price, amenities);
                roomManagement.suites.add((Suite) room);
                break;
            default:
                System.out.println("Invalid room type.");
                return;
        }

        if (useDatabase) {
            RoomDataAccess.addRoom(room);  // Save to database
        }
        System.out.println("Room added successfully.");
    }
    private static void bookRoomDatabase(int guestID, String roomType, int nights, double additionalCharges) {

        
    
        double totalCost =0;
        if(roomType=="single")
        {
            double serviceCharges=5000 * 0.05; //service Charges -> 5% of basePrice
            totalCost=5000 + serviceCharges * nights ;   
        }
        else if(roomType=="double")
        {
            double serviceCharges=8000 * 0.05; //service Charges -> 5% of basePrice
            totalCost = (8000 + serviceCharges + additionalCharges) * nights  ;
        }
        else{
            double serviceCharges=12000 * 0.05; //service Charges -> 5% of basePrice
            totalCost = (8000 + serviceCharges + additionalCharges) * nights  ;
        }
        GuestDataAccess.updateGuestTotalFee(guestID, totalCost);
    
        // Insert booking record into Bookings table
        RoomDataAccess.addBooking(guestID, RoomDataAccess.getFirstAvailableRoomType(roomType), nights, additionalCharges, totalCost);
    
        // Add guest to GuestStay table for current stay
        GuestDataAccess.GuestStay(guestID, room.getRoomNum());
    
    
        System.out.println("Room booked successfully. Total cost: " + totalCost);
    }
    
    private static void addGuest() {
        System.out.print("Enter guest type (Regular/Frequent/Corporate): ");
        String guestType = scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        switch (guestType.toLowerCase()) {
            case "regular":
                guestManagement.addRegular(name, email, phone, address);
                break;
            case "frequent":
                guestManagement.addFrequent(name, email, phone, address);
                break;
            case "corporate":
                guestManagement.addCorporate(name, email, phone, address);
                break;
            default:
                System.out.println("Invalid guest type.");
        }

        if(useDatabase){
            GuestDataAccess.addGuest(name, email, phone, address, guestType);
        }
    }

    private static void bookRoom() {
        System.out.print("Enter guest ID: ");
        int guestID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Guest guest = guestManagement.findGuestByID(guestID);
        if (guest == null && !useDatabase) {
            System.out.println("Guest not found.");
            return;
        }

        System.out.print("Enter room type to book (Single/Double/Suite): ");
        String roomType = scanner.nextLine();
        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        System.out.print("Enter additional charges (if any): ");
        double additionalCharges = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Room room = findAvailableRoom(roomType);
        if (room == null && !useDatabase) {
            System.out.println("No available rooms of this type.");
            return;
        }

        double totalCost = room.BookingCharges(nights, additionalCharges);
        guest.setTotalFee(guest.getTotalFee()+totalCost);
        guest.addBookingHistory("Room " + room.getRoomNum() + " (" + roomType + "), " + nights + " nights");

        room.setAvailability(false); // Mark room as unavailable
        guestManagement.AddinHotelGuest(guest);
        if(useDatabase)
        {
            bookRoomDatabase(guestID, roomType.toLowerCase(), nights, additionalCharges);
        }
        if(!useDatabase)
        {
            System.out.println("Room booked successfully. Total cost: " + totalCost);
        }
        
    }



    private static Room findAvailableRoom(String roomType) {
        switch (roomType.toLowerCase()) {
            case "single":
                for (SingleRoom room : roomManagement.singleRooms) {
                    if (room.isAvailable()) {
                        return room;
                    }
                }
                break;
            case "double":
                for (DoubleRoom room : roomManagement.doubleRooms) {
                    if (room.isAvailable()) {
                        return room;
                    }
                }
                break;
            case "suite":
                for (Suite room : roomManagement.suites) {
                    if (room.isAvailable()) {
                        return room;
                    }
                }
                break;
        }
        return null;
    }

  
    private static void showAvailableRooms() {
        if (useDatabase) {
            RoomDataAccess.showAvailableRooms();
        } else {
            roomManagement.SingleRoomAvail();
            roomManagement.DoubleRoomAvail();
            roomManagement.SuiteAvial();
        }
    }
}
