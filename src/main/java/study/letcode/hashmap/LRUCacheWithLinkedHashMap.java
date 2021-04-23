package study.letcode.hashmap;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * # 146 定义一个LRU缓存
 */
public class LRUCacheWithLinkedHashMap extends LinkedHashMap<Integer,Integer> {

    //LRU容量
    private int capacity ;

    public LRUCacheWithLinkedHashMap(int capacity){
        // accessOrder = true 按照访问次序去调整当前双向链表的访问次序
        super(capacity,0.75f,true);
        this.capacity = capacity ;
    }

    //访问数据的get方法
    public int get(int key){
        if (super.get(key) == null) return -1 ;
        return super.get(key) ;
    }

    //put 方法
    public void put(int key , int value){
        super.put(key,value) ;
    }

    //重写是否删除元素的方法

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size()>capacity ;
    }
}
