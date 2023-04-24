import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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
  ArrayList<PowerLine> edges;

  public TreeNode(String cityName, int level, TreeNode parent) {
    this.parent = parent;
    this.cityName = cityName;
    this.level = level;
    this.children = new ArrayList<>();
    this.highPoint = level;
    this.edges = new ArrayList<>();
  }
}

public class PowerGrid {

  int numCities;
  int numLines;
  String[] cityNames;
  PowerLine[] powerLines;

  // Students can define private variables here
  private TreeNode DFSroot;
  private TreeNode BFSroot;
  private HashMap<String, Boolean> visitedDFS;
  private HashMap<String, Boolean> visitedBFS;
  private HashMap<String, ArrayList<String>> graph;
  private HashMap<String, TreeNode> DFSNodeAddresses;
  private HashMap<String, TreeNode> BFSNodeAddresses;

  public PowerGrid(String filename) throws Exception {
    // Initliasing Global Variables

    graph = new HashMap<>();
    visitedDFS = new HashMap<>();
    visitedBFS = new HashMap<>();
    DFSNodeAddresses = new HashMap<>();
    BFSNodeAddresses = new HashMap<>();

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
      if (DFSNodeAddresses.get(powerLine.cityB).parent != null) {
        if (
          DFSNodeAddresses.get(powerLine.cityA) ==
          DFSNodeAddresses.get(powerLine.cityB).parent
        ) {
          TreeNode node = DFSNodeAddresses.get(powerLine.cityB);
          if (node.highPoint >= node.level) {
            result.add(powerLine);
          }
        }
      } else if (DFSNodeAddresses.get(powerLine.cityA).parent != null) {
        if (
          DFSNodeAddresses.get(powerLine.cityB) ==
          DFSNodeAddresses.get(powerLine.cityA).parent
        ) {
          TreeNode node = DFSNodeAddresses.get(powerLine.cityA);
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

    BFS(); // O(m+n)
    return;
  }

  private void BFS() {
    Queue<String> q = new LinkedList<>();
    q.add(cityNames[0]);
    TreeNode root = new TreeNode(cityNames[0], 0, null);
    BFSNodeAddresses.put(cityNames[0], root);
    visitBFS(cityNames[0]);
    BFSroot = root;

    while (!q.isEmpty()) {
      String start = q.remove();
      ArrayList<String> neighbours = graph.get(start);
      for (String neighbour : neighbours) {
        if (!visitedBFS.get(neighbour)) {
          q.add(neighbour);
          visitBFS(neighbour);

          insertBFSTree(neighbour, BFSNodeAddresses.get(start));
        }
      }
    }
  }

  private void insertBFSTree(String node, TreeNode parent) {
    TreeNode n = new TreeNode(node, parent.level + 1, parent);
    if (parent.edges.size() != 0) {
      n.edges.addAll(parent.edges);
    }
    n.edges.add(new PowerLine(parent.cityName, node));
    parent.children.add(n);
    BFSNodeAddresses.put(node, n);
  }

  public int numImportantLines(String cityA, String cityB) {
    /*
         * Implement an efficient algorithm to compute the number of important
         * transmission lines between two cities. Calls to numImportantLines will be
         * made only after calling the preprocessImportantLines() method once.
         
         * Expected running time: O(logn), where n is the number of cities.
         */
    ArrayList<PowerLine> bridges = criticalLines();
    ArrayList<PowerLine> cityAPath = BFSNodeAddresses.get(cityA).edges;
    ArrayList<PowerLine> cityBPath = BFSNodeAddresses.get(cityB).edges;
    cityAPath = intersection(bridges, cityAPath);
    cityBPath = intersection(bridges, cityBPath);
    ArrayList<PowerLine> common = intersection(cityAPath, cityBPath);
    int answer = cityAPath.size() + cityBPath.size() - 2 * common.size();
    return answer;
  }

  private ArrayList<PowerLine> intersection(
    ArrayList<PowerLine> list1,
    ArrayList<PowerLine> list2
  ) {
    ArrayList<PowerLine> list = new ArrayList<PowerLine>();

    for (PowerLine t1 : list1) {
      for (PowerLine t2 : list2) {
        if (isEqualLine(t1, t2)) {
          list.add(t1);
        }
      }
    }

    return list;
  }

  private boolean isEqualLine(PowerLine line1, PowerLine line2) {
    return (
      (line1.cityA.equals(line2.cityA) && line1.cityB.equals(line2.cityB)) ||
      (line1.cityA.equals(line2.cityB) && line1.cityB.equals(line2.cityA))
    );
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
        visitedDFS.put(line.cityA, false);
        visitedBFS.put(line.cityA, false);
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
        visitedDFS.put(line.cityB, false);
        visitedBFS.put(line.cityB, false);
      }
    }
  }

  private void visitDFS(String u) {
    visitedDFS.replace(u, true);
  }

  private void visitBFS(String u) {
    visitedBFS.replace(u, true);
  }

  private void DFS() {
    DFSUtil(cityNames[0], null);
  }

  private void DFSUtil(String s, TreeNode parent) {
    // Visit
    visitDFS(s);

    TreeNode temp;
    // Node Creation
    if (parent != null) {
      temp = new TreeNode(s, parent.level + 1, parent);
      parent.children.add(temp);
      DFSNodeAddresses.put(s, temp);
    } else {
      temp = new TreeNode(s, 0, null);
      DFSNodeAddresses.put(s, temp);
      DFSroot = temp;
    }

    ArrayList<String> Neighbours = graph.get(s);
    for (String string : Neighbours) {
      if (!visitedDFS.get(string)) {
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
        if (DFSNodeAddresses.get(neighbour) != DFSroot.parent) {
          hp = Math.min(DFSNodeAddresses.get(neighbour).level, hp);
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
      if (DFSNodeAddresses.get(neighbour) != DFSroot.parent) {
        hp = Math.min(DFSNodeAddresses.get(neighbour).level, hp);
      }
    }
    for (TreeNode child : children) {
      hp = Math.min(child.highPoint, hp);
    }
    DFSroot.highPoint = hp;
    return;
  }

  // Developer Functions
  // public static void main(String[] args) throws Exception {
  //   PowerGrid obj = new PowerGrid("input2.txt");
  //   obj.preprocessImportantLines();
  //   System.out.println(obj.numImportantLines("D", "E"));
  //   System.out.println(obj.numImportantLines("K", "N"));
  //   System.out.println(obj.numImportantLines("H", "O"));
  //   System.out.println(obj.numImportantLines("G", "J"));
  //   // ArrayList<PowerLine> result = obj.criticalLines();
  //   // for (PowerLine powerLine : result) {
  //   //   System.out.println(powerLine.cityA + " " + powerLine.cityB);
  //   // }
  // }
}
