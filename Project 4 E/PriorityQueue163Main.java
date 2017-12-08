/** Output produced by the main method, given below:

A priority queue of Events163 type events, ordered by time
(E0002) 12:00:50am ARRIVAL B0 
(E0001) 12:02:05am ARRIVAL B0 
(E0004) 12:02:05am ARRIVAL B0 
(E0003) 12:03:20am ARRIVAL B0 

first:  (E0002) 12:00:50am ARRIVAL B0 
second: (E0001) 12:02:05am ARRIVAL B0 
first.compareTo(second): -1
second.compareTo(first): 1

Polling Event163 objects from the priority queue
(E0002) 12:00:50am ARRIVAL B0 
(E0001) 12:02:05am ARRIVAL B0 
(E0004) 12:02:05am ARRIVAL B0 

The resulting priority queue of Events163 type events
(E0003) 12:03:20am ARRIVAL B0 

 */

public class PriorityQueue163Main
{
    public static void main(String args[]) {
        PriorityQueue163< Event163 > list = new PriorityQueue163< Event163 > ();
        list.add( new Event163( "ARRIVAL", 125 ) );
        list.add( new Event163( "ARRIVAL", 50 ) );
        list.add( new Event163( "ARRIVAL", 200 ) );
        list.add( new Event163( "ARRIVAL", 125 ) );

        System.out.println( "A priority queue of Events163 type events, ordered by time" );
        list.print( );
        System.out.println();

        Event163 first  = list.poll();
        Event163 second = list.poll();
        System.out.println( "first:  " + first.toString() );
        System.out.println( "second: " + second.toString() );
        System.out.println( "first.compareTo(second): " + first.compareTo(second) );
        System.out.println( "second.compareTo(first): " + second.compareTo(first) );

        System.out.println( "\nPolling Event163 objects from the priority queue" );
        System.out.println( first.toString() );
        System.out.println( second.toString() );
        System.out.println( list.poll().toString() );
//        System.out.println( list.poll().toString() );
//        System.out.println( list.poll().toString() );

        System.out.println( "\nThe resulting priority queue of Events163 type events" );
        list.print( );
    }
}
