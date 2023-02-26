public class BalancedBST extends BST {

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

  public void balance(BSTNode root, int key) {
    // Get Root's Balance Factor
    int bf = getBalance(root);

    // Left Left Case
    if (bf > 1 && key < root.left.value) {
      root = rightRotate(root);
    }

    // Right Right Case
    if (bf < -1 && key > root.right.value) {
      root = leftRotate(root);
    }

    // Left Right Case
    if (bf > 1 && key > root.left.value) {
      root.left = leftRotate(root.left);
      root = rightRotate(root);
    }

    // Right Left Case
    if (bf < -1 && key < root.right.value) {
      root.right = rightRotate(root.right);
      root = leftRotate(root);
    }
  }

  public void insert(int key) {
    if (root == null) {
      root = new BSTNode(key);
      return;
    }

    if (key < root.value) {
      BSTNode temp = root;
      root = root.left;
      insert(key);
      root.height++;
      temp.left = root;
      root = temp;
    } else if (key > root.value) {
      BSTNode temp = root;
      root = root.right;
      insert(key);
      root.height++;
      temp.right = root;
      root = temp;
    } else {
      return;
    }
    balance(root, key);
  }

  public boolean delete(int key) {
    if (root == null) {
      return false;
    }
    if (root.value > key) {
      BSTNode temp = root;
      root = root.left;
      boolean ans = delete(key);
      temp.left = root;
      root = temp;
      return ans;
    } else if (root.value < key) {
      BSTNode temp = root;
      root = root.right;
      boolean ans = delete(key);
      temp.right = root;
      root = temp;
      return ans;
    } else {
      // 0 Child
      if (root.left == null && root.right == null) {
        root = null;
        balance(root, key);
        return true;
      }
      // 1 Child
      if (root.left == null) {
        root = root.right;
        balance(root, key);
        return true;
      } else if (root.right == null) {
        root = root.left;
        balance(root, key);
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
      balance(root, key);
      return true;
    }
  }

  public static void main(String[] args) {
    BalancedBST b = new BalancedBST();
    b.insert(10);
    b.insert(20);
    b.insert(30);
    b.insert(40);
    b.insert(50);
    b.insert(25);
    b.preorder();
    for (int i = 0; i < b.preorder().size(); i++) {
      System.out.print(b.preorder().get(i) + " ");
    }
    System.out.println();
    b.delete(30);
    b.preorder();
    for (int i = 0; i < b.preorder().size(); i++) {
      System.out.print(b.preorder().get(i) + " ");
    }
  }
}
