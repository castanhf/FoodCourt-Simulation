import java.awt.*;
import java.util.Random;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class PersonProducer implements ClockListener {

    public  Window window;
    public  Clock clock;          // the clock is shared, where clock.time is current simulation time

    public  FoodCourt nextBlock;  // the FoodCount immediately follows the PersonProducer simulation block

    public  int       blockIndex;           // 0, 1, 2, or 3
    
    public  int arrivals;      // a count of the persons produced

    private int thisBlockIndex = 0;
    private int nextBlockIndex = 1;  /** the next block, following this block, is the FoodCourt */

    public PersonProducer(Clock clock) {
        window = new Window("PersonProducer", 25, 25, 300, 200, Color.MAGENTA, false);

        this.clock = clock;
    }

    public void performAction(Event163 e) {

        this.blockIndex = e.blockIndex;
        
        /** Time for person to be born into the simulation. */
        Person person = new Person();
        person.trackingNumber = e.trackingNumber;

        double p = Math.random();   // 10% ASSIST, 20% BUSINESS, 70% COACH by default      
        if ( p < 0.1)
        {
            person.attention = Person.ASSIST;
        }
        else if( p < 0.3)
        {
            person.attention = Person.BUSINESS;            
        }
        else
        {
            person.attention = Person.COACH;            
        }        

        person.arrivalTime = e.eventTime;                   
        person.blockIndex  = thisBlockIndex;

        this.report( e, person );

        /** Person now exists and is now on her way to the FoodCourt. */   

        this.nextBlock.add(person, e);

        /**
         * The person now exists and is ready to move through the system.
         * 
         * Create an event to ENTER the FoodCourt, and add it to the 
         * EventChain.futureEvents so that the simulation clock will take it off
         * and invoke the FoodCourt actionPerform( this ENTER event ) method.
         * */
        Event163 nextEvent = 
            new Event163( "ENTER", 
                this.clock.time, 
                this.blockIndex + 1,
                e.serverIndex,
                person.trackingNumber );
        this.clock.add( nextEvent );

        this.arrivals++;

        //person = null;  //DO NOT INCLUDE THIS STATEMENT! IF YOU DO, person WILL VANISH ! 
    }

    private void report( Event163 e, Person p )
    {
        if (this.window != null)
        {
            this.window.println( e.trackingNumber + p.attention + " ARRIVAL " + Clock.inSeconds( e.eventTime) );
        }        
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "PersonProducer Statistics" );
        this.window.println( "Simulation block: " + "B" + this.blockIndex );
        this.window.println( "Clock time:\t" + Clock.inSeconds(this.clock.time));
        this.window.println( "Arrivals:\t" + this.arrivals );
        this.window.println();  
    }
}