package me.bigmonkey.java.jcf;

import java.util.HashSet;
import java.util.Iterator;

public class JavaSet {

    public static void main(String[] args) {
        JavaSet javaSet = new JavaSet();

        javaSet.test1();
    }

    // HashSet 기본 사용법
    private void test1() {
        HashSet<Integer> hashSet = new HashSet<>();

        hashSet.add(10);
        hashSet.add(20);
        hashSet.add(30);
        hashSet.add(40);
        hashSet.add(50);

        // 중복을 허용하지 않음
        hashSet.add(10);
        hashSet.add(20);
        hashSet.add(30);

        hashSet.remove(40);

        System.out.println(hashSet.contains(40));

        System.out.println(hashSet.isEmpty());

        Iterator<Integer> iterator = hashSet.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        hashSet.clear();
    }
}
