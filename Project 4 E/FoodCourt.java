
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * A FoodCourt is a collection of food vendors, referred to as eateries in this simulation.
 * Each eatery has its own PQ (person wait) queue, as well as an EQ (event queue) for
 * pending events that can not be serviced at the scheduled time for the event.
 * 
 * Note that all simulation blocks, which includes the food court, share access to the
 * simulation clock for quick reference to the current clock.time.
 * 
 * True to life in a FoodCourt, each person chooses an eatery by personal preference,
 * which happens here in the FoodCourt, not somewhere else in the simulation.
 * 
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class FoodCourt implements ClockListener {

    public Window window;
    public Clock    clock;      // the clock is shared, where clock.time is current simulation time

    public int      blockIndex;

    public Eatery[] eatery;     // Each eatery is indexed.
    public int eateries;        // Limited to 0, 1, 2, 3, or 4 eateries

    public Checkout nextBlock;  // The Checkout block follows the FoodCourt and its eateries
//    public ClockListener nextBlock;  // The Checkout block follows the FoodCourt and its eateries
    public static final int MAX_EATERIES = 4;
    
    private static Random uniformRandom = new Random(); // Each generated number is equally likely to occur

    public FoodCourt(Clock clock)
    {
        this.window = new Window("FoodCourt", 1100, 300, 400, 150, Color.BLUE, false);

        this.clock = clock;

        this.eatery = new Eatery[ MAX_EATERIES ];
        this.eateries = 0;
    }

    /**
     * This add method indexes the eatery, specified by
     * the parameter, into the "this.eatery" array.
     * The checkout block, denoted by "this.nextBlock",
     * is assiged to be the "nextBlock" for the eatery.
     */
    public void add(Eatery eatery)
    {
        this.eatery[this.eateries] = eatery;
        this.eatery[this.eateries].nextBlock = this.nextBlock;
        this.eateries++;
    }

    /**
     * This add method chooses an eatery at random from the
     * number of available eateries.
     * 
     * The eatery number needs to be remembered in the event
     * object, e.serverIndex, for later reference, when the 
     * performAction method is invoked by the clock object.
     * 
     */
    public void add (Person person, Event163 e)
    {        
        if (this.eateries > 0)
        {
            e.serverIndex = uniformRandom.nextInt( this.eateries );
            this.eatery[ e.serverIndex ].add( person );
        }
    }

    /**
     * Carefully review this method for accuracy and for understanding of how
     * a food court, as part of the simulation, should handle ENTER and LEAVE
     * events, of Event163 type, and appropriately channel the person through
     * their selected eatery. Some circumstances require special consideration.
     * 
     *       What if all the eateries are closed, i.e.
     *       where "this.eateries == 0"?  
     *       
     *       What if the selected eatery server is busy,
     *       i.e. where "eatery[ e.serverIndex ].person != null"?
     */
    public void performAction( Event163 e ){
        
        this.report( e );
        
        this.blockIndex = e.blockIndex;

        if (e.eventType.equals( "ENTER" ))
        {
            if (this.eateries > 0)
            {
                if (this.eatery[ e.serverIndex ].person != null)  /** eatery is busy */
                {               
                    this.eatery[ e.serverIndex ].EQ.add( e );    /** adds the event to the selected eatery event queue, EQ */
                    return;
                }
            }
            else
            {
                return;  // The food court is closed for business.
            }
        }
        
        this.eatery[ e.serverIndex ].performAction( e );
    }

    /**
     * This method returns the sum total of all customers
     * currently in anyone of the available eateries.
     */
    public int personsInFoodCourt()
    {
        int left = 0;
        for (int n = 0; n < this.eateries; n++)
        {
            left += this.eatery[ n ].PQ.size();
        }
        return left; 
    }

    public int getThroughput() {
        int throughput = 0;
        for (int n = 0; n < this.eateries; n++)
        {
            throughput += this.eatery[ n ].throughput;
        }
        return throughput;
    }

    public String toString()
    {
        return "FoodCourt " + Clock.inSeconds(this.clock.time);
    }

    private void report( Event163 e )
    {
        String clk = Clock.inSeconds(clock.time);
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
            this.window.println(  e.trackingNumber + " " + e.eventType + " " + clk );
        }
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "FoodCourt Statistics " );
        this.window.println( "Simulation block: " + "B" + this.blockIndex );
        this.window.println( "Clock time:\t\t" + Clock.inSeconds(this.clock.time) );
        this.window.println( "Eateries:\t\t" + this.eateries );
        this.window.println( "Persons in FoodCourt:\t" + this.personsInFoodCourt() );
        for (int n= 0; n < this.eateries; n++)
        {
            this.eatery[ n ].showStatistics();
        }
        this.window.println();  
    }
}
