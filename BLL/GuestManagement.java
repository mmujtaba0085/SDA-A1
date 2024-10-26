package BLL;


//import DAL.GuestDataAccess;
import java.util.ArrayList;
import java.util.List;


public class GuestManagement {
    public List<Regular> regularGuest;
    public List<Frequent> frequentGuest;
    public List<Corporate> corporateGuest;
    public List<Guest> TotalGuest;
    public List<Guest> guestStayList; // Unified list for guests currently staying

    public GuestManagement(){
        regularGuest=new ArrayList<>();
        frequentGuest=new ArrayList<>();
        corporateGuest=new ArrayList<>();
        TotalGuest=new ArrayList<>();
        guestStayList =new ArrayList<>();
    }

    public void addRegular(String Name,String Email,String phoneNumber,String Address){
        Regular temp= new Regular(Name, Email, phoneNumber, Address);
        regularGuest.add(temp);
        TotalGuest.add(temp);
    }

    public void addFrequent(String Name,String Email,String phoneNumber,String Address){
        Frequent temp= new Frequent(Name, Email, phoneNumber, Address);
        frequentGuest.add(temp);
        TotalGuest.add(temp);
    }

    public void addCorporate(String Name,String Email,String phoneNumber,String Address){
        Corporate temp= new Corporate(Name, Email, phoneNumber, Address);
        corporateGuest.add(temp);
        TotalGuest.add(temp);
    }

    public void GuestDetails(int id){
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
    public void GuestIdName(){
       for(Guest temp : TotalGuest){
            System.out.println("Guest Id: " + temp.guestID + " Guest Name: " + temp.Name +"\n" );
       }
    }
    public Guest findGuestByID(int guestID) {
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
    public void AddinHotelGuest(Guest guest){
        guestStayList.add(guest);
    }

    public void ShowinHotelGuest(){
        for(Guest temp: guestStayList){
            temp.PrintInfo();
        }
    }
    public void RemoveinHotelGuest(Guest guest){
        guestStayList.remove(guest);
    }
    
}
