import Exception.NegativeCapacityException;

/**
 * Invariante de representación:
 * - front == -1 (cola vacía) || (front >= 0 && front < capacity)
 * - back == -1 (cola vacía) || (back >= 0 && back < capacity)
 * - capacity > 0;
 * - Can't add objects if the queue it's full
 * - Can't take off objets if the queue it's empty
 */

public class Queue<T> {
    //We need the array to store the elements
    private Object[] elements;
    //We need the last position of the last element pushed to the array
    private int front;
    //Last position
    private int back;
    // Max capacity of the array
    private int capacity;

    //Constructor
    public Queue(int capacity){
        setCapacity(capacity);
        this.elements = new Object[getCapacity()];;
        this.front = -1;
        this.back = -1;
    }

    // Getter & Setter
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) {
        if(capacity < 0) {
            throw new NegativeCapacityException("Capacity should be a positive value");
        }
        this.capacity = capacity;
    }

    //Methods
    public void enqueue(T element){
        if(isFull()){
            System.out.println("Queue is full.");
            return;
        }
        if(front == -1){
            front = 0;
        }
        back = (back + 1) % capacity;
        elements[back] = element;
        System.out.println("Inserted");
    }

    //Take off the top element and return it
    public T dequeue(){
        if(isEmpty()){
            System.out.println("Queue is empty.");
            return null;
        }
        T element = (T) elements[front];

        if(front == back){
            front = -1;
            back = -1;
        } else {
            front = (front + 1) % capacity;
        }
        return element;
    }

    public T front() {
        if (isEmpty()){
            return null;
        }
        return (T) elements[front];
    }

    public boolean isEmpty(){
        return front == -1;
    }

    public boolean isFull(){
        if((front == 0 && back == (capacity - 1)) || (front - 1 == back)){
            return true;
        }
        return false;
    }
}
