package me.bigmonkey.backjoon;

import java.util.LinkedList;

// 로직은 맞는데 메모리 초과.. 백준 자바는 특별한 수 쓰지 않은 이상 메모리 초과는 대응하기 힘들 것 같고..
// 2차원 배열로 그래프 구현할 것은 2차원 list 자료형으로 전체 배열을 돌지 말게 구현하는 게 더 좋은 것 같다!
public class bac2252X {

    static int n = 3;
    static int m = 2;
    static int[][] inputs = {{1, 3}, {2, 3}}; // 정답 1 2 3

    public static void main(String[] args) {

        int[][] graph = new int[n + 1][n + 1];
        int[] inDegree = new int[n + 1];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < inputs.length; i++) {
            int[] cur = inputs[i];
            inDegree[cur[1]]++;
            graph[cur[0]][cur[1]] = 1;
        }
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i < inDegree.length; i++) {
            if(inDegree[i] == 0) queue.add(i);

        }

        while (!queue.isEmpty()) {
            Integer cur = queue.pop();
            sb.append(cur + " ");

            for (int i = 1; i <= n; i++) {
                if(graph[cur][i] == 1) {
                    inDegree[i]--;
                    if(inDegree[i] == 0) queue.add(i);
                }
            }

        }

        System.out.println(sb.toString().trim());
    }

}
