abstract class Room {
    protected int roomNumber;
    protected String roomType;
    protected boolean AvailabilityStatus;
    protected double basePrice;
    protected String Amenities;

    Room(int roomNumber,String roomType,double basePrice,String Amenities){
        this.roomNumber=roomNumber;
        this.roomType=roomType;
        this.AvailabilityStatus=true;
        this.basePrice=basePrice;
        this.Amenities=Amenities;
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
        System.out.println("Type: "+roomType+"\nRoom Number: "+roomNumber+"\nBasePrice: "+"\nAmenities: "+Amenities);
    }
}

class SingleRoom extends Room{
    SingleRoom(int roomNumber,double basePrice,String Amenities){
        super(roomNumber,"Single", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice + 0.5; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges) * nights ;   
    }
    
}

class DoubleRoom extends Room{
    DoubleRoom(int roomNumber,double basePrice,String Amenities){
        super(roomNumber,"Double", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice + 0.5; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges + additionalCharges) * nights  ;
    }
    
}

class Suite extends Room{
    Suite(int roomNumber,double basePrice,String Amenities){
        super(roomNumber,"Suite", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice * 0.05; //service Charges -> 5% of basePrice
        double luxuryTax=basePrice * 0.15; //luxury Tax -> 15% of basePrice
        return (basePrice + serviceCharges + luxuryTax+ additionalCharges) * nights  ;
    }
    
}