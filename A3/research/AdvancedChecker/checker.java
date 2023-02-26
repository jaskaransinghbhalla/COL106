import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
// import java.io.PrintWriter;
import java.util.*;
// import java.io.PrintWriter;
import packagee.*;

class Randomizer {

  /*
   * An object randomizer of class Randomizer has been initialized in the SkipList class. It has a function binaryRandomGen, which returns true or false in a (pseudo) random manner. While inserting a node, you should initialize its height to 1, and using randomizer.binaryRandomGen(), you should iteratively check whether its height should be increased. If the node already has a height larger than the skip list height, stop and do not call the binaryRandomGen() again. Using this procedure is necessary, not adhering may cause inconsistency in output.
   */
  private Random rnd;

  Randomizer() {
    rnd = new Random(106);
  }

  boolean binaryRandomGen() {
    return rnd.nextBoolean();
  }
}

public class checker {

  public static void main(String[] args) {
    try {
      // Input Reader
      String inputFile = "skiplistinput.txt";
      BufferedReader inputReader = new BufferedReader(
        new FileReader(inputFile)
      );
      String inputLine;

      // Output Reader
      String outputFile = "skiplistoutput.txt"; // replace with the desired input
      BufferedReader outputReader = new BufferedReader(
        new FileReader(outputFile)
      );
      String outputLine;

      //   Time
      long startTime = System.currentTimeMillis();

      //   Ans
      ArrayList<String> ans = new ArrayList<>();

      // No of Testcases
      int x = 0;
      int passed = 0;
      int failed = 0;

      while (
        (inputLine = inputReader.readLine()) != null &&
        (outputLine = outputReader.readLine()) != null &&
        x != 99
      ) {
        String input = inputLine;
        String output = outputLine;
        long a = System.currentTimeMillis();

        input = input.substring(1, input.length() - 1); // remove square brackets
        String[] arr = input.split(", ");
        ArrayList<Integer> list = new ArrayList<>();

        for (String s : arr) {
          list.add(Integer.parseInt(s));
        }

        try {
          // System.out.println(
          //   "Length of " + (x + 1) + "th testcase: " + arr.length
          // );

          SkipList obj = new SkipList();

          for (Integer s : list) {
            obj.insert(s);
          }

          Randomizer rd = new Randomizer();
          for (Integer s : list) {
            if (rd.binaryRandomGen() == true) {
              obj.delete(s);
            }
          }

          if (obj.print().equals(output)) {
            System.out.println((x + 1) + "th test case Passed");
            passed++;
          } else {
            System.out.println((x + 1) + "th test case Failed");
            failed++;
          }
          FileWriter y = new FileWriter("./output/" + x + ".txt");
          y.write(obj.print());
          y.close();
        } catch (Exception e) {
          System.out.println("Error in Code");
          ans.add("Wrong Ans");
          System.out.println(e.getMessage());
        }

        long b = System.currentTimeMillis();
        System.out.println("Time : " + (b - a) + " ms");
        x++;
      }
      //   Reader Close
      inputReader.close();
      outputReader.close();

      //   Time Calculation
      long endTime = System.currentTimeMillis();
      long elapsedTime = endTime - startTime;

      System.out.println();

      //   Test Cases
      System.out.println("Total Passed testcases: " + passed);
      System.out.println("Total Failed testcase: " + failed);

      //   Time Print
      System.out.println(
        "Total Elapsed time: " + elapsedTime / 1000 + " Second"
      );
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
