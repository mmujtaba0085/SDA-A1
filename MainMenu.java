public class MainMenu{
    public static void main(String args[]){
        SingleRoom room1 = new SingleRoom(101, 15000, "Wifi,AC");
        room1.PrintInfo();
        DoubleRoom room2 = new DoubleRoom(102,25000, "Wifi, AC");
        room2.PrintInfo();
        Suite room3 = new Suite(103,35000, "Wifi, AC");
        room3.PrintInfo();
    }
}