import java.util.*;

public class Bakery {
    static int solve(ArrayList<Integer> cakes){
        // TO be completed by students
        SkipList tops = new SkipList();
        int answer = 0;
        for (Integer f: cakes) {
            int x = tops.upperBound(f);
            if (x == Integer.MAX_VALUE) {
                answer += 1;
                tops.insert(f);
            } else {
                tops.delete(x);
                tops.insert(f);
            }
        }
        return answer;
    }
}
