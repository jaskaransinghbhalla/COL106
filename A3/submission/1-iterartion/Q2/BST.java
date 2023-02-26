import java.util.*;

public class BST {

  public BSTNode root;

  public BST() {
    root = null;
  }

  public void insert(int num) {
    // System.out.println(preorder());
    // System.out.println("insert " + num);

    if (root == null) {
      root = new BSTNode(num);
      return;
    }

    if (num < root.value) {
      BSTNode temp = root;
      root = root.left;
      insert(num);
      temp.left = root;
      root = temp;
    } else {
      BSTNode temp = root;
      root = root.right;
      insert(num);
      temp.right = root;
      root = temp;
    }
  }

  public boolean delete(int num) {
    System.out.println("delete");
    // Modify
    BSTNode p1 = root;
    BSTNode p2 = null;

    if (root == null) {
      return false;
    }
    // Searching for position of Node to be Deleted
    while (p1 != null && p1.value != num) {
      p2 = p1;
      if (num < p1.value) {
        p1 = p1.left;
      } else {
        p1 = p1.right;
      }
    }

    // Node not found
    if (p1 == null) {
      return false;
    }

    // 1 Child
    if (p1.left == null || p1.right == null) {
      BSTNode temp = null;

      if (p1.left == null) {
        temp = p1.right;
      }
      if (p1.right == null) {
        temp = p1.left;
      }
      // Root Node
      if (p2 == null) {
        root = temp;
        return true;
      }

      //   Left or Right Child
      if (p1 == p2.left) {
        p2.left = temp;
      } else if (p1 == p2.right) {
        p2.right = temp;
      }
    }
    // 2 Children
    else if (p1.left != null && p1.right != null) {
      BSTNode p = null;
      BSTNode c = p1.right;

      //   Computing the Left Most Node in the Right Subtree
      while (c.left != null) {
        p = c;
        c = c.left;
      }

      //   Replacing the Current Node Value with Inorder Successor
      //   int t = c.value;
      //   delete(c.value);
      //   p1.value = t;

      if (p != null) {
        p.left = c.right;
      } else {
        p1.right = c.right;
      }
      p1.value = c.value;
      return true;
    }
    return false;
  }

  public boolean search(int num) {
    System.out.println("search");
    // O(logn)
    BSTNode p1 = root;

    while (p1 != null) {
      if (num == p1.value) {
        return true;
      }
      if (num < p1.value) {
        p1 = p1.left;
      } else {
        p1 = p1.right;
      }
    }
    return false;
  }

  public ArrayList<Integer> inorder() {
    if (root == null) {
      return new ArrayList<>();
    }
    ArrayList<Integer> al = new ArrayList<>();

    BSTNode temp = root;

    root = root.left;
    ArrayList<Integer> left = inorder();
    for (int i = 0; i < left.size(); i++) {
      al.add(left.get(i));
    }
    root = temp;

    al.add(root.value);

    root = root.right;
    ArrayList<Integer> right = inorder();
    for (int i = 0; i < right.size(); i++) {
      al.add(right.get(i));
    }
    root = temp;

    return al;
  }

  public ArrayList<Integer> preorder() {
    if (root == null) {
      return new ArrayList<>();
    }
    ArrayList<Integer> al = new ArrayList<>();

    BSTNode temp = root;

    al.add(root.value);

    root = root.left;
    ArrayList<Integer> left = preorder();
    root = temp;
    for (int i = 0; i < left.size(); i++) {
      al.add(left.get(i));
    }

    root = root.right;
    ArrayList<Integer> right = preorder();
    root = temp;
    for (int i = 0; i < right.size(); i++) {
      al.add(right.get(i));
    }

    return al;
  }

  public ArrayList<Integer> postorder() {
    if (root == null) {
      return new ArrayList<>();
    }
    ArrayList<Integer> al = new ArrayList<>();

    BSTNode temp = root;

    root = root.left;
    ArrayList<Integer> left = postorder();
    root = temp;
    for (int i = 0; i < left.size(); i++) {
      al.add(left.get(i));
    }

    root = root.right;
    ArrayList<Integer> right = postorder();
    root = temp;
    for (int i = 0; i < right.size(); i++) {
      al.add(right.get(i));
    }

    al.add(root.value);

    return al;
  }

  public static void main(String[] args) {
    BST b = new BST();
    b.insert(2);
    b.insert(0);
    b.insert(7);
    b.insert(4);
    ArrayList<Integer> al = b.preorder();
    for (int i = 0; i < al.size(); i++) {
      System.out.print(al.get(i) + " ");
    }
  }
}
