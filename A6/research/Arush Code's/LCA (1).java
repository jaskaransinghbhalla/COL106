import java.util.ArrayList;

public class LCA {

    ArrayList<Integer>[] dfsTree;
    int[][] lcaparent;
    int[] lcadeptharr;
    int log;
    int numVertices;
    boolean[] lcavisitarr;

    void precomputefn(int var, int prim, int dnum) {
        lcavisitarr[var] = true;
        lcaparent[var][0] = prim;
        lcadeptharr[var] = dnum;

        for (int i = 1; i < log + 1; i++) {
            if (lcaparent[var][i - 1] != -1) {
                lcaparent[var][i] = lcaparent[lcaparent[var][i - 1]][i - 1];
            }
        }

        for (int v : dfsTree[var]) {
            if (!lcavisitarr[v]) {
                precomputefn(v, var, dnum + 1);
            }
        }
    }

    int lca(int num1, int num2) {
        if (lcadeptharr[num1] < lcadeptharr[num2]) {
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }

        for (int i = log; i > -1; i--) {
            if (lcadeptharr[num1] - (1 << i) >= lcadeptharr[num2]) {
                num1 = lcaparent[num1][i];
            }
        }

        if (num1 == num2) {
            return num1;
        }

        for (int i = log; i > -1; i--) {
            if (lcaparent[num1][i] != lcaparent[num2][i]) {
                num1 = lcaparent[num1][i];
                num2 = lcaparent[num2][i];
            }
        }

        return lcaparent[num1][0];
    }

    public void lcacaller(int u, int v) {
        numVertices = 7;
        log = (int) Math.ceil(Math.log(numVertices) / Math.log(2));

        lcadeptharr = new int[numVertices];
        lcavisitarr = new boolean[numVertices];
        lcaparent = new int[numVertices][log + 1];

        dfsTree = new ArrayList[numVertices];

        for (int i = 0; i <= numVertices - 1; i++) {
            dfsTree[i] = new ArrayList<>();
        }

        dfsTree[0].add(1);
        dfsTree[1].add(0);
        dfsTree[0].add(2);
        dfsTree[2].add(0);
        dfsTree[1].add(3);
        dfsTree[3].add(1);
        dfsTree[1].add(4);
        dfsTree[4].add(1);
        dfsTree[2].add(5);
        dfsTree[5].add(2);
        dfsTree[2].add(6);
        dfsTree[6].add(2);


        precomputefn(0, -1, 0);

        int lca = lca(u, v);
        System.out.println("LCA of " + u + " and " + v + " is " + lca);
    }

    public static void main(String[] args) throws Exception {
        LCA l = new LCA();
        l.lcacaller(3, 4);

    }
}
