// REDO
// 24) TODO:   add brief documentation to describe this class
/**
 * This class is the superclass of Queue163<AnyType> and PriorityQueue163<AnyType>, which means that 
 * those two said classes can use the fields and methods of this class (LinkedList163<AnyType>) without
 * declaring new variables and/or creating new methods.
 * In this class, a list (of datatype LinkedList163<AnyType>) is created, and each element of it is of
 * datatype Node<AnyType>, which uses two fields: (AnyType) data, and (Node<AnyType>) next.
 */
public class LinkedList163<AnyType>
{
    protected final Node<AnyType> NOT_FOUND = null;

    protected int theSize;
    protected Node<AnyType> beginMarker;
    protected Node<AnyType> endMarker;
    protected Node<AnyType> currentMarker;

    public LinkedList163( )
    {
        this.beginMarker      = new Node<AnyType>( null, null, null );
        this.endMarker        = new Node<AnyType>( null, beginMarker, null );
        this.beginMarker.next = endMarker;

        this.theSize = 0;
    }

    public void print()
    {
        Node<AnyType> node = this.beginMarker.next;
        while ( node != this.endMarker)
        {
            System.out.println( node.data.toString() );
            node = node.next;
        }
    }

    public String toString()
    {
        String str = "";

        Node<AnyType> node = this.beginMarker.next;
        while ( node != this.endMarker)
        {
            str += node.data.toString() + "\n";
            node = node.next;
        }
        return str;
    }

    public void begin()
    {
        this.currentMarker = this.beginMarker.next;
    }

    public boolean hasNext()
    {
        return (this.currentMarker != this.endMarker);
    }

    public AnyType next()
    {
        AnyType obj = this.currentMarker.data;

        if (this.currentMarker != this.endMarker)
        {
            this.currentMarker = this.currentMarker.next;
        }

        return obj;
    }

    public int size( )
    {
        return this.theSize;
    }

    /**
     * Adds the object to the end of the list
     */
    public void add( AnyType object )
    {
        // DONE
        // 25) TODO: 4 lines of code
        Node<AnyType> node = this.endMarker;
        Node<AnyType> newNode = new Node<AnyType>(object, node.prev, node);
        node.prev.next = newNode;
        node.prev = newNode;

        this.theSize++;
    }

    /**
     * Adds the object at the beginning of the list
     */
    public void addFirst( AnyType object )
    {
        Node<AnyType> newNode;

        // DONE
        // 26) TODO:  three lines of code
        newNode = new Node<AnyType>(object, beginMarker, beginMarker);
        this.beginMarker.next.prev = newNode;
        this.beginMarker.next = newNode;

        this.theSize++;
    }

    public AnyType remove( int index)
    {
        if( index >= this.size( ) )
        {
            throw new NoSuchElementException( );
        }
        Node<AnyType> node = this.getNode( index );

            // DONE
        // 27) TODO: two lines of code
        node.next.prev = node.prev;
        node.prev.next = node.next;

        this.theSize--;
        return node.data;    
    }    

    /**
     * Returns the node for the first item matching object in this collection,
     * or NOT_FOUND if not found.
     * 
     * Two definitions for findNode. 
     *        Are they both correct? 
     *        Which definition for findNode is best?
     * 
     * @param object
     * @return the position of first item matching object in this collection,
     * or NOT_FOUND if not found.
     */
    //     public Node<AnyType> findNode( Object object )
    //     {
    //         for( Node<AnyType> node = beginMarker.next; node != endMarker; node = node.next )
    //         {
    //             if( object == null )
    //             {
    //                 if( node.data == null )
    //                 {
    //                     return node;
    //                 }
    //             }
    //             else if( object.equals( node.data ) )
    //             {
    //                 return node;
    //             }
    //         }
    // 
    //         return NOT_FOUND;
    //     }

    public Node<AnyType> findNode( Object object )
    {
        Node<AnyType> node;
        for( node = beginMarker.next; node != endMarker; node = node.next )
        {
            // Remark: equals is inherited from the Object class, and can be overridden
            if( node.equals( object ) )
            {
                return node;
            }
        }

        return NOT_FOUND;
    }

    /**
     * returns the node at position index: 0,1,.., this.size()
     */
    public Node<AnyType> getNode( int index )
    {
        Node<AnyType> node = null;

        if( index > this.size() )
        {
            throw new IndexOutOfBoundsException( 
                "getNode index: " + 
                index + 
                "; size: " +
                this.size( ) );
        }
        else
        {
            node = this.beginMarker.next;
            
            // DONE
            // 28) TODO: use a for loop to iterate one line of code
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node;
    }

    /**
     * returns the data value stored at position index,
     * but does not in any way alter the list.
     */
    public AnyType peek( int index )
    {
        Node<AnyType> node = this.getNode( index );

        return node.data;
    }

    public static void main(String[] args)
    {
        LinkedList163<Integer> list = new LinkedList163<Integer>( );

        System.out.println( "A list of integers" );
        list.add( 0 );      // vs list.addFirst( 0 ) 
        list.add( 1 );
        list.add( 2 );
        list.add( 3 );

        list.print();

        System.out.println( "After removing " + list.remove( 0 ) ); 
        System.out.println( "After removing " + list.remove( 0 ) ); 
        System.out.println( "After removing " + list.remove( 0 ) ); 
        // optional inclusions        
        //         System.out.println( "After removing " + list.remove( 0 ) ); 
        //         System.out.println( "After removing " + list.remove( 0 ) ); 

        System.out.println( "The resulting list of integers" );
        list.print();

        // DONE
        // 29) TODO:  execute the program from the main method        
    }
}