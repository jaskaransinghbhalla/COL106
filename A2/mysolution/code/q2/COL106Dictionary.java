import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

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

    public int length = 0;

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */

        if (key == null) {
            throw new NullKeyException();
        }

        int hash = hash(key);

        DictionaryEntry<K, V> de = new DictionaryEntry<K, V>(key, value);
        
        LinkedList<HashTableEntry<K, V>> items = hashTable[hash];

        if (items == null) {
            items = new LinkedList<HashTableEntry<K, V>>();
            HashTableEntry<K, V> item = new HashTableEntry<K, V>(key, de);
            dict.add(de);
            length++;
            items.add(item);
            hashTable[hash] = items;
        } else {
            for (HashTableEntry<K, V> item : items) {
                if (item.key.equals(key)) {
                    throw new KeyAlreadyExistException();
                }
            }
            HashTableEntry<K, V> item = new HashTableEntry<K, V>(key, de);
            dict.add(de);
            length++;
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

        if (key == null) {
            throw new NullKeyException();
        }

        int hash = hash(key);

        V v;

        boolean found = false;

        LinkedList<HashTableEntry<K, V>> temp = hashTable[hash];

        int i = 0;

        if (temp == null) {
            throw new KeyNotFoundException();
        }

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
            length--;
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

        if (key == null) {
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
        if (key == null) {
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
        return length;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */

        // if(dict == null) {
        // return null;
        // }

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

        // if(dict == null) {
        // return null;
        // }
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
        return s;
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
