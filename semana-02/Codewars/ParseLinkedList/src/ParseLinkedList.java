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



