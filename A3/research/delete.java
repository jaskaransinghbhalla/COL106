public boolean delete(int num) {
    if (root == null) {
      return false;
    }

    BSTNode p1 = root;
    BSTNode p2 = null;

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