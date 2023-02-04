import java.util.LinkedList;
import java.lang.reflect.Array;
import java.util.*;

public class q3 {
    public static class KeyNotFoundException extends Exception {
        public KeyNotFoundException() {
            System.out.println("KeyNotFound");
        }
    }

    public static class KeyAlreadyExistException extends Exception {
        public KeyAlreadyExistException() {
            System.out.println("KeyAlreadyExist");
        }
    }

    public static class NullKeyException extends Exception {
        public NullKeyException() {
            System.out.println("NullKey");
        }

    }

    public static class DictionaryEntry<K, V> {
        public K key;
        public V value;

        public DictionaryEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class HashTableEntry<K, V> {
        public K key;
        public DictionaryEntry<K, V> dictEntry;

        public HashTableEntry(K key, DictionaryEntry<K, V> dictEntry) {
            this.key = key;
            this.dictEntry = dictEntry;
        }
    }

    public static class COL106Dictionary<K, V> {

        private LinkedList<DictionaryEntry<K, V>> dict;
        /*
         * dict is a Linked-List, where every node of linked-list is of type
         * DictionaryEntry.
         * DictionaryEntry is a key-value pair, where the type of key and value is K and
         * V respectively.
         */
        public LinkedList<HashTableEntry<K, V>>[] hashTable;
        /*
         * hashTable is an array of Linked-Lists which is initialized by the
         * COL106Dictionary constructor.
         * Each index of hashTable stores a linked-list whose nodes are of type
         * HashTableEntry
         * HashTableEntry is a key-address pair, where the type of key is K and the
         * corresponding address is the address of the DictionaryEntry in the
         * linked-list corresponding to the key of HashTableEntry
         */

        @SuppressWarnings("unchecked")
        COL106Dictionary(int hashTableSize) {
            dict = new LinkedList<DictionaryEntry<K, V>>();
            // This statement initiailizes a linked-list where each node is of type
            // DictionaryEntry with key and value of type K and V respectively.
            hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
            // This statement initiailizes the hashTable with an array of size hashTableSize
            // where at each index the element is an instance of LinkedList class and
            // this array is type-casted to an array of LinkedList where the LinkedList
            // contains nodes of type HashTableEntry with key of type K.
        }

        public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
            /*
             * To be filled in by the student
             * Input: A key of type K and it corresponding value of type V
             * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
             */

            // -----------
            // O(String Length)
            // O(1)
            if (key == null || key == "") {
                throw new NullKeyException();
            }

            int hash = hash(key);
            DictionaryEntry<K, V> de = new DictionaryEntry<K, V>(key, value);
            dict.add(de);
            LinkedList<HashTableEntry<K, V>> items = hashTable[hash];
            if (items == null) {
                items = new LinkedList<HashTableEntry<K, V>>();
                HashTableEntry<K, V> item = new HashTableEntry<K, V>(key, de);
                items.add(item);
                hashTable[hash] = items;
            } else {
                for (HashTableEntry<K, V> item : items) {
                    if (item.key.equals(key)) {
                        throw new KeyAlreadyExistException();
                    }
                }
                HashTableEntry<K, V> item = new HashTableEntry<K, V>(key, de);
                items.add(item);
                hashTable[hash] = items;
            }
        }

        public V delete(K key) throws NullKeyException, KeyNotFoundException {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the associated value of type V with the argumented key
             * Working: Deletes the key-value pair from the Dictionary in O(1)
             */

            if (key == null || key == "") {
                throw new NullKeyException();
            }

            int hash = hash(key);

            V v;
            boolean found = false;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            if (temp == null) {
                throw new KeyNotFoundException();
            }
            int i = 0;
            while (i < temp.size()) {
                if (key.equals(temp.get(i).key)) {
                    found = true;
                    break;
                }
                i++;
            }

            if (found) {
                v = temp.get(i).dictEntry.value;
                dict.remove(hashTable[hash].get(i).dictEntry);
                hashTable[hash].remove(i);
                if (hashTable[hash].size() == 0) {
                    hashTable[hash] = null;
                }
                return v;
            } else {
                throw new KeyNotFoundException();
            }
        }

        public V update(K key, V value) throws NullKeyException, KeyNotFoundException {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the previously associated value of type V with the argumented
             * key
             * Working: Updates the value associated with argumented key with the argumented
             * value in O(1)
             */
            if (key == null || key == "") {
                throw new NullKeyException();
            }
            int hash = hash(key);
            V v;
            boolean found = false;
            DictionaryEntry<K, V> d;
            HashTableEntry<K, V> h;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            if (temp == null) {
                throw new KeyNotFoundException();
            }
            int i = 0;
            while (i < temp.size()) {
                if (key.equals(temp.get(i).key)) {
                    found = true;
                    break;
                }
                i++;
            }
            if (found) {
                h = temp.get(i);
                d = temp.get(i).dictEntry;
                v = temp.get(i).dictEntry.value;

                // Update
                d.value = value;
                h.dictEntry = d;

                // Setting
                hashTable[hash].set(i, h);
                return v;
            } else {
                throw new KeyNotFoundException();
            }
        }

        public V get(K key) throws NullKeyException, KeyNotFoundException {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the associated value of type V with the argumented key in
             * O(1)
             */

            // ----------------------
            if (key == null || key == "") {
                throw new NullKeyException();
            }
            int hash = hash(key);
            V v;
            boolean found = false;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            int i = 0;
            while (i < temp.size()) {
                if (key.equals(temp.get(i).key)) {
                    found = true;
                    break;
                }
                i++;
            }

            if (found) {
                v = temp.get(i).dictEntry.value;
                return v;
            } else {
                throw new KeyNotFoundException();
            }
        }

        public int size() {
            /*
             * To be filled in by the student
             * Return: Returns the size of the Dictionary in O(1)
             */
            return dict.size();
        }

        @SuppressWarnings("unchecked")
        public K[] keys(Class<K> cls) {
            /*
             * To be filled in by the student
             * Return: Returns array of keys stored in dictionary.
             */

            K[] arr = (K[]) Array.newInstance(cls, dict.size());
            int di = 0;
            while (di < dict.size()) {
                arr[di] = dict.get(di).key;
                di++;
            }
            return arr;
        }

        @SuppressWarnings("unchecked")
        public V[] values(Class<V> cls) {
            /*
             * To be filled in by the student
             * Return: Returns array of keys stored in dictionary.
             */

            V[] arr = (V[]) Array.newInstance(cls, dict.size());
            int di = 0;
            while (di < dict.size()) {
                arr[di] = dict.get(di).value;
                di++;
            }
            return arr;
        }

        public int hash(K key) {

            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the hash of the argumented key using the Polynomial Rolling
             * Hash Function.
             */

            String k = (String) key;
            int s = ((int) k.charAt(0) + 1);
            int m = hashTable.length;
            int p = 131;
            for (int i = 1; i < k.length(); i++) {
                s = ((s % m) + ((((int) k.charAt(i) + 1) % m) * (p % m)) % m) % m;
                p = (p * 131) % m;

            }
            return s % m;

        }

        public void printLinkedList(LinkedList<HashTableEntry<K, V>> l) {

            if (l == null) {
                System.out.print("null");
            } else {
                for (int i = 0; i < l.size(); i++) {
                    System.out.print(l.get(i).dictEntry.key + " : ");
                    System.out.print(l.get(i).dictEntry.value);
                    if (i != l.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
            }
        }

        public void printDictionary(LinkedList<DictionaryEntry<K, V>> l) {
            if (l == null) {
                return;
            } else {
                for (int i = 0; i < l.size(); i++) {
                    System.out.print(l.get(i).key + " : ");
                    System.out.print(l.get(i).value);
                    if (i != l.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
            }
        }

        public void printHashTable(LinkedList<HashTableEntry<K, V>>[] hashT) {
            System.out.println("----------------------------------------");
            for (int k = 0; k < hashT.length; k++) {
                printLinkedList(hashT[k]);
                System.out.println();
            }
            System.out.println("----------------------------------------");
        }

        public boolean exist(K key) {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the associated value of type V with the argumented key in
             * O(1)
             */

            // ----------------------

            int hash = hash(key);
            boolean found = false;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            if (temp == null) {
                return false;
            }
            int i = 0;
            while (i < temp.size()) {

                if (key.equals(temp.get(i).key)) {
                    found = true;
                    break;
                }

                i++;
            }

            return found;
        }
    }
    // import java.util.ArrayList;

    public static class FrequencyAnalysis {
        // sizes of hash-tables are updated
        static final int[] hashTableSizes = { 11, 6733, 100003 };
        // static final int[] hashTableSizes = { 173, 6733, 100003 };
        static COL106Dictionary<String, Integer> dict1 = new COL106Dictionary<String, Integer>(hashTableSizes[0]);
        static COL106Dictionary<String, Integer> dict2 = new COL106Dictionary<String, Integer>(hashTableSizes[1]);
        static COL106Dictionary<String, Integer> dict3 = new COL106Dictionary<String, Integer>(hashTableSizes[2]);

        static void fillDictionaries(String inputString)
                throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
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

        static int[] noOfCollisions() {
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

        static char hexHelper(int n) {
            if (n == 0) {
                return '0';
            }
            if (n == 1) {
                return '1';
            }
            if (n == 10) {
                return '2';
            }
            if (n == 11) {
                return '3';
            }
            if (n == 100) {
                return '4';
            }
            if (n == 101) {
                return '5';
            }
            if (n == 110) {
                return '6';
            }
            if (n == 111) {
                return '7';
            }
            if (n == 1000) {
                return '8';
            }
            if (n == 1010) {
                return 'A';
            }
            if (n == 1011) {
                return 'B';
            }
            if (n == 1100) {
                return 'C';
            }
            if (n == 1101) {
                return 'D';
            }
            if (n == 1110) {
                return 'E';
            }
            if (n == 1111) {
                return 'F';
            }
            return '0';
        }

        static String hexEvaluator(String a1) {
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
                int t1 = Integer.parseInt(temp1);
                final_a1 = final_a1 + hexHelper(t1);
                i += 4;
                if (i == a1.length()) {
                    break;
                }
            }
            // String final_a = "";
            // int k = 0;
            // while (final_a1.charAt(k) == '0') {
            // k++;
            // }
            // for (int m = k; m < final_a1.length(); m++) {
            // final_a = final_a + final_a1.charAt(m);
            // }
            return final_a1;
        }

        static String[] hashTableHexaDecimalSignature() {
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
            dict1.printHashTable(dict1.hashTable);
            System.out.println(a1);
            System.out.println((hexEvaluator(a1)));
            System.out.println(convertBinToHex(a1));
            return ans;
        }

        static String[] distinctWords() {
            /*
             * To be filled in by the student
             */
            String[] ans = new String[dict1.size()];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = dict1.dict.get(i).key;
            }

            return ans;
        }

        static Integer[] distinctWordsFrequencies() {
            /*
             * To be filled in by the student
             */
            Integer[] ans = new Integer[dict1.size()];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = dict1.dict.get(i).value;
            }
            return ans;
        }

        static void createMap(Map<String, Character> um) {
            um.put("0000", '0');
            um.put("0001", '1');
            um.put("0010", '2');
            um.put("0011", '3');
            um.put("0100", '4');
            um.put("0101", '5');
            um.put("0110", '6');
            um.put("0111", '7');
            um.put("1000", '8');
            um.put("1001", '9');
            um.put("1010", 'A');
            um.put("1011", 'B');
            um.put("1100", 'C');
            um.put("1101", 'D');
            um.put("1110", 'E');
            um.put("1111", 'F');
        }

        static String convertBinToHex(String bin) {
            int l = bin.length();
            int t = bin.indexOf('.');

            // Length of string before '.'
            int len_left = t != -1 ? t : l;

            // Add min 0's in the beginning to make
            // left substring length divisible by 4
            for (int i = 1; i <= (4 - len_left % 4) % 4; i++)
                bin = '0' + bin;

            // If decimal point exists
            if (t != -1) {

                // Length of string after '.'
                int len_right = l - len_left - 1;

                // Add min 0's in the end to make right
                // substring length divisible by 4
                for (int i = 1; i <= (4 - len_right % 4) % 4; i++)
                    bin = bin + '0';
            }

            // Create map between binary and its
            // equivalent hex code
            Map<String, Character> bin_hex_map = new HashMap<String, Character>();
            createMap(bin_hex_map);

            int i = 0;
            String hex = "";

            while (true) {

                // One by one extract from left, substring
                // of size 4 and add its hex code
                hex += bin_hex_map.get(
                        bin.substring(i, i + 4));
                i += 4;

                if (i == bin.length())
                    break;

                // If '.' is encountered add it
                // to result
                if (bin.charAt(i) == '.') {
                    hex += '.';
                    i++;
                }
            }

            // Required hexadecimal number
            return hex;
        }
    }

    // public static void main(String[] args) throws NullKeyException,
    // KeyAlreadyExistException, KeyNotFoundException {
    // FrequencyAnalysis.fillDictionaries("b e f h i k");
    // String[] arr = FrequencyAnalysis.hashTableHexaDecimalSignature();

    // }
    public static void main(String[] args) throws KeyAlreadyExistException, NullKeyException, KeyNotFoundException {
        COL106Dictionary<String, Integer> D = new COL106Dictionary<>(140);

        

}