import java.util.*;

public class BST {

  public BSTNode root;

  public BST() {
    root = null;
  }

  // ---------------------------------------------------------------
  // Helper Functions
  public static int height(BSTNode root) {
    if (root == null) {
      return 0;
    }
    return root.height;
  }

  // Root Height = 1
  // public BSTNode insertHelper(BSTNode root, int num, int height) {
  //   if (root == null) {
  //     BSTNode temp = new BSTNode(num);
  //     temp.height = height;
  //     return temp;
  //   }
  //   if (num < root.value) {
  //     root.left = insertHelper(root.left, num, height + 1);
  //   }
  //   if (num > root.value) {
  //     root.right = insertHelper(root.right, num, height + 1);
  //   }
  //   return root;
  // }

  public BSTNode insertHelper(BSTNode root, int num) {
    if (root == null) {
      return new BSTNode(num);
    }
    if (num < root.value) {
      root.left = insertHelper(root.left, num);
    }
    if (num > root.value) {
      root.right = insertHelper(root.right, num);
    }
    // Root Height Maxiumum
    root.height = 1 + Math.max(height(root.left), height(root.right));
    return root;
  }

  public boolean searchHelper(BSTNode root, int num) {
    if (root == null) {
      return false;
    }
    if (num < root.value) {
      return searchHelper(root.left, num);
    }
    if (num > root.value) {
      return searchHelper(root.right, num);
    }
    if (root.value == num) {
      return true;
    }
    return false;
  }

  public BSTNode deleteHelper(BSTNode root, int num) {
    if (num > root.value) {
      root.right = deleteHelper(root.right, num);
    } else if (num < root.value) {
      root.left = deleteHelper(root.left, num);
    } else {
      // Equality

      // 0 Child
      if (root.left == null && root.right == null) {
        return null;
      }

      // 1 Child
      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      }

      //   2 Children
      BSTNode IS = findInorderSuccessor(root.right);
      root.value = IS.value;
      root.right = deleteHelper(root.right, IS.value);
    }
    root.height = 1 + Math.max(height(root.left), height(root.right));
    return root;
  }

  public static BSTNode findInorderSuccessor(BSTNode root) {
    while (root.left != null) {
      root = root.left;
    }
    return root;
  }

  // ---------------------------------------------------------------

  public void insert(int num) {
    root = insertHelper(root, num);
    // root = insertHelper(root, num, 1);
  }

  public boolean delete(int num) {
    if (root == null) {
      return false;
    }
    root = deleteHelper(root, num);
    return searchHelper(root, num);
  }

  public boolean search(int num) {
    return searchHelper(root, num);
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
