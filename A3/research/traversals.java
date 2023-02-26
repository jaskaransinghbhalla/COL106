public class traversals {
  //   public ArrayList<Integer> inorder() {
  //     if (root == null) {
  //       return new ArrayList<>();
  //     }
  //     ArrayList<Integer> al = new ArrayList<>();

  //     BSTNode temp = root;

  //     root = root.left;
  //     ArrayList<Integer> left = inorder();
  //     for (int i = 0; i < left.size(); i++) {
  //       al.add(left.get(i));
  //     }
  //     root = temp;

  //     al.add(root.value);

  //     root = root.right;
  //     ArrayList<Integer> right = inorder();
  //     for (int i = 0; i < right.size(); i++) {
  //       al.add(right.get(i));
  //     }
  //     root = temp;

  //     return al;
  //   }

  //   public ArrayList<Integer> preorder() {
  //     if (root == null) {
  //       return new ArrayList<>();
  //     }
  //     ArrayList<Integer> al = new ArrayList<>();

  //     BSTNode temp = root;

  //     al.add(root.value);

  //     root = root.left;
  //     ArrayList<Integer> left = preorder();
  //     root = temp;
  //     for (int i = 0; i < left.size(); i++) {
  //       al.add(left.get(i));
  //     }

  //     root = root.right;
  //     ArrayList<Integer> right = preorder();
  //     root = temp;
  //     for (int i = 0; i < right.size(); i++) {
  //       al.add(right.get(i));
  //     }

  //     return al;
  //   }

  //   public ArrayList<Integer> postorder() {
  //     if (root == null) {
  //       return new ArrayList<>();
  //     }
  //     ArrayList<Integer> al = new ArrayList<>();

  //     BSTNode temp = root;

  //     root = root.left;
  //     ArrayList<Integer> left = postorder();
  //     root = temp;
  //     for (int i = 0; i < left.size(); i++) {
  //       al.add(left.get(i));
  //     }

  //     root = root.right;
  //     ArrayList<Integer> right = postorder();
  //     root = temp;
  //     for (int i = 0; i < right.size(); i++) {
  //       al.add(right.get(i));
  //     }

  //     al.add(root.value);

  //     return al;
  //   }

}
