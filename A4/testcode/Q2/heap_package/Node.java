package heap_package;

// Do not edit this file.

public class Node {

  public int key;
  public int value;
  public Node left;
  public Node right;
  public Node parent;
  public int height;
  public boolean is_complete;

  public Node(int key, int value, Node parent) {
    this.key = key;
    this.value = value;
    this.parent = parent;

    this.left = null;
    this.right = null;
    this.height = 1; // Height of sub-tree rooted at this node.
    this.is_complete = true; // Whether binary sub-tree rooted at this node is complete or not.
  }
}
