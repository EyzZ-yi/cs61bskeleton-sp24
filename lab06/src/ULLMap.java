import java.util.Iterator;
import java.util.Set;

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Key operations are get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. */
/** 一种使用链表存储键值对的数据结构。
 *  任何键在字典中最多只能出现一次，但值可以出现多次。主要操作包括 get(key)、put(key, value) 和 contains(key) 方法。与某个键关联的值是最近一次调用 put 方法时该键对应的值。*/
public class ULLMap<K extends Comparable<K>, V>  implements Map61B<K, V> {

    int size = 0;

    /** Returns the value corresponding to KEY or null if no such value exists. */
    /** 返回与 KEY 对应的值；如果不存在这样的值，则返回 null。*/
    public V get(K key) {
        if (list == null) {
            return null;
        }
        Entry lookup = list.get(key);
        if (lookup == null) {
            return null;
        }
        return lookup.val;
    }

    @Override
    public int size() {
        return size;
    }

    /** Removes all of the mappings from this map. */
    /** 从此映射中移除所有映射关系。*/
    @Override
    public void clear() {
        size = 0;
        list = null;
    }

    /** Inserts the key-value pair of KEY and VALUE into this dictionary,
     *  replacing the previous value associated to KEY, if any. */
    /** 将键值对 KEY 和 VALUE 插入到此字典中，

     * 如果存在，则替换与 KEY 关联的先前值。*/
    public void put(K key, V val) {
        if (list != null) {
            Entry lookup = list.get(key);
            if (lookup == null) {
                list = new Entry(key, val, list);
            } else {
                lookup.val = val;
            }
        } else {
            list = new Entry(key, val, list);
            size = size + 1;
        }
    }

    /** Returns true if and only if this dictionary contains KEY as the
     *  key of some key-value pair. */
    /** 当且仅当此字典包含以 KEY 作为键的键值对时，返回 true。*/
    public boolean containsKey(K key) {
        if (list == null) {
            return false;
        }
        return list.get(key) != null;
    }

    @Override
    public Iterator<K> iterator() {
        return new ULLMapIter();
    }

    /** Keys and values are stored in a linked list of Entry objects.
     *  This variable stores the first pair in this linked list. */
    /** 键和值存储在由 Entry 对象组成的链表中。
     *  此变量存储该链表中的第一个键值对。*/
    private Entry list;

    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    /** 表示链表中的一个节点，用于存储字典中的键值对。*/
    private class Entry {

        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        /** 将 KEY 存储为此键值对的键，VAL 存储为值，
         *  NEXT 存储为链表中的下一个节点。*/
        Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        /** 返回此键值对链表中键等于 KEY 的条目，如果不存在这样的条目，则返回 null。*/
        Entry get(K k) {
            if (k != null && k.equals(key)) {
                return this;
            }
            if (next == null) {
                return null;
            }
            return next.get(key);
        }

        /** Stores the key of the key-value pair of this node in the list. */
        /** 存储此节点在列表中键值对的键。*/
        K key;
        /** Stores the value of the key-value pair of this node in the list. */
        /** 存储列表中此节点的键值对的值。*/
        V val;
        /** Stores the next Entry in the linked list. */
        /** 存储链表中的下一个条目。*/
        Entry next;

    }

    /** An iterator that iterates over the keys of the dictionary. */
    /** 一个用于遍历字典键的迭代器。*/
    private class ULLMapIter implements Iterator<K> {

        /** Create a new ULLMapIter by setting cur to the first node in the
         *  linked list that stores the key-value pairs. */
        /** 通过将 cur 设置为存储键值对的链表中的第一个节点来创建一个新的 ULLMapIter 对象。*/
        public ULLMapIter() {
            cur = list;
        }

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public K next() {
            K ret = cur.key;
            cur = cur.next;
            return ret;
        }

        /** Stores the current key-value pair. */
        /** 存储当前的键值对。*/
        private Entry cur;

    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

}
