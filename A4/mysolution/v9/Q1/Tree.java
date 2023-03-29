import java.util.ArrayList;

public class Tree {

  public TreeNode root;

  public Tree() {
    root = null;
  }

  //   Insert Helper Functions
  //   Helper Functions
  private boolean compareString(String s1, String s2) {
    if (s1.compareTo(s2) >= 0) {
      // If s1 >= s2
      return true;
    } else {
      return false;
    }
  }

  private void initalizeRoot(String newString) {
    root = new TreeNode();
    root.s.add(newString);
    root.val.add(1);
    root.parent = null;
    return;
  }

  private void insertEntry(TreeNode node, String newString, int value) {
    int i = 0;
    while (i < node.s.size()) {
      if (!compareString(node.s.get(i), newString)) {
        i++;
      } else {
        break;
      }
    }
    node.s.add(i, newString);
    node.val.add(i, value);
  }

  private void splitAtRoot(TreeNode node) {
    // Create Nodes
    TreeNode parNode = new TreeNode();
    TreeNode firstChild = new TreeNode();
    TreeNode secondChild = new TreeNode();

    // Create First Child
    firstChild.s.add(node.s.get(0));
    firstChild.val.add(node.val.get(0));
    firstChild.parent = parNode;

    // Create Parent
    parNode.s.add(node.s.get(1));
    parNode.val.add(node.val.get(1));

    // Create Second Child
    secondChild.s.add(node.s.get(2));
    secondChild.val.add(node.val.get(2));
    secondChild.s.add(node.s.get(3));
    secondChild.val.add(node.val.get(3));
    secondChild.parent = parNode;

    // Assign Children
    parNode.children.add(firstChild);
    parNode.children.add(secondChild);

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);
      firstChild.height = firstChild.children.get(0).height + 1;
      secondChild.height = secondChild.children.get(0).height + 1;
    }

    // Adjust Parent Height
    parNode.height = parNode.children.get(0).height + 1;

    // Parent becomes the Root
    root = parNode;
  }

  private void splitAt2NodeParent(TreeNode node) {
    //   private void splitAt2NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;
    // insertEntry(parNode, node.s.get(1));
    insertEntry(parNode, node.s.get(1), node.val.get(1));

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

    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);
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

  private void splitAt3NodeParent(TreeNode node) {
    //   private void splitAt3NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;

    insertEntry(parNode, node.s.get(1), node.val.get(1));

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

    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);

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

  private void splitAt4NodeParent(TreeNode node) {
    //   private void splitAt4NodeParent(TreeNode node, TreeNode parNode) {
    // Transfer 1st Entry to Parent
    // Added
    TreeNode parNode = node.parent;
    insertEntry(parNode, node.s.get(1), node.val.get(1));

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

    // Assigning Parent
    firstChild.parent = parNode;
    secondChild.parent = parNode;

    if (node.children.size() != 0) {
      TreeNode l1 = node.children.get(0);
      l1.parent = firstChild;
      TreeNode l2 = node.children.get(1);
      l2.parent = firstChild;

      firstChild.children.add(l1);
      firstChild.children.add(l2);

      TreeNode r1 = node.children.get(2);
      r1.parent = secondChild;
      TreeNode r2 = node.children.get(3);
      r2.parent = secondChild;
      TreeNode r3 = node.children.get(4);
      r3.parent = secondChild;

      secondChild.children.add(r1);
      secondChild.children.add(r2);
      secondChild.children.add(r3);
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

  private void handleOverflow(TreeNode node) {
    //   private void handleOverflow(TreeNode node, TreeNode parNode) {
    TreeNode parNode = node.parent;
    // Case 1 -> No Parent Root Node
    if (node == root || parNode == null) {
      splitAtRoot(node);
      return;
    }

    // Case 2 -> Parent node is originally 2-Node
    if (parNode.s.size() == 1) {
      splitAt2NodeParent(node);
      //   splitAt2NodeParent(node, parNode);
      return;
    }
    // Case 3 -> Parent Node is originally 3-Node
    if (parNode.s.size() == 2) {
      splitAt3NodeParent(node);
      //   splitAt3NodeParent(node, parNode);
      return;
    }
    // Case 4 -> Parent Node is originally 4-Node
    if (parNode.s.size() == 3) {
      splitAt4NodeParent(node);
      //   splitAt4NodeParent(node, parNode);
      return;
    }
  }

  private void insertRecursively(TreeNode node, String newString) {
    if (node.children.size() == 0) {
      insertEntry(node, newString, 1);
      if (node.s.size() == 4) {
        handleOverflow(node);
      }
      return;
    } else {
      int i = 0;
      while (i < node.s.size()) {
        if (compareString(node.s.get(i), newString)) {
          insertRecursively(
            node.children.get(i),
            // node,
            // superParNode,
            newString
          );
          break;
        }
        if (i + 1 == node.s.size()) {
          insertRecursively(
            node.children.get(i + 1),
            // node,
            // superParNode,
            newString
          );
          break;
        }
        i++;
      }

      if (node != null) {
        if (node.s.size() == 4) {
          handleOverflow(node);
          //   handleOverflow(node, node.parent);
        }
      }
      return;
    }
  }

  public void insert(String s) {
    // System.out.println("t.insert(" + s + ");");
    // TO be completed by students
    if (root == null) {
      initalizeRoot(s);
      return;
    } else {
      //   insertRecursively(root, null, null, s);
      insertRecursively(root, s);
      return;
    }
  }

  private TreeNode findInOrderPredecessorRecursive(TreeNode node, String s) {
    if (node == null) {
      return null;
    }
    if (node.children.size() == 0) {
      return node;
    }
    int i = 0;
    while (i < node.s.size()) {
      if (i < node.children.size()) {
        if (node.s.get(i).compareTo(s) >= 0) {
          return findInOrderPredecessorRecursive(node.children.get(i), s);
        }
        if (i + 1 == node.s.size()) {
          return findInOrderPredecessorRecursive(node.children.get(i + 1), s);
        }
      }
      i++;
    }
    return null;
  }

  public int getIdx(TreeNode node, String s) {
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        return i;
      }
      i++;
    }
    return 0;
  }

  private TreeNode findInOrderPredecessor(String s) {
    // TO be completed by students
    return findInOrderPredecessorRecursive(root, s);
  }

  private int findInOrderPredecessorIndexInNode(TreeNode node, String s) {
    // TO be completed by students
    int i = 0;
    while (i < node.s.size()) {
      if (!(node.s.get(i).compareTo(s) >= 0)) {
        i++;
      } else {
        break;
      }
    }
    if (i == 0) {
      return 0;
    }
    return i - 1;
  }

  private void swapNodes(
    TreeNode deleteNode,
    int deleteIdx,
    TreeNode inOrderPred,
    int inOrderPredIdx
  ) {
    // If Root Node is Swapped
    // Any Other Node is Swapped
    String deleteString = deleteNode.s.get(deleteIdx);
    String inOrderString = inOrderPred.s.get(inOrderPredIdx);
    int deleteValue = deleteNode.val.get(deleteIdx);
    int inOrderValue = inOrderPred.val.get(inOrderPredIdx);

    deleteNode.s.set(deleteIdx, inOrderString);
    deleteNode.val.set(deleteIdx, inOrderValue);

    inOrderPred.s.set(inOrderPredIdx, deleteString);
    inOrderPred.val.set(inOrderPredIdx, deleteValue);
  }

  private void deleteValue(TreeNode deleteNode, int deleteIdx) {
    deleteNode.s.remove(deleteIdx);
    deleteNode.val.remove(deleteIdx);
  }

  private boolean checkSibilings2Nodes(TreeNode node) {
    TreeNode parNode = node.parent;

    if (parNode == null || parNode.s.size() == 1) {
      return false;
    }

    boolean check = false;

    for (int i = 0; i < parNode.children.size(); i++) {
      if (parNode.children.get(i) == node) {
        continue;
      } else if (parNode.children.get(i).s.size() == 1) { // <= fishy
        check = true;
      } else {
        check = false;
      }
    }
    return check;
  }

  private boolean checkAtLeastOneSibiling34Node(TreeNode node) {
    TreeNode parNode = node.parent;
    if (parNode == null) {
      return false;
    }
    boolean check = false;
    for (int i = 0; i < parNode.children.size(); i++) {
      if (parNode.children.get(i) == node) {
        continue;
      } else {
        int s = parNode.children.get(i).s.size();
        if (s == 2 || s == 3) {
          check = true;
        }
      }
    }
    return check;
  }

  private boolean checkSiblingParent2Node(TreeNode node) {
    TreeNode parNode = node.parent;
    if (parNode == null) {
      return false;
    }
    boolean check = false;

    if (parNode.s.size() == 1) {
      for (int i = 0; i < parNode.children.size(); i++) {
        if (parNode.children.get(i) == node) {
          continue;
        } else if (parNode.children.get(i).s.size() == 1) {
          check = true;
        }
      }
    }

    return check;
  }

  private void sibilings2NodesParens34(TreeNode node) {
    TreeNode parNode = node.parent;
    int noOfSibilings = parNode.s.size();

    // 3 Node
    if (noOfSibilings == 2) {
      if (parNode.children.get(0) == node) {
        String s = parNode.s.remove(0);
        int v = parNode.val.remove(0);

        TreeNode Child = parNode.children.get(1);

        Child.s.add(0, s);
        Child.val.add(0, v);

        parNode.children.remove(0);
        parNode.children.set(0, Child);
        Child.parent = parNode;
        if (node.children.size() != 0) {
          Child.children.add(0, node.children.remove(node.children.size() - 1));
        }
        return;
      }

      if (parNode.children.get(1) == node) {
        String s = parNode.s.remove(0);
        int v = parNode.val.remove(0);

        TreeNode rightChild = parNode.children.get(0);

        rightChild.s.add(s);
        rightChild.val.add(v);

        parNode.children.remove(1);
        parNode.children.set(0, rightChild);

        rightChild.parent = parNode;
        if (node.children.size() != 0) {
          rightChild.children.add(node.children.remove(0));
        }
        return;
        // String s = parNode.s.remove(1);
        // int v = parNode.val.remove(1);

        // TreeNode rightChild = parNode.children.get(2);

        // rightChild.s.add(0, s);
        // rightChild.val.add(0, v);

        // parNode.children.remove(2);
        // parNode.children.set(1, rightChild);

        // rightChild.parent = parNode;
        // return;
      }

      if (parNode.children.get(2) == node) {
        String s = parNode.s.remove(1);
        int v = parNode.val.remove(1);
        TreeNode middleChild = parNode.children.get(1);
        middleChild.s.add(s);
        middleChild.val.add(v);
        parNode.children.remove(2);
        parNode.children.set(1, middleChild);
        middleChild.parent = parNode;
        if (node.children.size() != 0) {
          middleChild.children.add(node.children.remove(0));
        }
        return;
        // String s = parNode.s.remove(1);
        // int v = parNode.val.remove(1);
        // TreeNode middleChild = parNode.children.get(1);
        // middleChild.s.add(1, s);
        // middleChild.val.add(1, v);
        // parNode.children.remove(2);
        // parNode.children.set(1, middleChild);
        // middleChild.parent = parNode;
        // return;
      }
    }
    // 4 Node
    else if (noOfSibilings == 3) {
      if (parNode.children.get(0) == node) {
        String s = parNode.s.remove(0);
        int v = parNode.val.remove(0);
        TreeNode leftChild = parNode.children.get(1);
        leftChild.s.add(0, s);
        leftChild.val.add(0, v);
        parNode.children.remove(0);
        parNode.children.set(0, leftChild);
        leftChild.parent = parNode;
        if (node.children.size() != 0) {
          leftChild.children.add(
            0,
            node.children.remove(node.children.size() - 1)
          );
        }
        return;
      }

      if (parNode.children.get(1) == node) {
        // String s = parNode.s.remove(1);
        // int v = parNode.val.remove(1);
        // TreeNode rightChild = parNode.children.get(2);
        // rightChild.s.add(0, s);
        // rightChild.val.add(0, v);
        // parNode.children.remove(1);
        // parNode.children.set(1, rightChild);
        // rightChild.parent = parNode;
        // return;
        String s = parNode.s.remove(0);
        int v = parNode.val.remove(0);
        TreeNode rightChild = parNode.children.get(0);
        rightChild.s.add(s);
        rightChild.val.add(v);
        parNode.children.remove(1);
        parNode.children.set(0, rightChild);
        rightChild.parent = parNode;
        if (node.children.size() != 0) {
          rightChild.children.add(node.children.remove(0));
        }
        return;
      }

      if (parNode.children.get(2) == node) {
        String s = parNode.s.remove(1);
        int v = parNode.val.remove(1);
        TreeNode middleChild = parNode.children.get(1);
        middleChild.s.add(s);
        middleChild.val.add(v);
        parNode.children.remove(2);
        parNode.children.set(1, middleChild);
        middleChild.parent = parNode;
        if (node.children.size() != 0) {
          middleChild.children.add(node.children.remove(0));
        }
        return;
        // String s = parNode.s.remove(2);
        // int v = parNode.val.remove(2);
        // TreeNode lastChild = parNode.children.get(3);
        // lastChild.s.add(0, s);
        // lastChild.val.add(0, v);
        // parNode.children.remove(2);
        // parNode.children.set(2, lastChild);
        // lastChild.parent = parNode;
        // return;

      }

      if (parNode.children.get(3) == node) {
        String s = parNode.s.remove(2);
        int v = parNode.val.remove(2);
        TreeNode middleChild = parNode.children.get(2);
        middleChild.s.add(s);
        middleChild.val.add(v);
        parNode.children.remove(3);
        parNode.children.set(2, middleChild);
        middleChild.parent = parNode;
        if (node.children.size() != 0) {
          middleChild.children.add(node.children.remove(0));
        }
        return;
        // String s = parNode.s.remove(2);
        // int v = parNode.val.remove(2);
        // TreeNode middleChild = parNode.children.get(2);
        // middleChild.s.add(s);
        // middleChild.val.add(v);
        // parNode.children.remove(2);
        // parNode.children.set(2, middleChild);
        // middleChild.parent = parNode;
        // return;
      }
    }
  }

  private void atLeastOneSibiling34Node(TreeNode node) {
    // I guess no height upgradation is needed
    TreeNode parNode = node.parent;

    // Parent Node -> 2 Node
    if (parNode.s.size() == 1) {
      // I am First Child Check
      if (parNode.children.get(0) == node) {
        TreeNode firstChild = parNode.children.get(0);
        TreeNode secondChild = parNode.children.get(1);

        String s1 = parNode.s.remove(0);
        int v1 = parNode.val.remove(0);

        firstChild.s.add(0, s1);
        firstChild.val.add(0, v1);

        String s2 = secondChild.s.remove(0);
        int v2 = secondChild.val.remove(0);

        parNode.s.add(s2);
        parNode.val.add(v2);

        if (node.children.size() != 0) {
          firstChild.children.add(secondChild.children.remove(0));
        }
        return;
      }

      // I am Second Child Check
      if (parNode.children.get(1) == node) {
        TreeNode firstChild = parNode.children.get(0);
        TreeNode secondChild = parNode.children.get(1);

        String s1 = parNode.s.remove(0);
        int v1 = parNode.val.remove(0);

        secondChild.s.add(0, s1);
        secondChild.val.add(0, v1);

        String s2 = firstChild.s.remove(firstChild.s.size() - 1);
        int v2 = firstChild.val.remove(firstChild.s.size() - 1);

        parNode.s.add(s2);
        parNode.val.add(v2);

        if (node.children.size() != 0) {
          secondChild.children.add(
            0,
            firstChild.children.remove(firstChild.children.size() - 1)
          );
        }

        return;
      }
    }

    // Parent Node -> 3 Node
    if (parNode.s.size() == 2) {
      TreeNode firstChild = parNode.children.get(0);
      TreeNode secondChild = parNode.children.get(1);
      TreeNode thirdChild = parNode.children.get(2);

      // I am First Child
      if (parNode.children.get(0) == node) {
        // Case 1 Borrow From Right Checked
        if (secondChild.s.size() > 1) {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          firstChild.s.add(0, s1);
          firstChild.val.add(0, v1);

          String s2 = secondChild.s.remove(0);
          int v2 = secondChild.val.remove(0);

          parNode.s.add(0, s2);
          parNode.val.add(0, v2);
          if (node.children.size() != 0) {
            firstChild.children.add(secondChild.children.remove(0));
          }
          return;
        }
        // Case 2 Merger with Right Check
        else {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          secondChild.s.add(0, s1);
          secondChild.val.add(0, v1);

          if (node.children.size() != 0) {
            secondChild.children.add(firstChild.children.remove(0));
          }
          parNode.children.remove(0);
          return;
        }
      }

      // I am Second Child
      if (parNode.children.get(1) == node) {
        // Borrow Left Child Check
        if (firstChild.s.size() > 1) {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          secondChild.s.add(s1);
          secondChild.val.add(v1);

          String s2 = firstChild.s.remove(firstChild.s.size() - 1);
          int v2 = firstChild.val.remove(firstChild.s.size() - 1);

          parNode.s.add(s2);
          parNode.val.add(v2);
          if (node.children.size() != 0) {
            secondChild.children.add(
              0,
              firstChild.children.remove(firstChild.children.size() - 1)
            );
          }
          return;
        }
        // Merge with Left Check
        else {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          firstChild.s.add(s1);
          firstChild.val.add(v1);
          if (node.children.size() != 0) {
            firstChild.children.add(secondChild.children.remove(0));
          }
          parNode.children.remove(1);
          return;
        }
      }

      //   I am Third Child
      if (parNode.children.get(2) == node) {
        // Borrow Left Child Check
        if (secondChild.s.size() > 1) {
          String s1 = parNode.s.remove(1);
          int v1 = parNode.val.remove(1);

          thirdChild.s.add(0, s1);
          thirdChild.val.add(0, v1);

          String s2 = secondChild.s.remove(secondChild.s.size() - 1);
          int v2 = secondChild.val.remove(secondChild.s.size() - 1);

          parNode.s.add(s2);
          parNode.val.add(v2);
          if (node.children.size() != 0) {
            thirdChild.children.add(
              0,
              secondChild.children.remove(secondChild.children.size() - 1)
            );
          }
          return;
        }
        // Merge with Left Check
        else {
          String s1 = parNode.s.remove(1);
          int v1 = parNode.val.remove(1);

          secondChild.s.add(s1);
          secondChild.val.add(v1);

          parNode.children.remove(2);
          if (node.children.size() != 0) {
            secondChild.children.add(thirdChild.children.remove(0));
          }
          return;
        }
      }

      return;
    }

    // Parent Node -> 4 Node
    if (parNode.s.size() == 3) {
      TreeNode firstChild = parNode.children.get(0);
      TreeNode secondChild = parNode.children.get(1);
      TreeNode thirdChild = parNode.children.get(2);
      TreeNode fourthChild = parNode.children.get(3);
      // I am First Child
      if (parNode.children.get(0) == node) {
        // Case 1 Borrow From Right Checked
        if (secondChild.s.size() > 1) {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          firstChild.s.add(0, s1);
          firstChild.val.add(0, v1);

          String s2 = secondChild.s.remove(0);
          int v2 = secondChild.val.remove(0);

          parNode.s.add(0, s2);
          parNode.val.add(0, v2);
          if (node.children.size() != 0) {
            firstChild.children.add(secondChild.children.remove(0));
          }
          return;
        }
        // Case 2 Merger with Right Check
        else {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          secondChild.s.add(0, s1);
          secondChild.val.add(0, v1);

          if (node.children.size() != 0) {
            secondChild.children.add(firstChild.children.remove(0));
          }
          parNode.children.remove(0);
          return;
        }
      }

      // I am Second Child
      if (parNode.children.get(1) == node) {
        // Borrow Left Child Check
        if (firstChild.s.size() > 1) {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          secondChild.s.add(0, s1);
          secondChild.val.add(0, v1);

          String s2 = firstChild.s.remove(firstChild.s.size() - 1);
          int v2 = firstChild.val.remove(firstChild.s.size() - 1);

          parNode.s.add(0, s2);
          parNode.val.add(0, v2);
          if (node.children.size() != 0) {
            secondChild.children.add(
              0,
              firstChild.children.remove(firstChild.children.size() - 1)
            );
          }
          return;
        }
        // Merge with Left Check
        else {
          String s1 = parNode.s.remove(0);
          int v1 = parNode.val.remove(0);

          firstChild.s.add(s1);
          firstChild.val.add(v1);
          if (node.children.size() != 0) {
            firstChild.children.add(secondChild.children.remove(0));
          }
          parNode.children.remove(1);
          return;
        }
      }

      //   I am Third Child
      if (parNode.children.get(2) == node) {
        // Borrow Left Child Check
        if (secondChild.s.size() > 1) {
          String s1 = parNode.s.remove(1);
          int v1 = parNode.val.remove(1);

          thirdChild.s.add(0, s1);
          thirdChild.val.add(0, v1);

          String s2 = secondChild.s.remove(secondChild.s.size() - 1);
          int v2 = secondChild.val.remove(secondChild.s.size() - 1);

          parNode.s.add(1, s2);
          parNode.val.add(1, v2);
          if (node.children.size() != 0) {
            thirdChild.children.add(
              0,
              secondChild.children.remove(secondChild.children.size() - 1)
            );
          }
          return;
        }
        // Merge with Left Check
        else {
          String s1 = parNode.s.remove(1);
          int v1 = parNode.val.remove(1);

          secondChild.s.add(s1);
          secondChild.val.add(v1);
          if (node.children.size() != 0) {
            secondChild.children.add(thirdChild.children.remove(0));
          }
          parNode.children.remove(2);
          return;
        }
      }

      //   I am Fourth Child
      if (parNode.children.get(3) == node) {
        // Borrow Left Child Check
        if (thirdChild.s.size() > 1) {
          String s1 = parNode.s.remove(2);
          int v1 = parNode.val.remove(2);

          fourthChild.s.add(0, s1);
          fourthChild.val.add(0, v1);

          String s2 = thirdChild.s.remove(thirdChild.s.size() - 1);
          int v2 = thirdChild.val.remove(thirdChild.s.size() - 1);

          parNode.s.add(s2);
          parNode.val.add(v2);
          if (node.children.size() != 0) {
            fourthChild.children.add(
              0,
              thirdChild.children.remove(thirdChild.children.size() - 1)
            );
          }
          return;
        }
        // Merge with Left Check
        else {
          String s1 = parNode.s.remove(2);
          int v1 = parNode.val.remove(2);

          thirdChild.s.add(s1);
          thirdChild.val.add(v1);
          if (node.children.size() != 0) {
            thirdChild.children.add(fourthChild.children.remove(0));
          }
          parNode.children.remove(3);
          return;
        }
      }
    }
  }

  private void sibilingParent2Node(TreeNode node) {
    TreeNode parNode = node.parent;

    TreeNode firstChild = parNode.children.get(0);
    TreeNode secondChild = parNode.children.get(1);

    TreeNode newNode = new TreeNode();

    // Case 1 Merger with Parent
    if (parNode.children.get(0) == node) {
      newNode.s.add(parNode.s.get(0));
      newNode.val.add(parNode.val.get(0));
      newNode.s.add(secondChild.s.get(0));
      newNode.val.add(secondChild.val.get(0));

      parNode.s.remove(0);
      parNode.val.remove(0);

      parNode.children.remove(0);
      parNode.children.remove(0);

      parNode.children.add(newNode);
      newNode.parent = parNode;

      if (parNode == root) {
        root = newNode;
        newNode.parent = null;
      } else {
        // To Be Tested
        handleUnderFlow(parNode);
      }

      if (node.children.size() != 0) {
        newNode.children.add(node.children.get(0));
        newNode.children.add(secondChild.children.get(0));
        newNode.children.add(secondChild.children.get(1));
        newNode.height = newNode.children.get(0).height + 1;
      }
      return;
    }
    // Case 2 Merge with
    else if (parNode.children.get(1) == node) {
      newNode.s.add(firstChild.s.get(0));
      newNode.val.add(firstChild.val.get(0));
      newNode.s.add(parNode.s.get(0));
      newNode.val.add(parNode.val.get(0));

      parNode.s.remove(0);
      parNode.val.remove(0);

      parNode.children.remove(0);
      parNode.children.remove(0);
      parNode.children.add(newNode);
      newNode.parent = parNode;

      if (parNode == root) {
        root = newNode;
        newNode.parent = null;
      } else {
        handleUnderFlow(parNode);
      }

      if (node.children.size() != 0) {
        newNode.children.add(node.children.get(0));
        newNode.children.add(firstChild.children.get(0));
        newNode.children.add(firstChild.children.get(1));
        newNode.height = newNode.children.get(0).height + 1;
      }
      return;
    }
  }

  private void handleUnderFlow(TreeNode inOrderPred) {
    // Deletion at Root

    // Case 1
    if (checkSibilings2Nodes(inOrderPred)) {
      sibilings2NodesParens34(inOrderPred);
      return;
    }
    // Case 2 Check

    if (checkAtLeastOneSibiling34Node(inOrderPred)) {
      atLeastOneSibiling34Node(inOrderPred);
      return;
    }
    // Case 3
    if (checkSiblingParent2Node(inOrderPred)) {
      sibilingParent2Node(inOrderPred);
    }
  }

  private boolean deleteUtil(String s) {
    if (search(s)) {
      TreeNode deleteNode = getNodeRecursive(root, s);
      int deleteIdx = getIdx(deleteNode, s);
      TreeNode inOrderPred = findInOrderPredecessor(s);
      int inOrderPredIdx = findInOrderPredecessorIndexInNode(inOrderPred, s);
      swapNodes(deleteNode, deleteIdx, inOrderPred, inOrderPredIdx);
      deleteValue(inOrderPred, inOrderPredIdx);

      // System.out.println();
      // printNode(root);
      if (inOrderPred == root && inOrderPred.s.size() == 0) {
        root = null;
      } else {
        if (inOrderPred.s.size() == 0) {
          handleUnderFlow(inOrderPred);
        }
      }
      return true;
    } else {
      return false;
    }
  }

  public boolean delete(String s) {
    // TO be completed by students
    return deleteUtil(s);
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

    return root.height;
  }

  public int getVal(String s) {
    // TO be completed by students
    TreeNode node = getNodeRecursive(root, s);
    int i = 0;
    while (i < node.s.size()) {
      if (node.s.get(i).compareTo(s) == 0) {
        return node.val.get(i);
      }
      i++;
    }

    return 0;
  }

  private void printNode(TreeNode n) {
    if (n == null) {
      return;
    }
    System.out.println();
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

}
