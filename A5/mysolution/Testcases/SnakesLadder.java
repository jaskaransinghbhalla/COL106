import java.io.*;
import java.util.*;

// public class SnakesLadder extends AbstractSnakeLadders {
public class SnakesLadder {

  int N, M;

  int snakes[];
  int ladders[];
  ArrayList<ArrayList<Integer>> snakesReverse;
  ArrayList<ArrayList<Integer>> laddersReverse;
  int distanceFromSource[];
  int distanceFromDestination[];
  boolean visitedFromSource[];
  boolean visitedFromDestination[];

  // Find Best New Snake PreProcessing
  int maxArray[];
  int minArray[];

  public SnakesLadder(String name) throws Exception {
    File file = new File(name);
    BufferedReader br = new BufferedReader(new FileReader(file));
    N = Integer.parseInt(br.readLine());
    M = Integer.parseInt(br.readLine());
    snakes = new int[N];
    ladders = new int[N];
    snakesReverse = new ArrayList<>();
    laddersReverse = new ArrayList<>();

    // Initializing
    for (int i = 0; i < N; i++) {
      ArrayList<Integer> n = new ArrayList<>();
      n.add(-1);
      snakesReverse.add(n);
    }
    for (int i = 0; i < N; i++) {
      ArrayList<Integer> n = new ArrayList<>();
      n.add(-1);
      laddersReverse.add(n);
    }

    distanceFromSource = new int[N + 2];
    distanceFromDestination = new int[N + 2];

    visitedFromSource = new boolean[N + 2];
    visitedFromDestination = new boolean[N + 2];

    for (int i = 0; i < N; i++) {
      snakes[i] = ladders[i] = -1;
    }
    for (int i = 0; i < N + 2; i++) {
      distanceFromSource[i] = distanceFromDestination[i] = 0;
      visitedFromSource[i] = visitedFromDestination[i] = false;
    }

    for (int i = 0; i < M; i++) {
      String e = br.readLine();

      StringTokenizer st = new StringTokenizer(e);
      int source = Integer.parseInt(st.nextToken());
      int destination = Integer.parseInt(st.nextToken());
      if (source < destination) {
        ladders[source] = destination;

        if (laddersReverse.get(destination).get(0) == -1) {
          ArrayList<Integer> n = new ArrayList<>();
          n.add(source);
          laddersReverse.set(destination, n);
        } else {
          // Add Both
          ArrayList<Integer> n = laddersReverse.get(destination);
          n.add(source);
          laddersReverse.set(destination, n);
        }
      } else {
        snakes[source] = destination;

        if (snakesReverse.get(destination).get(0) == -1) {
          ArrayList<Integer> n = new ArrayList<>();
          n.add(source);
          snakesReverse.set(destination, n);
        } else {
          //  Add Both
          ArrayList<Integer> n = snakesReverse.get(destination);
          n.add(source);
          snakesReverse.set(destination, n);
        }
      }
    }

    forwardBFS();
    backwardBFS();

    if (distanceFromSource[N] != 0) {
      maxArray = new int[distanceFromSource[N]];
      minArray = new int[distanceFromSource[N]];
      for (int i = 0; i < maxArray.length; i++) {
        maxArray[i] = -1;
        minArray[i] = -1;
      }
      maxArray[0] = 0;
      minArray[0] = 0;

      for (int i = 1; i < N + 1; i++) {
        int index = distanceFromSource[i];
        if (index < maxArray.length) {
          if (maxArray[index] != -1) {
            maxArray[index] = Math.max(i, maxArray[index]);
          } else {
            maxArray[index] = i;
          }
        }
      }
      for (int i = 1; i < N + 1; i++) {
        int index = distanceFromDestination[i];
        if (index < maxArray.length) {
          if (minArray[index] != -1) {
            minArray[index] = Math.min(i, minArray[index]);
          } else {
            minArray[index] = i;
          }
        }
      }
    }
  }

  public int OptimalMoves() {
    return (visitedFromSource[N]) ? distanceFromSource[N] : -1;
  }

  public int Query(int x, int y) {
    if (!visitedFromSource[x]) {
      return -1;
    }
    if (distanceFromSource[N] == 0) {
      return distanceFromSource[x] + distanceFromDestination[y] != 0 ? 1 : -1;
    }
    return (computeImprovement(x, y, N)) ? 1 : -1;
  }

  public int[] FindBestNewSnake() {
    return computeBestSnake();
  }

  private void forwardBFS() {
    Queue<Integer> q1 = new LinkedList<>();
    q1.add(0);

    while (!q1.isEmpty()) {
      int start = q1.remove();
      for (int i = start + 1; i <= start + 6 && i < 101; i++) {
        if (!visitedFromSource[i]) {
          q1.add(finalPosition(i, start));
        }
      }
    }
  }

  private void backwardBFS() {
    Queue<Integer> q2 = new LinkedList<>();
    q2.add(100);

    int d = 0;

    while (!q2.isEmpty()) {
      int start = q2.remove();

      for (int i = start - 1; i >= start - 6 && i > 0; i--) {
        if (!visitedFromDestination[i]) {
          if (snakes[i] != -1 || ladders[i] != -1) {
            continue;
          } else {
            ArrayList<Integer> tobeAdded = finalPositionArrayList(
              i,
              start,
              new ArrayList<>()
            );
            if (tobeAdded != null) {
              q2.addAll(tobeAdded);
              if (distanceFromDestination[tobeAdded.get(0)] > d) {
                d = distanceFromDestination[tobeAdded.get(0)];
              }
            }
          }
        }
      }
    }
  }

  private int finalPosition(int i, int start) {
    // Doubt
    if (visitedFromSource[i]) {
      return distanceFromSource[i];
    }
    visitedFromSource[i] = true;
    distanceFromSource[i] = distanceFromSource[start] + 1;
    if (i == 100 || i == 1 || (snakes[i] == -1 && ladders[i] == -1)) {
      return i;
    }
    return (snakes[i] != -1)
      ? finalPosition(snakes[i], start)
      : finalPosition(ladders[i], start);
  }

  private ArrayList<Integer> finalPositionArrayList(
    int i,
    int start,
    ArrayList<Integer> tobeAdded
  ) {
    tobeAdded = new ArrayList<>();
    visitedFromDestination[i] = true;
    distanceFromDestination[i] = distanceFromDestination[start] + 1;
    if (
      i == 100 ||
      i == 1 ||
      (snakesReverse.get(i).get(0) == -1 && laddersReverse.get(i).get(0) == -1)
    ) {
      tobeAdded.add(i);
      return tobeAdded;
    }
    tobeAdded.add(i);
    tobeAdded.addAll(visitAll(i, distanceFromDestination[i], tobeAdded));
    return tobeAdded;
  }

  private ArrayList<Integer> visitAll(
    int val,
    int distance,
    ArrayList<Integer> tobeAdded
  ) {
    tobeAdded = new ArrayList<>();
    visitedFromDestination[val] = true;
    distanceFromDestination[val] = distance;
    if (
      snakesReverse.get(val).get(0) == -1 &&
      laddersReverse.get(val).get(0) == -1
    ) {
      return new ArrayList<>();
    } else if (snakesReverse.get(val).get(0) != -1) {
      for (Integer k : snakesReverse.get(val)) {
        ArrayList<Integer> temp = visitAll(k, distance, tobeAdded);
        tobeAdded.add(k);
        tobeAdded.addAll(temp);
      }
    }
    if (laddersReverse.get(val).get(0) != -1) {
      for (Integer k : laddersReverse.get(val)) {
        ArrayList<Integer> temp = visitAll(k, distance, tobeAdded);
        tobeAdded.add(k);
        tobeAdded.addAll(temp);
      }
    }

    return tobeAdded;
  }

  private boolean computeImprovement(int x, int y, int N) {
    return (
      distanceFromSource[x] + distanceFromDestination[y] < distanceFromSource[N]
    );
  }

  private int[] computeBestSnake() {
    int OptimalMoves = visitedFromSource[N] ? distanceFromSource[N] : -1;
    if (OptimalMoves == -1) {
      return computeSnake2();
    }
    int result[] = new int[2];
    result[0] = result[1] = -1;
    int x = 0;
    int y = 0;
    int sum = distanceFromSource[N];

    for (int i = 1; i < maxArray.length; i++) {
      for (int j = minArray.length - 1; j > 0; j--) {
        if (maxArray[i] > minArray[j] && i + j < sum) {
          sum = i + j;
          x = maxArray[i];
          y = minArray[j];
        }
      }
    }
    if (sum != 0 && sum < OptimalMoves) {
      result[0] = x;
      result[1] = y;
    }
    return result;
  }

  public static void main(String[] args) throws Exception {
    SnakesLadder s = new SnakesLadder("input.txt");
    System.out.println(s.computeSnake2()[0] + " " + s.computeSnake2()[1]);
    System.out.println(s.FindBestNewSnake()[0] + " " + s.FindBestNewSnake()[1]);
  }

  private int finalPositionBestSnake(int i, int snakesT[], int laddersT[]) {
    if (i == 100 || i == 1 || (snakesT[i] == -1 && laddersT[i] == -1)) {
      return i;
    }
    return (snakesT[i] != -1)
      ? finalPositionBestSnake(snakesT[i], snakesT, laddersT)
      : finalPositionBestSnake(laddersT[i], snakesT, laddersT);
  }

  private int[] computeSnake2() {
    // Initialize Ladders
    int laddersUtil[] = new int[N + 1];
    ArrayList<ArrayList<Integer>> laddersNew = new ArrayList<>();
    int OptimalMoves = visitedFromSource[N] ? distanceFromSource[N] : -1;

    // Initialize Result
    int result[] = new int[2];
    result[0] = -1;
    result[1] = -1;

    // Handle Optimal Moves = -1;
    if (OptimalMoves == -1) {
      return result;
    }

    for (int i = 0; i < ladders.length; i++) {
      if (ladders[i] != -1) {
        laddersUtil[i] = finalPositionBestSnake(ladders[i], snakes, ladders);
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(i);
        temp.add(finalPositionBestSnake(ladders[i], snakes, ladders));
        laddersNew.add(temp);
      } else {
        laddersUtil[i] = -1;
      }
    }

    for (int i = 0; i < laddersNew.size(); i++) {
      for (int j = 0; j < laddersNew.size(); j++) {
        if (
          i != j &&
          laddersNew.get(i).get(0) < laddersNew.get(j).get(0) &&
          laddersNew.get(j).get(0) < laddersNew.get(i).get(1) &&
          laddersNew.get(i).get(1) < laddersNew.get(j).get(1)
        ) {
          int x = laddersNew.get(i).get(1);
          int y = laddersNew.get(j).get(0);
          if (result[0] == -1 && result[1] == -1) {
            result[0] = x;
            result[1] = y;
            OptimalMoves = distanceFromSource[x] + distanceFromDestination[y];
          } else {
            if (
              distanceFromSource[x] + distanceFromDestination[y] < OptimalMoves
            ) {
              OptimalMoves = distanceFromSource[x] + distanceFromDestination[y];
              result[0] = x;
              result[1] = y;
            }
          }
        }
      }
    }
    return result;
  }
}
