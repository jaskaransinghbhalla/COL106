import java.util.ArrayList;

public class Tree {

  public TreeNode root;

  public Tree() {
    root = null;
  }

  //   Insert Helper Functions
  //   Helper Functions
  private boolean compareString(String s1, String s2) {
    if (s1.compareTo(s2) > 0) {
      // If s1 > s2
      return true;
    } else {
      return false;
    }
  }

  private void initalizeRoot(String newString) {
    root = new TreeNode();
    root.s.add(newString);
    root.val.add(1);
    return;
  }

  private void insertEntry(TreeNode node, String newString) {
    int i = 0;
    while (i < node.s.size()) {
      if (!compareString(node.s.get(i), newString)) {
        i++;
      } else {
        break;
      }
    }
    node.s.add(i, newString);
    node.val.add(i, 1);
  }

  private void splitAtRoot(TreeNode node) {
    // Create Nodes
    TreeNode parNode = new TreeNode();
    TreeNode leftChild = new TreeNode();
    TreeNode rightChild = new TreeNode();

    // Create Left Child
    leftChild.s.add(node.s.get(0));
    leftChild.val.add(node.val.get(0));

    // Create Parent
    parNode.s.add(node.s.get(1));
    parNode.val.add(node.val.get(1));

    // Create Right Child
    rightChild.s.add(node.s.get(2));
    rightChild.val.add(node.val.get(2));
    rightChild.s.add(node.s.get(3));
    rightChild.val.add(node.val.get(3));

    // Assign Children
    parNode.children.add(leftChild);
    parNode.children.add(rightChild);

    if (node.children.size() != 0) {
      leftChild.children.add(node.children.get(0));
      leftChild.children.add(node.children.get(1));
      rightChild.children.add(node.children.get(2));
      rightChild.children.add(node.children.get(3));
      rightChild.children.add(node.children.get(4));
      leftChild.height = leftChild.children.get(0).height + 1;
      rightChild.height = rightChild.children.get(0).height + 1;
    }

    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Parent becomes the Root
    root = parNode;
  }

  private void splitAt2NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent

    insertEntry(parNode, node.s.get(1));

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    if (node.children.size() != 0) {
      firstChild.children.add(node.children.get(0));
      firstChild.children.add(node.children.get(1));
      secondChild.children.add(node.children.get(2));
      secondChild.children.add(node.children.get(3));
      secondChild.children.add(node.children.get(4));
      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }
    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;
    // Assign Children
    // If I was Left Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.add(0, firstChild);
      parNode.children.set(1, secondChild);
    }
    // If I was Right Child of Parent
    else {
      parNode.children.set(1, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void splitAt3NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent

    insertEntry(parNode, node.s.get(1));

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    if (node.children.size() != 0) {
      firstChild.children.add(node.children.get(0));
      firstChild.children.add(node.children.get(1));
      secondChild.children.add(node.children.get(2));
      secondChild.children.add(node.children.get(3));
      secondChild.children.add(node.children.get(4));
      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }
    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;
    // Assign Children
    // If I was First Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.set(0, firstChild);
      parNode.children.add(1, secondChild);
    }
    // If I was Second Child of Parent
    if (parNode.children.get(1) == node) {
      parNode.children.set(1, firstChild);
      parNode.children.add(2, secondChild);
    }
    // If I was Third Child of Parent
    if (parNode.children.get(2) == node) {
      parNode.children.set(2, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void splitAt4NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent
    insertEntry(parNode, node.s.get(1));

    // Create Nodes
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));

    if (node.children.size() != 0) {
      firstChild.children.add(node.children.get(0));
      firstChild.children.add(node.children.get(1));
      secondChild.children.add(node.children.get(2));
      secondChild.children.add(node.children.get(3));
      secondChild.children.add(node.children.get(4));
      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }
    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Assign Children
    // If I was First Child of Parent
    if (parNode.children.get(0) == node) {
      parNode.children.set(0, firstChild);
      parNode.children.add(1, secondChild);
    }
    // If I was Second Child of Parent
    if (parNode.children.get(1) == node) {
      parNode.children.set(1, firstChild);
      parNode.children.add(2, secondChild);
    }
    // If I was Third Child of Parent
    if (parNode.children.get(2) == node) {
      parNode.children.set(2, firstChild);
      parNode.children.add(3, secondChild);
    }
    // If I was Fourth Child of Parent
    if (parNode.children.get(3) == node) {
      parNode.children.set(3, firstChild);
      parNode.children.add(secondChild);
    }
  }

  private void handleOverflow(TreeNode node, TreeNode parNode) {
    // Case 1 -> No Parent Root Node
    if (node == root || parNode == null) {
      splitAtRoot(node);
      return;
    }

    // Case 2 -> Parent node is originally 2-Node
    if (parNode.s.size() == 1) {
      splitAt2NodeParent(node, parNode);
      return;
    }
    // Case 3 -> Parent Node is originally 3-Node
    if (parNode.s.size() == 2) {
      splitAt3NodeParent(node, parNode);
      return;
    }
    // Case 4 -> Parent Node is originally 4-Node
    if (parNode.s.size() == 3) {
      splitAt4NodeParent(node, parNode);
      return;
    }
  }

  private void insertRecursively(
    TreeNode node,
    TreeNode parNode,
    TreeNode superParNode,
    String newString
  ) {
    if (node.children.size() == 0) {
      insertEntry(node, newString);
      if (node.s.size() == 4) {
        handleOverflow(node, parNode);
      }
      return;
    } else {
      int i = 0;
      while (i < node.s.size()) {
        if (compareString(node.s.get(i), newString)) {
          insertRecursively(
            node.children.get(i),
            node,
            superParNode,
            newString
          );
          break;
        }
        if (i + 1 == node.s.size()) {
          insertRecursively(
            node.children.get(i + 1),
            node,
            superParNode,
            newString
          );
          break;
        }
        i++;
      }

      if (node != null) {
        if (node.s.size() == 4) {
          handleOverflow(node, parNode);
        }
      }
      return;
    }
  }

  public void insert(String s) {
    System.out.println("Insert Only Called");
    // TO be completed by students
    if (root == null) {
      initalizeRoot(s);
      return;
    } else {
      insertRecursively(root, null, null, s);
      return;
    }
  }

  public boolean delete(String s) {
    System.out.println("Delete Only Called");
    // TO be completed by students

    return false;
  }

  //   -------------------------------------
  //   Search Helper Functions
  private boolean searchEntry(TreeNode node, String newString) {
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(newString) == 0) {
        return true;
      }
      i++;
    }
    return false;
  }

  private boolean searchRecursively(TreeNode node, String newString) {
    if (node == null) {
      return false;
    }
    if (searchEntry(node, newString)) {
      return searchEntry(node, newString);
    }
    int i = 0;
    while (i < node.s.size()) {
      if (i < node.children.size()) {
        if (compareString(node.s.get(i), newString)) {
          return searchRecursively(node.children.get(i), newString);
        }
        if (i + 1 == node.s.size()) {
          return searchRecursively(node.children.get(i + 1), newString);
        }
      }
      i++;
    }
    return false;
  }

  public boolean search(String s) {
    System.out.println("Searc Only Called");
    // TO be completed by students
    if (root == null) {
      return false;
    } else {
      if (searchRecursively(root, s)) {
        return true;
      }
    }
    return false;
  }

  private TreeNode getNodeRecursive(TreeNode node, String newString) {
    if (node == null) {
      return null;
    }
    if (searchEntry(node, newString)) {
      return node;
    }
    int i = 0;
    while (i < node.s.size()) {
      if (i < node.children.size()) {
        if (compareString(node.s.get(i), newString)) {
          return getNodeRecursive(node.children.get(i), newString);
        }
        if (i + 1 == node.s.size()) {
          return getNodeRecursive(node.children.get(i + 1), newString);
        }
      }
      i++;
    }
    return null;
  }

  public int increment(String s) {
    System.out.println("Increment Only Called");
    // TO be completed by students
    TreeNode node = getNodeRecursive(root, s);
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        node.val.set(i, node.val.get(i) + 1);
        return node.val.get(i);
      }
      i++;
    }
    return 0;
  }

  public int decrement(String s) {
    // TO be completed by students
    TreeNode node = getNodeRecursive(root, s);
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        node.val.set(i, node.val.get(i) - 1);
        return node.val.get(i);
      }
      i++;
    }
    // If value goes below 1 Error
    return 0;
  }

  public int getHeight() {
    // TO be completed by students
    System.out.println("Get Height Only Called");
    return root.height;
  }

  public int getVal(String s) {
    System.out.println("getVal Called");
    // TO be completed by students
    TreeNode node = getNodeRecursive(root, s);
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        return node.val.get(i);
      }
      i++;
    }
    System.out.println("getVal Exited");
    return 0;
  }

  private void printNode(TreeNode n) {
    System.out.println(n.s);
    if (n.children.size() > 1) {
      ArrayList<String> left = n.children.get(0).s;
      System.out.print("First :" + left + "\t");
    }

    if (n.children.size() >= 2) {
      ArrayList<String> middle = n.children.get(1).s;
      System.out.print("Second :" + middle + "\t");
    }
    if (n.children.size() >= 3) {
      ArrayList<String> right = n.children.get(2).s;
      System.out.print("Third :" + right + "\t");
    }
    if (n.children.size() >= 4) {
      ArrayList<String> fourth = n.children.get(3).s;
      System.out.print("Fourth :" + fourth + "\t");
    }
  }

  // ----------------------------------------------

  public static void main(String[] args) {
    System.out.println("Output");
    Tree t = new Tree();

    t.insert("h0");
    t.insert("h1");
    t.insert("h2");
    t.insert("h3");
    t.insert("h4");
    t.insert("h5");
    t.insert("h6");
    t.insert("h7");
    t.insert("h8");
    t.insert("h9");
    t.insert("h99");
    t.insert("h991");
    t.insert("h992");
    t.insert("h999");
  }
}
