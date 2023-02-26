import java.util.*;

public class BST {

  public BSTNode root;

  public BST() {
    root = null;
  }

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

  public BSTNode insertUtil(BSTNode root, int num) {
    if (root == null) {
      return new BSTNode(num);
    } else if (num < root.value) {
      root.left = insertUtil(root.left, num);
    } else if (num > root.value) {
      root.right = insertUtil(root.right, num);
    }
    root.height = 1 + max(height(root.left), height(root.right));
    return root;
  }

  public BSTNode deleteUtil(BSTNode root, int num) {
    if (num > root.value) {
      root.right = deleteUtil(root.right, num);
    } else if (num < root.value) {
      root.left = deleteUtil(root.left, num);
    } else {
      if (root.left == null && root.right == null) {
        return null;
      }

      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      }

      BSTNode IP = getInorderPredecessor(root.left);
      root.value = IP.value;
      root.left = deleteUtil(root.left, IP.value);
    }
    root.height = 1 + max(height(root.left), height(root.right));
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

  public void inorderUtil(ArrayList<Integer> arr, BSTNode root) {
    if (root == null) {
      return;
    }
    if (root.left != null) {
      inorderUtil(arr, root.left);
    }
    arr.add(root.value);
    if (root.right != null) {
      inorderUtil(arr, root.right);
    }
  }

  public void preorderUtil(ArrayList<Integer> arr, BSTNode root) {
    if (root == null) {
      return;
    }
    arr.add(root.value);
    if (root.left != null) {
      preorderUtil(arr, root.left);
    }
    if (root.right != null) {
      preorderUtil(arr, root.right);
    }
  }

  public void postorderUtil(ArrayList<Integer> arr, BSTNode root) {
    if (root == null) {
      return;
    }
    if (root.left != null) {
      postorderUtil(arr, root.left);
    }
    if (root.right != null) {
      postorderUtil(arr, root.right);
    }
    arr.add(root.value);
  }

  // ---------------------------------------------------------------

  public void insert(int num) {
    root = insertUtil(root, num);
  }

  public boolean delete(int num) {
    if (root == null || searchUtil(root, num) != true) {
      return false;
    }
    root = deleteUtil(root, num);
    return true;
  }

  public boolean search(int num) {
    return searchUtil(root, num);
  }

  public ArrayList<Integer> inorder() {
    ArrayList<Integer> finalAnswer = new ArrayList<>();
    inorderUtil(finalAnswer, root);
    return finalAnswer;
  }

  public ArrayList<Integer> preorder() {
    ArrayList<Integer> finalAnswer = new ArrayList<>();
    preorderUtil(finalAnswer, root);
    return finalAnswer;
  }

  public ArrayList<Integer> postorder() {
    ArrayList<Integer> finalAnswer = new ArrayList<>();
    postorderUtil(finalAnswer, root);
    return finalAnswer;
  }
}
