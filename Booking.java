public class Booking {
    Guest guest;
    Room room;
    int nights;
    double additionalCharges;

    Booking(Guest guest, Room room, int nights, double additionalCharges) {
        this.guest = guest;
        this.room = room;
        this.nights = nights;
        this.additionalCharges = additionalCharges;
    }

    public double calculateTotalCost() {
        return room.BookingCharges(nights, additionalCharges);
    }

    public void displayBookingDetails() {
        System.out.println("Guest: " + guest.Name);
        System.out.println("Room Type: " + room.roomType);
        System.out.println("Room Number: " + room.roomNumber);
        System.out.println("Total Nights: " + nights);
        System.out.println("Total Charges: " + calculateTotalCost());
    }
}
