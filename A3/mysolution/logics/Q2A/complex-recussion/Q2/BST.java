import java.util.*;

public class BST {

  public BSTNode root;

  public BST() {
    root = null;
  }

  public void insert(int num) {

    if (root == null) {
      root = new BSTNode(num);
      return;
    }

    if (num < root.value) {
      BSTNode temp = root;
      root = root.left;
      insert(num);
      root.height++;
      temp.left = root;
      root = temp;
    } else if (num > root.value) {
      BSTNode temp = root;
      root = root.right;
      insert(num);
      root.height++;
      temp.right = root;
      root = temp;
    } else {
      return;
    }
  }

  public boolean delete(int num) {
    if (root == null) {
      return false;
    }
    if (root.value > num) {
      BSTNode temp = root;
      root = root.left;
      boolean ans = delete(num);
      temp.left = root;
      root = temp;
      return ans;
    } else if (root.value < num) {
      BSTNode temp = root;
      root = root.right;
      boolean ans = delete(num);
      temp.right = root;
      root = temp;
      return ans;
    } else {
      // 0 Child
      if (root.left == null && root.right == null) {
        root = null;
        return true;
      }
      // 1 Child
      if (root.left == null) {
        root = root.right;
        return true;
      } else if (root.right == null) {
        root = root.left;
        return true;
      }

      //   2 Children
      BSTNode IS = root.right;
      while (IS.left != null) {
        IS = IS.left;
      }
      int val = IS.value;
      BSTNode temp2 = root;
      root = root.right;
      delete(IS.value);
      temp2.value = val;
      temp2.right = root;
      root = temp2;
      return true;
    }
  }

  public boolean search(int num) {
    if (root == null) {
      return false;
    }

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
}
