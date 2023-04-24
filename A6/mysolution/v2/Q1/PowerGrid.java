import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

class PowerLine {

  String cityA;
  String cityB;

  public PowerLine(String cityA, String cityB) {
    this.cityA = cityA;
    this.cityB = cityB;
  }
}

class TreeNode {

  TreeNode parent;
  String cityName;
  ArrayList<TreeNode> children;
  int level;
  int highPoint;
  ArrayList<PowerLine> edges;
  int noOfImpLines;

  public TreeNode(String cityName, int level, TreeNode parent) {
    this.parent = parent;
    this.cityName = cityName;
    this.level = level;
    this.children = new ArrayList<>();
    this.highPoint = level;
    this.edges = new ArrayList<>();
    this.noOfImpLines = 0;
  }
}

public class PowerGrid {

  int numCities;
  int numLines;
  String[] cityNames;
  PowerLine[] powerLines;

  private TreeNode DFSroot;
  private HashMap<String, Boolean> visitedDFS;
  private HashMap<String, Boolean> visitedEvaluateImportantLines;
  private HashMap<String, Boolean> visitedDFS3;
  private HashMap<PowerLine, Boolean> isBridgeEdge;
  private HashMap<String, ArrayList<String>> graph;
  private HashMap<String, String[]> dp;
  private HashMap<String, TreeNode> DFSNodeAddresses;
  private HashMap<String, PowerLine> powerLinesCities;

  public PowerGrid(String filename) throws Exception {
    graph = new HashMap<>();
    visitedDFS = new HashMap<>();
    visitedEvaluateImportantLines = new HashMap<>();
    visitedDFS3 = new HashMap<>();
    DFSNodeAddresses = new HashMap<>();
    isBridgeEdge = new HashMap<>();
    powerLinesCities = new HashMap<>();
    dp = new HashMap<>();
    int log = 30;

    File file = new File(filename);
    BufferedReader br = new BufferedReader(new FileReader(file));
    numCities = Integer.parseInt(br.readLine());
    numLines = Integer.parseInt(br.readLine());
    cityNames = new String[numCities];

    for (int i = 0; i < numCities; i++) {
      cityNames[i] = br.readLine();
      dp.put(cityNames[i], new String[log]);
    }
    powerLines = new PowerLine[numLines];
    for (int i = 0; i < numLines; i++) {
      String[] line = br.readLine().split(" ");
      powerLines[i] = new PowerLine(line[0], line[1]);
      isBridgeEdge.put(powerLines[i], false);
      powerLinesCities.put(line[0] + line[1], powerLines[i]);
      powerLinesCities.put(line[1] + line[0], powerLines[i]);
    }

    br.close();

    createGraph();
    DFS();
    highPoint();
  }

  public ArrayList<PowerLine> criticalLines() {
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
            isBridgeEdge.replace(powerLine, true);
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
            isBridgeEdge.replace(powerLine, true);
          }
        }
      }
    }
    return result;
  }

  public void preprocessImportantLines() {
    criticalLines();
    evaluateImportantLines();
    calculateAncestors(DFSroot.cityName, null);
    return;
  }

  public int numImportantLines(String cityA, String cityB) {
    TreeNode A = DFSNodeAddresses.get(cityA);
    TreeNode B = DFSNodeAddresses.get(cityB);
    TreeNode LCA = DFSNodeAddresses.get(LCA(cityA, cityB));
    int ans = A.noOfImpLines + B.noOfImpLines - 2 * LCA.noOfImpLines;
    return ans;
  }

  private void createGraph() {
    for (PowerLine line : powerLines) {
      if (graph.get(line.cityA) != null) {
        ArrayList<String> neighbours = graph.get(line.cityA);
        neighbours.add(line.cityB);
        graph.replace(line.cityA, neighbours);
      } else {
        ArrayList<String> neighbours = new ArrayList<>();
        neighbours.add(line.cityB);
        graph.put(line.cityA, neighbours);
        visitedDFS.put(line.cityA, false);
        visitedEvaluateImportantLines.put(line.cityA, false);
        visitedDFS3.put(line.cityA, false);
      }

      if (graph.get(line.cityB) != null) {
        ArrayList<String> neighbours = graph.get(line.cityB);
        neighbours.add(line.cityA);
        graph.replace(line.cityB, neighbours);
      } else {
        ArrayList<String> neighbours = new ArrayList<>();
        neighbours.add(line.cityA);
        graph.put(line.cityB, neighbours);
        visitedDFS.put(line.cityB, false);
        visitedEvaluateImportantLines.put(line.cityB, false);
        visitedDFS3.put(line.cityB, false);
      }
    }
  }

  private void visitDFS(String u) {
    visitedDFS.replace(u, true);
  }

  private void visitevaluateImportantLines(String u) {
    visitedEvaluateImportantLines.replace(u, true);
  }

  private void visitDFS3(String u) {
    visitedDFS3.replace(u, true);
  }

  private void DFS() {
    DFSUtil(cityNames[0], null);
  }

  private void DFSUtil(String s, TreeNode parent) {
    visitDFS(s);

    TreeNode temp;

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

  private void evaluateImportantLines() {
    ArrayList<PowerLine> bridges = criticalLines();
    dp.put(cityNames[0], null);
    evaluateImportantLinesUtil(cityNames[0], bridges);
  }

  private void insert(String i, int j, String parent) {
    String[] parents = dp.get(i);
    if (parents == null) return;
    if (parents[j] == null) {
      parents[j] = parent;
      dp.replace(i, parents);
    }
  }

  private void calculateAncestors(String s, String parent) {
    int log = 20;
    visitDFS3(s);

    ArrayList<String> Neighbours = graph.get(s);

    for (String string : Neighbours) {
      if (!visitedDFS3.get(string)) {
        int level = level(string);

        insert(string, 0, s);

        for (int i = 1; i < log && i < level; i++) {
          if (dp.get(string) == null) {
            break;
          }
          String u = dp.get(string)[i - 1];

          if (dp.get(u) == null) {
            break;
          }
          String p = dp.get(u)[i - 1];
          insert(string, i, p);
        }
        calculateAncestors(string, s);
      }
    }
  }

  private void evaluateImportantLinesUtil(
    String s,
    ArrayList<PowerLine> bridges
  ) {
    visitevaluateImportantLines(s);

    TreeNode node = DFSNodeAddresses.get(s);

    if (node.parent != null) {
      int var = node.parent.noOfImpLines;

      String hash = s + node.parent.cityName;
      PowerLine p = powerLinesCities.get(hash);
      if (isBridgeEdge.get(p)) {
        var = var + 1;
      }
      node.noOfImpLines = var;
    }

    ArrayList<String> Neighbours = graph.get(s);
    for (String string : Neighbours) {
      if (!visitedEvaluateImportantLines.get(string)) {
        evaluateImportantLinesUtil(string, bridges);
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

  private String LCA(String a, String b) {
    int log = 20;
    if (level(a) < level(b)) {
      String temp = a;
      a = b;
      b = temp;
    }

    for (int i = log - 1; i >= 0; i--) {
      int k = level(a) - level(b);
      if ((k >= (1 << i))) {
        a = dp.get(a)[i];
      }
    }
    if (a.equals(b)) {
      return a;
    }

    for (int i = log - 1; i >= 0; i--) {
      if (dp.get(a)[i] != dp.get(b)[i]) {
        a = dp.get(a)[i];
        b = dp.get(b)[i];
      }
    }
    return dp.get(a)[0];
  }

  private int level(String a) {
    return DFSNodeAddresses.get(a).level;
  }
}
