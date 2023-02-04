import java.util.LinkedList;

import Includes.DictionaryEntry;
import Includes.HashTableEntry;
import Includes.KeyAlreadyExistException;
import Includes.KeyNotFoundException;
import Includes.NullKeyException;

import java.lang.reflect.Array;

public class COL106Dictionary<K, V> {

    private LinkedList<DictionaryEntry<K, V>> dict;
    public LinkedList<HashTableEntry<K, V>>[] hashTable;

    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
    }

    public void insert(K key, V value) throws KeyAlreadyExistException, NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K and it corresponding value of type V
         * Working: Inserts the argumented key-value pair in the Dictionary in O(1)
         */
    }

    public V delete(K key) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key
         * Working: Deletes the key-value pair from the Dictionary in O(1)
         */

        return dict.getFirst().value;
    }

    public V update(K key, V value) throws NullKeyException, KeyNotFoundException{
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the previously associated value of type V with the argumented key
         * Working: Updates the value associated with argumented key with the argumented value in O(1)
         */

        return dict.getFirst().value;
    }

    public V get(K key) throws NullKeyException, KeyNotFoundException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */

        return dict.getFirst().value;
    }

    public Boolean exist(K key) throws NullKeyException {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the associated value of type V with the argumented key in O(1)
         */

        return false;
    }

    public int size() {
        /*
         * To be filled in by the student
         * Return: Returns the size of the Dictionary in O(1)
         */
        return 0;
    }

    @SuppressWarnings("unchecked")
    public K[] keys(Class<K> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */

        return (K[]) Array.newInstance(cls, dict.size());
    }

    @SuppressWarnings("unchecked")
    public V[] values(Class<V> cls) {
        /*
         * To be filled in by the student
         * Return: Returns array of keys stored in dictionary.
         */

        return (V[]) Array.newInstance(cls, dict.size());
    }

    public int hash(K key) {
        /*
         * To be filled in by the student
         * Input: A key of type K
         * Return: Returns the hash of the argumented key using the Polynomial Rolling
         * Hash Function.
         */

        return 0;
    }
}
