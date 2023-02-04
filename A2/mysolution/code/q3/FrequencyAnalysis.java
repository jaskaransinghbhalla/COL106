// import java.util.ArrayList;

import Includes.*;

public class FrequencyAnalysis {
    // sizes of hash-tables are updated
    static final int[] hashTableSizes = { 173, 6733, 100003 };
    COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
    COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
    COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);

    void fillDictionaries(String inputString) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        /*
         * To be filled in by the student
         */
        if (inputString == null) {
            throw new NullKeyException();
        }
        // if (inputString == "")
        // if inputString = "" after removal of special characters case.

        String s = "";

        for (int i = 0; i < inputString.length(); i++) {
            int t = (int) inputString.charAt(i);
            if ((t <= 122 && t >= 97) || (t >= 65 && t <= 90 || t == 32)) {
                s = s + (char) t;
            }
        }

        for (int j = 0; j < s.length(); j++) {
            String input = "";

            while (j < s.length()) {
                int c = (int) s.charAt(j);
                if (c == 32) {
                    break;
                }
                input += s.charAt(j);
                j++;

            }

            if (input.length() == 0 || input == " ") {
                continue;
            }

            input = input.toLowerCase();
            if (!dict1.exist(input)) {
                dict1.insert(input, 1);
            } else {
                dict1.update(input, dict1.get(input) + 1);
            }
            if (!dict2.exist(input)) {
                dict2.insert(input, 1);
            } else {
                dict2.update(input, dict2.get(input) + 1);
            }
            if (!dict3.exist(input)) {
                dict3.insert(input, 1);
            } else {
                dict3.update(input, dict3.get(input) + 1);
            }
        }
    }

    long[] profile() {

        /*
         * To be filled in by the student
         */
        return new long[4];
    }

    int[] noOfCollisions() {

        /*
         * To be filled in by the student
         */
        int[] ans = new int[3];
        for (int i = 0; i < hashTableSizes[0]; i++) {
            if (dict1.hashTable[i] != null) {
                ans[0] = ans[0] + (dict1.hashTable[i].size() - 1);
            }
        }
        for (int i = 0; i < hashTableSizes[1]; i++) {
            if (dict2.hashTable[i] != null) {
                ans[1] = ans[1] + (dict2.hashTable[i].size() - 1);
            }
        }
        for (int i = 0; i < hashTableSizes[2]; i++) {
            if (dict3.hashTable[i] != null) {
                ans[2] = ans[2] + (dict3.hashTable[i].size() - 1);
            }
        }

        return (ans);
    }

    static char hexHelper(String n) {
        if (n.equals("0000")) {
            return '0';
        }
        if (n.equals("0001")) {
            return '1';
        }
        if (n.equals("0010")) {
            return '2';
        }
        if (n.equals("0011")) {
            return '3';
        }
        if (n.equals("0100")) {
            return '4';
        }
        if (n.equals("0101")) {
            return '5';
        }
        if (n.equals("0110")) {
            return '6';
        }
        if (n.equals("0111")) {
            return '7';
        }
        if (n.equals("1000")) {
            return '8';
        }
        if (n.equals("1001")) {
            return '9';
        }
        if (n.equals("1010")) {
            return 'A';
        }
        if (n.equals("1011")) {
            return 'B';
        }
        if (n.equals("1100")) {
            return 'C';
        }
        if (n.equals("1101")) {
            return 'D';
        }
        if (n.equals("1110")) {
            return 'E';
        }
        if (n.equals("1111")) {
            return 'F';
        }
        return '0';
    }

    String hexEvaluator(String a1) {
        if (a1.length() % 4 != 0) {
            for (int i = 0; i < a1.length() % 4; i++) {
                a1 = "0" + a1;
            }
        }
        String final_a1 = "";
        String temp1 = "";
        int i = 0;
        while (true) {
            temp1 = a1.substring(i, i + 4);
            final_a1 = final_a1 + hexHelper(temp1);
            i += 4;
            if (i == a1.length()) {
                break;
            }
        }
        String final_a = "";
        int k = 0;
        while (final_a1.charAt(k) == '0') {
            k++;
        }
        for (int m = k; m < final_a1.length(); m++) {
            final_a = final_a + final_a1.charAt(m);
        }
        return final_a1;
    }

    String[] hashTableHexaDecimalSignature() {

        /*
         * To be filled in by the student
         */
        // int n = hashTableSizes[0] - i - 1;
        /*
         * To be filled in by the student
         */
        String[] ans = new String[3];
        String a1 = "";
        String a2 = "";
        String a3 = "";
        for (int i = 0; i < hashTableSizes[0]; i++) {
            if (dict1.hashTable[i] != null) {
                a1 = a1 + "1";
            } else {
                a1 = a1 + "0";
            }
        }
        for (int i = 0; i < hashTableSizes[1]; i++) {
            if (dict2.hashTable[i] != null) {
                a2 = a2 + "1";
            } else {
                a2 = a2 + "0";
            }
        }
        for (int i = 0; i < hashTableSizes[2]; i++) {
            if (dict3.hashTable[i] != null) {
                a3 = a3 + "1";
            } else {
                a3 = a3 + "0";
            }
        }

        ans[0] = hexEvaluator(a1);
        ans[1] = hexEvaluator(a2);
        ans[2] = hexEvaluator(a3);
        return ans;
    }

    String[] distinctWords() {

        String[] ans = new String[dict1.size()];
        ans = dict1.keys(String.class);
        return ans;
    }

    Integer[] distinctWordsFrequencies() {

        /*
         * To be filled in by the student
         */
        Integer[] ans = new Integer[dict1.size()];
        ans = dict1.values(Integer.class);
        return ans;
    }
}
