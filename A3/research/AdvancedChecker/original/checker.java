import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.FileWriter;
// import java.io.PrintWriter;
import packagee.*;
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
public class checker {

    public static void main(String[] args) {
        try {
            System.out.println("input file loading file");
            String filename = "skiplistinput.txt"; // replace with the desired filename
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            ArrayList<String> ans=new ArrayList<>();
            int x=0;
            long startTime = System.currentTimeMillis();
            System.out.println("total test cases 99");
             String filename1= "skiplistoutput.txt"; // replace with the desired filename
            BufferedReader reader1 = new BufferedReader(new FileReader(filename1));
            String linee;
            int passed=0;
            int failed=0;
            while ((line = reader.readLine()) != null && (linee = reader1.readLine()) != null) {
                if(x==99){
                    break;
                }
                String strr=linee;
                long a = System.currentTimeMillis();
                String str=line;
                str=str.substring(1, str.length() - 1); // remove square brackets
                String[] arr = str.split(", ");
                ArrayList<Integer> list = new ArrayList<>();
                for (String s : arr) {
                    list.add(Integer.parseInt(s));
                }
                try{
                    System.out.println("length of "+(x+1) + "th testcase: " +arr.length);
                    SkipList obj=new SkipList();
                    for (Integer s : list) {
                        obj.insert(s);
                    }
                    Randomizer rd= new Randomizer();
                    for (Integer s : list) {
                        if(rd.binaryRandomGen()==true){
                            obj.delete(s);
                        }
                    }
                    if(obj.print().equals(strr)){
                    System.out.println();
                    System.out.println((x+1)+"th test case passed");
                    passed++;
                }else{
                    System.out.println();
                    System.out.println((x+1)+"th test case failed");
                    System.out.println();
                    failed++;
                    // break;
                }
                }catch(Exception e){
                    System.out.println("there is an error in the code in this test cases");
                    ans.add("wrong ans");
                    System.out.println(e.getMessage());
                }
                long b = System.currentTimeMillis();
                System.out.println("time for checking "+(x+1)+"th testcase is "+(b-a)+" mili second");
                System.out.println();
                x++;
            }
            reader.close();
            System.out.println("output file loading");
            reader1.close();
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            System.out.println();
            System.out.println("Total passed testcases: "+passed);
            System.out.println("Total failed testcase: "+failed);
            System.out.println("Total Elapsed time: " + elapsedTime/1000 + " ssecond");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}