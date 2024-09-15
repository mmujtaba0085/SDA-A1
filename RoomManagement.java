import java.util.ArrayList;
import java.util.List;


public class RoomManagement {
    public List<SingleRoom> singleRooms;
    public List<DoubleRoom> doubleRooms;
    public List<Suite> suites;

   
    
    RoomManagement(){
        singleRooms = new ArrayList<>();
        doubleRooms=new ArrayList<>();
        suites=new ArrayList<>();   
    }

    void addSingle(double price,String amenities){
        SingleRoom temp = new SingleRoom(price, amenities);
        singleRooms.add(temp);
        System.out.println("Single Room Added, Room Number: "+temp.roomNumber);
    }
    void addDouble(double price,String amenities){
        DoubleRoom temp = new DoubleRoom(price, amenities);
        doubleRooms.add(temp);
        System.out.println("Double Room Added, Room Number: "+temp.roomNumber);
    }
    void addSuites(double price,String amenities){
        Suite temp = new Suite(price, amenities);
        suites.add(temp);
        System.out.println("Suite Room Added, Room Number: "+temp.roomNumber);
    }
}
