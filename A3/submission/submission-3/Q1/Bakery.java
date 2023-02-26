import java.util.*;

public class Bakery {

  static int solve(ArrayList<Integer> cakes) {
    // Before Cakes Problem.

    SkipList s = new SkipList();
    for (int i = 0; i < cakes.size(); i++) {
      int t = cakes.get(i);
      int u = s.upperBound(t);
      if (u == Integer.MAX_VALUE) {
        s.insert(t);
      } else {
        s.delete(u);
        s.insert(t);
      }
    }

    int answer = 0;
    SkipListNode temp = s.head;
    while (temp != null) {
      temp = temp.next.get(0);
      answer++;
    }
    return answer - 2;
  }
}
