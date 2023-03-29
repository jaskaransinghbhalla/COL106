/*
 * DO NOT EDIT THIS FILE
 */

import java.util.ArrayList;

public class TreeNode{

    /*
    * Class for node of a Tree.
    */
    public ArrayList<String> s;
    public ArrayList<Integer> val;
    public int height;
    public ArrayList<TreeNode> children;
    public int count;
    public String max_s;
    public int max_value;

    public TreeNode(){
        /*
        * DO NOT EDIT THIS FUNCTION
        */
        this.s = new ArrayList<String>();
        this.val = new ArrayList<Integer>();
        this.height = 1;
        this.children = new ArrayList<TreeNode>();
        this.count = 0;
        this.max_s = "";
        this.max_value = 0;
    }
}
