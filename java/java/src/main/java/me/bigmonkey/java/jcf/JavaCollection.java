package me.bigmonkey.java.jcf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class JavaCollection {

    public static void main(String[] args) {
        Collection collection = new ArrayList<Integer>();
        collection.add(3);
        collection.add(4);
        doSomething(collection);

        collection = new HashSet();
        collection.add(5);
        collection.add(6);
        doSomething(collection);
    }

    private static void doSomething(Collection collection) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next.toString());
        }
    }
}
