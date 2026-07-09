import Exception.*;
/**
 * Invariante de representación:
 * - counter >= 0
 * - counter == 0 → first == null && last == null
 * - counter == 1 → first == last
 * - counter > 1 → first != last && last.nextNode == null
 */

public class LinkedList<T> {

    private Node<T> first;
    private Node<T> last;
    private int counter;

    public LinkedList(){
        setFirst(null);
        setLast(null);
        counter = 0;
    }

    /* Setter */
    public void setFirst(Node<T> first) {
        this.first = first;
    }

    public void setLast(Node<T> last) {
        this.last = last;
    }

    /* Getter */
    public Node<T> getFirst() {
        return first;
    }

    public Node<T> getLast() {
        return last;
    }

    public int getCounter() {
        return counter;
    }

    public void addFirst(T element){
        Node<T> firstNode = new Node<>(element);
        if(counter == 0){
            setFirst(firstNode);
            setLast(firstNode);
        } else {
            firstNode.setNextNode(getFirst());
            setFirst(firstNode);
        }
        counter++;
    }

    public void addLast(T element){
        Node<T> lastNode = new Node<>(element);
        if(counter == 0){
            setFirst(lastNode);
            setLast(lastNode);
        } else {
            getLast().setNextNode(lastNode);
            setLast(lastNode);
        }
        counter++;
    }

    public void removeFirst() {
        if(counter <= 0){
            throw new SizeException("Error with size");
        }
        setFirst(getFirst().getNextNode());
        counter--;

        // In case there are not elements in the linkedList
        if(counter == 0){
            setLast(null);
        }
    }

    public boolean contains (T element) {
        if(counter <= 0){
            return false;
        }
        Node<T> nextNode = getFirst();
        while (nextNode != null){
            if(nextNode.getElement().equals(element)){
                return true;
            }
            nextNode = nextNode.getNextNode();
        }

        return false;
    }

    public int size(){
        return counter;
    }
}
