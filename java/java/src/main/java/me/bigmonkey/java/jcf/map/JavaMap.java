package me.bigmonkey.java.jcf.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class JavaMap {

    public static void main(String[] args) {
        JavaMap javaMap = new JavaMap();

        javaMap.test1();
        javaMap.test2();
        javaMap.test3();

    }

    // HashMap 기초 사용법 테스트
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

        System.out.println("============= test1 =============");
    }

    // 키 정렬 comparator 방법
    private void test2() {
        // Map 선언
        Map<Integer, String> testMap = new HashMap<>();

        // Map에 데이터 추가
        testMap.put(1, "apple");
        testMap.put(4, "pineapple");
        testMap.put(2, "orange");
        testMap.put(5, "strawberry");
        testMap.put(3, "melon");

        // 키로 정렬
        Object[] mapkey = testMap.keySet().toArray();
        Arrays.sort(mapkey);

        // 결과 출력
        for (Integer nKey : testMap.keySet()) {
            System.out.println(testMap.get(nKey));
        }

        System.out.println("============= test2 =============");
    }

    // key 정렬 treeMap 사용
    private void test3() {
        // Map 선언
        Map<Integer, String> testMap = new TreeMap<>();

        // Map에 데이터 저장
        testMap.put(1, "apple");
        testMap.put(5, "pineapple");
        testMap.put(2, "orange");
        testMap.put(3, "strawberry");
        testMap.put(4, "melon");

        // 결과 출력
        for (Integer nKey : testMap.keySet())
        {
            System.out.println(testMap.get(nKey));
        }

    }
}