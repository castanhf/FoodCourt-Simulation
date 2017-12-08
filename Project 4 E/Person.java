/**
 * 
 */

/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
import java.text.DecimalFormat;

public class Person {
    public String trackingNumber;
    public String attention = COACH;    // ASSIST, COACH, BUSINESS
    public int    blockIndex;           // current location in the simulation
    public double arrivalTime;

    public double timeToOrder;   // amount of time to process the order
    public double timeAtCashier; // amount of time to check out
    public double timeWaiting;   // time waiting in a queue

    public static DecimalFormat df = new DecimalFormat("##0.00");
    public static final String BUSINESS = "B";
    public static final String ASSIST   = "A";
    public static final String COACH    = "C";

    public Person()
    {
        this.timeWaiting = 0.0;
    }
    
    public String toString()
    {
        String arrivalStr = trackingNumber + attention + " arrived " + Clock.inSeconds(arrivalTime) ;

        String eateryStr = " " + df.format(this.timeToOrder);
        eateryStr = " Eatery: " + eateryStr.substring( eateryStr.length()- 5 )+ " secs. ";

        String cashierStr = " " + df.format( this.timeAtCashier );
        cashierStr = "Cashier: " + cashierStr.substring( cashierStr.length()- 5 ) + " sec. ";

        String waitStr = " " + df.format(timeWaiting);
        waitStr = "Waited " + waitStr.substring( waitStr.length()- 5 ) + " secs";

        return arrivalStr + eateryStr + cashierStr + waitStr;
    }

}
