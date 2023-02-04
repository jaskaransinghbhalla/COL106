public class test {
    public static class LinkedList {
        public class Node {

            public int data;
            public Node next;

            // Constructor
            public Node(int d) {
                data = d;
                next = null;
            }
        }

        public Node head = null;

        public void insert(int c) {
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
            // to be completed by the student
        }

        public int len() {
            if (head == null) {
                return 0;
            }
            int c = 1;
            Node temp = head;

            while (temp.next != null) {
                temp = temp.next;
                c++;
            }
            // to be completed by the student
            return c;
        }

        public void printLinkedList() {
            if (head == null) {
                System.out.println("Empty Linked List");
                return;
            }
            Node temp = head;
            while (temp != null) {
                System.out.print(temp.data + " ");
                temp = temp.next;
            }
            // System.out.println();
        }
    }

    public static class Polynomial extends LinkedList {

        public Polynomial add(Polynomial p) {

            Polynomial p3 = new Polynomial();
            Node temp1 = head;
            Node temp2 = p.head;

            int len1 = len();
            int len2 = p.len();

            int diff = 0, min = 0;

            if (len1 >= len2) {
                diff = len1 - len2;
                min = len2;
                while (diff != 0) {
                    p3.insert(temp1.data);
                    temp1 = temp1.next;
                    diff--;
                }
            }

            if (len1 < len2) {
                diff = len2 - len1;
                min = len1;
                while (diff != 0) {
                    p3.insert(temp2.data);
                    temp2 = temp2.next;
                    diff--;
                }
            }
            while (min != 0) {
                p3.insert(temp1.data + temp2.data);
                temp1 = temp1.next;
                temp2 = temp2.next;
                min--;

            }
            while (p3.head.data == 0 && p3.len() != 1) {
                p3.head = p3.head.next;
            }
            return p3;
        }

        public Polynomial mult(Polynomial p) {
            Polynomial p3 = new Polynomial();

            int pow1 = len() - 1;
            int pow2 = p.len() - 1;
            int maxpow = (pow1 + pow2);
            for (int i = 0; i < maxpow + 1; i++) {
                p3.insert(0);
            }
            Node pt1 = head;
            Node pt2 = p.head;
            Node pt3 = p3.head;
            Node pt4 = pt3;
            while (pt1 != null) {
                while (pt2 != null) {
                    int coeff = pt1.data * pt2.data;
                    pt4.data = pt4.data + coeff;
                    pt2 = pt2.next;
                    pt4 = pt4.next;
                }
                pt1 = pt1.next;
                pt2 = p.head;
                pt3 = pt3.next;
                pt4 = pt3;

            }
            while (p3.head.data == 0 && p3.len() != 1) {
                p3.head = p3.head.next;
            }
            return p3;

        }

    }

    public static void main(String[] args) {
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        p1.insert(-1);
        p1.insert(-1);

        p2.insert(1);
        p2.insert(0);

        System.out.println("P1");
        p1.printLinkedList();
        System.out.println();
        System.out.println("P2");
        p2.printLinkedList();
        System.out.println();
        System.out.println("P3");
        System.out.println("Add");
        Polynomial p3 = p1.add(p2);
        p3.printLinkedList();
        System.out.println();
        System.out.println("Multiply");
        Polynomial p4 = p1.mult(p2);
        p4.printLinkedList();

    }
}