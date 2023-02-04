package Includes;

public class HashTableEntry<K, V> {
    public K key;
    public DictionaryEntry<K, V> dictEntry;
    HashTableEntry(K key, DictionaryEntry<K, V> dictEntry) {
        this.key = key;
        this.dictEntry = dictEntry;
    }
}
