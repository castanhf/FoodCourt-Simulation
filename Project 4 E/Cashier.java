import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Cashier implements ClockListener {

    public Window    window;

    public Clock     clock;                // the clock is shared, where clock.time is current simulation time

    public int       blockIndex;           // 0, 1, 2, or 3
    public int       serverIndex;          // 0, 1, 2, or 3

    public String    name;                 // the name of the cashier doing the checking out, the serving

    public Person    person;               // the person being served, i.e. checking out with this cashier
    public Terminate nextBlock;            // the terminate block immediately follows the cashiers simulation block

    public  double   serviceTimeEstimate;  // expected amount of time required to checkout
    private double   totalServiceTime;
    private int      throughput = 0;

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static RandomNormal random = new RandomNormal(); //random.setSeed(13); would generate the same repeatable random sequence

    public Cashier( Clock clock, String name, double serviceTimeEstimate, Terminate nextBlock, int x )    
    {
        this.window = new Window("Cashier " + name, x, 675, 300, 200, Color.BLUE, false);

        this.clock = clock;
        this.name = name;
        this.serviceTimeEstimate = serviceTimeEstimate;
    }

    /**
     * The Cashier performAction methods checks out a person.
     * The performAction method implements the simulated action 
     * for a person to first ENTER and (when called a second time)
     * later LEAVE with the same cashier.
     * 
     * The Cashier field "this.person" is a reference to the real
     * person that the casher advances through the ENTER-LEAVE
     * event sequence. The last statement to execute in the
     * LEAVE sequence is
     *                this.person = null;
     * which means that this casher has checked out this.person.
     */
    public void performAction(Event163 e){

        this.report( e );

        if (e.eventType.equals( "ENTER" ))
        {
            /** Start the process of checking out the next person in line */

            this.blockIndex  = e.blockIndex;
            this.serverIndex = e.serverIndex;

            /** Updating person fields */

            this.person.timeWaiting  += this.clock.time - e.eventTime;
            this.person.timeAtCashier = random.number( this.serviceTimeEstimate );
            this.person.blockIndex    = e.blockIndex;

            double timeOfNextEvent = this.clock.time + this.person.timeAtCashier;

            Event163 nextEvent = new Event163(  "LEAVE",
                    timeOfNextEvent, 
                    e.blockIndex, 
                    e.serverIndex, 
                    this.person.trackingNumber );

            this.clock.add( nextEvent );

        }
        else if (e.eventType.equals( "LEAVE" ))
        {
            /** Performing the event for the person to leave the cashier. */ 

            this.totalServiceTime += this.person.timeAtCashier;
            this.throughput++;

            this.nextBlock.add( this.person );   

            int serverNotDetermined = -1;        // the future server is not determined here
            Event163 nextEvent = new Event163(
                    "DEPART",
                    this.clock.time,             // the time for this event is now, in simulation time.
                    e.blockIndex + 1,
                    serverNotDetermined,
                    this.person.trackingNumber );

            this.clock.add( nextEvent );

            /** the real person still exists, but this.person no longer contains a reference to it*/
            this.person = null;
        }
    }  


    private void report( Event163 e )
    {
        if (this.window != null)
        {
            if ( e.eventType.equals( "ENTER"))
            {
                this.window.setColor( Color.BLUE );
            }
            else
            {
                this.window.setColor( Color.RED );
            }
            this.window.println( 
                e.trackingNumber + " " +
                e.eventType      + " " +
                this.name        + " " +
                Clock.inSeconds(this.clock.time)          
            );
        }
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "Cashier Statistics for " + this.name );
        this.window.println( "Simulation block: " + "B" + this.blockIndex + " S" + this.serverIndex );
        this.window.println( "Clock time:\t" + Clock.inSeconds(this.clock.time) );
        this.window.println( "Throughput:\t" + this.throughput );
        this.window.println( "Utilization:\t" + df.format(this.totalServiceTime / this.clock.time) );
        this.window.println();   
    }

}
