import java.util.*;
public class BST {

    public BSTNode root;

    public BST() {
        root = null;
    }

    public void insert(int num) {
        // TO be completed by students
        insert_r(root, null, num);
    }

    public boolean delete(int num) {
        // TO be completed by students
        return delete_r(root, null, num);
    }

    public boolean search(int num) {
        // TO be completed by students
        boolean set = false;
        if (root != null) {
            BSTNode t = root;
            while (t != null) {
                if (num > t.value) {
                    t = t.right;
                } else if (num < t.value) {
                    t = t.left;
                } else {
                    set = true;
                    break;
                }
            }
        }
        return set;
    }
public void insert_r(BSTNode x, BSTNode y, int num) {
    if (x == null) {
        if (y == null) {
            root = new BSTNode(num);
        } else {
            if (num > y.value) {
                y.right = new BSTNode(num);
                return;
            } else {
                y.left = new BSTNode(num);
                return;
            }
        }
    } else {
        if (num > x.value) {
            insert_r(x.right, x, num);
        } else {
            insert_r(x.left, x, num);
        }
        int left = (x.left == null) ? 0 : x.left.height;
        int right = (x.right == null) ? 0 : x.right.height;
        x.height = (left > right) ? (left + 1) : (right + 1);
    }
}
public boolean delete_r(BSTNode x, BSTNode y, int num) {
    boolean set = false;
    if (x == null) {
        return false;
    } else {
        if (num > x.value) {
            set = delete_r(x.right, x, num);
            int left = (x.left == null) ? 0 : x.left.height;
            int right = (x.right == null) ? 0 : x.right.height;
            x.height = (left > right) ? (left + 1) : (right + 1);
        } else if (num < x.value) {
            set = delete_r(x.left, x, num);
            int left = (x.left == null) ? 0 : x.left.height;
            int right = (x.right == null) ? 0 : x.right.height;
            x.height = (left > right) ? (left + 1) : (right + 1);
        } else {
            set = true;
            if (y == null) {
                if (x.left == null) {
                    root = x.right;
                } else if (x.right == null) {
                    root = x.left;
                } else {
                    BSTNode a = x.left;
                    while (a.right!=null) {
                        a = a.right;
                    }
                    int k = a.value;
                    delete_r(root, null, k);
                    root.value = k;
                }
            } else {
                if (x.left == null) {
                    if (y.right == x) {
                        y.right = x.right;
                    } else {
                        y.left = x.right;
                    }
                } else if (x.right == null) {
                    if (y.right == x) {
                        y.right = x.left;
                    } else {
                        y.left = x.left;
                    }
                } else {
                    BSTNode a = x.left;
                    while (a.right!=null) {
                        a = a.right;
                    }
                    int k = a.value;
                    delete_r(x, y, k);
                    x.value = k;
                }
            }
        }
    }
    return set;
}
    public ArrayList<Integer> inorder() {
        // TO be completed by students
        ArrayList<Integer> al = new ArrayList<>();
        al = inor(root);
        return al;
    }
    ArrayList<Integer> inor(BSTNode x) {
        ArrayList<Integer> k = new ArrayList<>();
        if (x == null) {
            return k;
        }
        ArrayList<Integer> y = new ArrayList<>();
        if (x.left != null) {
            y = inor(x.left);
        }
        for (Integer j: y) {
            k.add(j);
        }
        if (x!=null) {
            k.add(x.value);
        }
        ArrayList<Integer> z = new ArrayList<>();
        if (x.right!=null) {
            z = inor(x.right);
        }
        for (Integer j: z) {
            k.add(j);
        }
        return k;
    }
    ArrayList<Integer> postor(BSTNode x) {
        ArrayList<Integer> k = new ArrayList<>();
        if (x == null) {
            return k;
        }
        ArrayList<Integer> y = new ArrayList<>();
        if (x.left != null) {
            y = postor(x.left);
        }
        for (Integer j: y) {
            k.add(j);
        }
        ArrayList<Integer> z = new ArrayList<>();
        if (x.right!=null) {
            z = postor(x.right);
        }
        for (Integer j: z) {
            k.add(j);
        }
        if (x!=null) {
            k.add(x.value);
        }
        return k;
    }
    ArrayList<Integer> preor(BSTNode x) {
        ArrayList<Integer> k = new ArrayList<>();
        if (x == null) {
            return k;
        }
        k.add(x.value);
        ArrayList<Integer> y = new ArrayList<>();
        if (x.left != null) {
            y = preor(x.left);
        }
        for (Integer j: y) {
            k.add(j);
        }
        ArrayList<Integer> z = new ArrayList<>();
        if (x.right!=null) {
            z = preor(x.right);
        }
        for (Integer j: z) {
            k.add(j);
        }
        return k;
    }
    public ArrayList<Integer> preorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
        al = preor(root);
		return al;
    }

    public ArrayList<Integer> postorder() {
        // TO be completed by students
		ArrayList<Integer> al = new ArrayList<>();
        al = postor(root);
		return al;
    }
}