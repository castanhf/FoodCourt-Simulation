
import java.text.DecimalFormat;

/*****************************************************************
 * Event163
 * 
 * A class for creating four types of events:
 *       ARRIVE at the simulation 
 *       DEPART from the simulation, 
 *       ENTER a queue within the simulation,
 *       LEAVE a queue within the simulation.
 * 
 * An event is like an entry on one's TODO list, possibly placed
 * there by someone. When the event time comes, the action is 
 * supposed to take place, i.e. be executed.
 *        ARRIVE GVSU,  12:30pm
 *        ENTER  MAC,    1:00pm, 1st floor, B1116
 *        LEAVE  MAC,    1:50pm
 *        ENTER  MAC,    2:30pm, 1st floor, B1118
 *        LEAVE  MAC,    3:45pm
 *        ENTER  MAC     4:00pm, 1st floor, B1118
 *        LEAVE  MAC     5:15pm
 *        DEPART GVSU    5:15pm
 *        
 * A simulation consists of a sequence of locations. For ease of reference,
 * locations are numbered with blockIndices, and possibly subdivided into
 * places of service, numbered with serverIndices.
 * 
 * For Project 4, an Event163 type object maintains the following
 *    1) event type
 *    2) event time
 *    3) blockIndex
 *    4) server index
 *    5) trackingNumber
 *    6) eventNumber
 * for a simulation event. It is the responsibility within a simulation
 * to determine what these terms mean.
 * 
 * For example, a package in route would receive a tracking number upon
 * arrival that would still be retained at the time of departure. However,
 * an arrival and a departure would be separate events with different
 * event numbers. Similarly, a customer upon arrival could be given a
 * service number to determine the order for receiving service.
 * 
 * @author   Filipe Castanheira
 * @version  04/21/2017
 ****************************************************/
public class Event163 implements Comparable
{
    /** event types */
    public String eventType;       /** E.G. ARRIVE, DEPART, ENTER, LEAVE */
    public double eventTime;       /** time the event is to occur */
    public int    blockIndex;      /** which block, in a sequence of blocks (eventLocations),`needs to be notified */
    public int    serverIndex;     /** an index into an array of servers/listeners, within a block of servers*/
    public String eventNumber;     /** serial number for all events */
    public String trackingNumber;  /** serial number for all arrivals */

    /** extra fields to facilitate the simulation */
    private static int arrivalNumber = 0;
    private static int eventCounter = 0;

    /** The following constructor might be useful for creating an event to ARRIVE or to ENTER */
    public Event163( String eventType, double eventTime )
    {
        this.eventType      = eventType;
        this.eventTime      = eventTime;
        this.blockIndex     = 0;
        this.serverIndex    = -1;  // The server is to be determined later in the simulation.

        eventCounter++;
        this.eventNumber    = eventNumberString();

        arrivalNumber++;
        this.trackingNumber = arrivalNumberString(); 
    }

    /** The following constructor might be useful for creating an event to DEPART or to ENTER */
    public Event163( String eventType, double eventTime, int block, int serverNumber, String trackingNumber )
    {
        this.eventType      = eventType;
        this.eventTime      = eventTime;
        this.blockIndex     = block;

        this.serverIndex    = serverNumber;

        this.trackingNumber = trackingNumber;

        eventCounter++;
        this.eventNumber    = eventNumberString();
    }

    /** ------------------------------------------------------------------------------------
     * @returns true if currentType field matches the eventType parameter, otherwise false.
     * ------------------------------------------------------------------------------------ */
    public boolean hasType( String eventType )
    {
        return  this.eventType.equals( eventType );
    }

    /******************************************************
     * This method is required for the Comparable interface
     * It returns a negative number if the first event is 
     * scheduled to occur before the second event.  It is 
     * not explicitely invoked within a program but instead
     * used whenever a comparison is necessary.
     * @param e is expected to be another Event
     *****************************************************/    
    public int compareTo(Object e)
    {
        if (e == null || !(e instanceof Event163))
        {
            return 1;
        }

        Event163 otherEvent = (Event163) e;
        int result = 0;
        if (this.eventTime > otherEvent.eventTime)
        {
            result = 1;
        }
        else if (this.eventTime < otherEvent.eventTime)
        {
            result = -1;
        }
        return result;
    }

    private String arrivalNumberString( )
    {
        String threeDigits = "" + arrivalNumber;

        if (threeDigits.length()<= 3)
        {
            return "#" + "000".substring( threeDigits.length()) + threeDigits;
        }
        else
        {
            return threeDigits;
        }
    }

    private String eventNumberString( )
    {
        String fourDigits = "" + eventCounter;

        if (fourDigits.length()<= 4)
        {
            fourDigits = "E" + "0000".substring( fourDigits.length()) + fourDigits;
            return fourDigits;
        }
        else
        {
            return fourDigits;
        }
    }

    private String serverIndexString()
    {
        if ( serverIndex == -1)
        {
            return "";
        }
        else
        {
            return "S" + serverIndex;
        }
    }

    public String toString()
    {
        String stringHelper = "(" +
            eventNumber + ") " +
            Clock.inSeconds( (int)eventTime ) + " " + 
            trackingNumber + " " +
            eventType + " " +
            "B" + blockIndex + " " +
            serverIndexString();

        return stringHelper;
    }

    public String toString( String time )
    {
        String timeString;
        if ( time.toUpperCase().equals( "SECONDS" ))
        {
            timeString = Clock.inSeconds( (int)eventTime );
        }
        else
        {
            timeString = Clock.inMinutes( (int)eventTime );
        }
        String stringHelper = "(" +
            this.eventNumber + ") " +
            timeString + " " + 
            this.trackingNumber + " " +
            this.eventType + " " +
            "B" + this.blockIndex + " " +
            this.serverIndexString();

        return stringHelper;
    }

    public static void reset()
    {
        arrivalNumber = 0;
    }      

    public static void main(String args[])
    {
        Event163 arrive = new Event163( "ARRIVE", 10420 );
        Event163 enter  = new Event163( "ENTER",  10525, 2, 10, arrive.trackingNumber );
        Event163 leave  = new Event163( "LEAVE",  10550, 2, 10, arrive.trackingNumber );
        Event163 depart = new Event163( "DEPART", 10730, 6, 24, arrive.trackingNumber );

        System.out.println( arrive.toString( ) );
        System.out.println( enter.toString( ) );
        System.out.println( leave.toString( ) );
        System.out.println( depart.toString( ) );
    }

}
