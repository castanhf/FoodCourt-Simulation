
/**
 * 32) TODO:   add very brief documentation to describe this class
 * This class inherits the variables and methods from its superclass, which is
 * Queue163<AnyType>. In order for the class PriorityQueue163<AnyType> to call a method
 * that belongs to its parent class, the word "super" or "this" has to preceed the method call.
 * Unless the default constructor is called, then the method call should be
 * "super()". There are three methods in this class, which are PriorityQueue163(),
 * add(), and poll(). The first overrides the same method from the class Queue163<AnyType>,
 * which in turn also overrides the same method from the class LinkedList163<AnyType>.
 * The second and the third override the methods from the class LinkedList163<AnyType>,
 * respectfully, add(AnyType object) when the variable theSize (also from the said superclass)
 * equals zero. and remove( int index). This class shows three examples of overriding.
 * 
 * @author Filipe Castanheira 
 * @version 04/21/2017
 */
public class PriorityQueue163<AnyType> extends Queue163<AnyType>
{

    public PriorityQueue163()
    {
        super();
    }

    //     public void add( AnyType object )
    //     {
    // 
    //         if (this.size() == 0)
    //         {
    //             super.add( object );
    //         }
    //         else
    //         {          
    //             Node<AnyType> node = this.beginMarker.next;
    //             Event163 event;       
    //             int comp = -1;
    //             while ( comp < 0 && node != this.endMarker)
    //             {
    //                 event = (Event163)node.data;
    //                 comp = event.compareTo( (Event163)object );
    // 
    //                 if (comp < 0)
    //                 {
    //                     node = node.next;
    //                 }
    //             }
    // 
    //             /** 
    //              * Consider the logic in the above algorithm 
    //              * to insert a newNode BEFORE node, the node
    //              * justvdetermined above.
    //              */
    //             Node<AnyType> newNode = new Node<AnyType>( object, node.prev, node );
    // 
    //             newNode.prev.next     = newNode;
    //             node.prev             = newNode;         
    //             this.theSize++;
    //         }
    //     }

    /**
     * The add method adds an Event163 object to the
     * priority queue so that the events in the list
     * are always in chronological order, where the
     * pending event with the earliest time is at the
     * front of the list, at
     *              this.beginMarker.next
     * 
     * TODO:  Please write this method so that we can
     * use it there in place of the java API ArrayList
     * and java API PriorityQueue classes.
     *
     * Please check out the textbook to see how to use 
     * "compareTo" with AnyType. The Event163 class defines 
     * "compareTo" that depends on the event time field. 
     * You need to use the Event163 "compareTo" method in
     * the following "add" method to keep the priority
     * queue in chronological order.
     * 
     * Search for the correct place, i.e. node,
     * in the priority queue for adding the new
     * Event163 type object, starting at the end,
     * and then search backwards with the iterative
     * step
     *                 node = node.prev;  
     *                 
     * Remember that the beginMarker and endMarker
     * nodes do not contain data.
     * 
     * Iteration postively needs to stop whenever
     *                node == beginMarker  
     * if not before, for whatever is the correct place.
     * Remark: do not use the size method to control
     *         the number of iterations
     *           1) you do not need it
     *           2) an error in the size method
     *              may result in a null reference
     *              
     * @param object.
     */

    public void add( AnyType object )
    {

        if (this.size() == 0)
        {
            super.add( object );
        }
        else
        {          

            /**
             * Searching backwards from endMarker.prev in the
             * priority queue to the correct place at which
             * to link in the object.
             * 
             * You may compare with the above "forward"
             * implementation for add.
             */
            // TODO 33 A) fill in the blanks for the search
            Node<AnyType> node = this.endMarker.prev;
            Event163 event;       
            int comp = 1;
                while (comp > 0 && node != this.beginMarker )
                {
                    event = (Event163)node.data;
                    comp = event.compareTo( (Event163)object );

                    if (comp > 0)
                    {
                        node = node.prev;;
                    }
                }
            /**
             * Consider the logic in your algorithm whether
             * to insert a newNode AFTER node, the node just
             * determined above. 
             */
            // TODO 33 B) Now create a new node for object in one line of code
            Node<AnyType> newNode = new Node<AnyType>( object, node, node.next );

            // TODO 33 C) Relink the immediate neighboring nodes with 
            //            forward (next) and backward (prev) references to the
            //            newNode in two lines of code.
            newNode.next.prev     = newNode;
            node.next             = newNode;
            

            this.theSize++;
        }
    }

    public AnyType poll( )
    {
        return this.remove( 0 );
    }

    public static void main(String args[]) {
        PriorityQueue163< Event163 > list = new PriorityQueue163< Event163 > ();
        list.add( new Event163( "ARRIVAL", 125 ) );
        list.add( new Event163( "ARRIVAL", 50 ) );
        list.add( new Event163( "ARRIVAL", 200 ) );
        list.add( new Event163( "ARRIVAL", 125 ) );

        // 34) TODO:  add one Event163 instance of your choosing to the list.
        list.add( new Event163( "ARRIVAL", 100 ) );
        

        System.out.println( "A priority queue of Events163 type events, ordered by time" );
        list.print( );

        System.out.println( "\nPolling Event163 objects from the priority queue" );
        System.out.println( list.poll().toString() );
        System.out.println( list.poll().toString() );
        System.out.println( list.poll().toString() );
        //        System.out.println( list.poll().toString() );
        //        System.out.println( list.poll().toString() );

        System.out.println( "\nThe resulting priority queue of Events163 type events" );
        list.print( );

        // 35) TODO:  execute the program from the main method        
    }
}
