import java.util.ArrayList;

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
    if (search(num)) {
      int heightI = this.height;
      int nodeHeight = this.height;

      ArrayList<SkipListNode> prevArr = new ArrayList<>();
      ArrayList<SkipListNode> nextArr = new ArrayList<>();

      SkipListNode prevNode = this.head;
      SkipListNode nextNode = this.head;

      while (heightI >= 1) {
        int level = heightI - 1;
        nextNode = prevNode;
        while (nextNode.value < num) {
          prevNode = nextNode;
          nextNode = nextNode.next.get(level);
        }
        prevArr.add(prevNode);
        if (nextNode.value == num) {
          nextArr.add(nextNode.next.get(level));
        } else {
          nextArr.add(prevNode.next.get(level));
          nodeHeight--;
        }
        heightI--;
      }

      for (int level = nodeHeight - 1; level >= 0; level--) {
        // Get Predecessor
        prevNode = prevArr.get(prevArr.size() - 1 - level);
        nextNode = nextArr.get(nextArr.size() - 1 - level);
        prevNode.next.set(level, nextNode);
      }


      int h = this.height;

      // this.height > 1;

      for (int level = h - 1; level >= 0; level-- ) {
        if (this.head.next.get(level) == this.tail) {
          this.head.next.remove(level);
          this.tail.next.remove(level);
          this.head.height--;
          this.tail.height--;
          this.height--;
        }
      }

      return true;
    }
    return false;
  }

  public boolean search(int num) {
    int h = this.height;

    SkipListNode pre = this.head;
    SkipListNode succ = this.tail;
    SkipListNode temp;

    while (h >= 1) {
      int level = h - 1;
      temp = pre;
      while (temp.next.get(level) != null && temp.value < succ.value) {
        if (temp.value == num) {
          return true;
        }
        if (temp.value < num) {
          pre = temp;
          temp = temp.next.get(level);
        }
        if (temp.value > num) {
          succ = temp;
        }
      }
      h--;
    }
    return false;
  }

  public Integer upperBound(int num) {
    int heightI = this.height;
    SkipListNode prev = this.head;
    SkipListNode temp = new SkipListNode(Integer.MAX_VALUE, 1);

    while (heightI >= 1) {
      int level = heightI - 1;
      temp = prev;
      while (temp.next.get(level) != null) {
        if (temp.value <= num) {
          prev = temp;
          temp = temp.next.get(level);
        } else {
          break;
        }
      }
      heightI--;
    }
    return temp.value;
  }

  public void insert(int num) {
    // Computing Height
    int nodeHeight = 1;
    int skipListHeight = this.height;

    while (nodeHeight < skipListHeight + 1) {
      if (randomizer.binaryRandomGen()) {
        nodeHeight++;
      } else {
        break;
      }
    }

    // Creating new Node
    SkipListNode newNode = new SkipListNode(num, nodeHeight);
    for (int i = 0; i < nodeHeight; i++) {
      newNode.next.add(null);
    }

    // Increment height
    if (nodeHeight > skipListHeight) {
      this.height = nodeHeight;
      this.head.next.add(this.tail);
      this.tail.next.add(null);
      this.head.height++;
      this.tail.height++;
    }

    // Finding Insert Position
    int heightI = this.height;
    SkipListNode prevNode = this.head;
    SkipListNode nextNode = this.head;

    while (heightI >= 1) {
      int levelI = heightI - 1;
      nextNode = prevNode;
      while (nextNode != null) {
        if (num <= nextNode.value) {
          if (heightI <= nodeHeight) {
            prevNode.next.set(levelI, newNode);
            newNode.next.set(levelI, nextNode);
          }
          break;
        } else if (num > nextNode.value) {
          prevNode = nextNode;
          nextNode = nextNode.next.get(levelI);
        }
      }
      heightI--;
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

  public static void main(String[] args) {
    SkipList s = new SkipList();
    s.insert(1);
    s.delete(1);
    // s.insert(1);
    // s.insert(2);
    // s.insert(100);
    // s.insert(4);
    // s.insert(5);
    // s.insert(6);
    // s.insert(5);
    // s.insert(1);
    // s.insert(1);
    // s.insert(2);
    // s.insert(4);
    // s.insert(5);
    // s.insert(6);
    // s.insert(3);
    // s.insert(15);
    // s.insert(16);
    // s.insert(5);
    // s.delete(2);
    // s.delete(6);
    // s.delete(6);
    // s.delete(6);
    // s.insert(41);
    // s.insert(3);
    // s.insert(12);
    // s.delete(3);
    // s.delete(3);
    s.print();
  }
}
//
// s.insert(1);
//     s.insert(1);
//     s.insert(2);
//     s.insert(100);
//     s.insert(4);
//     s.insert(5);
//     s.insert(6);
//     s.insert(5);
//     s.insert(1);
//     s.insert(1);
//     s.insert(2);
//     s.insert(4);
//     s.insert(5);
//     s.insert(6);
//     s.insert(3);
//     s.insert(15);
//     s.insert(16);
//     s.insert(5);
//     s.delete(2);
//     s.delete(6);
//     s.delete(6);
//     s.delete(6);
//     s.insert(41);
//     s.insert(3);
//     s.insert(12);
//     s.delete(3);
//     s.delete(3);
