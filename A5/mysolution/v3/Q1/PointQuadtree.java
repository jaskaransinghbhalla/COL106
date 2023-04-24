public class PointQuadtree {

  enum Quad {
    NW,
    NE,
    SW,
    SE,
  }

  public PointQuadtreeNode root;

  public PointQuadtree() {
    this.root = null;
  }

  public boolean insert(CellTower a) {
    return cellTowerAt(a.x, a.y) ? false : executeInsertion(a);
  }

  public boolean cellTowerAt(int x, int y) {
    return root == null ? false : cellTowerAtUtil(root, x, y);
  }

  public CellTower chooseCellTower(int x, int y, int r) {
    return chooseCellTowerUtil(root, x, y, r, null);
  }

  // Helper Functions
  private boolean executeInsertion(CellTower a) {
    if (root == null) {
      root = new PointQuadtreeNode(a);
    } else {
      insertUtil(root, new PointQuadtreeNode(a));
    }
    return true;
  }

  private void insertUtil(PointQuadtreeNode centre, PointQuadtreeNode a) {
    int q = quadUtil(centre.celltower, a.celltower);
    if (centre.quadrants[q] == null) {
      centre.quadrants[q] = a;
      return;
    }
    insertUtil(centre.quadrants[q], a);
  }

  private int quadUtil(CellTower centre, int x, int y) {
    return (x > centre.x)
      ? ((y > centre.y) ? 1 : 3)
      : ((x < centre.x) ? ((y < centre.y) ? 2 : 0) : (y > centre.y) ? 1 : 2);
  }

  private int quadUtil(CellTower centre, CellTower a) {
    return (a.x > centre.x)
      ? ((a.y > centre.y) ? 1 : 3)
      : (
        (a.x < centre.x) ? ((a.y < centre.y) ? 2 : 0) : (a.y > centre.y) ? 1 : 2
      );
  }

  private boolean cellTowerAtUtil(PointQuadtreeNode centre, int x, int y) {
    if (centre.celltower.x == x && centre.celltower.y == y) {
      return true;
    } else {
      return centre.quadrants[quadUtil(centre.celltower, x, y)] == null
        ? false
        : cellTowerAtUtil(
          centre.quadrants[quadUtil(centre.celltower, x, y)],
          x,
          y
        );
    }
  }

  private CellTower pointSelection(
    PointQuadtreeNode node,
    CellTower minCostTower,
    int x,
    int y,
    int r
  ) {
    if (minCostTower == null) {
      if (node.celltower.distance(x, y) <= r) {
        minCostTower = node.celltower;
      }
    } else {
      if (minCostTower.cost > node.celltower.cost) {
        minCostTower = node.celltower;
      }
    }
    return minCostTower;
  }

  private CellTower handleCase1(
    PointQuadtreeNode node,
    int x,
    int y,
    int r,
    CellTower minCostTower
  ) {
    // Case 1 Circle lies in 1 Quadrant
    int q = quadUtil(node.celltower, x, y);
    if (q == 1 && touchXAxis(node, x, y, r)) {
      PointQuadtreeNode SE_Node = node.quadrants[3];
      // New Addition
      if (SE_Node != null) {
        if (
          SE_Node.celltower.y == node.celltower.y && x == SE_Node.celltower.x
        ) {
          if (minCostTower == null) {
            if (SE_Node.celltower.distance(x, y) <= r) {
              minCostTower = SE_Node.celltower;
            }
          } else {
            if (minCostTower.cost > SE_Node.celltower.cost) {
              minCostTower = SE_Node.celltower;
            }
          }
        }
        CellTower temp1 = chooseCellTowerUtil(
          node.quadrants[Quad.SE.ordinal()],
          x,
          y,
          r,
          minCostTower
        );
        minCostTower = compareCellTower(minCostTower, temp1);
      }
    }
    if (q == 2 && touchXAxis(node, x, y, r)) {
      PointQuadtreeNode NW_Node = node.quadrants[0];

      if (NW_Node != null) {
        if (
          NW_Node.celltower.y == node.celltower.y && x == NW_Node.celltower.x
        ) {
          if (minCostTower == null) {
            if (NW_Node.celltower.distance(x, y) <= r) {
              minCostTower = NW_Node.celltower;
            }
          } else {
            if (minCostTower.cost > NW_Node.celltower.cost) {
              minCostTower = NW_Node.celltower;
            }
          }
        }
        // To Be Checked
        CellTower temp1 = chooseCellTowerUtil(
          node.quadrants[0],
          x,
          y,
          r,
          minCostTower
        );
        minCostTower = compareCellTower(minCostTower, temp1);
      }
    }
    if (q == 0 && touchYAxis(node, x, y, r)) {
      PointQuadtreeNode NE_Node = node.quadrants[2];
      if (NE_Node != null) {
        if (
          NE_Node.celltower.x == node.celltower.x && y == NE_Node.celltower.y
        ) {
          if (minCostTower == null) {
            if (NE_Node.celltower.distance(x, y) <= r) {
              minCostTower = NE_Node.celltower;
            }
          } else {
            if (minCostTower.cost > NE_Node.celltower.cost) {
              minCostTower = NE_Node.celltower;
            }
          }
        }
        CellTower temp1 = chooseCellTowerUtil(
          node.quadrants[Quad.NE.ordinal()],
          x,
          y,
          r,
          minCostTower
        );
        minCostTower = compareCellTower(minCostTower, temp1);
      }
    }
    if (q == 3 && touchYAxis(node, x, y, r)) {
      PointQuadtreeNode SW_Node = node.quadrants[2];
      if (SW_Node != null) {
        if (
          SW_Node.celltower.x == node.celltower.x && y == SW_Node.celltower.y
        ) {
          // Compare
          if (minCostTower == null) {
            if (SW_Node.celltower.distance(x, y) <= r) {
              minCostTower = SW_Node.celltower;
            }
          } else {
            if (minCostTower.cost > SW_Node.celltower.cost) {
              minCostTower = SW_Node.celltower;
            }
          }
        }
        CellTower temp1 = chooseCellTowerUtil(
          node.quadrants[Quad.SW.ordinal()],
          x,
          y,
          r,
          minCostTower
        );
        minCostTower = compareCellTower(minCostTower, temp1);
      }
    }

    // Quadrant Always
    if (node.quadrants[q] != null) {
      minCostTower =
        compareCellTower(
          minCostTower,
          chooseCellTowerUtil(node.quadrants[q], x, y, r, minCostTower)
        );
    }
    return minCostTower;
  }

  private CellTower handleCase2(
    PointQuadtreeNode node,
    int x,
    int y,
    int r,
    CellTower minCostTower
  ) {
    // Case 2
    // Circle Intersects Two Quadrants- NW-NE
    if (
      x - r < node.celltower.x &&
      node.celltower.x < x + r &&
      node.celltower.y <= y - r
    ) {
      if (node.celltower.y == y - r) {
        minCostTower = pointSelection(node, minCostTower, x, y, r);
      }
      minCostTower =
        compareCellTower(
          minCostTower,
          chooseCellTowerUtil(node.quadrants[0], x, y, r, minCostTower)
        );
      minCostTower =
        compareCellTower(
          minCostTower,
          chooseCellTowerUtil(node.quadrants[1], x, y, r, minCostTower)
        );
      return minCostTower;
    }
    // Circle Intersects Two Quadrants - SW-SE
    if (
      x - r < node.celltower.x &&
      node.celltower.x < x + r &&
      node.celltower.y >= y + r
    ) {
      if (node.celltower.y == y + r) {
        // Compare with node
        minCostTower = pointSelection(node, minCostTower, x, y, r);
      }
      CellTower temp;
      temp =
        chooseCellTowerUtil(
          node.quadrants[Quad.SW.ordinal()],
          x,
          y,
          r,
          minCostTower
        );
      minCostTower = compareCellTower(minCostTower, temp);
      temp =
        chooseCellTowerUtil(
          node.quadrants[Quad.SE.ordinal()],
          x,
          y,
          r,
          minCostTower
        );
      minCostTower = compareCellTower(minCostTower, temp);
      return minCostTower;
    }
    // Circle Intersects Two Quadrants - NE - SE
    if (
      y - r < node.celltower.y &&
      node.celltower.y < y + r &&
      node.celltower.x <= x - r
    ) {
      if (node.celltower.x == x - r) {
        minCostTower = pointSelection(node, minCostTower, x, y, r);
      }

      CellTower temp1 = chooseCellTowerUtil(
        node.quadrants[Quad.NE.ordinal()],
        x,
        y,
        r,
        minCostTower
      );
      minCostTower = compareCellTower(minCostTower, temp1);
      CellTower temp2 = chooseCellTowerUtil(
        node.quadrants[Quad.SE.ordinal()],
        x,
        y,
        r,
        minCostTower
      );
      minCostTower = compareCellTower(minCostTower, temp2);
      return minCostTower;
    }
    // Circle Intersects Two Quadrants - NW, SW
    if (
      y - r < node.celltower.y &&
      node.celltower.y < y + r &&
      node.celltower.x >= x + r
    ) {
      if (node.celltower.x == x + r) {
        minCostTower = pointSelection(node, minCostTower, x, y, r);
      }

      minCostTower =
        compareCellTower(
          minCostTower,
          chooseCellTowerUtil(node.quadrants[0], x, y, r, minCostTower)
        );
      minCostTower =
        compareCellTower(
          minCostTower,
          chooseCellTowerUtil(node.quadrants[2], x, y, r, minCostTower)
        );
      return minCostTower;
    }
    return minCostTower;
  }

  private CellTower handleCase3(
    PointQuadtreeNode node,
    int x,
    int y,
    int r,
    CellTower minCostTower
  ) {
    if (node.celltower.distance(x, y) == r) {
      minCostTower = pointSelection(node, minCostTower, x, y, r);
    }
    int oppQuad = getOppositionQuad(quadUtil(node.celltower, x, y));
    for (int i = 0; i < 4; i++) {
      if (i != oppQuad) {
        minCostTower =
          compareCellTower(
            minCostTower,
            chooseCellTowerUtil(node.quadrants[i], x, y, r, minCostTower)
          );
      }
    }
    return minCostTower;
  }

  private CellTower handleCase4(
    PointQuadtreeNode node,
    int x,
    int y,
    int r,
    CellTower minCostTower
  ) {
    if (minCostTower == null) {
      if (node.celltower.distance(x, y) <= r) {
        minCostTower = node.celltower;
      }
    } else if (minCostTower.cost > node.celltower.cost) {
      minCostTower = node.celltower;
    }

    for (int i = 0; i < node.quadrants.length; i++) {
      CellTower temp = chooseCellTowerUtil(
        node.quadrants[i],
        x,
        y,
        r,
        minCostTower
      );
      minCostTower = compareCellTower(minCostTower, temp);
    }
    return minCostTower;
  }

  private CellTower chooseCellTowerUtil(
    PointQuadtreeNode node,
    int x,
    int y,
    int r,
    CellTower minCostTower
  ) {
    // Null Node
    if (node == null) {
      return minCostTower;
    }

    // Case 4
    // d < r || Intersects 4 Quadrants
    if (node.celltower.distance(x, y) < r) {
      minCostTower =
        compareCellTower(
          minCostTower,
          handleCase4(node, x, y, r, minCostTower)
        );
      return minCostTower;
    }
    // d >= r
    if (node.celltower.distance(x, y) >= r) {
      // Point Circle
      if (node.celltower.distance(x, y) == 0 && r == 0) {
        return node.celltower;
      }

      // Case 1

      if (
        (y - r >= node.celltower.y && node.celltower.x >= x + r) ||
        (y - r >= node.celltower.y && node.celltower.x <= x - r) ||
        (y + r <= node.celltower.y && node.celltower.x >= x + r) ||
        (y + r <= node.celltower.y && node.celltower.x <= x - r)
      ) {
        minCostTower =
          compareCellTower(
            minCostTower,
            handleCase1(node, x, y, r, minCostTower)
          );
        return minCostTower;
      }

      // Case 2

      if (
        (
          x - r < node.celltower.x &&
          node.celltower.x < x + r &&
          node.celltower.y <= y - r
        ) ||
        (
          x - r < node.celltower.x &&
          node.celltower.x < x + r &&
          node.celltower.y >= y + r
        ) ||
        (
          y - r < node.celltower.y &&
          node.celltower.y < y + r &&
          node.celltower.x <= x - r
        ) ||
        (
          y - r < node.celltower.y &&
          node.celltower.y < y + r &&
          node.celltower.x >= x + r
        )
      ) {
        minCostTower =
          compareCellTower(
            minCostTower,
            handleCase2(node, x, y, r, minCostTower)
          );
        return minCostTower;
      }
      if (
        x - r < node.celltower.x &&
        node.celltower.x < x + r &&
        y - r < node.celltower.y &&
        node.celltower.y < y + r
      ) {
        minCostTower =
          compareCellTower(
            minCostTower,
            handleCase3(node, x, y, r, minCostTower)
          );
        return minCostTower;
      }
    }
    return minCostTower;
  }

  private boolean touchYAxis(PointQuadtreeNode node, int x, int y, int r) {
    return (x + r == node.celltower.x || x - r == node.celltower.x)
      ? true
      : false;
  }

  private boolean touchXAxis(PointQuadtreeNode node, int x, int y, int r) {
    return (y + r == node.celltower.y || y - r == node.celltower.y)
      ? true
      : false;
  }

  private int getOppositionQuad(int q) {
    switch (q) {
      case 1:
        return 2;
      case 2:
        return 1;
      case 3:
        return 0;
      case 0:
        return 3;
      default:
        return -1;
    }
  }

  private CellTower compareCellTower(CellTower a, CellTower b) {
    return (a == null || b == null)
      ? (a == null ? b : a)
      : (a.cost > b.cost ? b : a);
  }
}
