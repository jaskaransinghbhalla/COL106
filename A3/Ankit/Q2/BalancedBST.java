public class BalancedBST extends BST {
    public void insert_r(BSTNode x, BSTNode y, int num) {//recursive helper function for insertion
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
            }//update heights after recursive inner function ends
            int left = (x.left == null) ? 0 : x.left.height;
            int right = (x.right == null) ? 0 : x.right.height;
            x.height = (left > right) ? (left + 1) : (right + 1);
            if ((left - right > 1)||((left - right <-1))) {
                balance(x, y);//balance (recursive check from bottom to top)
            }
        }
    }
    public boolean delete_r(BSTNode x, BSTNode y, int num) {//recursive helper function for deletion
        boolean set = false;
        if (x == null) {
            return false;
        } else {
            if (num > x.value) {//height updation and balancing after recursive inner function
                set = delete_r(x.right, x, num);
                int left = (x.left == null) ? 0 : x.left.height;
                int right = (x.right == null) ? 0 : x.right.height;
                x.height = (left > right) ? (left + 1) : (right + 1);
                if ((left - right > 1)||((left - right <-1))) {
                    balance(x, y);
                }
            } else if (num < x.value) {
                set = delete_r(x.left, x, num);
                int left = (x.left == null) ? 0 : x.left.height;
                int right = (x.right == null) ? 0 : x.right.height;
                x.height = (left > right) ? (left + 1) : (right + 1);
                if ((left - right > 1)||((left - right <-1))) {
                    balance(x, y);
                }
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
    public void insert(int key){
        // TO be completed by students
        insert_r(root, null, key);
    }

void balance (BSTNode x, BSTNode y) {//balancing helper function
    int left = (x.left == null) ? 0 : x.left.height;
    int right = (x.right == null) ? 0 : x.right.height;
    if (left - right > 1) {
        //rotate, update heights
        BSTNode t = x.left.right;
        int lr = (x.left.right == null) ? 0 : x.left.right.height;
        int ll = (x.left.left == null) ? 0 : x.left.left.height;
        int k = 0; //locating changes
        if (lr > ll) {//LR imbalance
            if (y != null) {
                if (y.left == x) {
                    k = 0;
                    y.left = t;
                } else {
                    k = 1;
                    y.right = t;
                }
                x.left.right = t.left;
                t.left = x.left;
                x.left = t.right;
                t.right = x;
                if (k == 1) {
                    int rrr = (y.right.right.right == null) ? 0 : y.right.right.right.height;
                    int rrl = (y.right.right.left == null) ? 0 : y.right.right.left.height;
                    int rlr = (y.right.left.right == null) ? 0 : y.right.left.right.height;
                    int rll = (y.right.left.left == null) ? 0 : y.right.left.left.height;
                    y.right.left.height = (rlr > rll) ? (rlr+1) : (rll + 1);
                    y.right.right.height = (rrr > rrl) ? (rrr+1) : (rrl+1);
                    y.right.height = (y.right.right.height > y.right.left.height) ? (y.right.right.height+1) : (y.right.left.height+1);
                } else {
                    int lll = (y.left.left.left == null) ? 0 : y.left.left.left.height;
                    int llr = (y.left.left.right == null) ? 0 : y.left.left.right.height;
                    int lrl = (y.left.right.left == null) ? 0 : y.left.right.left.height;
                    int lrr = (y.left.right.right == null) ? 0 : y.left.right.right.height;
                    y.left.left.height = (llr > lll) ? (llr +1) : (lll+1);
                    y.left.right.height = (lrr > lrl) ? (lrr + 1) : (lrl + 1);
                    y.left.height = (y.left.right.height > y.left.left.height) ? (y.left.right.height + 1) : (y.left.left.height + 1);
                }
            } else {
                x.left.right = t.left;
                t.left = x.left;
                x.left = t.right;
                t.right = x;
                root = t;
                int ll1 = (root.left.left == null) ? 0 : (root.left.left.height);
                int lr1 = (root.left.right == null) ? 0 : (root.left.right.height);
                int rr1 = (root.right.right == null) ? 0 : (root.right.right.height);
                int rl1 = (root.right.left == null) ? 0 : (root.right.left.height);
                root.left.height = (ll1>lr1) ? (ll1+1) : (lr1+1);
                root.right.height = (rl1>rr1) ? (rl1+1) : (rr1+1);
                root.height = (root.left.height > root.right.height) ? (root.left.height+1) : (root.right.height + 1);
            }
        } else {//LL imbalance
            if (y != null) {
                if (y.left == x) {
                    k = 0;
                    y.left = x.left;
                    y.left.right = x;
                } else {
                    k = 1;
                    y.right = x.left;
                    y.right.right = x;
                }
                x.left = t;
                if (k == 1) {
                    int rrr = (y.right.right.right == null) ? 0 : y.right.right.right.height;
                    int rrl = (y.right.right.left == null) ? 0 : y.right.right.left.height;
                    int rlr = (y.right.left.right == null) ? 0 : y.right.left.right.height;
                    int rll = (y.right.left.left == null) ? 0 : y.right.left.left.height;
                    y.right.left.height = (rlr > rll) ? (rlr+1) : (rll + 1);
                    y.right.right.height = (rrr > rrl) ? (rrr+1) : (rrl+1);
                    y.right.height = (y.right.right.height > y.right.left.height) ? (y.right.right.height+1) : (y.right.left.height+1);
                } else {
                    int lll = (y.left.left.left == null) ? 0 : y.left.left.left.height;
                    int llr = (y.left.left.right == null) ? 0 : y.left.left.right.height;
                    int lrl = (y.left.right.left == null) ? 0 : y.left.right.left.height;
                    int lrr = (y.left.right.right == null) ? 0 : y.left.right.right.height;
                    y.left.left.height = (llr > lll) ? (llr +1) : (lll+1);
                    y.left.right.height = (lrr > lrl) ? (lrr + 1) : (lrl + 1);
                    y.left.height = (y.left.right.height > y.left.left.height) ? (y.left.right.height + 1) : (y.left.left.height + 1);
                }
            } else {
                root = x.left;
                x.left = t;
                root.right = x;
                int ll1 = (root.left.left == null) ? 0 : (root.left.left.height);
                int lr1 = (root.left.right == null) ? 0 : (root.left.right.height);
                int rr1 = (root.right.right == null) ? 0 : (root.right.right.height);
                int rl1 = (root.right.left == null) ? 0 : (root.right.left.height);
                root.left.height = (ll1>lr1) ? (ll1+1) : (lr1+1);
                root.right.height = (rl1>rr1) ? (rl1+1) : (rr1+1);
                root.height = (root.left.height > root.right.height) ? (root.left.height+1) : (root.right.height + 1);
            }
        }
    } else if (left - right < -1) {
        //rotate, update heights
        BSTNode t = x.right.left;
        int rl = (x.right.left == null) ? 0 : x.right.left.height;
        int rr = (x.right.right == null) ? 0 : x.right.right.height;
        int k = 0;
        if (rl > rr) {//RL imbalance
            if (y != null) {
                if (y.left == x) {
                    k = 0;
                    y.left = t;
                } else {
                    k = 1;
                    y.right = t;
                }
                x.right.left = t.right;
                t.right = x.right;
                x.right = t.left;
                t.left = x;
                if (k == 1) {
                    int rrr = (y.right.right.right == null) ? 0 : y.right.right.right.height;
                    int rrl = (y.right.right.left == null) ? 0 : y.right.right.left.height;
                    int rlr = (y.right.left.right == null) ? 0 : y.right.left.right.height;
                    int rll = (y.right.left.left == null) ? 0 : y.right.left.left.height;
                    y.right.left.height = (rlr > rll) ? (rlr+1) : (rll + 1);
                    y.right.right.height = (rrr > rrl) ? (rrr+1) : (rrl+1);
                    y.right.height = (y.right.right.height > y.right.left.height) ? (y.right.right.height+1) : (y.right.left.height+1);
                } else {
                    int lll = (y.left.left.left == null) ? 0 : y.left.left.left.height;
                    int llr = (y.left.left.right == null) ? 0 : y.left.left.right.height;
                    int lrl = (y.left.right.left == null) ? 0 : y.left.right.left.height;
                    int lrr = (y.left.right.right == null) ? 0 : y.left.right.right.height;
                    y.left.left.height = (llr > lll) ? (llr +1) : (lll+1);
                    y.left.right.height = (lrr > lrl) ? (lrr + 1) : (lrl + 1);
                    y.left.height = (y.left.right.height > y.left.left.height) ? (y.left.right.height + 1) : (y.left.left.height + 1);
                }
            } else {
                x.right.left = t.right;
                t.right = x.right;
                x.right = t.left;
                t.left = x;
                root = t;
                int ll1 = (root.left.left == null) ? 0 : (root.left.left.height);
                int lr1 = (root.left.right == null) ? 0 : (root.left.right.height);
                int rr1 = (root.right.right == null) ? 0 : (root.right.right.height);
                int rl1 = (root.right.left == null) ? 0 : (root.right.left.height);
                root.left.height = (ll1>lr1) ? (ll1+1) : (lr1+1);
                root.right.height = (rl1>rr1) ? (rl1+1) : (rr1+1);
                root.height = (root.left.height > root.right.height) ? (root.left.height+1) : (root.right.height + 1);
            }
        } else {//RR imbalance
            if (y != null) {
                if (y.left == x) {
                    k = 0;
                    y.left = x.right;
                    y.left.left = x;
                } else {
                    k = 1;
                    y.right = x.right;
                    y.right.left = x;
                }
                x.right = t;
                if (k == 1) {
                    int rrr = (y.right.right.right == null) ? 0 : y.right.right.right.height;
                    int rrl = (y.right.right.left == null) ? 0 : y.right.right.left.height;
                    int rlr = (y.right.left.right == null) ? 0 : y.right.left.right.height;
                    int rll = (y.right.left.left == null) ? 0 : y.right.left.left.height;
                    y.right.left.height = (rlr > rll) ? (rlr+1) : (rll + 1);
                    y.right.right.height = (rrr > rrl) ? (rrr+1) : (rrl+1);
                    y.right.height = (y.right.right.height > y.right.left.height) ? (y.right.right.height+1) : (y.right.left.height+1);
                } else {
                    int lll = (y.left.left.left == null) ? 0 : y.left.left.left.height;
                    int llr = (y.left.left.right == null) ? 0 : y.left.left.right.height;
                    int lrl = (y.left.right.left == null) ? 0 : y.left.right.left.height;
                    int lrr = (y.left.right.right == null) ? 0 : y.left.right.right.height;
                    y.left.left.height = (llr > lll) ? (llr +1) : (lll+1);
                    y.left.right.height = (lrr > lrl) ? (lrr + 1) : (lrl + 1);
                    y.left.height = (y.left.right.height > y.left.left.height) ? (y.left.right.height + 1) : (y.left.left.height + 1);
                }
            } else {
                root = x.right;
                x.right = t;
                root.left = x;
                int ll1 = (root.left.left == null) ? 0 : (root.left.left.height);
                int lr1 = (root.left.right == null) ? 0 : (root.left.right.height);
                int rr1 = (root.right.right == null) ? 0 : (root.right.right.height);
                int rl1 = (root.right.left == null) ? 0 : (root.right.left.height);
                root.left.height = (ll1>lr1) ? (ll1+1) : (lr1+1);
                root.right.height = (rl1>rr1) ? (rl1+1) : (rr1+1);
                root.height = (root.left.height > root.right.height) ? (root.left.height+1) : (root.right.height + 1);
            }
        }
    }
}
    public boolean delete(int key){
        // TO be completed by students
        return delete_r(root, null, key);
    }
}