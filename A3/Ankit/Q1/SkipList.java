import java.util.ArrayList;
import java.util.Collections;

public class SkipList {

        public SkipListNode head;
        public SkipListNode tail;
        public int height;
        public Randomizer randomizer;
        private final int NEG_INF = Integer.MIN_VALUE;
        private final int POS_INF = Integer.MAX_VALUE;

        SkipList(){
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
            SkipListNode d = this.head;
            int i = this.height - 1;
            boolean set = false;
            while((d != this.tail) && (i >= 0)) {
                if (d.next.get(i).value > num) {
                    i -= 1;
                } else if (d.next.get(i).value == num)  {
                    set = true;
                    if (i == 0) {//d has value less than num so if next at 0th level is num, it means that is first occurrence of num
                        d = d.next.get(i);//Now, d stores first instance of num in SkipList
                    }
                        i -= 1;//ends traversal when i = 0 changes to i = -1
                } else {
                    d = d.next.get(i);
                }
            }
        if (d.value == num) {
            set = true;
            SkipListNode e = this.head;
            int j = this.height - 1;
            while((e != this.tail) && (j >= 0)) {
                if (e.next.get(j) == d)  {
                    e.next.set(j, e.next.get(j).next.get(j));
                    j -= 1;//done to verify if it is the first instance
                } else if (e.next.get(j).value >= num) {
                    j -= 1;
                } else {
                    e = e.next.get(j);
                }
            }
            while ((this.height > 1) && (this.head.next.get(this.height - 1) == tail)) {
                this.head.next.remove(this.height - 1);
                this.head.height -= 1;
                this.tail.next.remove(this.height - 1);
                this.tail.height -= 1;
                this.height -= 1;
            }
        }
            return set;
        }

        public boolean search(int num){
            // TO be completed by students
            SkipListNode d = this.head;
            int i = this.height - 1;
            while((d != this.tail) && (i >= 0)) {
            if (d.next.get(i).value > num) {
                i -= 1;
            } else if (d.next.get(i).value == num)  {
                return true;
            } else {
                d = d.next.get(i);
            }
        }
            return false;
        }

        public Integer upperBound(int num){ 
            // TO be completed by students
            SkipListNode d = this.head;
            int i = this.height - 1;
            while((d.next.get(0) != this.tail) && (i >= 0)) {
            if (d.next.get(i).value > num) {
                    i -= 1;
            } else {
                d = d.next.get(i);
            }
        }
            return d.next.get(0).value;
        }

        public void insert(int num){
            // TO be completed by students
            int h = 1;
            while (this.randomizer.binaryRandomGen()) {
                h += 1;
                if (h > this.height) {
                    this.head.next.add(this.tail);
                    this.tail.next.add(null);
                    this.head.height += 1;
                    this.tail.height += 1;
                    this.height += 1;
                    break;
                }
            }
            SkipListNode d = this.head;
            int i = this.height - 1;
            SkipListNode f = new SkipListNode(num, h);
            while((d != this.tail) && (i>=0)) {
                if (d.next.get(i).value >= num) {
                    if (i < h) {
                    f.next.add(0, d.next.get(i));
                    d.next.set(i, f);
                    }
                i -= 1;
            } else {
                d = d.next.get(i);
            }
        }
        }

        public void print(){
            /*
            * DO NOT EDIT THIS FUNCTION
            */
            for(int i = this.height ; i>=1; --i){
                SkipListNode it = this.head;
                while(it!=null){
                    if(it.height >= i){
                        System.out.print(it.value + "\t");
                    }
                    else{
                        System.out.print("\t");
                    }
                    it = it.next.get(0);
                }
                System.out.println("null");
            }
        }
}