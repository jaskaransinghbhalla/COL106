// import java.util.ArrayList;
// import java.util.Collections;

public class SkipList {

  public SkipListNode head;
  public SkipListNode tail;
  public int height;
  public Randomizer randomizer;
  private final int NEG_INF = Integer.MIN_VALUE;
  private final int POS_INF = Integer.MAX_VALUE;

  SkipList() {
    /*
     * DO NOT EDIT THIS FUNCTION
     */
    this.head = new SkipListNode(NEG_INF, 1);
    this.tail = new SkipListNode(POS_INF, 1);
    this.head.next.add(0, this.tail);
    this.tail.next.add(0, null);
    this.height = 1;
    this.randomizer = new Randomizer();
  }

  public boolean delete(int num) {
    // Find Position of SkipListNode

    // Go to Each Level <= height of node to be delete and change next of prev to next node
    return false;
  }

  public boolean search(int num) {
    int h = this.height;

    SkipListNode pre = this.head;
    SkipListNode succ = this.tail;

    while (h >= 1) {
      int level = h - 1;
      SkipListNode temp = pre;

      while (temp.next.get(level) != null) {
        // while (temp.value < succ.value) {
        if (temp.value == num) {
          return true;
        }
        if (temp.value < num) {
          pre = temp;
          temp = temp.next.get(level);
        } else {
          succ = temp;
          break;
          // temp = temp.next.get(level);
        }
      }
      h--;
    }

    return false;
  }

  public Integer upperBound(int num) {
    // To be Modified

    SkipListNode temp = this.head;
    while (temp.next.get(0) != null) {
      if (temp.value == num) {
        return (Integer) temp.next.get(0).value;
      } else {
        temp = temp.next.get(0);
      }
    }
    return Integer.MAX_VALUE;
  }

  public void insert(int num) {
    // Evalualting Height of SkipList
    int h = 1;
    while (randomizer.binaryRandomGen() != false && h < this.height + 1) { //Verify height + 1 condition
      // while (randomizer.binaryRandomGen() != false) { //Verify height + 1 condition
      h++;
    }
    // Creating new Node
    SkipListNode newNode = new SkipListNode(num, h);
    for (int level = 0; level < h; level++) {
      if (level + 1 > this.height) {
        this.height = level + 1;
        this.head.next.add(this.tail);
        this.tail.next.add(null);
        this.head.height++;
        this.tail.height++;
      }
      // If No Node was Present
      if (this.head.next.get(level) == this.tail) {
        this.head.next.set(level, newNode);
        newNode.next.add(this.tail);
      }
      // If Node was Present
      else {
        // Finding Location
        SkipListNode prev_node = this.head;
        SkipListNode next_node = this.head;
        while (newNode.value > next_node.value) {
          prev_node = next_node;
          next_node = next_node.next.get(level);
        }
        prev_node.next.set(level, newNode);
        newNode.next.add(next_node);
      }
    }
  }

  public void print() {
    /*
     * DO NOT EDIT THIS FUNCTION
     */
    for (int i = this.height; i >= 1; --i) {
      SkipListNode it = this.head;
      while (it != null) {
        if (it.height >= i) {
          System.out.print(it.value + "\t");
        } else {
          System.out.print("\t");
        }
        it = it.next.get(0);
      }
      System.out.println("null");
    }
  }
  // public static void main(String[] args) {
  //   SkipList sl = new SkipList();
  //   sl.insert(31);
  //   sl.insert(2);
  //   sl.insert(23);
  //   sl.insert(4);
  //   sl.insert(315);
  //   sl.insert(6);
  //   sl.insert(27);
  //   sl.print();
  //   System.out.println(sl.upperBound(27));
  // }
}
