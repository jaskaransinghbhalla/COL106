package heap_package;

public class Helper {
    // public class Queue {

  //   ArrayList<Node> list;
  //   Integer size;
  //   Integer front;
  //   Integer rear;

  //   Queue() {
  //     list = new ArrayList<>();
  //     rear = -1;
  //     front = -1;
  //   }

  //   public boolean isEmpty() {
  //     return rear == -1;
  //   }

  //   public void insert(Node data) {
  //     if (rear == -1) {
  //       front++;
  //       rear++;
  //       list.add(data);
  //       return;
  //     } else {
  //       rear++;
  //       list.add(data);
  //       return;
  //     }
  //   }

  //   public Node remove() {
  //     if (isEmpty()) {
  //       return null;
  //     } else {
  //       Node val = list.get(front);
  //       if (front == rear) {
  //         rear = -1;
  //         front = -1;
  //       } else {
  //         front++;
  //       }

  //       return val;
  //     }
  //   }

  //   public Node removeLast() {
  //     if (isEmpty()) {
  //       return null;
  //     } else {
  //       Node val = list.get(rear);
  //       if (front == rear) {
  //         rear = -1;
  //         front = -1;
  //       } else {
  //         rear--;
  //       }

  //       return val;
  //     }
  //   }

  //   public void printq() {
  //     System.out.println(list);
  //   }

  //   public Node peek() {
  //     return list.get(front);
  //   }
  // }

  // private void levelorder(Node root) {
  //   if (root == null) {
  //     return;
  //   }
  //   Queue q = new Queue();
  //   q.insert(root);
  //   q.insert(null);
  //   while (!q.isEmpty()) {
  //     Node currNode = q.remove();
  //     if (currNode == null) {
  //       System.out.println();
  //       if (q.isEmpty()) {
  //         break;
  //       } else {
  //         q.insert(null);
  //       }
  //     } else {
  //       System.out.print(currNode.value + " ");
  //       if (currNode.left != null) {
  //         q.insert(currNode.left);
  //       }
  //       if (currNode.right != null) {
  //         q.insert(currNode.right);
  //       }
  //     }
  //   }
  // }

  // private void insertlevelorder(int keys_array[], int values_array[])
  //   throws Exception {
  //   Queue q = new Queue();
  //   Queue q1 = new Queue();
  //   root = new Node(keys_array[0], values_array[0], null);
  //   q.insert(root);
  //   q1.insert(root);
  //   int i = 1;
  //   while (i < keys_array.length) {
  //     Node currNode = q.remove();

  //     if (currNode.left == null) {
  //       if (nodes_array[keys_array[i]] != null) {
  //         throw new Exception(KeyAlreadyExistsException);
  //       }
  //       Node newNode = new Node(keys_array[i], values_array[i], currNode);
  //       nodes_array[keys_array[i]] = newNode;
  //       currNode.left = newNode;
  //       q.insert(newNode);
  //       q1.insert(newNode);
  //       i++;
  //     }
  //     if (i >= keys_array.length) {
  //       break;
  //     }

  //     if (currNode.right == null) {
  //       if (nodes_array[keys_array[i]] != null) {
  //         throw new Exception(KeyAlreadyExistsException);
  //       }
  //       Node newNode = new Node(keys_array[i], values_array[i], currNode);
  //       nodes_array[keys_array[i]] = newNode;
  //       currNode.right = newNode;
  //       q.insert(newNode);
  //       q1.insert(newNode);
  //       i++;
  //     }
  //   }
  //   while (!q1.isEmpty()) {
  //     heapify(q1.removeLast());
  //   }
  // }
}
