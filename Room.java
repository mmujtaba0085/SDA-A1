abstract class Room {
    protected int roomNumber;
    protected String roomType;
    protected boolean AvailabilityStatus;
    protected String Unavailability_Reason;
    protected double basePrice;
    protected String Amenities;
    private static int totalRooms=0;

    Room(String roomType,double basePrice,String Amenities){
        this.roomNumber=++totalRooms;
        this.roomType=roomType;
        this.AvailabilityStatus=true;
        this.basePrice=basePrice;
        this.Amenities=Amenities;
        if((totalRooms%100) % 7 == 0)
        {

            totalRooms-=7;
            totalRooms+=100;
        }
    }

    public boolean isAvailable(){
        return AvailabilityStatus;
    }

    public void setAvailability(boolean AvailabilityStatus){
        this.AvailabilityStatus=AvailabilityStatus;
    }

    abstract public double BookingCharges(int nights, double additionalCharges);

    public void PrintInfo()
    {
        System.out.println("Type: "+roomType+"\nRoom Number: "+roomNumber+"\nBasePrice: "+basePrice+"\nAmenities: "+Amenities+"\n");
    }
}

class SingleRoom extends Room{
    SingleRoom(double basePrice,String Amenities){
        super("Single", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice + 0.5; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges) * nights ;   
    }
    
}

class DoubleRoom extends Room{
    DoubleRoom(double basePrice,String Amenities){
        super("Double", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice + 0.5; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges + additionalCharges) * nights  ;
    }
    
}

class Suite extends Room{
    Suite(double basePrice,String Amenities){
        super("Suite", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice * 0.05; //service Charges -> 5% of basePrice
        double luxuryTax=basePrice * 0.15; //luxury Tax -> 15% of basePrice
        return (basePrice + serviceCharges + luxuryTax+ additionalCharges) * nights  ;
    }
    
}