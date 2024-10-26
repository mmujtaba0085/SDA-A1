package BLL;
public class SingleRoom extends Room{
    public SingleRoom(double basePrice,String Amenities){
        super("Single", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice * 0.05; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges) * nights ;   
    }
    
}