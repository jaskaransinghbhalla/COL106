public class BalancedBST extends BST {

  // ---------------------------------------------------------------
  // Helper Functions

  public int max(int a, int b) {
    if (a > b) {
      return a;
    } else {
      return b;
    }
  }

  public int height(BSTNode root) {
    if (root == null) {
      return 0;
    }
    return root.height;
  }

  public BSTNode getInorderPredecessor(BSTNode root) {
    while (root.right != null) {
      root = root.right;
    }
    return root;
  }

  public int getbalanceFactor(BSTNode root) {
    if (root == null) {
      return 0;
    }
    return height(root.left) - height(root.right);
  }

  public BSTNode rightRotate(BSTNode y) {
    BSTNode x = y.left;
    BSTNode T2 = x.right;

    // Perform Rotation
    x.right = y;
    y.left = T2;

    // Update heights
    y.height = max(height(y.left), height(y.right)) + 1;
    x.height = max(height(x.left), height(x.right)) + 1;

    // Return new Root
    return x;
  }

  public BSTNode leftRotate(BSTNode x) {
    BSTNode y = x.right;
    BSTNode T2 = y.left;

    // Perform Rotation
    y.left = x;
    x.right = T2;

    // Update heights
    x.height = max(height(x.left), height(x.right)) + 1;
    y.height = max(height(y.left), height(y.right)) + 1;

    // Return new Root
    return y;
  }

  public BSTNode insertBalancedUtil(BSTNode root, int num) {
    if (root == null) {
      return new BSTNode(num);
    }
    if (num < root.value) {
      root.left = insertBalancedUtil(root.left, num);
    }
    if (num > root.value) {
      root.right = insertBalancedUtil(root.right, num);
    }
    root.height = 1 + max(height(root.left), height(root.right));
    int balanceFactor = getbalanceFactor(root);

    if (balanceFactor > 1 && num < root.left.value) {
      root = rightRotate(root);
    }
    if (balanceFactor < -1 && num > root.right.value) {
      root = leftRotate(root);
    }
    if (balanceFactor > 1 && num > root.left.value) {
      root.left = leftRotate(root.left);
      root = rightRotate(root);
    }
    if (balanceFactor < -1 && num < root.right.value) {
      root.right = rightRotate(root.right);
      root = leftRotate(root);
    }
    return root;
  }

  public BSTNode deleteBalancedUtil(BSTNode root, int num) {
    if (num > root.value) {
      root.right = deleteBalancedUtil(root.right, num);
    } else if (num < root.value) {
      root.left = deleteBalancedUtil(root.left, num);
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
        BSTNode IP = getInorderPredecessor(root.left);
        root.value = IP.value;
        root.left = deleteUtil(root.left, IP.value);
      }
    }

    if (root == null) {
      return root;
    }

    root.height = 1 + max(height(root.left), height(root.right));
    int balanceFactor = getbalanceFactor(root);

    // Left Left Case
    if (balanceFactor > 1 && getbalanceFactor(root.left) >= 0) {
      root = rightRotate(root);
    }

    // Right Right Case
    if (balanceFactor < -1 && getbalanceFactor(root.right) <= 0) {
      root = leftRotate(root);
    }

    // Left Right Case
    if (balanceFactor > 1 && getbalanceFactor(root.left) < 0) {
      root.left = leftRotate(root.left);
      root = rightRotate(root);
    }

    // Right Left Case
    if (balanceFactor < -1 && getbalanceFactor(root.right) > 0) {
      root.right = rightRotate(root.right);
      root = leftRotate(root);
    }
    return root;
  }

  public boolean searchUtil(BSTNode root, int num) {
    if (root == null) {
      return false;
    }
    if (num < root.value) {
      return searchUtil(root.left, num);
    }
    if (num > root.value) {
      return searchUtil(root.right, num);
    }
    return root.value == num;
  }

  // ---------------------------------------------------------------

  public void insert(int key) {
    root = insertBalancedUtil(root, key);
  }

  public boolean delete(int key) {
    if (root == null || !searchUtil(root, key)) {
      return false;
    } else {
      root = deleteBalancedUtil(root, key);
      return true;
    }
  }
}
