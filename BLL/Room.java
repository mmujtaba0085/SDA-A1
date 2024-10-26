package BLL;
public abstract class Room {
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
    Room(String roomType,double basePrice,String Amenities,int roomNumber){
        this.roomNumber=roomNumber;
        this.roomType=roomType;
        this.AvailabilityStatus=true;
        this.basePrice=basePrice;
        this.Amenities=Amenities;
        totalRooms=++roomNumber;
        
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
        System.out.println("------------------------------------------------------------------");
        System.out.println("Type: "+roomType+"\nRoom Number: "+roomNumber+"\nBasePrice: "+basePrice+"\nAmenities: "+Amenities+"\n");
        System.out.println("------------------------------------------------------------------");
    }
    public int TotalRoomInSystem(int val){
        totalRooms=val%7 + (val/7)*100;
        return totalRooms;
    }
    public int UpdateRoomCount(){
        int temp=++totalRooms;
        if((totalRooms%100) % 7 == 0)
        {

            totalRooms-=7;
            totalRooms+=100;
        }
        return temp;
    }

    public int getRoomNum(){
        return roomNumber;
    }
    public String getRType(){
        return roomType;
    }
    public double getBasePrice(){
        return basePrice;
    }
    public String getAmenities(){
        return Amenities;
    }
}


