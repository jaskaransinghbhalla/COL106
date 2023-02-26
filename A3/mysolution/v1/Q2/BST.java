public class BST {

  public BSTNode root;

  public BST() {
    root = null;
  }

  public void insert(int num) {
    // Modify
    // O(logn)
    BSTNode newNode = new BSTNode(num);
    BSTNode p1 = root;
    BSTNode p2 = null;
    if (root == null) {
      root = newNode;
      return;
    }

    while (p1 != null) {
      p2 = p1;
      if (num < p1.value) {
        p1 = p1.left;
        newNode.height++;
      } else {
        p1 = p1.right;
        newNode.height++;
      }
    }

    if (num < p2.value) {
      p2.left = newNode;
    } else {
      p2.right = newNode;
    }
  }

  public void delete(int num) {
    // Modify
    BSTNode p1 = root;
    BSTNode p2 = null;

    if (root == null) {
      return;
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
      return;
    }

    // No Child
    if (p1.left == null && p1.right == null) {
      //   Left Child
      if (p2.left == p1) {
        p2.left = null;
      }
      //   Right Child
      if (p2.right == p1) {
        p2.right = null;
      }
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
        return;
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
    }
  }

  public boolean search(int num) {
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

  public void inorder() {
    if (root == null) {
      return;
    }
    BSTNode temp = root;

    root = root.left;
    inorder();
    root = temp;

    System.out.print(root.value);
    System.out.print(" ");

    root = root.right;
    inorder();
    root = temp;
  }

  public void preorder() {
    if (root == null) {
      return;
    }
    System.out.print(root.value);
    System.out.print(" ");

    BSTNode temp = root;

    root = root.left;
    preorder();
    root = temp;

    root = root.right;
    preorder();
    root = temp;
  }

  public void postorder() {
    if (root == null) {
      return;
    }

    BSTNode temp = root;

    root = root.left;
    postorder();
    root = temp;

    root = root.right;
    postorder();
    root = temp;

    System.out.print(root.value);
    System.out.print(" ");
  }

}
