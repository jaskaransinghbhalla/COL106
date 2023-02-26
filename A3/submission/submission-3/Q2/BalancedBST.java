public class BalancedBST extends BST {

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
    // Get Root's Balance Factor
    int bf = getBalance(root);

    // Left Left Case
    if (bf > 1 && num < root.left.value) {
      root = rightRotate(root);
    }

    // Right Right Case
    if (bf < -1 && num > root.right.value) {
      root = leftRotate(root);
    }

    // Left Right Case
    if (bf > 1 && num > root.left.value) {
      root.left = leftRotate(root.left);
      root = rightRotate(root);
    }

    // Right Left Case
    if (bf < -1 && num < root.right.value) {
      root.right = rightRotate(root.right);
      root = leftRotate(root);
    }
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
      if (root.left == null || root.right == null) {
        BSTNode temp = null;

        // 1 Child
        if (root.left == null) {
          temp = root.right;
        } else if (root.right == null) {
          temp = root.left;
        }
        if (temp == null) {
          temp = root;
          root = null;
        } else {
          root = temp;
        }
      } else {
        //   2 Children
        BSTNode IS = findInorderSuccessor(root.right);
        root.value = IS.value;
        root.right = deleteHelper(root.right, IS.value);
      }
    }
    if (root == null) {
      return root;
    }
    int bf = getBalance(root);

    // Left Left Case
    if (bf > 1 && getBalance(root.left) >= 0) {
      root = rightRotate(root);
    }

    // Right Right Case
    if (bf < -1 && getBalance(root.right) <= 0) {
      root = leftRotate(root);
    }

    // Left Right Case
    if (bf > 1 && getBalance(root.left) < 0) {
      root.left = leftRotate(root.left);
      root = rightRotate(root);
    }

    // Right Left Case
    if (bf < -1 && getBalance(root.right) > 0) {
      root.right = rightRotate(root.right);
      root = leftRotate(root);
    }
    return root;
  }

  public static BSTNode findInorderSuccessor(BSTNode root) {
    while (root.left != null) {
      root = root.left;
    }
    return root;
  }

  public static int height(BSTNode root) {
    if (root == null) {
      return 0;
    }
    return root.height;
  }

  public static int getBalance(BSTNode root) {
    if (root == null) {
      return 0;
    }
    return height(root.left) - height(root.right);
  }

  public static BSTNode rightRotate(BSTNode y) {
    BSTNode x = y.left;
    BSTNode T2 = x.right;

    // Perform Rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.height = Math.max(height(y.left), height(y.right)) + 1;
    x.height = Math.max(height(x.left), height(x.right)) + 1;

    // Return new Root
    return x;
  }

  public static BSTNode leftRotate(BSTNode x) {
    BSTNode y = x.right;
    BSTNode T2 = y.left;

    // Perform Rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.height = Math.max(height(x.left), height(x.right)) + 1;
    y.height = Math.max(height(y.left), height(y.right)) + 1;

    // Return new Root
    return y;
  }

  public void insert(int key) {
    root = insertHelper(root, key);
  }

  public boolean delete(int key) {
    if (root == null || !searchHelper(root, key)) {
      return false;
    } else {
      root = deleteHelper(root, key);
      return true;
    }
  }
}
