import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Write a description of class Server here.
 * 
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Eatery
{
    public Window   window;               // a window to report the simulation progress for this Eatery

    public Clock    clock;                // the clock is shared, where clock.time is current simulation time

    public int      blockIndex;           // 0, 1, 2, or 3
    public int      serverIndex;          // 0, 1, 2, or 3

    public String   name;                 // name of the server, or place of service

    public Person   person;               // any person being served in the eatery at some point in time

    // DONE
// 38) TODO comment out the following two lines
//    public ArrayList<Person>   PQ;        // a person queue for waiting in line
//    public ArrayList<Event163> EQ;        // an event queue of Event163 events

    // DONE
// 38) TODO uncomment the following two lines
     public Queue163<Person>   PQ;        // a person queue for waiting in line
     public Queue163<Event163> EQ;        // an event queue of Event163 events

    public Checkout nextBlock;            // checkout immediately follows the FoodCount of Eateries

    public double   serviceTimeEstimate;  // an estimate for the time to place and process an order

    /** statistics on service */
    public double   totalServiceTime;     // total time with customers
    public int      throughput;           // number of customers served

    /** statistics on waiting for service */
    public double   totalWaitTime;        // total time customers waited in line, Q
    public double   averageWaitTime;      // computed by a methods as totalWaitTime / personsNeedingtoWait
    public int      maxPQlength;

    private static int xCoordinate = 25;
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static RandomNormal random = new RandomNormal();

    public Eatery(  Clock clock, String name, double serviceTimeEstimate)
    {
        this.window = new Window(name, xCoordinate, 250, 600, 200, Color.BLUE, false);
        xCoordinate += 350;
        this.clock = clock;

    // DONE
// 39) TODO comment out the following two lines
//        this.PQ = new ArrayList<Person>();          
//        this.EQ = new ArrayList<Event163>();

    // DONE
// 39) TODO uncomment the following two lines
         this.PQ = new Queue163<Person>();          
         this.EQ = new Queue163<Event163>();

        this.name = name;

        this.person = null;

        this.maxPQlength = 0;
        this.throughput = 0;
        this.serviceTimeEstimate = serviceTimeEstimate;
        this.totalServiceTime  = 0.0;        
    }

    public void add (Person person)
    {
        this.PQ.add( person );

        if (this.PQ.size() > this.maxPQlength)
        {
            this.maxPQlength = this.PQ.size();
        }
    }    

    public void performAction( Event163 e ){

        this.report( e );

        this.blockIndex  = e.blockIndex;
        this.serverIndex = e.serverIndex;

        if (this.person == null && PQ.size() >= 1) {

            /** (e.eventType.equals( "ENTER" ))  */

            this.person = this.PQ.remove(0);       
            this.person.blockIndex  = e.blockIndex;              // Where is this person?
            this.person.timeWaiting += this.clock.time - e.eventTime;  // How long has this person waited in line?
            this.person.timeToOrder = random.number( serviceTimeEstimate ); // How long before this person has the food

            double nextEventTime = this.clock.time + this.person.timeToOrder;

            Event163 nextEvent = 
                new Event163( "LEAVE", 
                    nextEventTime, 
                    e.blockIndex, 
                    e.serverIndex,
                    this.person.trackingNumber );
            this.clock.add( nextEvent );
        }
        else   
        {
            if (this.person != null)  // Is this condition necessary?
            {
                /** (e.eventType.equals( "LEAVE" ))  */

                this.throughput++;
                this.totalServiceTime += this.person.timeToOrder;  // Add the time to order to the totalServiceTimes

                this.nextBlock.add(this.person);

                int serverNotDetermined = -1;
                Event163 nextEvent = 
                    new Event163( "ENTER", 
                        this.clock.time, 
                        e.blockIndex + 1, 
                        serverNotDetermined, 
                        this.person.trackingNumber );

                this.clock.add( nextEvent );

                this.person = null;

                
                /** Recursive call to serve the next person in line. */
                if ( this.EQ.size() >= 1)
                {
                    this.performAction( this.EQ.remove( 0 ) );  /** Notice the event queue, EQ */
                }
            }
        }
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
            this.window.println( 
                e.trackingNumber + " " +
                e.eventType + " " +
                clk + 
                this.PQtoString()  );
        }
    }

    // DONE
// 40) TODO comment out the following method
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
// 40) TODO uncomment the following method
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
    
    public void showStatistics()
    {
        this.window.println();
        this.window.println( "Eatery Statistics for " + this.name);
        this.window.println( "Simulation block: " + "B" + this.blockIndex + " S" + this.serverIndex);
        this.window.println( "Clock time:\t" + Clock.inSeconds(this.clock.time) );
        this.window.println( "Throughput:\t" + this.throughput );
        this.window.println( "Utilization:\t" + df.format(this.totalServiceTime / this.clock.time) );
        this.window.println( "Maximum PQ:\t" + this.maxPQlength );
        this.window.println();       
    } 

}