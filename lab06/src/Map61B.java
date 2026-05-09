import java.util.Set;

/* Your implementation BSTMap should implement this interface. To do so,
 * append "implements Map61B<K, V>" to the end of your "public class..."
 * declaration, though you can and should use other type parameters when
 * necessary.
 *//* 您的 BSTMap 实现类应该实现此接口。为此，请在您的“public class...”声明末尾添加“implements Map61B<K, V>”，当然，如有必要，您也可以并且应该使用其他类型参数。
 */
public interface Map61B<K, V> extends Iterable<K> {

    /** Associates the specified value with the specified key in this map.
     *  If the map already contains the specified key, replaces the key's mapping
     *  with the value specified. */
    /** 将指定的值与此映射中的指定键关联起来。
     *  如果此映射已包含指定的键，则用指定的值替换该键的映射。*/
    void put(K key, V value);

    /** Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. */
    /** 返回指定键所映射的值；如果此映射不包含该键的映射关系，则返回 null。*/
    V get(K key);

    /** Returns whether this map contains a mapping for the specified key. */
    /** 返回此映射是否包含指定键的映射关系。*/
    boolean containsKey(K key);

    /** Returns the number of key-value mappings in this map. */
    /** 返回此映射中键值对的数量。*/
    int size();

    /** Removes every mapping from this map. */
    /** 从此映射中移除所有映射关系。*/
    void clear();

    /** Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    /** 返回此映射中包含的键的集合视图。这不是实验七的要求。
     * 如果您不实现此方法，请抛出 UnsupportedOperationException 异常。*/
    Set<K> keySet();

    /** Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    /** 如果此映射中存在指定键的映射关系，则移除该映射；
     * 如果不存在此类映射关系，则返回 null。
     * 实验七不需要实现此方法。如果您不实现此方法，请抛出 UnsupportedOperationException 异常。*/
    V remove(K key);
}
