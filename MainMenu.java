public class MainMenu{
    public static void main(String args[]){
        // SingleRoom room1 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room2 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room3 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room4 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room5 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room6 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room7 = new SingleRoom( 15000, "Wifi,AC");
        // SingleRoom room8 = new SingleRoom( 15000, "Wifi,AC");
        // DoubleRoom room2 = new DoubleRoom(102,25000, "Wifi, AC");
        // Suite room3 = new Suite(103,35000, "Wifi, AC");

        //  room6.PrintInfo();
        //  room7.PrintInfo();
        //  room8.PrintInfo();

        Regular guest1 = new Regular("Shawn Mend","blahblah123@gmail.com","03331306001","street3,Hyderabad");
        // Frequent guest2 = new Frequent("Shawn eed","blahblah124@gmail.com","03331306002","street6,Hyderabad");
        // Corporate guest3 = new Corporate("hawn eed","blahssblah124@gmail.com","03321306002","street8,Hyderabad");
        
        // guest1.PrintInfo();
        // guest2.PrintInfo();
        // guest3.PrintInfo();

        // Frequent guestChange = new Frequent(guest1);
        // guestChange.PrintInfo();


        //RoomManagement rmMang= new RoomManagement();

        // rmMang.addSingle(15000, "Wifi,AC");
        // rmMang.addSingle(20000, "Wifi,AC");
        // rmMang.addSingle(35000, "Wifi,AC");
        // rmMang.addSingle(15000, "Wifi,AC");
        // rmMang.addDouble(60000,"Wifi,AC");
        // rmMang.addDouble(20000,"Wifi,AC");
        // rmMang.addDouble(25000,"Wifi,AC");
        // rmMang.addSuites(35000,"Wifi,AC");
        // rmMang.addSuites(20000,"Wifi,AC");
        // rmMang.addSuites(40000,"Wifi,AC");
        // rmMang.addSuites(30000,"Wifi,AC");

        //rmMang.setAvail_Status(103,false,"Maintenace");
        //rmMang.Price_Range_Room(40000);

        //rmMang.SuiteAvial();

        GuestManagement gmMang=new GuestManagement();

        gmMang.AddinHotelGuest(guest1);
        gmMang.ShowinHotelGuest();

    }
}