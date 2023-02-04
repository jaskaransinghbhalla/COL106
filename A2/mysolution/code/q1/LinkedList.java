public class LinkedList {

    public Node head;

    public LinkedList() {
        head = null;
    }

    public void insert(int c) {
        // to be completed by the student
        Node newNode = new Node(c);
        if (head == null) {
            head = newNode;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }
    }

    public int len() {
        // to be completed by the student
        if (head == null) {
            return 0;
        }
        int c = 1;
        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
            c++;
        }
        return c;
    }
}
