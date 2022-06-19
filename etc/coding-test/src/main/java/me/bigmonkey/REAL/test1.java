package me.bigmonkey.REAL;

import java.util.PriorityQueue;
import java.util.Queue;

public class test1 {

    /*static int[] A = {2, 1, 4, 4};*/
    static int[] A = {6, 2, 3, 5, 6, 3};

    public static void main(String[] args) {
        int count = 0;

        Queue<Integer> queue = new PriorityQueue<>();

        for (int i = 0; i < A.length; i++) {
            queue.add(A[i]);
        }

        for (int i = 1; i <= A.length; i++) {
            Integer poll = queue.poll();
            if(poll == i) continue;
            else if(poll < i) {
                for (int j = poll; j < i; j++) {
                    count++;
                }
            }
            else if(poll > i) {
                for (int j = poll; j > i; j--) {
                    count++;
                }
            }
        }
        if (count > 1000000000) {
            System.out.println(-1);
        }

        System.out.println("count = " + count);
    }
}
