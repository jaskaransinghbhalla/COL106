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

// Students can define new classes here

class Graph {

  private int V;

  public static ArrayList<Integer>[] adjacencylist;
  private int time = 0;
  private ArrayList<Integer> bridgearr;
  private ArrayList<Integer>[] dfsTree;
  int[][] lcaparent;
  int[] level;
  int log;
  int numVertices;
  boolean[] lcavisitarr;
  static int[][] dp;
  static int logn, n;

  public Graph(int v) {
    this.V = v;
    adjacencylist = new ArrayList[v];
    dfsTree = new ArrayList[v];
    for (int i = 0; i <= v - 1; ++i) {
      adjacencylist[i] = new ArrayList<>();
      dfsTree[i] = new ArrayList<>();
    }
    bridgearr = new ArrayList<>();
    level = new int[numVertices];
    lcavisitarr = new boolean[numVertices];
    lcaparent = new int[numVertices][log + 1];
  }

  public void edgeplusfn(int a, int b) {
    adjacencylist[a].add(b);
    adjacencylist[b].add(a);
  }

  private int minfn(int a, int b) {
    if (a > b) {
      return b;
    }
    return a;
  }

  private void dfsfn(
    int firstancestor[],
    int u,
    boolean visitarr[],
    int vect[],
    int low[]
  ) {
    visitarr[u] = true;

    vect[u] = low[u] = ++time;

    for (int v : adjacencylist[u]) {
      if (!visitarr[v]) {
        firstancestor[v] = u;
        dfsfn(firstancestor, v, visitarr, vect, low);
        low[u] = minfn(low[u], low[v]);
        if (low[v] > vect[u]) {
          bridgearr.add(u);
          bridgearr.add(v);
          // bridgepowerarr.add();
          // bridgepowerarr.add(v);
          System.out.println(u + " " + v);
        }
      } else if (v != firstancestor[u]) {
        low[u] = minfn(low[u], vect[v]);
      }
    }
  }

  public ArrayList<Integer> bridgefn() {
    int firstancestor[] = new int[V];
    int vect[] = new int[V];
    int low[] = new int[V];

    boolean visitarr[] = new boolean[V];

    for (int i = 0; i <= V - 1; i++) {
      visitarr[i] = false;
      firstancestor[i] = -1;
    }

    for (int i = 0; i <= V - 1; i++) {
      if (visitarr[i] != true) {
        dfsfn(firstancestor, i, visitarr, vect, low);
      }
    }

    return bridgearr;
  }

  private void dfstree(int u, boolean[] visited, int[] parent) {
    visited[u] = true;

    for (int v : adjacencylist[u]) {
      if (!visited[v]) {
        parent[v] = u;
        dfsTree[u].add(v);
        dfstree(v, visited, parent);
      }
    }
  }

  public void printdfstreefn() {
    for (int i = 0; i < dfsTree.length; i++) {
      System.out.print(i + " : ");
      for (int j = 0; j < dfsTree[i].size(); j++) {
        System.out.print(dfsTree[i].get(j) + " ");
      }
      System.out.println();
    }
  }

  public void dfstreecaller() {
    int firstancestor[] = new int[V];
    boolean visitarr[] = new boolean[V];

    for (int i = 0; i <= V - 1; i++) {
      visitarr[i] = false;
      firstancestor[i] = -1;
    }
    dfstree(0, visitarr, firstancestor);
    n = numVertices;
    logn = (int) Math.ceil(Math.log(n) / Math.log(2));
    dp = new int[n][logn + 1];
    dfs(0, 0, 0, level); // root is assumed to be 0
  }

  void dfs(int u, int p, int l, int[] level) {
    dp[u][0] = p;
    level[u] = l;
    for (int i = 1; i <= logn; i++) {
      dp[u][i] = dp[dp[u][i - 1]][i - 1];
    }
    for (int v : dfsTree[u]) {
      if (v != p) {
        dfs(v, u, l + 1, level);
      }
    }
  }

  int lca(int u, int v) {
    if (level[u] < level[v]) {
      int temp = u;
      u = v;
      v = temp;
    }
    for (int i = logn; i >= 0; i--) {
      if (level[u] - (1 << i) >= level[v]) {
        u = dp[u][i];
      }
    }
    if (u == v) {
      return u;
    }
    for (int i = logn; i >= 0; i--) {
      if (dp[u][i] != dp[v][i]) {
        u = dp[u][i];
        v = dp[v][i];
      }
    }
    return dp[u][0];
  }

  int distance(int u, int v) {
    int p = lca(u, v);
    return level[u] + level[v] - 2 * level[p];
  }

  public void fn2() {}
}

public class PowerGrid {

  int numCities;
  int numLines;
  String[] cityNames;
  PowerLine[] powerLines;

  // Students can define private variables here

  HashMap<String, Integer> grapher = new HashMap<String, Integer>();
  HashMap<Integer, String> rgrapher = new HashMap<Integer, String>();
  HashMap<Integer, Integer> implines;
  HashMap<String, Boolean> isBridge;
  int[] level;

  Graph p;

  public PowerGrid(String filename) throws Exception {
    implines = new HashMap<>();

    isBridge = new HashMap<>();
    File file = new File(filename);
    BufferedReader br = new BufferedReader(new FileReader(file));
    numCities = Integer.parseInt(br.readLine());
    numLines = Integer.parseInt(br.readLine());
    cityNames = new String[numCities];

    p = new Graph(numCities);

    for (int i = 0; i < numCities; i++) {
      cityNames[i] = br.readLine();
      grapher.put(cityNames[i], i);
      rgrapher.put(i, cityNames[i]);
    }

    powerLines = new PowerLine[numLines];

    for (int i = 0; i < numLines; i++) {
      String[] line = br.readLine().split(" ");
      powerLines[i] = new PowerLine(line[0], line[1]);

      p.edgeplusfn(grapher.get(line[0]), grapher.get(line[1]));
    }
    for (int i = 0; i < powerLines.length; i++) {
      isBridge.put(powerLines[i].cityA + "" + powerLines[i].cityB, false);
      isBridge.put(powerLines[i].cityB + "" + powerLines[i].cityA, false);
    }
    // p.dfstreecaller();

    // TO be completed by students
  }

  public ArrayList<PowerLine> criticalLines() {


    ArrayList<Integer> bridgepower = p.bridgefn();
    ArrayList<PowerLine> pwrl = new ArrayList<>();

    if (bridgepower.size() > 0) {
      for (int i = 0; i < bridgepower.size() - 1; i++) {
        pwrl.add(
          new PowerLine(
            rgrapher.get(bridgepower.get(i)),
            rgrapher.get(bridgepower.get(i + 1))
          )
        );
        i = i + 1;
      }
    }

    return pwrl;
  }

  private void dfsImportantLines() {
    boolean visited[] = new boolean[numCities];
    implines.put(0, 0);
    dfsImportantLinesUtil(visited, 0, -1);
  }

  private void dfsImportantLinesUtil(boolean[] visited, int node, int parent) {
    if (!visited[node]) {
      visited[node] = true;
      if (isBridge.get(rgrapher.get(node) + rgrapher.get(parent))) {
        implines.put(node, implines.get(parent) + 1);
      } else {
        implines.put(node, implines.get(parent));
      }
      for (int u : Graph.adjacencylist[node]) {
        dfsImportantLinesUtil(visited, u, node);
      }
    }
  }

  public void preprocessImportantLines() {
    ArrayList<PowerLine> lines = criticalLines();
    for (PowerLine powerLine : lines) {
      isBridge.replace(powerLine.cityA + powerLine.cityB, true);
      isBridge.replace(powerLine.cityB + powerLine.cityA, true);
    }
    p.dfstreecaller();
    dfsImportantLines();
    return;
  }

  public int numImportantLines(String cityA, String cityB) {
    int numA = grapher.get(cityA);
    int numB = grapher.get(cityB);
    int x = p.lca(numA, numB);
    int ans = implines.get(numA) + implines.get(numB) - implines.get(x) * 2;
    return ans;
  }

  public static void main(String[] args) throws Exception {
    PowerGrid p = new PowerGrid("input.txt");
    p.numImportantLines("Chennai", "Delhi");
  }
}
