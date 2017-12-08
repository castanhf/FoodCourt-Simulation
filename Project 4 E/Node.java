    /**
     * This is the doubly-linked list node.
     */
    public class Node<AnyType>
    {
        public AnyType data;
        public Node<AnyType>   prev;
        public Node<AnyType>   next;

        public Node( AnyType d, Node<AnyType> p, Node<AnyType> n )
        {
            data = d; prev = p; next = n;
        }
        
        public String toString( )
        {
            return data.toString();
        }
    }
