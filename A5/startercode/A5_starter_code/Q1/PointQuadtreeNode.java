class CellTower {
    int x;
    int y;
    int cost;

    public CellTower(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }

    public double distance(int x, int y) {
        // TO be completed by students
        // Make use of this in chooseCellTower in PointQuadtree.java
        return 0;
    }
}

public class PointQuadtreeNode {

    public CellTower celltower;
    public PointQuadtreeNode[] quadrants;

    public PointQuadtreeNode(CellTower a) {
        this.celltower = a;
        this.quadrants = new PointQuadtreeNode[4];
    }

}
