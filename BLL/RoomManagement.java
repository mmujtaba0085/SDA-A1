package BLL;

//import DAL.RoomDataAccess;
import java.util.ArrayList;
import java.util.List;


public class RoomManagement {
    public List<SingleRoom> singleRooms;
    public List<DoubleRoom> doubleRooms;
    public List<Suite> suites;

   
    
    public RoomManagement(){
        singleRooms = new ArrayList<>();
        doubleRooms=new ArrayList<>();
        suites=new ArrayList<>();   
    }

    public void addSingle(double price,String amenities){
        SingleRoom temp = new SingleRoom(price, amenities);
        singleRooms.add(temp);
        System.out.println("Single Room Added, Room Number: "+temp.roomNumber);
    }
    public void addDouble(double price,String amenities){
        DoubleRoom temp = new DoubleRoom(price, amenities);
        doubleRooms.add(temp);
        System.out.println("Double Room Added, Room Number: "+temp.roomNumber);
    }
    public void addSuites(double price,String amenities){
        Suite temp = new Suite(price, amenities);
        suites.add(temp);
        System.out.println("Suite Room Added, Room Number: "+temp.roomNumber);
    }
    public void Price_Range_Room(double max){
        if(max<0){
            System.out.println("Error (Value<0)");
            return;
        }

        for (SingleRoom temp : singleRooms){
            if(temp.basePrice<=max && temp.isAvailable())
                temp.PrintInfo();
        }
        for(DoubleRoom temp : doubleRooms){
            if(temp.basePrice<=max && temp.isAvailable())
                temp.PrintInfo();
        }
        for(Suite temp : suites){
            if(temp.basePrice<=max && temp.isAvailable())
                temp.PrintInfo();
        }
    }

    public void SingleRoomAvail(){
        for (SingleRoom temp : singleRooms){
            if(temp.isAvailable())
                temp.PrintInfo();
        }
    }

    public void DoubleRoomAvail(){
        for(DoubleRoom temp : doubleRooms){
            if(temp.isAvailable())
                temp.PrintInfo();
        }
    }

    public void SuiteAvial(){
        for(Suite temp : suites){
            if(temp.isAvailable())
                temp.PrintInfo();
        }
    }

    void setAvail_Status(int roomNumber,boolean AvailabilityStatus,String UnAvail_Reason){
        for (SingleRoom temp : singleRooms) {
            if(temp.roomNumber==roomNumber){
                temp.AvailabilityStatus=AvailabilityStatus;
                temp.Unavailability_Reason=UnAvail_Reason;
            }
        }
        for(DoubleRoom temp : doubleRooms){
            if(temp.roomNumber==roomNumber){
                temp.AvailabilityStatus=AvailabilityStatus;
                temp.Unavailability_Reason=UnAvail_Reason;
            }
        }
        for(Suite temp : suites){
            if(temp.roomNumber==roomNumber){
                temp.AvailabilityStatus=AvailabilityStatus;
                temp.Unavailability_Reason=UnAvail_Reason;
            }
        }
    }
    
}
