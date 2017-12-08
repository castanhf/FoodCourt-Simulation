import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
/**
 * @author   Filipe Castanheira
 * @version  04/21/2017
 */
public class Terminate implements ClockListener {

    public Window window;

    public  Clock clock;            // the clock is shared, where clock.time is current simulation time

    private int blockIndex;         // this block index

    private Person person;          // the person yp DEPART the simulation

        // DONE
    // 45) TODO Comment out the following two lines
    //    private ArrayList<Person>  PQ;  // a person queue for waiting in line
    //    public ArrayList<Event163> EQ;  // an event queue of Event163 events
    
        // DONE
    // 45) TODO Uncomment out the following two lines
    private Queue163<Person>  PQ;  // a person queue for waiting in line
    public Queue163<Event163> EQ;  // an event queue of Event163 events

    public  int departures;         // a count of persons that DEPART from the simulation

    private int maxPQlength;

    public Terminate(){

    }

    public Terminate(Clock clock) {  

        this.window = new Window("Terminate", 350, 25, 1125, 200, Color.BLUE, false);

        this.clock = clock;

            // DONE
        // 46) TODO comment out the following two lines
        //        this.PQ = new ArrayList<Person>();
        //        this.EQ = new ArrayList<Event163>();
        
            //DONE
        // 46) TODO Uncomment out the following two lines
        this.PQ = new Queue163<Person>();
        this.EQ = new Queue163<Event163>();

        this.maxPQlength = 0;
        this.person = null;
    }

    public void add (Person person){
        this.PQ.add(person);
        if (this.PQ.size() > this.maxPQlength)
        {
            this.maxPQlength = this.PQ.size();
        }
    }

    public void performAction(Event163 e){

        this.blockIndex = e.blockIndex;

        if (e.eventType.equals( "DEPART" ))
        {
            if (this.person != null)
            {               
                this.EQ.add( e );    //add the event to the selected server EQ
            }            
            else if ( this.PQ.size() >= 1) {
                this.person = this.PQ.remove(0);
                this.person.blockIndex = this.blockIndex;

                this.report( "DEPARTS ", Color.BLUE, this.person );
                this.person = null;

                this.departures++;

                if ( this.PQ.size() >= 1)
                {
                    this.performAction( this.EQ.remove( 0 ) );
                }
            }
        }
    }  

        // DONE
    // 47) TODO comment out the following method
    /*
    public String PQtoString()
    {
    String str = " PQ "+ this.PQ.size() + ": " ;
    for (Person p: this.PQ)
    {
    str += p.trackingNumber + " ";
    }
    return str;
    }
     */

    
    // DONE
    // 47) TODO Uncomment out the following method
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

    private void report( String eventType, Color color, Person person )
    {
        String trn = person.trackingNumber + person.attention + " ";
        String evt = eventType + " ";
        String clk = Clock.inSeconds(this.clock.time) + " ";
        if (this.window != null)
        {
            this.window.setColor( color );
            this.window.println( trn + evt + clk + person.toString());
        }
    }

    public void showStatistics()
    {
        this.window.println();
        this.window.println( "Terminate Statistics" );
        this.window.println( "Simulation block: " + "B" + this.blockIndex );
        this.window.println( "Clock time:\t" + Clock.inSeconds(this.clock.time) );
        this.window.println( "Departures:\t" + this.departures );
        this.window.println();  
    }
}
