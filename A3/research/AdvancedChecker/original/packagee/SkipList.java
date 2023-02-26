package packagee;

import java.util.*;
class Randomizer {
    /*
    * An object randomizer of class Randomizer has been initialized in the SkipList class. It has a function binaryRandomGen, which returns true or false in a (pseudo) random manner. While inserting a node, you should initialize its height to 1, and using randomizer.binaryRandomGen(), you should iteratively check whether its height should be increased. If the node already has a height larger than the skip list height, stop and do not call the binaryRandomGen() again. Using this procedure is necessary, not adhering may cause inconsistency in output.
    */    
    private Random rnd;

    Randomizer(){
        rnd = new Random(106);
    }

    boolean binaryRandomGen(){
        return rnd.nextBoolean();
    }
}
class SkipListNode{
    public int value;
    public int height;
    public ArrayList<SkipListNode> next;

    SkipListNode(int key,int height){
        value = key;
        this.height = height;
        next = new ArrayList<SkipListNode>(height);
    }
}
public class SkipList {

        public SkipListNode head;
        public SkipListNode tail;
        public int height;
        public Randomizer randomizer;
        private final int NEG_INF = Integer.MIN_VALUE;
        private final int POS_INF = Integer.MAX_VALUE;
        
        public SkipList(){
            /*
            * DO NOT EDIT THIS FUNCTION
            */
            this.head = new SkipListNode(NEG_INF,1);
            this.tail = new SkipListNode(POS_INF,1);
            this.head.next.add(0,this.tail);
            this.tail.next.add(0,null);
            this.height = 1;
            this.randomizer = new Randomizer();
        }
        public boolean delete(int num){
            // TO be completed by students
            return false;
        }

        public boolean search(int num){
            // TO be completed by students
            return false;
        }

        public Integer upperBound(int num){ 
            // TO be completed by students           
            return Integer.MAX_VALUE;
        }

        public void insert(int num){
            // TO be completed by students
        }
        public String print(){
            StringBuilder sb = new StringBuilder();
            for(int i = this.height ; i>=1; --i){
                SkipListNode it = this.head.next.get(i-1);
                while(it!=this.tail){
                    sb.append(it.value);
                    sb.append("*");
                    it=it.next.get(i-1);
                }
                sb.append("&");
            }
            String str=sb.toString();
            return str;
        } 
}