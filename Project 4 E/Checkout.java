import java.awt.*;
import java.util.*;
/**
 * Checkout is a collection/array of Cashier instances. The Checkout class manages one single Checkout PQ
 * (person wait) queue, as well as a Checkout EQ (event queue), that each person must pass through before
 * being serviced by a cashiers. The possibilities are 0,1,2,or 3 cashiers. With zero cashiers at work,
 * no person will leave the Checkout PQ. 
 * 
 * For each person passing thorugh this Checkout PQ, the Checkout class selects a cashier at random.
 * Whenever a cashier completes an ENTER-LEAVE event sequence for any given person, Checkout must go
 * through this same deliberating process of choosing a cashier until the Checkout PQ is empty. The
 * Checkout process must be fair, so that each cashier has the same opportunity of being selected to
 * checkout a person, and that no cashier could be persistently left out.
 * 
 * In comparison with the Eatery class, each eatery has its own PQ (person wait) queue, along with
 * an EQ (event queue) for pending events that can not be serviced at the scheduled time for the
 * event.
 * 
 * Along with Checkout and associated cashiers, all simulation blocks share access to the
 * simulation clock which allows for a direct reference to the current "clock.time".
 * 
 * As each person is removed from the PQ, the checkout block chooses an available cashier. A
 * person chooses an eatery. In contrast, a person does not choose a cashier. Instead, checkout
 * does the choosing of a cashier for the person.
 * 
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Checkout implements ClockListener {
    
    public  Window    window;
    public  Clock     clock;          // the clock is shared, where clock.time is current simulation time

    private int       blockIndex;

    // DONE
// 41) TODO comment out the following two lines
//    public  LinkedList<Person>  PQ;   // a queue of people waiting in line
//    private ArrayList<Event163> EQ;   // an event queue of Event163 events

    // DONE
// 41) TODO comment out the following two lines
     public  Queue163<Person>  PQ;   // a queue of people waiting in line
     private Queue163<Event163> EQ;   // an event queue of Event163 events

    public Cashier[] cashier;         //all persons checking out with a cashier, 0,1,2, etc.
    public int       numCashiers;

    private int       maxPQlength;
    private int       throughput;

    private int       turn;           // index of the cashier having taken his/her turn
    public  Terminate nextBlock;      // the terminate block immediately follows this Checkout simulation block

    public static final int REGISTERS = 4;

    private static int[] xCoordinate = {25, 350, 500, 650};
    private static String[] name = {"Adam", "Beth", "Carl", "Dawn"};

    /**
     * One option (recommended), for Checkout, would be to create all 4 cashier
     * instances from the start, and then bring a cashier into service, or take
     * one out of service, all under the control of the GUI, of course.
     */
    public Checkout(Clock clock) {

        this.window = new Window("Checkout", 1100, 500, 400, 100, Color.BLUE, false);

        this.clock = clock;

    // DONE
// 42) TODO comment out the following two lines
//        this.PQ = new LinkedList<Person>(); 
//        this.EQ = new ArrayList<Event163>();

    // DONE
// 42) TODO comment out the following two lines
         this.PQ = new Queue163<Person>(); 
         this.EQ = new Queue163<Event163>();
        
        this.cashier = new Cashier[REGISTERS];
        this.numCashiers = 0;
        this.turn = 0;
        
        
        for (int n = 0; n < REGISTERS; n++)
        {
            this.cashier[n] = new Cashier( 
                this.clock, 
                name[n], 
                0,  //estimateCheckoutTime,
                null, 
                xCoordinate[n] );

                this.cashier[n].window.setVisible(false);        
        }
    }

    /**
     * To add a cashier, use the numCashiers method to index into the cashier array.
     * 
     * Set the serviceTimeEstimate field for the cashier brought into service.
     * 
     * If "EQ.size() > 0", call performAction to put this cashier to work, immediately.
     */
    public void addCashier(double serviceTimeEstimate)
    {

        if (this.numCashiers < REGISTERS)
        {
            this.cashier[numCashiers].serviceTimeEstimate = serviceTimeEstimate;
            this.cashier[numCashiers].window.setVisible(true);
            this.cashier[numCashiers].nextBlock = this.nextBlock;
            this.numCashiers++;

            /** A call to performAction to checkout the next person in line */           
            if ( this.EQ.size() >= 1)
            {
                this.performAction( this.EQ.remove( 0 ) ); 
            }
        }
    }

    public void add (Person person){

        this.PQ.add(person);

        if (this.PQ.size() > this.maxPQlength)
        {
            this.maxPQlength = this.PQ.size();
        }
    }

    /** 
     * Choose a cashier so that they all take turns. Starting with turn,
     * cycle through the list of cashiers with the % operater to choose
     * the next cashier.  It is possible that all of the cashiers may be
     * busy, but still a valid cashier index must be returned. Always,
     * the next value of turn should be one "greater" than the index
     * that is actually returned for the current turn.
     */
    private int chooseCashier()
    {
        if (this.numCashiers == 0)
        {
            return -1;
        }

        this.turn = (this.turn + 1)% numCashiers;
        int n = 0;
        while (this.cashier[this.turn].person != null && n < this.numCashiers)
        {
            n++;
            this.turn = (this.turn + 1)% this.numCashiers;
        }
        return this.turn;
    }

    /**
     * This Checkout performAction method selects the cashier.
     * The method is not at all trivial, as it handles various
     * conditions. Think about this carefully as performAction 
     * is to model the real life situation.
     * 
     * Part of the logic, here, is to determine whether or not
     * the performAction method in Checkout is ready to invoke
     * the performAction( e ) method in Cashier for a cashier
     * 
     *               this.cashier[ ? ].performAction( e );
     *               
     * and to determine which cashier (?) to use.
     * 
     * For an ENTER event, this method must choose a cashier, n, 
     * and execute
     * 
     *            this.cashier[ n ].person = PQ.remove( 0 );
     *                     
     * before calling this.cashier[ n ].performAction( e ) 
     * 
     * For a LEAVE event, this method must execute
     * 
     *     this.cashier[ e.serverIndex ].performAction( e );
     *             
     * where e.serverIndex remembers the cashier index from
     * the time to ENTER until the time to LEAVE.
     * 
     * After a LEAVE event, there is an issue of what to do if
     * there are still pending events in the event queue, EQ.
     */
    
    public void performAction(Event163 e){
 
        this.report( e );
        
        this.blockIndex = e.blockIndex;

        if (e.eventType.equals( "ENTER" ))
        {          
            if ( this.numCashiers == 0 )
            {
                this.EQ.add( e );
                return;
            } 

            int n = this.chooseCashier();
            e.serverIndex = n;
            if ( this.cashier[n].person != null)
            {
                this.EQ.add( e );
            } 
            else
            {
                if ( this.numCashiers == 0 )
                {
                    return;
                } 
                if ( this.PQ.size() >= 1) {
                    e.serverIndex = n;

                    /** Starts the process of checking out the next person in line */

                    this.cashier[ n ].person = this.PQ.remove( 0 );
                    this.cashier[ n ].performAction( e );
                }
            }
        }
        else if (e.eventType.equals( "LEAVE" ))
        {
            this.cashier[ e.serverIndex ].performAction( e );

            /** 
             * If the event queue, EQ, is not empty, make a recursive call
             * to this.performAction, with the next event from the EQ as
             * the parameter, so that the cashier deliberation process will
             * start with the next person waiting for a cashier.
             */ 
            if ( this.EQ.size() >= 1)
            {
                this.performAction( this.EQ.remove( 0 ) ); // Recursion! Checkout the next person in line               
            }
        }
    }  

    // DONE
// 43) TODO comment out the following method
//    public String PQtoString()
//    {
//        String str = " PQ "+ this.PQ.size() + ": " ;
//        for (Person p: this.PQ)
//        {
//            str += p.trackingNumber + " ";
//        }
//        return str;
//    }

    // DONE
// 43) TODO comment out the following method,
//     and add a comment to explain its purpose
     public String PQtoString()
     {
         String str = " PQ "+ this.PQ.size() + ": " ;
         PQ.begin();
         while ( PQ.hasNext())
         {
             str += PQ.next().trackingNumber + " ";
         }
        return str;
     }
//  44) The project should now be using your Queue163 and
//      PriorityQueue classes. It should be ready to go.
//      Compile, and try out the GUI.
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
            this.window.println(  e.trackingNumber + " " + e.eventType + " " + clk + this.PQtoString() );
        }
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "Checkout Statistics ");
        this.window.println( "Simulation block:\t" + "B"  + this.blockIndex );
        this.window.println( "Clock time:\t\t" + Clock.inSeconds(this.clock.time) );
        this.window.println( "Number of cashiers:\t" + this.numCashiers );
        this.window.println( "maximum PQ length:\t" + this.maxPQlength  );
        for (int n = 0; n < this.numCashiers; n++)
        {
            this.cashier[n].showStatistics();
        }
        this.window.println();  
    }
}
