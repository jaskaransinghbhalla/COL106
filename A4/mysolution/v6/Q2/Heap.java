package heap_package;

import java.util.ArrayList;

public class Heap {

  protected Node root; // root of the heap
  protected Node[] nodes_array; // Stores the address of node corresponding to the keys
  private int max_size; // Maximum number of nodes heap can have

  private static final String NullKeyException = "NullKey"; // Null key exception
  private static final String NullRootException = "NullRoot"; // Null root exception
  private static final String KeyAlreadyExistsException = "KeyAlreadyExists"; // Key already exists exception

  /* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Not allowed to use any data structure. 
	*/

  // ------------------------------------
  // Helper Functions
  private int height(Node n) {
    if (n == null) {
      return 0;
    }
    return n.height;
  }

  private boolean is_complete(Node root) {
    if (root.left == null && root.right == null) {
      return true;
    }

    return (
      height(root.left) == height(root.right) &&
      root.left.is_complete == root.right.is_complete
    );
  }

  private void heapify(Node n) {
    Node leftChild = n.left;
    Node rightChild = n.right;
    if (leftChild == null && rightChild == null) {
      return;
    }
    if (leftChild != null) {
      heapify(leftChild);
    }
    if (rightChild != null) {
      heapify(rightChild);
    }

    if (leftChild != null) {
      if (leftChild.value > n.value) {
        int k = n.key;
        int v = n.value;

        n.key = leftChild.key;
        n.value = leftChild.value;
        nodes_array[n.key] = n;

        leftChild.key = k;
        leftChild.value = v;
        nodes_array[leftChild.key] = leftChild;
        heapify(leftChild);
      }
    }

    if (rightChild != null) {
      if (rightChild.value > n.value) {
        int k = n.key;
        int v = n.value;
        n.key = rightChild.key;
        n.value = rightChild.value;
        nodes_array[n.key] = n;
        rightChild.key = k;
        rightChild.value = v;
        nodes_array[rightChild.key] = rightChild;
        heapify(rightChild);
      }
    }

    n.height = Integer.max(height(n.left), height(n.right)) + 1;
    n.is_complete = is_complete(n);
  }

  private void buildHeapUtil(int[] keys_array, int[] values_array)
    throws Exception {
    if (keys_array == null || values_array == null) {
      return;
    }

    // Randomized Tree
    ArrayList<Node> narr = new ArrayList<>();
    // O(n)

    // No. of Nodes to be initalized in Max Heap
    int noOfNodes = keys_array.length;

    // Adding Root Node
    Node zeroNode = new Node(keys_array[0], values_array[0], null);
    narr.add(zeroNode);
    nodes_array[keys_array[0]] = zeroNode;

    // Adding other Nodes and assigning their parentNodes to them
    for (int i = 1; i < noOfNodes; i++) {
      Node parNode = narr.get((i - 1) / 2);
      if (nodes_array[keys_array[i]] != null) {
        throw new Exception(KeyAlreadyExistsException);
      }
      Node newNode = new Node(keys_array[i], values_array[i], parNode);
      narr.add(newNode);
      nodes_array[keys_array[i]] = newNode;
    }

    if (noOfNodes != 1) {
      // Assigning Left and Right Children
      for (int i = 0; i <= noOfNodes / 2 - 1; i++) {
        Node currNode = narr.get(i);
        Node leftChild = narr.get(2 * i + 1);
        currNode.left = leftChild;
        if (2 * i + 2 < noOfNodes) {
          Node rightChild = narr.get(2 * i + 2);
          currNode.right = rightChild;
        }
        narr.set(i, currNode);
      }
    }
    root = narr.get(0);

    // Heapify
    for (int i = narr.size() - 1; i >= 0; i--) {
      heapify(narr.get(i));
    }
  }

  private void insertLeftMostNode(Node root, Node newNode) {
    if (root.left != null) {
      insertLeftMostNode(root.left, newNode);
      root.height = Integer.max(height(root.left), height(root.right)) + 1;
      root.is_complete = is_complete(root);
    } else {
      newNode.parent = root;
      root.left = newNode;
      root.height = Integer.max(height(root.left), height(root.right)) + 1;
      root.is_complete = is_complete(root);
      nodes_array[root.key] = root;
      nodes_array[newNode.key] = newNode;
      return;
    }
  }

  private void insertTargetNode(Node root, Node newNode) {
    if (root.left == null) {
      newNode.parent = root;
      root.left = newNode;
      root.height = Integer.max(height(root.left), height(root.right)) + 1;
      root.is_complete = is_complete(root);
      nodes_array[newNode.key] = newNode;
      nodes_array[root.key] = root;
      return;
    }
    if (root.right == null) {
      newNode.parent = root;
      root.right = newNode;
      root.height = Integer.max(height(root.left), height(root.right)) + 1;
      root.is_complete = is_complete(root);
      nodes_array[newNode.key] = newNode;
      nodes_array[root.key] = root;
      return;
    }
    if (
      root.left.is_complete &&
      root.right.is_complete &&
      height(root.left) == height(root.right)
    ) {
      insertLeftMostNode(root, newNode);
      root.height = Integer.max(height(root.left), height(root.right)) + 1;
      root.is_complete = is_complete(root);
      return;
    }

    if (!root.left.is_complete) {
      insertTargetNode(root.left, newNode);
    } else {
      insertTargetNode(root.right, newNode);
    }
    root.height = Integer.max(height(root.left), height(root.right)) + 1;
    root.is_complete = is_complete(root);
  }

  private Node findTargetNode(Node root) {
    // O(logn)
    if (root.left == null && root.right == null) {
      return root;
    }
    if (root.right == null) {
      return findTargetNode(root.left);
    }
    if (root.left == null) {
      return findTargetNode(root.right);
    }

    if (root.left.is_complete && !root.right.is_complete) {
      return findTargetNode(root.right);
    }

    if (
      root.left.is_complete &&
      root.right.is_complete &&
      height(root.left) == height(root.right)
    ) {
      return findTargetNode(root.right);
    } else {
      return findTargetNode(root.left);
    }
  }

  private void swapParentNode(Node newNode, Node parNode) {
    if (parNode == null) {
      return;
    }
    while (parNode.value < newNode.value) {
      int k = parNode.key;
      int v = parNode.value;
      parNode.key = newNode.key;
      parNode.value = newNode.value;
      nodes_array[parNode.key] = parNode;

      newNode.key = k;
      newNode.value = v;
      nodes_array[newNode.key] = newNode;

      newNode = parNode;
      parNode = parNode.parent;

      if (parNode == null) {
        break;
      }
    }
  }

  private ArrayList<Integer> deleteMaxUtil(ArrayList<Integer> max_keys) {
    // Finding Target Node
    Node endNode = findTargetNode(root);
    Node rootNode = root;

    // Swapping Node
    int k = rootNode.key;
    int v = rootNode.value;
    rootNode.key = endNode.key;
    rootNode.value = endNode.value;
    nodes_array[rootNode.key] = rootNode;

    endNode.key = k;
    endNode.value = v;
    nodes_array[endNode.key] = endNode;

    // Removing Node
    max_keys.add(endNode.key);
    Node parNode = endNode.parent;
    nodes_array[endNode.key] = null;
    if (parNode.left == endNode) {
      parNode.left = null;
      parNode.height =
        Integer.max(height(parNode.left), height(parNode.right)) + 1;
      parNode.is_complete = is_complete(parNode);
    } else {
      parNode.right = null;
      parNode.height =
        Integer.max(height(parNode.left), height(parNode.right)) + 1;
      // parNode.is_complete = is_complete(parNode);
    }
    // Heapify
    heapify(root);
    // Returning
    return max_keys;
  }

  private ArrayList<Integer> getMaxUtil(
    Node root,
    int val,
    ArrayList<Integer> max_keys
  ) {
    if (root.value != val) {
      return max_keys;
    }
    max_keys.add(root.key);
    if (root.left != null) {
      max_keys = getMaxUtil(root.left, val, max_keys);
    }
    if (root.right != null) {
      max_keys = getMaxUtil(root.right, val, max_keys);
    }
    return max_keys;
  }

  private ArrayList<Integer> getKeysUtil(Node root, ArrayList<Integer> keys) {
    keys.add(root.key);
    if (root.left != null) {
      getKeysUtil(root.left, keys);
    }
    if (root.right != null) {
      getKeysUtil(root.right, keys);
    }
    return keys;
  }

  public Heap(int max_size, int[] keys_array, int[] values_array)
    throws Exception {
    /* 
     1. Create Max Heap for elements present in values_array.
     2. keys_array.length == values_array.length and keys_array.length number of nodes should be created. 
     3. Store the address of node created for keys_array[i] in nodes_array[keys_array[i]].
     4. Heap should be stored based on the value i.e. root element of heap should 
        have value which is maximum value in values_array.
     5. max_size denotes maximum number of nodes that could be inserted in the heap. 
     6. keys will be in range 0 to max_size-1.
     7. There could be duplicate keys in keys_array and in that case throw KeyAlreadyExistsException. 
  */

    /* 
     For eg. keys_array = [1,5,4,50,22] and values_array = [4,10,5,23,15] : 
     => So, here (key,value) pair is { (1,4), (5,10), (4,5), (50,23), (22,15) }.
     => Now, when a node is created for element indexed 1 i.e. key = 5 and value = 10, 
         that created node address should be saved in nodes_array[5]. 
  */

    /*
     n = keys_array.length
     Expected Time Complexity : O(n).
  */

    this.max_size = max_size;
    this.nodes_array = new Node[this.max_size];

    // To be filled in by the student
    buildHeapUtil(keys_array, values_array);
  }

  public ArrayList<Integer> getMax() throws Exception {
    // System.out.println("Get Max");
    /* 
		   1. Returns the keys with maximum value in the heap.
		   2. There could be multiple keys having same maximum value. You have
		      to return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

    ArrayList<Integer> max_keys = new ArrayList<Integer>(); // Keys with maximum values in heap.

    // To be filled in by the student
    if (root == null) {
      throw new Exception(NullRootException);
    }
    int val = root.value;
    return getMaxUtil(root, val, max_keys);
  }

  public void insert(int key, int value) throws Exception {
    // System.out.println("Insert");
    /* 
		   1. Insert a node whose key is "key" and value is "value" in heap 
		      and store the address of new node in nodes_array[key]. 
		   2. If key is already present in heap, throw KeyAlreadyExistsException.

		   Expected Time Complexity : O(logn).
		*/

    // Finding Key in O(log n )

    // Inserting Key in O(log n)

    // To be filled in by the student

    if (nodes_array[key] != null) {
      throw new Exception(KeyAlreadyExistsException);
    }

    // Initializing Node
    Node newNode = new Node(key, value, null);
    nodes_array[key] = newNode;

    if (root == null) {
      root = newNode;
      return;
    }
    if (root.is_complete) {
      // Insert Node at the Left Most Point
      insertLeftMostNode(root, newNode);
      // heapify(root);
      swapParentNode(newNode, newNode.parent);
    } else {
      // Find Location
      insertTargetNode(root, newNode);
      // heapify(root);
      swapParentNode(newNode, newNode.parent);
    }
  }

  public ArrayList<Integer> deleteMax() throws Exception {
    /* 
		   1. Remove nodes with the maximum value in the heap and returns their keys.
		   2. There could be multiple nodes having same maximum value. You have
		      to delete all such nodes and return all such keys in ArrayList (order doesn't matter).
		   3. If heap is empty, throw NullRootException.

		   Expected Average Time Complexity : O(logn).
		*/

    ArrayList<Integer> max_keys = new ArrayList<Integer>(); // Keys with maximum values in heap that will be deleted.

    // To be filled in by the student

    if (root == null) {
      throw new Exception(NullRootException);
    }

    if (root.left == null && root.right == null) {
      max_keys.add(root.key);
      root = null;
      nodes_array[root.key] = null;
      return max_keys;
    }

    int val = root.value;
    while (root.value == val) {
      max_keys = deleteMaxUtil(max_keys);
      if (root == null) {
        break;
      }
      if (root.left == null && root.right == null) {
        max_keys.add(root.key);
        nodes_array[root.key] = null;
        root = null;
        break;
      }
    }

    return max_keys;
  }

  public void update(int key, int diffvalue) throws Exception {
    /* 
		   1. Update the heap by changing the value of the node whose key is "key" to value+diffvalue.
		   2. If key doesn't exists in heap, throw NullKeyException.

		   Expected Time Complexity : O(logn).
		*/

    // To be filled in by the student
    if (nodes_array[key] == null) {
      throw new Exception(NullKeyException);
    }
    nodes_array[key].value += diffvalue;
    heapify(root);
  }

  public int getMaxValue() throws Exception {
    /* 
		   1. Returns maximum value in the heap.
		   2. If heap is empty, throw NullRootException.

		   Expected Time Complexity : O(1).
		*/

    // To be filled in by the student
    if (root == null) {
      throw new Exception(NullRootException);
    }
    return root.value;
  }

  public ArrayList<Integer> getKeys() throws Exception {
    /*
		   1. Returns keys of the nodes stored in heap.
		   2. If heap is empty, throw NullRootException.
		 
		   Expected Time Complexity : O(n).
		*/

    ArrayList<Integer> keys = new ArrayList<Integer>(); // Stores keys of nodes in heap

    // To be filled in by the student
    if (root == null) {
      throw new Exception(NullRootException);
    }
    for (int i = 0; i < nodes_array.length; i++) {
      if (nodes_array[i] != null) {
        keys.add(nodes_array[i].key);
      }
    }
    return keys;
    // return getKeysUtil(root, keys);
  }
  // Write helper functions(if any) here (They have to be private).

  // ------------------------------------
  // Developer Functions
  // private void preorder(Node root) {
  //   if (root == null) {
  //     System.out.print(-1 + " ");
  //     return;
  //   }

  //   System.out.print(root.value + " ");
  //   preorder(root.left);
  //   preorder(root.right);
  // }

  // private void preorderv(Node root) {
  //   if (root == null) {
  //     System.out.print("");
  //     return;
  //   }

  //   System.out.print(root.value + ",");
  //   preorderv(root.left);
  //   preorderv(root.right);
  // }

  // private void preorderk(Node root) {
  //   if (root == null) {
  //     System.out.print(-1 + "\t");
  //     return;
  //   }

  //   System.out.print(root.key + "\t");
  //   preorderk(root.left);
  //   preorderk(root.right);
  // }

  // private void preorderh(Node root) {
  //   if (root == null) {
  //     System.out.print(-1 + "\t");
  //     return;
  //   }

  //   System.out.print(root.height + "\t");
  //   preorderh(root.left);
  //   preorderh(root.right);
  // }

  // private void preorderb(Node root) {
  //   if (root == null) {
  //     System.out.print(-1 + "\t");
  //     return;
  //   }

  //   System.out.print(root.is_complete + "\t");
  //   preorderb(root.left);
  //   preorderb(root.right);
  // }

  // private void printKeyValuePairs() {
  //   for (int i = 0; i < nodes_array.length; i++) {
  //     if (nodes_array[i] != null) {
  //       System.out.print(nodes_array[i].key + "\t");
  //       System.out.println(nodes_array[i].value + "\t");
  //     }
  //   }
  // }

  // ------------------------------------
}
