// Q2 Iterative Insert

// public void insert(int num) {
// BSTNode p1 = root;
// BSTNode p2 = null;

// while (p1 != null) {
//   p2 = p1;
//   if (num < p1.value) {
//     p1 = p1.left;
//     newNode.height++;
//   } else {
//     p1 = p1.right;
//     newNode.height++;
//   }
// }
// if (p2 == null) {
//   root = newNode;
//   return;
// } else if (num < p2.value) {
//   p2.left = newNode;
// } else {
//   p2.right = newNode;
// }
// }

// Search Iterative

//   public boolean search(int num) {
//     if(root == null ){
//         return false;
//     }

//     BSTNode p1 = root;

//     while (p1 != null) {
//       if (num == p1.value) {
//         return true;
//       }
//       if (num < p1.value) {
//         p1 = p1.left;
//       } else {
//         p1 = p1.right;
//       }
//     }
//     return false;
//   }

// Search Recurssive
// public boolean search(int num) {
//     if(root == null ){
//         return false;
//     }

//     while (root != null) {
//       if (num == root.value) {
//         return true;
//       }
//       else if (num < root.value){
//         BSTNode temp = root;
//         root = root.left;
//         boolean ans = search(num);
//         root = temp;
//         return ans;
//       } else {
//         BSTNode temp = root;
//         root = root.right;
//         boolean ans = search(num);
//         root = temp;
//         return ans;
//       }
//     }
//     return false;
//   }


// Delete Iterative
// public boolean delete(int num) {
//     System.out.println("delete");
//     // Modify
//     BSTNode p1 = root;
//     BSTNode p2 = null;

//     if (root == null) {
//       return false;
//     }
//     // Searching for position of Node to be Deleted
//     while (p1 != null && p1.value != num) {
//       p2 = p1;
//       if (num < p1.value) {
//         p1 = p1.left;
//       } else {
//         p1 = p1.right;
//       }
//     }

//     // Node not found
//     if (p1 == null) {
//       return false;
//     }

//     // 1 Child
//     if (p1.left == null || p1.right == null) {
//       BSTNode temp = null;

//       if (p1.left == null) {
//         temp = p1.right;
//       }
//       if (p1.right == null) {
//         temp = p1.left;
//       }
//       // Root Node
//       if (p2 == null) {
//         root = temp;
//         return true;
//       }

//       //   Left or Right Child
//       if (p1 == p2.left) {
//         p2.left = temp;
//       } else if (p1 == p2.right) {
//         p2.right = temp;
//       }
//     }
//     // 2 Children
//     else if (p1.left != null && p1.right != null) {
//       BSTNode p = null;
//       BSTNode c = p1.right;

//       //   Computing the Left Most Node in the Right Subtree
//       while (c.left != null) {
//         p = c;
//         c = c.left;
//       }

//       //   Replacing the Current Node Value with Inorder Successor
//       //   int t = c.value;
//       //   delete(c.value);
//       //   p1.value = t;

//       if (p != null) {
//         p.left = c.right;
//       } else {
//         p1.right = c.right;
//       }
//       p1.value = c.value;
//       return true;
//     }
//     return false;
//   }