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
     * dict is a Linked-List, where every node of linked-list is of type DictionaryEntry.
     * DictionaryEntry is a key-value pair, where the type of key and value is K and V respectively.
     */ 
    public LinkedList<HashTableEntry<K, V>>[] hashTable;
    /*
     * hashTable is an array of Linked-Lists which is initialized by the COL106Dictionary constructor.
     * Each index of hashTable stores a linked-list whose nodes are of type HashTableEntry
     * HashTableEntry is a key-address pair, where the type of key is K and the corresponding address is the address of the DictionaryEntry in the linked-list corresponding to the key of HashTableEntry
     */ 
    
    @SuppressWarnings("unchecked")
    COL106Dictionary(int hashTableSize) {
        dict = new LinkedList<DictionaryEntry<K, V>>();
        // This statement initiailizes a linked-list where each node is of type DictionaryEntry with key and value of type K and V respectively.
        hashTable = (LinkedList<HashTableEntry<K, V>>[]) Array.newInstance(LinkedList.class, hashTableSize);
        // This statement initiailizes the hashTable with an array of size hashTableSize where at each index the element is an instance of LinkedList class and
        // this array is type-casted to an array of LinkedList where the LinkedList contains nodes of type HashTableEntry with key of type K. 
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

