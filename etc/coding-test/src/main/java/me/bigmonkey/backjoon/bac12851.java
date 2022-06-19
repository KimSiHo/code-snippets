package me.bigmonkey.backjoon;

import java.util.LinkedList;
import java.util.Queue;

public class bac12851 {

    static int[] arr = new int[100001];

    /*static int n = 5;
    static int k = 17; // 정답 4, 2*/

    /*static int n = 5;
    static int k = 237; // 정답 10, 5*/

    static int n = 1;
    static int k = 10; // 정답 4, 2

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(n);
        arr[n] = 0;

        int cnt = 0;
        int time = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Integer cur = queue.poll();
            if(time < arr[cur]) return;

            if(cur == k) {
                time = arr[cur];
                cnt++;
                continue;
            }

            for (int i = 0; i < 3; i++) {
                int pos = -1;
                if(i == 0) pos = cur - 1;
                if(i == 1) pos = cur + 1;
                if(i == 2) pos = cur * 2;
                if(pos < 0 || pos > 100000) continue;

                if(arr[pos] == 0) {
                    queue.add(pos);
                    arr[pos] = arr[cur] + 1;
                }
                else if(arr[pos] == arr[cur] + 1) queue.add(pos);
            }
        }

        System.out.println(time);
        System.out.println(cnt);
    }
}
