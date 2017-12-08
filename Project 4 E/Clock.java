
import java.awt.event.*;
import javax.swing.*;

import java.awt.*;
import java.util.PriorityQueue;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Clock {

    public Window window;
    
    //DONE
// 36) TODO comment out the following line
//    public PriorityQueue<Event163> futureEvents;

    //DONE
// 36) TODO uncomment the following line
    public PriorityQueue163<Event163> futureEvents;    

    private ClockListener[] myListeners;

    private int numListeners;
    private int MAX = 100;

    public double time;              // simulation clock time
    public int    eventCount;        // number of events processed with <listener>.actionPerform( <event> ) 

    private static RandomNormal random = new RandomNormal();

    public Clock() {
        this.window = new Window("Clock / all events", 675, 300, 400, 200, Color.BLUE, false);

        //DONE
// 37) TODO comment out the following line
//        this.futureEvents = new PriorityQueue<Event163>( );
// 37) TODO uncomment the following line   
        this.futureEvents = new PriorityQueue163<Event163>( );

        this.numListeners = 0;
        this.myListeners = new ClockListener[MAX];

        this.time = 0.0;
    }

    public void nextStep( )
    {
        if (this.futureEvents.size() > 0)
        {
            this.eventCount++;

            Event163 e = this.futureEvents.poll();
            this.time = e.eventTime;

            this.window.println( e.toString( ) );

            if (e.blockIndex < numListeners)
            {
                this.myListeners[ e.blockIndex ].performAction( e );
            }
        }
    }

    public void run() {

        while (this.futureEvents.size() > 0)
        {
            this.nextStep( );
        }
    }

    public void run(int endingTime) {

        while (this.futureEvents.size() > 0)
        {
            this.nextStep( );
        }
    }

    /**
     * The continuousSteps method repeatedly calls
     * nextStep followed by a peek into futureEvents
     *      for the next event
     *   1) The first step is a peek into futureEvents
     *      for the next event.
     *   2) As long as the event is not null and
     *      continuousTime >= event.eventTime the method
     *      continues loop execution of the following
     *          A) invokes the nextStep method 
     *          B) Use the peek method with futureEvents
     *             for the next event.     
     */

    public void continuousSteps(int continuousTime)
    {
        Event163 event = this.futureEvents.peek();

        //CHECK
// 22)  TODO complete code for the following loop according
//           to the documentation
         while ((this.futureEvents.size() > 0) && (continuousTime >= event.eventTime) ){
                nextStep();
                event = this.futureEvents.peek();
  
         }
//  23) TODO try out the GUI. It should work. Then go to 24 TODO in LinkedList163
    }

    public void add(ClockListener cl) {
        this.myListeners[this.numListeners] = cl;
        this.numListeners++;
    }

    public void add(Event163 e) {
        this.futureEvents.add( e );
    }

    public void scheduleArrival( double averageInterEventTime, double maxTime )
    {
        this.time = this.time + random.number(averageInterEventTime);

        if (this.time <= maxTime)
        {
            this.futureEvents.add( new Event163( "ARRIVAL", this.time ) );
        }
    }   

    public void scheduleAllArrivals( double averageInterArrivalTime, double maxTime )
    {

        while (this.time < maxTime)
        {
            this.scheduleArrival( averageInterArrivalTime, maxTime );
        }
        this.time = 0.0;
        window.println( futureEvents.size() );
        window.print(futureEvents.toString());
    }

    public void scheduleAllArrivals(  )
    {
        /** Arrival data could be read from file as Event163 events. */
    }

    public void saveArrivals(  )
    {
        /** Arrival data could be saved in a file as Event163 events. */
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "Clock Statistics" );
        this.window.println( "Clock time:\t" + Clock.inSeconds(this.time));
        this.window.println( "Event count:\t" + this.eventCount );
        this.window.println();  
    }

    public static String inSeconds( double time ) 
    {
        String AmPm = "";
        String clockTime = "";

        int seconds = (int)time;
        int secs = seconds % 60;
        int mns = (seconds/60)%60;
        int hrs = (seconds/3600); 

        if (hrs < 12)
        {
            AmPm = "am";
        }
        else
        {
            AmPm = "pm";
        }

        hrs = hrs % 12;
        if (hrs == 0)
        {
            hrs = 12; 
        }      
        if ( hrs < 10)
        {
            clockTime = " ";
        }
        clockTime += hrs + ":";

        if ( mns < 10)
        {
            clockTime += "0";
        }

        clockTime += mns + ":";
        if ( secs < 10)
        {
            clockTime += "0";
        }
        clockTime += secs + AmPm;

        return clockTime;
    }        

    public static String inMinutes( double time ) 
    {
        String AmPm = "";
        String clockTime = "";

        int minutes = (int)time;
        int mns = (minutes)%60;
        int hrs = (minutes/60); 

        if (hrs < 12)
        {
            AmPm = "am";
        }
        else
        {
            AmPm = "pm";
        }

        hrs = hrs % 12;
        if (hrs == 0)
        {
            hrs = 12; 
        }      
        if ( hrs < 10)
        {
            clockTime = " ";
        }
        clockTime += hrs + ":";

        if ( mns < 10)
        {
            clockTime += "0";
        }
        clockTime += mns + AmPm;

        return clockTime;
    }   

    public static void main( String[] args )
    {
        int interArrivalTimes = 20;  // average time between arrivals

        Clock clock = new Clock();

        clock.scheduleAllArrivals( interArrivalTimes, 200 );

        clock.window.println();
        clock.window.println( "A Clock object stores Event163 events in a priority queue, called futureEvents." );
        clock.window.println( "During a simulation, the Clock \"nextStep\" method, exclusively, removes Event163" );
        clock.window.println( "events in chronological order from this priority queue with the poll method, e.g." );
        clock.window.println( "                     futureEvents.poll( )" );
        clock.window.println( "Code from service-provider block within the project can add an Event163 to this" );
        clock.window.println( "priority queue as follows" );
        clock.window.println( "            clock.futureEvents.add(<an Event163 instance>)" );        
        clock.window.println( "This is possible, as every service-provider block is constructed with a reference" );
        clock.window.println( "to the common \"clock\" object." );
        clock.window.println( "--------------------------------------------------------------------------" );

        clock.futureEvents.add( new Event163( "DEPARTS", 100, 2, 2, "#002" ) );
        int ub = clock.futureEvents.size();
        for ( int n = 0; n < ub; n++ )
        {
            clock.window.println( clock.futureEvents.poll().toString() );
        }
    }    
}
