import java.util.ArrayList;
import java.util.List;

abstract class Guest {
    protected int guestID;
    protected String Name;
    protected String Email;
    protected List<String> BookingHistory;
    protected double TotalFee;
    protected String phoneNumber;
    protected String Address;
    protected String GuestType;
    protected int TotalEntries;  //How many times he has visited the hotel
    private static int totalGuest=0;

    Guest(String Name,String Email,String phoneNumber,String Address,String GuestType){
        this.guestID=++totalGuest;
        this.Name=Name;
        this.Email=Email;
        this.BookingHistory= new ArrayList<>();
        this.TotalFee=0;
        this.phoneNumber=phoneNumber;
        this.Address=Address;
        this.GuestType=GuestType;
        System.out.println("Guest Id: " + this.guestID + " Guest Name: " + this.Name);
    }

    Guest(String Name,String Email,String phoneNumber,String Address,String GuestType,int GuestID){  //For Transfering Customer from 
        this.guestID=GuestID;                                                                        //one child class to another (i.e Regular->Frequent)
        this.Name=Name;
        this.Email=Email;
        this.BookingHistory=null;
        this.TotalFee=0;
        this.phoneNumber=phoneNumber;
        this.Address=Address;
        this.GuestType=GuestType;
    }
    public void PrintInfo(){
        System.out.println("------------------------------------------------------------------");
        System.out.println("Guest ID: " + guestID);
        System.out.println("Name: " + Name);
        System.out.println("Email: " + Email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + Address);
        System.out.println("Guest Type: " + GuestType);
        System.out.println("Total Fee: " + TotalFee);
        System.out.println("Total Entries: " + TotalEntries);
        System.out.println("Booking History: " + BookingHistory);
        System.out.println("------------------------------------------------------------------");
    }
}


class Regular extends Guest{

    Regular(String Name,String Email,String phoneNumber,String Address){
        super(Name, Email, phoneNumber, Address,"Regular");
    }

    Frequent FrequentGuestCheck(){
        if(TotalEntries>=3) //if total visitation is more or equal to 3 he is a frequent Guest
        {
            return new Frequent(this);
        }
        return null;
    }
}


class Frequent extends Guest{

    Frequent(String Name,String Email,String phoneNumber,String Address){
        super(Name, Email, phoneNumber, Address,"Frequent");
    }

    Frequent(Regular guest){    
        super(guest.Name, guest.Email, guest.phoneNumber, guest.Address,"Frequent",guest.guestID);
        this.BookingHistory=guest.BookingHistory;
        this.TotalEntries=guest.TotalEntries;
        this.TotalFee=guest.TotalFee;
    }
}

class Corporate extends Guest{

    Corporate(String Name,String Email,String phoneNumber,String Address){
        super(Name, Email, phoneNumber, Address,"Corporate");
    }

}