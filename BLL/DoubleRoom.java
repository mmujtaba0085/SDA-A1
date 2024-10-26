package BLL;
public class DoubleRoom extends Room{
    public DoubleRoom(double basePrice,String Amenities){
        super("Double", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice * 0.05; //service Charges -> 5% of basePrice
        return (basePrice + serviceCharges + additionalCharges) * nights  ;
    }
    
}