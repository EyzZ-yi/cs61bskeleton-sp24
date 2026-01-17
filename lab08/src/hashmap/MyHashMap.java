package hashmap;

import java.util.*;

public class MyHashMap<K, V> implements Map61B<K, V> {
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    int Size=0;//存入元素个数
    int SizeOfcapacity;//预存数组容量
    double load;//当前负载
    double LoadFactor;//负载因子
    // You should probably define some more!

    /** Constructors */
    public MyHashMap() {
        SizeOfcapacity=16;
        LoadFactor=0.75;
        buckets=new Collection[16];
 }

    public MyHashMap(int initialCapacity/*初始容量*/) {
         //Collection<Node>[] p=buckets;
        SizeOfcapacity=initialCapacity;
        LoadFactor=0.75;
        buckets=new Collection[initialCapacity];
        for(int i=0;i<initialCapacity;i++){
            buckets[i]=createBucket();
        }

    }
    public MyHashMap(int initialCapacity, double loadFactor) {
        SizeOfcapacity=initialCapacity;
        buckets=new Collection[initialCapacity];
        load=0;
        LoadFactor=loadFactor;
        for(int i=0;i<initialCapacity;i++){
            buckets[i]=createBucket();
        }
    }


    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }
    @Override
    public void put(K key, V value) {
        Node now = new Node(key, value);
        if(size()==0){
            Size++;
            for(int i=0;i<SizeOfcapacity;i++){
            buckets[i]=createBucket();
        }
            int i = key.hashCode();
            int place = Math.floorMod(i, SizeOfcapacity);
            buckets[place].add(now);
            return;
        }
        int i = key.hashCode();
        int place = Math.floorMod(i, SizeOfcapacity);
        if (this.containsKey(key)) {//map包含该key
////           Node pre = new Node(key, get(key));
////          buckets[place].remove(pre);
////            buckets[place].add(now);
        ////为什么remove会失效？
            ////because：remove(pre) 失败是因为你传进去的 pre 是你刚 new 出来的一个新对象。虽然它的 key 和 value 跟原来的那个一样，但在 Java 看来，它们是两个完全不同的“人”（内存地址不同）。
////remove 的工作原理
/// Java 的 Collection.remove(Object o) 方法在底层是依靠 .equals() 方法来判断是否要删除某个元素的。
/// 在你的 Node 类定义中（我看不到你重写了 equals），默认继承的是 Object 类的 equals 方法。
/// 默认的 equals (Object类)：比较的是内存地址 (即 ==)。
            // 1. 遍历桶，查找是否已存在
            for (Node node : buckets[place]) {
                // 必须用 .equals 比较 key
                if (node.key.equals(key)) {
                    // 找到了！直接覆盖旧的值
                    node.value = value;
                    return; // 任务完成，直接结束，不用再 add 了
                }
            }
        } else {//map不包含该key
            Size++;
            if (1.0 * Size / SizeOfcapacity <= LoadFactor) {
                buckets[place].add(now);
            } else {//负载超过负载因子
                int reSizeOfcapacity=SizeOfcapacity*2;
                K[] preset = (K[]) keySet().toArray();
                Collection<Node>[] temporarybuckets = new Collection[reSizeOfcapacity];
                for(int m = 0; m <reSizeOfcapacity; m++) temporarybuckets[m]=createBucket();
                for (int j = 0; j < size()-1; j++) {
                     now = new Node(preset[j], get(preset[j]));
                    i = preset[j].hashCode();
                     place = Math.floorMod(i, reSizeOfcapacity);
                    temporarybuckets[place].add(now);
                }
                buckets=temporarybuckets;
                SizeOfcapacity *= 2;
                now = new Node(key, value);
                i = key.hashCode();
                 place = Math.floorMod(i, SizeOfcapacity);
                buckets[place].add(now);
                return;
            }
        }
    }

    @Override
    public V get(K key) {
        int i = key.hashCode();
        int place = Math.floorMod(i, SizeOfcapacity);
        LinkedList<Node> temporary=(LinkedList<Node>) buckets[place];
        if(buckets[place]!=null){
        int j;
        for(j=0;j<buckets[place].size();j++){
            if(temporary.get(j).key.equals(key)) {
                return temporary.get(j).value;}
        }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        //return get(key)!=null;
        int i = key.hashCode();
        int place = Math.floorMod(i, SizeOfcapacity);
        LinkedList<Node> temporary=(LinkedList<Node>) buckets[place];
        if(buckets[place]!=null){
        for(int j=0;j<buckets[place].size();j++){
            if(temporary.get(j).key.equals(key)) return true;
        }
        }
        return false;
    }

    @Override
    public int size() {
        return Size;
    }

    @Override
    public void clear() {
            // 遍历每一个桶，直接清空桶
            for (int i = 0; i < buckets.length; i++) {
                buckets[i].clear(); // LinkedList 自带的 clear，一键清空
            }
         Size = 0; // 别忘了重置总大小
//        for(int i=0;i<SizeOfcapacity;i++){
//            LinkedList<Node> temporary=(LinkedList<Node>) buckets[i];
//            while (temporary!=null && temporary.getFirst()!=null){
//                temporary.removeFirst();
//            }
//            buckets[i]=temporary;
//     }
//       Size=0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keyset=new HashSet<>();
        for(int i=0;i<SizeOfcapacity;i++){
            //pre of for
            for(Node n:buckets[i]){
                keyset.add(n.key);
            }
        }
        return keyset;
    }
//            LinkedList<Node> temporary=(LinkedList<Node>) buckets[i];
//            for(int j=0;j<buckets[i].size();j++){
//                keyset.add(temporary.get(j).key);
//            }

    @Override
    public V remove(K key) {
        if (!containsKey(key)) return null;
        Size--;
        int i = key.hashCode();
        int place = Math.floorMod(i, SizeOfcapacity);
        int z=0;
        for(Node n:buckets[place]){
            if(n.key.equals(key)){
                break;}
            z++;
        }
        return ((LinkedList<Node>) buckets[place]).remove(z).value;
//        LinkedList<Node> temporary = (LinkedList<Node>) buckets[place];
//        int j;
//        for (j = 0; j < buckets[place].size(); j++) {
//            if (temporary.get(j).key == key) {
//                break;
//            }
//        }
//        Node n=temporary.remove(j);
//        buckets[place]=temporary;
//        return n.value;
    }

    @Override
    public Iterator<K> iterator() {
        throw new IllegalArgumentException();
        //return null;
    }


}
