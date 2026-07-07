//Make a stack using an array
/**
 * Invariante de representación:
 * - top >= 0 && top <= capacity
 * - capacity > 0;
 * - Can't add objects if the stack it's full
 * - Can't take off or peek objets if the stack it's empty
 */
import Exceptions.CapacityException;
import Exceptions.EmptyException;
import Exceptions.NegativeCapacityException;

public class Stack<T> {
    //We need the array to store the elements
    private Object[] elements;
    //We need the last position of the last element pushed to the array
    private int top;
    // Max capacity of the array
    private int capacity;

    //Constructor
    public Stack(int capacity){
        setCapacity(capacity);
        this.elements = new Object[getCapacity()];;
        this.top = 0;
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
    public void push(T element){
        //Check if the stack it's not full
        if(size() >= capacity){
            throw new CapacityException("The stack it's full");
        }
        //We add the element
        elements[top] = element;
        //Sum up the top position
        top++;
    }

    //Take off the top element and return it
    public T pop(){
        //Check if the stack it's empty
        if(empty()){
            throw new EmptyException("There are no elements in the stack");
        }
        // Get the top element of the stack
        T returnObject = (T) elements[top - 1];
        //We set the top position to null
        elements[top - 1] = null;
        //We substract one value to the top position
        top--;
        //We return the top object
        return returnObject;
    }

    //Take off the top element and return it
    public Object peek(){
        //Check if the stack it's empty
        if(empty()){
            throw new EmptyException("There are no elements in the stack");
        }
        //Return the top element of the stack
        return elements[top - 1];
    }

    //Check if the top position equals 0, meaning there are no elements in the stack
    public boolean empty(){
        return top == 0;
    }

    //Get the size of the array
    public int size(){
        return top;
    }

    //Check if chain it's balanced
    public boolean balancedChain(String chain){
        if(chain.length() % 2 != 0){
            return false;
        }

        // We create a stack to push the elements
        Stack<Character> stack = new Stack<>(chain.length());

        for(int i = 0; i < chain.length(); i++){
            //Get the character
            char current = chain.charAt(i);

            //If it's one of these elements it means we are in the first half
            if(current == '(' || current == '[' || current == '{') {
                stack.push(current);
            }
            //But it's one of these elements we are in the second half
            else if(current == ')' || current == ']' || current == '}') {
                //We check if the stack it's empty, so it means the chain string it's not in a good order
                if(stack.empty()) return false;

                //We peek the last element
                char open = (char) stack.peek();

                //We check if the elements of the chain are in a good position and order
                if((current == ')' && open == '(') || (current == ']' && open == '[') || (current == '}' && open == '{')) {
                    //Pop the element
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }
}
