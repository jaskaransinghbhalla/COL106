import java.util.LinkedList;
import java.lang.reflect.Array;

public class test {
    public static class KeyNotFoundException extends Exception {
        public KeyNotFoundException() {
            System.out.println("KeyNotFound");
        }
    }

    public static class NullKeyException extends Exception {
        public NullKeyException() {
            System.out.println("NullKey");
        }
    }

    public static class KeyAlreadyExistException extends Exception {
        public KeyAlreadyExistException() {
            System.out.println("KeyAlreadyExist");
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
        public LinkedList<HashTableEntry<K, V>>[] hashTable;

        @SuppressWarnings("unchecked")
        COL106Dictionary(int hashTableSize) {
            dict = new LinkedList<DictionaryEntry<K, V>>();
            hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        }

        // Done
        public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
            /*
             * To be filled in by the student
             * Input: A key of type K and it corresponding value of type V
             * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
             */

            // -----------
            // O(String Length)
            // O(1)
            if (key == null) {
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

            // -----------
            // O()
            if (key == null) {
                throw new NullKeyException();
            }
            int hash = hash(key);

            V v;
            boolean found = false;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            int i = 0;
            while (i < temp.size()) {
                if (temp.get(i).key == key) {
                    found = true;
                    break;
                }
                i++;
            }

            if (found) {
                v = temp.get(i).dictEntry.value;
                hashTable[hash].remove(i);
                int di = 0;
                while (di < dict.size()) {
                    if (dict.get(di).key == key) {
                        break;
                    }
                    di++;
                }
                dict.remove(di);
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

            if (key == null) {
                throw new NullKeyException();
            }
            int hash = hash(key);
            V v;
            boolean found = false;
            DictionaryEntry<K, V> d;
            HashTableEntry<K, V> h;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            int i = 0;
            while (i < temp.size()) {
                if (temp.get(i).key == key) {
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

                int di = 0;
                while (di < dict.size()) {
                    if (dict.get(di).key == key) {
                        break;
                    }
                    di++;
                }
                dict.set(di, d);
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
            if (key == null) {
                throw new NullKeyException();
            }
            int hash = hash(key);
            V v;
            boolean found = false;
            HashTableEntry<K, V> h;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            int i = 0;
            while (i < temp.size()) {
                if (temp.get(i).key == key) {
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

        public Boolean exist(K key) throws NullKeyException {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the associated value of type V with the argumented key in
             * O(1)
             */
            if (key == null) {
                throw new NullKeyException();
            }
            int hash = hash(key);
            V v;
            boolean found = false;
            HashTableEntry<K, V> h;
            LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];
            int i = 0;
            while (i < temp.size()) {
                if (temp.get(i).key == key) {
                    found = true;
                    break;
                }
                i++;
            }
            return found;
        }

        public int size() {
            /*
             * To be filled in by the student
             * Return: Returns the size of the Dictionary in O(1)
             */

            // --------------
            // O(1)
            // --------------

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

        // Done
        public int hash(K key) {
            /*
             * To be filled in by the student
             * Input: A key of type K
             * Return: Returns the hash of the argumented key using the Polynomial Rolling
             * Hash Function.
             */

            // ---------------
            // O(String Length)
            // ---------------

            String k = (String) key;
            k = k.toLowerCase();
            int s = (int) k.charAt(0) + 1;
            int p = 131;
            for (int i = 1; i < k.length(); i++) {
                s = s + ((int) k.charAt(i) + 1) * p;
                p = p * p;
            }
            s = s % hashTable.length;
            return s;

        }

        public void printLinkedList(LinkedList<HashTableEntry<K, V>> l) {
            for (int i = 0; i < l.size(); i++) {
                System.out.print(l.get(i).dictEntry.key + " : ");
                System.out.print(l.get(i).dictEntry.value);
                if (i != l.size() - 1) {
                    System.out.print(" -> ");
                }

            }

        }
    }

    public static void main(String[] args) throws NullKeyException, KeyAlreadyExistException, KeyNotFoundException {
        COL106Dictionary<String, Integer> example = new COL106Dictionary<>(7);
        example.insert("Hello", 10);
        example.insert("Fellow", 20);
        // example.insert("Mellow", 30);
        // example.delete("Mellow");
        // example.printLinkedList(example.hashTable[example.hash("Hello")]);
        for (int i = 0; i < example.values(Integer.class).length; i++) {

            System.out.println(example.values(Integer.class)[i]);
        }
        // example.printLinkedList(example.hashTable[example.hash("Mello")]);
        // System.out.println(example.hashTable[example.hash("Hello")].getFirst().dictEntry.value);
    }
}