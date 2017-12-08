
/**     // DONE
 * 30) TODO:   add brief documentation to describe this class
 * This class inherits the variables and methods from its superclass, which is
 * LinkedList<AnyType>. In order for the class Queue163<AnyType> to call a method
 * that belongs to its parent class, the word "super" or "this" has to preceed the method call.
 * Unless the default constructor is called, then the method call should be
 * "super()". There are three methods in this class, which are Queue163(), remove(), and peek(),
 * and each of them call the respective methods from the superclass LinkedList163 < AnyType >,
 * LinkedList163( ), remove( int index), and peek( int index). This class shows three
 * examples of overriding.
 * 
 * @author Filipe Castanheira 
 * @version 04/21/2017
 */
public class Queue163<AnyType> extends LinkedList163 < AnyType >
{

    public Queue163()
    {
        super();
    }

    public AnyType remove( )
    {
        return this.remove( 0 );
    }

    public AnyType peek( )
    {
        return super.peek( 0 );   // or this.peek( 0 );
    }

    
    public static void main(String[] args )
    {
       Queue163<String> queue = new Queue163<String>();
        
        queue.add( "Adam" );
        queue.add( "Beth" );
        queue.add( "Carl" );
        queue.add( "Dawn" );
        
        System.out.println( "A queue of Strings" );
        queue.print();
        
        String str = queue.remove();
        System.out.print( "Removing a String value from the queue: " );
        System.out.println( str );
        
        System.out.println( "The resulting queue of Strings" );
        queue.print();

        // DONE
// 31) TODO:  execute the program from the main method        
    }
}
