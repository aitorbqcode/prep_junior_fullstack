public class Node<T> {

    private T element;
    private Node<T> nextNode;

    /* Constructor */
    /* When we don't have a next node and it's null */
    public Node(T element){
       setElement(element);
       setNextNode(null);
    }

    /* When we have a next node*/
    public Node(T element, Node<T> node){
        setElement(element);
        setNextNode(node);
    }

    /* Setter */
    public void setElement(T element) { this.element = element; }

    public void setNextNode(Node<T> node) { this.nextNode = node;}

    /* Getter */
    public T getElement() { return element; }

    public Node<T> getNextNode() { return nextNode; }
}

