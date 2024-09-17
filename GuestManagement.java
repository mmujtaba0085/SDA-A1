import java.util.ArrayList;
import java.util.List;


public class GuestManagement {
    public List<Regular> regularGuest;
    public List<Frequent> frequentGuest;
    public List<Corporate> corporateGuest;
    public List<Guest> TotalGuest;
    public List<Guest> guestStayList; // Unified list for guests currently staying

    GuestManagement(){
        regularGuest=new ArrayList<>();
        frequentGuest=new ArrayList<>();
        corporateGuest=new ArrayList<>();
        TotalGuest=new ArrayList<>();
        guestStayList =new ArrayList<>();
    }

    void addRegular(String Name,String Email,String phoneNumber,String Address){
        Regular temp= new Regular(Name, Email, phoneNumber, Address);
        regularGuest.add(temp);
        TotalGuest.add(temp);
    }

    void addFrequent(String Name,String Email,String phoneNumber,String Address){
        Frequent temp= new Frequent(Name, Email, phoneNumber, Address);
        frequentGuest.add(temp);
        TotalGuest.add(temp);
    }

    void addCorporate(String Name,String Email,String phoneNumber,String Address){
        Corporate temp= new Corporate(Name, Email, phoneNumber, Address);
        corporateGuest.add(temp);
        TotalGuest.add(temp);
    }

    void GuestDetails(int id){
        for(Corporate temp : corporateGuest){
            if(temp.guestID==id){
                temp.PrintInfo();
                return;
            }
        }

        for(Frequent temp : frequentGuest){
            if(temp.guestID==id){
                temp.PrintInfo();
                return;
            }
        }

        for(Regular temp : regularGuest){
            if(temp.guestID==id){
                temp.PrintInfo();
                return;
            }
        }

    }
    void GuestIdName(){
       for(Guest temp : TotalGuest){
            System.out.println("Guest Id: " + temp.guestID + " Guest Name: " + temp.Name +"\n" );
       }
    }
    Guest findGuestByID(int guestID) {
        for(Corporate temp : corporateGuest){
            if(temp.guestID==guestID){
               return temp;
            }
        }

        for(Frequent temp : frequentGuest){
            if(temp.guestID==guestID){
                return temp;
            }
        }

        for(Regular temp : regularGuest){
            if(temp.guestID==guestID){
                return temp;
            }
        }
        
        return null;
    }
    void AddinHotelGuest(Guest guest){
        guestStayList.add(guest);
    }

    void ShowinHotelGuest(){
        for(Guest temp: guestStayList){
            temp.PrintInfo();
        }
    }
    void RemoveinHotelGuest(Guest guest){
        guestStayList.remove(guest);
    }

}
