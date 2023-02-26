package packagee;

import java.util.*;

class Randomizer {

  /*
   * An object randomizer of class Randomizer has been initialized in the SkipList class. It has a function binaryRandomGen, which returns true or false in a (pseudo) random manner. While inserting a node, you should initialize its height to 1, and using randomizer.binaryRandomGen(), you should iteratively check whether its height should be increased. If the node already has a height larger than the skip list height, stop and do not call the binaryRandomGen() again. Using this procedure is necessary, not adhering may cause inconsistency in output.
   */
  private Random rnd;

  Randomizer() {
    rnd = new Random(106);
  }

  boolean binaryRandomGen() {
    return rnd.nextBoolean();
  }
}

class SkipListNode {

  public int value;
  public int height;
  public ArrayList<SkipListNode> next;

  SkipListNode(int key, int height) {
    value = key;
    this.height = height;
    next = new ArrayList<SkipListNode>(height);
  }
}

public class SkipList {

  public SkipListNode head;
  public SkipListNode tail;
  public int height;
  public Randomizer randomizer;
  private final int NEG_INF = Integer.MIN_VALUE;
  private final int POS_INF = Integer.MAX_VALUE;

  public SkipList() {
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

  // public boolean delete(int num) {
  //   if (search(num)) {
  //     int heightI = this.height;
  //     int nodeHeight = this.height;

  //     ArrayList<SkipListNode> prevArr = new ArrayList<>();
  //     ArrayList<SkipListNode> nextArr = new ArrayList<>();

  //     SkipListNode prevNode = this.head;
  //     SkipListNode nextNode = this.head;

  //     while (heightI >= 1) {
  //       int level = heightI - 1;
  //       nextNode = prevNode;
  //       while (nextNode.value < num) {
  //         prevNode = nextNode;
  //         nextNode = nextNode.next.get(level);
  //       }

  //       prevArr.add(prevNode);

  //       if (nextNode.value == num) {
  //         nextArr.add(nextNode.next.get(level));
  //       } else {
  //         nextArr.add(prevNode.next.get(level));
  //         nodeHeight--;
  //       }
  //       heightI--;
  //     }

  //     for (int level = nodeHeight - 1; level >= 0; level--) {
  //       // Get Predecessor
  //       prevNode = prevArr.get(prevArr.size() - 1 - level);
  //       nextNode = nextArr.get(nextArr.size() - 1 - level);
  //       prevNode.next.set(level, nextNode);

  //       if (this.head.next.get(level) == this.tail) {
  //         this.head.next.remove(level);
  //         this.tail.next.remove(level);
  //         this.head.height--;
  //         this.tail.height--;
  //         this.height--;
  //       }
  //     }

  //     return true;
  //   }
  //   return false;
  // }

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
    int k = this.height;
    SkipListNode prev = this.head;
    SkipListNode temp = new SkipListNode(Integer.MAX_VALUE, 1);

    while (k >= 1) {
      int level = k - 1;
      temp = prev;
      while (temp.next.get(level) != null) {
        if (temp.value <= num) {
          prev = temp;
          temp = temp.next.get(level);
        } else {
          break;
        }
      }
      k--;
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

  public String print() {
    StringBuilder sb = new StringBuilder();
    for (int i = this.height; i >= 1; --i) {
      SkipListNode it = this.head.next.get(i - 1);
      while (it != this.tail) {
        sb.append(it.value);
        sb.append("*");
        it = it.next.get(i - 1);
      }
      sb.append("&");
    }
    String str = sb.toString();
    return str;
  }
}
