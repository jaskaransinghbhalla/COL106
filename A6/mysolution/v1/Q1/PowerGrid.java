import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

class PowerLine {

  String cityA;
  String cityB;

  public PowerLine(String cityA, String cityB) {
    this.cityA = cityA;
    this.cityB = cityB;
  }
}

// Students can define new classes here
class TreeNode {

  TreeNode parent;
  String cityName;
  ArrayList<TreeNode> children;
  int level;
  int highPoint;

  public TreeNode(String cityName, int level, TreeNode parent) {
    this.parent = parent;
    this.cityName = cityName;
    this.level = level;
    children = new ArrayList<>();
    this.highPoint = level;
  }
}

public class PowerGrid {

  int numCities;
  int numLines;
  String[] cityNames;
  PowerLine[] powerLines;

  // Students can define private variables here
  private TreeNode DFSroot;
  private HashMap<String, Boolean> visited;
  private HashMap<String, ArrayList<String>> graph;
  private HashMap<String, TreeNode> nodeAddresses;

  public PowerGrid(String filename) throws Exception {
    // Initliasing Global Variables

    graph = new HashMap<>();
    visited = new HashMap<>();
    nodeAddresses = new HashMap<>();

    File file = new File(filename);
    BufferedReader br = new BufferedReader(new FileReader(file));
    numCities = Integer.parseInt(br.readLine());
    numLines = Integer.parseInt(br.readLine());
    cityNames = new String[numCities];
    for (int i = 0; i < numCities; i++) {
      cityNames[i] = br.readLine();
    }
    powerLines = new PowerLine[numLines];
    for (int i = 0; i < numLines; i++) {
      String[] line = br.readLine().split(" ");
      powerLines[i] = new PowerLine(line[0], line[1]);
    }

    // TO be completed by students
    createGraph();
    DFS();
    highPoint();
  }

  public ArrayList<PowerLine> criticalLines() {
    /*
         * Implement an efficient algorithm to compute the critical transmission lines
         * in the power grid.
         
         * Expected running time: O(m + n), where n is the number of cities and m is the
         * number of transmission lines.
         */
    ArrayList<PowerLine> result = new ArrayList<PowerLine>();
    for (PowerLine powerLine : powerLines) {
      if (nodeAddresses.get(powerLine.cityB).parent != null) {
        if (
          nodeAddresses.get(powerLine.cityA) ==
          nodeAddresses.get(powerLine.cityB).parent
        ) {
          TreeNode node = nodeAddresses.get(powerLine.cityB);
          if (node.highPoint >= node.level) {
            result.add(powerLine);
          }
        }
      } else if (nodeAddresses.get(powerLine.cityA).parent != null) {
        if (
          nodeAddresses.get(powerLine.cityB) ==
          nodeAddresses.get(powerLine.cityA).parent
        ) {
          TreeNode node = nodeAddresses.get(powerLine.cityA);
          if (node.highPoint >= node.level) {
            result.add(powerLine);
          }
        }
      }
    }
    return result;
  }

  public void preprocessImportantLines() {
    /*
         * Implement an efficient algorithm to preprocess the power grid and build
         * required data structures which you will use for the numImportantLines()
         * method. This function is called once before calling the numImportantLines()
         * method. You might want to define new classes and data structures for this
         * method.
         
         * Expected running time: O(n * logn), where n is the number of cities.
         */
        ArrayList<PowerLine> bridges = criticalLines(); 

    return;
  }

  public int numImportantLines(String cityA, String cityB) {
    /*
         * Implement an efficient algorithm to compute the number of important
         * transmission lines between two cities. Calls to numImportantLines will be
         * made only after calling the preprocessImportantLines() method once.
         
         * Expected running time: O(logn), where n is the number of cities.
         */
    return 0;
  }

  // Helper Functions
  private void createGraph() {
    // Creation of Graph

    for (PowerLine line : powerLines) {
      // Neighbour of First City
      if (graph.get(line.cityA) != null) {
        ArrayList<String> neighbours = graph.get(line.cityA);
        neighbours.add(line.cityB);
        graph.replace(line.cityA, neighbours);
      } else {
        ArrayList<String> neighbours = new ArrayList<>();
        neighbours.add(line.cityB);
        graph.put(line.cityA, neighbours);
        visited.put(line.cityA, false);
      }

      // Neighbour of Second City
      if (graph.get(line.cityB) != null) {
        ArrayList<String> neighbours = graph.get(line.cityB);
        neighbours.add(line.cityA);
        graph.replace(line.cityB, neighbours);
      } else {
        ArrayList<String> neighbours = new ArrayList<>();
        neighbours.add(line.cityA);
        graph.put(line.cityB, neighbours);
        visited.put(line.cityB, false);
      }
    }
  }

  private void visit(String u) {
    visited.replace(u, true);
  }

  private void DFS() {
    DFSUtil(cityNames[0], null);
  }

  private void DFSUtil(String s, TreeNode parent) {
    // Visit
    visit(s);

    TreeNode temp;
    // Node Creation
    if (parent != null) {
      temp = new TreeNode(s, parent.level + 1, parent);
      parent.children.add(temp);
      nodeAddresses.put(s, temp);
    } else {
      temp = new TreeNode(s, 0, null);
      nodeAddresses.put(s, temp);
      DFSroot = temp;
    }

    ArrayList<String> Neighbours = graph.get(s);
    for (String string : Neighbours) {
      if (!visited.get(string)) {
        DFSUtil(string, temp);
      }
    }
  }

  private void highPoint() {
    evaluateHighPoint(DFSroot);
  }

  private void evaluateHighPoint(TreeNode DFSroot) {
    ArrayList<TreeNode> children = DFSroot.children;

    if (children.size() == 0) {
      String current = DFSroot.cityName;
      int hp = DFSroot.highPoint;
      ArrayList<String> neighbours = graph.get(current);
      for (String neighbour : neighbours) {
        if (nodeAddresses.get(neighbour) != DFSroot.parent) {
          hp = Math.min(nodeAddresses.get(neighbour).level, hp);
        }
      }
      DFSroot.highPoint = hp;
      return;
    }

    for (TreeNode child : children) {
      evaluateHighPoint(child);
    }
    int hp = DFSroot.highPoint;
    String current = DFSroot.cityName;
    ArrayList<String> neighbours = graph.get(current);
    for (String neighbour : neighbours) {
      if (nodeAddresses.get(neighbour) != DFSroot.parent) {
        hp = Math.min(nodeAddresses.get(neighbour).level, hp);
      }
    }
    for (TreeNode child : children) {
      hp = Math.min(child.highPoint, hp);
    }
    DFSroot.highPoint = hp;
    return;
  }

  // Developer Functions
  public static void main(String[] args) throws Exception {
    PowerGrid obj = new PowerGrid("input2.txt");
    // ArrayList<PowerLine> result = obj.criticalLines();
    // for (PowerLine powerLine : result) {
    //   System.out.println(powerLine.cityA + " " + powerLine.cityB);
    // }
  }
}
