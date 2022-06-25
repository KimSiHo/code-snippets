package me.bigmonkey.backjoon;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class bac13913 {

    public static final int N = 5;
    public static final int K = 17;
    public static int[] path = new int[100001];
    public static int[] visited = new int[100001];

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(N);
        Arrays.fill(visited, -1);
        visited[N] = 0;

        while (!queue.isEmpty()) {
            Integer pos = queue.poll();
            if (pos == K) {
                break;
            }

            if (pos - 1 >= 0 && visited[pos - 1] == -1) {
                path[pos - 1] = pos;
                visited[pos - 1] = visited[pos] + 1;
                queue.offer(pos - 1);
            }

            if (pos + 1 <= 100000 && visited[pos + 1] == -1) {
                path[pos + 1] = pos;
                visited[pos + 1] = visited[pos] + 1;
                queue.offer(pos + 1);
            }

            if (pos * 2 <= 100000 && visited[pos * 2] == -1) {
                path[pos * 2] = pos;
                visited[pos * 2] = visited[pos] + 1;
                queue.offer(pos * 2);
            }
        }

        StringBuilder ans = new StringBuilder();
        System.out.println(visited[K]);
        ans.append(K + " ");
        for (int x = K; x != N; ) {
            ans.insert(0, path[x] + " ");
            x = path[x];
        }

        System.out.println("ans = " + ans.toString().trim());
    }
}
