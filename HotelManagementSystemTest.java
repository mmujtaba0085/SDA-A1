import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class HotelManagementSystemTest {

    private RoomManagement roomManagement;
    private GuestManagement guestManagement;

    @Before
    public void setUp() {
        roomManagement = new RoomManagement();
        guestManagement = new GuestManagement();
    }

    @Test
    public void testAddSingleRoom() {
        roomManagement.addSingle(100, "Wi-Fi, AC");
        assertEquals(1, roomManagement.singleRooms.size());
        assertEquals("Single", roomManagement.singleRooms.get(0).roomType);
    }

    @Test
    public void testAddDoubleRoom() {
        roomManagement.addDouble(150, "Wi-Fi, AC, TV");
        assertEquals(1, roomManagement.doubleRooms.size());
        assertEquals("Double", roomManagement.doubleRooms.get(0).roomType);
    }

    @Test
    public void testAddSuiteRoom() {
        roomManagement.addSuites(300, "Wi-Fi, AC, TV, Kitchen");
        assertEquals(1, roomManagement.suites.size());
        assertEquals("Suite", roomManagement.suites.get(0).roomType);
    }

    @Test
    public void testAddRegularGuest() {
        guestManagement.addRegular("John Doe", "john@example.com", "1234567890", "123 Street");
        assertEquals(1, guestManagement.regularGuest.size());
        assertEquals("John Doe", guestManagement.regularGuest.get(0).Name);
    }

    @Test
    public void testAddFrequentGuest() {
        guestManagement.addFrequent("Jane Doe", "jane@example.com", "0987654321", "456 Avenue");
        assertEquals(1, guestManagement.frequentGuest.size());
        assertEquals("Jane Doe", guestManagement.frequentGuest.get(0).Name);
    }

    @Test
    public void testAddCorporateGuest() {
        guestManagement.addCorporate("Corporate Client", "corporate@example.com", "5678912345", "789 Road");
        assertEquals(1, guestManagement.corporateGuest.size());
        assertEquals("Corporate Client", guestManagement.corporateGuest.get(0).Name);
    }

    @Test
    public void testBookRoom() {
        // Setup a single room and a guest
        roomManagement.addSingle(100, "Wi-Fi, AC");
        guestManagement.addRegular("John Doe", "john@example.com", "1234567890", "123 Street");

        // Fetch the guest by ID and check room availability
        Guest guest = guestManagement.findGuestByID(1);
        assertNotNull(guest);

        Room room = roomManagement.singleRooms.get(0);
        assertTrue(room.isAvailable());

        // Book the room
        double cost = room.BookingCharges(2, 10);
        guest.TotalFee += cost;
        room.setAvailability(false);

        // Validate the room is booked and guest's booking history is updated
        assertFalse(room.isAvailable());
        assertEquals(210.0, guest.TotalFee, 0.0); // 2 nights, base price 100 each and 5% service charges
    }

    @Test
    public void testAvailableRooms() {
        roomManagement.addSingle(100, "Wi-Fi, AC");
        roomManagement.addDouble(150, "Wi-Fi, AC, TV");
        roomManagement.addSuites(300, "Wi-Fi, AC, TV, Kitchen");

        // Initially, all rooms should be available
        assertEquals(1, roomManagement.singleRooms.size());
        assertTrue(roomManagement.singleRooms.get(0).isAvailable());

        assertEquals(1, roomManagement.doubleRooms.size());
        assertTrue(roomManagement.doubleRooms.get(0).isAvailable());

        assertEquals(1, roomManagement.suites.size());
        assertTrue(roomManagement.suites.get(0).isAvailable());
    }

   

    @Test
    public void testFrequentGuestUpgrade() {
        Regular regularGuest = new Regular("John Doe", "john@example.com", "1234567890", "123 Street");
        regularGuest.TotalEntries = 3; // Set enough visits to qualify for Frequent Guest

        Frequent upgradedGuest = regularGuest.FrequentGuestCheck();
        assertNotNull(upgradedGuest);  // Check if upgraded
        assertEquals("Frequent", upgradedGuest.GuestType);  // Ensure guest is upgraded
    }
   
    @Test
    public void testSetRoomUnavailableDueToMaintenance() {
        roomManagement.addSingle(100, "Wi-Fi, AC");
        int roomNumber = roomManagement.singleRooms.get(0).roomNumber;
        
        // Set the room to unavailable due to maintenance
        roomManagement.setAvail_Status(roomNumber, false, "Maintenance");

        SingleRoom room = roomManagement.singleRooms.get(0);
        assertFalse(room.isAvailable());
        assertEquals("Maintenance", room.Unavailability_Reason);
    }

    @Test
    public void testSetRoomUnavailableDueToOccupied() {
        roomManagement.addDouble(150, "Wi-Fi, AC, TV");
        int roomNumber = roomManagement.doubleRooms.get(0).roomNumber;
        
        // Set the room to unavailable due to being occupied
        roomManagement.setAvail_Status(roomNumber, false, "Occupied");

        DoubleRoom room = roomManagement.doubleRooms.get(0);
        assertFalse(room.isAvailable());
        assertEquals("Occupied", room.Unavailability_Reason);
    }

    @Test
    public void testSetRoomUnavailableDueToNoLongerInService() {
        roomManagement.addSuites(300, "Wi-Fi, AC, TV, Kitchen");
        int roomNumber = roomManagement.suites.get(0).roomNumber;
        
        // Set the room to unavailable as it is no longer in service
        roomManagement.setAvail_Status(roomNumber, false, "No longer in service");

        Suite room = roomManagement.suites.get(0);
        assertFalse(room.isAvailable());
        assertEquals("No longer in service", room.Unavailability_Reason);
    }
}
