package BLL;
public class Suite extends Room{
    public Suite(double basePrice,String Amenities){
        super("Suite", basePrice, Amenities);
    }

    public double BookingCharges(int nights, double additionalCharges){
        double serviceCharges=basePrice * 0.05; //service Charges -> 5% of basePrice
        double luxuryTax=basePrice * 0.15; //luxury Tax -> 15% of basePrice
        return (basePrice + serviceCharges + luxuryTax+ additionalCharges) * nights  ;
    }
    
}