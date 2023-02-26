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

      SkipListNode prevNode = this.head;
      SkipListNode nextNode = this.head;

      ArrayList<SkipListNode> prevNodeArr = new ArrayList<>();
      ArrayList<SkipListNode> nextNodeArr = new ArrayList<>();

      // Finding
      while (heightI >= 1) {
        int levelI = heightI - 1;
        nextNode = prevNode;

        // Finding
        while (nextNode != null) {
          if (nextNode.value < num) {
            prevNode = nextNode;
            nextNode = nextNode.next.get(levelI);
          } else if (num <= nextNode.value) {
            break;
          }
        }
        prevNodeArr.add(prevNode);
        nextNodeArr.add(nextNode);
        heightI--;
      }

      // Deleting
      int nodeHeight = nextNode.height;
      for (int levelI = 0; levelI < nodeHeight; levelI++) {
        SkipListNode beforeNode = prevNodeArr.remove(prevNodeArr.size() - 1);
        SkipListNode currNode = nextNodeArr.remove(nextNodeArr.size() - 1);
        SkipListNode afterNode = currNode.next.get(levelI);
        beforeNode.next.set(levelI, afterNode);
      }
      int levelI = this.height - 1;
      while (this.head.next.get(levelI) == this.tail && levelI > 0) {
        this.head.next.remove(levelI);
        this.tail.next.remove(levelI);
        this.head.height--;
        this.tail.height--;
        this.height--;
        levelI--;
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

    SkipListNode prevNode = this.head;
    SkipListNode upperNode = new SkipListNode(Integer.MAX_VALUE, 1);

    while (heightI >= 1) {
      int levelI = heightI - 1;
      upperNode = prevNode;
      while (upperNode.next.get(levelI) != null) {
        if (upperNode.value <= num) {
          prevNode = upperNode;
          upperNode = upperNode.next.get(levelI);
        } else {
          break;
        }
      }
      heightI--;
    }
    return upperNode.value;
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
    s.insert(10);
    s.delete(10);
    s.print();
  }
}
