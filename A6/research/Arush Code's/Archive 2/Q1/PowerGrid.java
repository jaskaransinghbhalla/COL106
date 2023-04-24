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

class Graph {
    private int V;

    private ArrayList<Integer>[] adjacencylist;
    private int time = 0;
    private ArrayList<Integer> bridgearr;
    private ArrayList<String> bridgepowerarr;
    private ArrayList<Integer>[] dfsTree;
    private ArrayList<Integer>[] bridgeadj;
    private int[] numofbridge;

    int[][] lcaparent;
    int[] level;
    int log;
    int numVertices;
    boolean[] lcavisitarr;

    static int[][] dp;

    static int logn, n;

    // int[] level;

    public Graph(int v) {
        this.V = v;
        adjacencylist = new ArrayList[v];
        dfsTree = new ArrayList[v];
        bridgeadj = new ArrayList[v];
        for (int i = 0; i <= v - 1; ++i) {
            adjacencylist[i] = new ArrayList<>();
            dfsTree[i] = new ArrayList<>();
            bridgeadj[i] = new ArrayList<>();
        }
        bridgearr = new ArrayList<>();
        level = new int[numVertices];
        lcavisitarr = new boolean[numVertices];
        lcaparent = new int[numVertices][log + 1];
        numofbridge = new int[v];
    }

    // public void printfn() {
    // for (int i = 0; i < adjacencylist.length; i++) {
    // System.out.print(i + " : ");
    // for (int j = 0; j < adjacencylist[i].size(); j++) {
    // System.out.print(adjacencylist[i].get(j) + " ");
    // }
    // System.out.println();
    // }
    // }

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

    private void dfsfn(int firstancestor[], int u, boolean visitarr[], int vect[], int low[]) {
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

        // for (int i = 0; i <= V - 1; i++) {
        // if (visitarr[i] != true) {
        dfstree(0, visitarr, firstancestor);
        // }
        // }

        printdfstreefn();

    }

    private void bridgedfstree(int u, boolean[] visited, int[] parent) {
        visited[u] = true;
        numofbridge[u] = 0;

        for (int v : adjacencylist[u]) {
            if (!visited[v]) {
                parent[v] = u;

                if (bridgeadj[u].contains(v)) {
                    numofbridge[v] = numofbridge[u] + 1;
                }

                dfsTree[u].add(v);
                bridgedfstree(v, visited, parent);
            }
        }

    }

    public void bridgedfstreecaller() {

        int firstancestor[] = new int[V];

        boolean visitarr[] = new boolean[V];

        ArrayList<Integer> temp = bridgefn();

        if (temp.size() > 0) {
            for (int i = 0; i < temp.size() - 1; i++) {
                bridgeadj[i].add(i + 1);
                bridgeadj[i + 1].add(i);
                i = i + 1;
            }
        }

        for (int i = 0; i <= V - 1; i++) {
            visitarr[i] = false;
            firstancestor[i] = -1;
            numofbridge[i] = -1;
        }

        for (int i = 0; i <= V - 1; i++) {
            if (visitarr[i] != true) {
                bridgedfstree(0, visitarr, firstancestor);
            }
        }
        // printdfstreefn();
    }

    // fn 3

    void dfs(int u, int p, int l) {
        dp[u][0] = p;
        level[u] = l;
        for (int i = 1; i <= logn; i++) {
            dp[u][i] = dp[dp[u][i - 1]][i - 1];
        }
        for (int v : dfsTree[u]) {
            if (v != p) {
                dfs(v, u, l + 1);
            }
        }
    }

    public int lca(int u, int v) {
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

    public int distance(int u, int v) {
        int p = lca(u, v);
        return numofbridge[u] + numofbridge[v] - 2 * numofbridge[p];
    }

    public void fn2() {
        n = numVertices;

        logn = (int) Math.ceil(Math.log(n) / Math.log(2));
        dp = new int[n][logn + 1];
        level = new int[n];

        dfs(0, 0, 0); // root is assumed to be 0
    }

}

public class PowerGrid {
    int numCities;
    int numLines;
    String[] cityNames;
    PowerLine[] powerLines;

    // Students can define private variables here

    HashMap<String, Integer> grapher = new HashMap<String, Integer>();
    HashMap<Integer, String> rgrapher = new HashMap<Integer, String>();
    Graph p;

    public PowerGrid(String filename) throws Exception {

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

        // p.dfstreecaller();

        // TO be completed by students
    }

    public ArrayList<PowerLine> criticalLines() {
        /*
         * Implement an efficient algorithm to compute the critical transmission lines
         * in the power grid.
         * 
         * Expected running time: O(m + n), where n is the number of cities and m is the
         * number of transmission lines.
         */

        System.out.println("critical lines called");

        ArrayList<Integer> bridgepower = p.bridgefn();
        ArrayList<PowerLine> pwrl = new ArrayList<>();

        if (bridgepower.size() > 0) {
            for (int i = 0; i < bridgepower.size() - 1; i++) {
                pwrl.add(new PowerLine(rgrapher.get(bridgepower.get(i)), rgrapher.get(bridgepower.get(i + 1))));
                i = i + 1;
            }
        }

        return pwrl;
    }

    public void preprocessImportantLines() {
        /*
         * Implement an efficient algorithm to preprocess the power grid and build
         * required data structures which you will use for the numImportantLines()
         * method. This function is called once before calling the numImportantLines()
         * method. You might want to define new classes and data structures for this
         * method.
         * 
         * Expected running time: O(n * logn), where n is the number of cities.
         */

        System.out.println("preprocess imp lines called");

        p.dfstreecaller();
        p.bridgedfstreecaller();
        p.fn2();

        // p.lcaprocesscaller();
        System.out.println("preprocess imp lines called ENDD");
        return;
    }

    public int numImportantLines(String cityA, String cityB) {
        /*
         * Implement an efficient algorithm to compute the number of important
         * transmission lines between two cities. Calls to numImportantLines will be
         * made only after calling the preprocessImportantLines() method once.
         * 
         * Expected running time: O(logn), where n is the number of cities.
         */

        int numA = grapher.get(cityA);
        int numB = grapher.get(cityB);

        int x = p.distance(numA, numB);

        return x;
    }


}