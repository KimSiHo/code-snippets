package me.bigmonkey.java.jcf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaMap {

    public static void main(String[] args) {
        JavaMap javaMap = new JavaMap();

        //javaMap.test1();
        javaMap.test2();
    }

    // HashMap 기초 사용법
    private void test1() {
        // HashMap 선언
        Map<Integer, String> mapTest = new HashMap<>();

        // 데이터 삽입        
        mapTest.put(1, "Java");
        mapTest.put(2, "C++");
        mapTest.put(3, "python");
        mapTest.put(4, "JavaScript");

        // Key 삭제        
        mapTest.remove(4);

        // Key 존재 확인        
        System.out.println(mapTest.containsKey(4)); // false
        Iterator<Integer> iterator = mapTest.keySet().iterator();
        while (iterator.hasNext()) {
            int key = iterator.next();
            String value = mapTest.get(key);
            System.out.println(key + " " + value);
        }
        System.out.println("======================");

        // Value 존재 확인        
        System.out.println(mapTest.containsValue("Java")); // true  

        // Key의 Value 확인        
        System.out.println(mapTest.get(1)); // Java

        // isEmpty()        
        System.out.println(mapTest.isEmpty()); // false

        // check HashMap size        
        System.out.println(mapTest.size()); // 3

        // HashMap toString        
        System.out.println(mapTest.toString()); // {1=Java, 2=C++, 3=python}   

        // Return a Collection view of the values contained in this map
        System.out.println(mapTest.values()); // [Java, C++, python] 

        // 데이터 출력        
        //  keySet : a set view of the iterator1 contained in this map
        Iterator<Integer> iterator1 = mapTest.keySet().iterator();
        while (iterator1.hasNext()) {
            int key = iterator1.next();
            String value = mapTest.get(key);
            System.out.println(key + " " + value);
        }

        // HashMap removes all of the mappings from this map
        mapTest.clear();
    }

    private void test2() {
        Map<String, Integer> hm = new HashMap<>();

        hm.put("key1", 1);
        hm.put("key2", 2);
        hm.put("key3", 3);

        if (hm.containsKey("key1") && hm.containsValue(1)) {
            System.out.println("YES");
        }

        hm.put("key1", hm.getOrDefault(("key1"), 0) * 10);

        List<String> keyList = new ArrayList<>(hm.keySet());

        // 오름차순으로 키 정렬
        Collections.sort(keyList, (s1, s2) -> Integer.compare(hm.get(s1), hm.get(s2)));

        // 출력
        for (String s : keyList) {
            System.out.println(s + " = " + hm.get(s));
        }

        System.out.println(hm.size());
    }
}
