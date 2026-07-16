public class ParseLinkedList {

    public static Node parseLinkedList(String str) {
        Node head = null;
        Node last = null;

        String[] strElements = str.trim().split(" -> ");

        for(String element : strElements){
            if (element.trim().equals("null")){
                break;
            } else if(head == null && last == null){
                head = new Node(Integer.parseInt(element.trim()), null);
                last = head;
            } else {
                last.next = new Node(Integer.parseInt(element.trim()), null);
                last = last.next;
            }
        }
        return head;
    }

    public static void main(String[] args) {
        ParseLinkedList.parseLinkedList("1 -> 2 -> 3 ->  null");
    }
}


/*
 Parse a linked list from a string

Related Kata

Although this Kata is not part of an official Series, you may want to complete this Kata before attempting this one as these two Kata are deeply related.


Preloaded

Preloaded for you is a class, struct or derived data type Node ( depending on the language ) used to construct linked lists in this Kata:


final class Node {

public final int data;

public Node next;

public Node(int data) {

this(data, null);

}

public Node(int data, Node next) {

this.data = data;

this.next = next;

}

}

Prerequisites

This Kata assumes that you are already familiar with the idea of a

linked list. If you do not know what that is, you may want to read up

on this article on Wikipedia. Specifically, the linked lists this Kata is referring to are singly linked lists, where the value of a specific node is stored in its data / $data/Data property, the reference to the next node is stored in its next / $next / Next property and the terminator for a list is null / NULL / nil / nullptr / null() / [].

Additionally, this Kata assumes that you have basic knowledge of

Object-Oriented Programming ( or a similar concept ) in the programming

language you are undertaking. If you have not come across

Object-Oriented Programming in your selected language, you may want to

try out an online course or read up on some code examples of OOP in your

selected language up to ( but not necessarily including ) Classical

Inheritance.

Specifically, if you are attempting this Kata in PHP and haven't come across OOP, you may want to try out the first 4 Kata in this Series.


Task

Create a function parse which accepts exactly one argument string / $string / s / strrep

( or similar, depending on the language ) which is a string

representation of a linked list. Your function must return the

corresponding linked list, constructed from instances of the Node

class/struct/type. The string representation of a list has the

following format: the value of the node, followed by a whitespace, an

arrow and another whitespace (" -> "), followed by the rest of the linked list. Each string representation of a linked list will end in "null" / "NULL" / "nil" / "nullptr" / "null()"

depending on the language you are undertaking this Kata in. For

example, given the following string representation of a linked list:

... your function should return:

Note that due to the way the constructor for Node is defined, if a second argument is not provided, the next / $next / Next field is automatically set to null / NULL / nil / nullptr

( or equivalent in your language ). That means your function could

also return the following ( if it helps you better visualise what is

actually going on ):

Another example: given the following string input:

... your function should return:

If the input string is just "null" / "NULL" / "nil" / "nullptr" / "null()", return null / NULL / nil / nullptr / null() / [] ( or equivalent ).

For the simplicity of this Kata, the values of the nodes in the string representation will always ever be non-negative integers, so the following would not occur: "Hello World -> Goodbye World -> 123 -> null" / "Hello World -> Goodbye World -> 123 -> NULL" / "Hello World -> Goodbye World -> 123 -> nil" / "Hello World -> Goodbye World -> 123 -> nullptr" ( depending on the language ). This also means that the values of each Node must also be non-negative integers so keep that in mind when you are parsing the list from the string.

Enjoy, and don't forget to check out my other Kata Series :D
 */

