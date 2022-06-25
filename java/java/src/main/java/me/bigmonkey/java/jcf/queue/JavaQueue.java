package me.bigmonkey.java.jcf.queue;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class JavaQueue {

    public static void main(String[] args) {
        JavaQueue javaQueue = new JavaQueue();

        javaQueue.test1();
        javaQueue.test2();
    }

    private void test1() {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        queue.offer(2);

        Integer peek = queue.peek();
        Integer poll = queue.poll();
        System.out.println("peek = " + peek);
        System.out.println("poll = " + poll);

        // add(value) 메서드의 경우 삽입에 성공하면 true 큐에 여유 공간이 없어서 실패하면 Exception

        // 첫번째 값을 반환하고 제거 비어있다면 null > poll()
        // 첫번째 값 제거 비어있다면 예외 발생 > remove()

        // 첫번째 값을 반환만 하고 제거 하지는 않는다.
        // 큐가 비어있다면 null을 반환 > peek()
        // 첫번째 값을 반환만 하고 제거 하지는 않는다 큐가 비어있다면 예외 발생 > element()

        System.out.println("============= test1 =============");
    }

    // iterator로 순회하면 순서 보장이 안 된다!
    private void test2() {
        Queue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        queue.add(2);
        queue.add(1);
        queue.add(4);
        queue.add(7);
        queue.add(15);
        queue.add(3);

        Iterator<Integer> iterator = queue.iterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("==");

        Queue<Integer> queueInverse = new PriorityQueue<>(Collections.reverseOrder());
        queueInverse.add(2);
        queueInverse.add(1);
        queueInverse.add(4);
        queueInverse.add(7);
        queueInverse.add(15);
        queueInverse.add(3);

        while(!queueInverse.isEmpty()) {
            System.out.println(queueInverse.poll());
        }

        System.out.println("============= test2 =============");
    }
}
