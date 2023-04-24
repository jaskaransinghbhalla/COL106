// Developer Functions
public void printer() {
    if (root == null) {
      return;
    }
    Queue<PointQuadtreeNode> queue = new LinkedList<>();
    queue.add(root);
    int nodesInCurrentLayer = 1;
    int nodesInNextLayer = 0;
    while (!queue.isEmpty()) {
      PointQuadtreeNode node = queue.remove();
      nodesInCurrentLayer--;
      System.out.print("(" + node.celltower.x + ", " + node.celltower.y + ")");
      System.out.println("");

      if (node.quadrants != null) {
        for (int i = 0; i < 4; i++) {
          if (node.quadrants[i] == null) {
            System.out.print(i + ":null" + "\t");
          }
          if (node.quadrants[i] != null) {
            System.out.print(
              i +
              ":(" +
              node.quadrants[i].celltower.x +
              ", " +
              node.quadrants[i].celltower.y +
              ")\t"
            );
            queue.add(node.quadrants[i]);
            nodesInNextLayer++;
          }
        }
        System.out.print("\t");
        System.out.println();
      }

      if (nodesInCurrentLayer == 0) {
        System.out.println();
        nodesInCurrentLayer = nodesInNextLayer;
        nodesInNextLayer = 0;
      }
    }
  }