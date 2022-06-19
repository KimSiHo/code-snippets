package me.bigmonkey.kakao.kakao2022;

import java.util.ArrayList;

//로직은 맞는데, arrayList 쓰는 게 맘에 걸리네.. 시간도 다 통과하긴 하는데..
public class 양과늑대 {
/*    static int[] info = {0,0,1,1,1,0,1,0,1,0,1,1};
    static int[][] edges = {{0,1}, {1,2}, {1,4}, {0,8}, {8,7}, {9,10}, {9,11}, {4,3}, {6,5}, {4,6}, {8,9}}; //정답 5*/

    static int[] info = {0, 1, 0, 1, 1, 0, 1, 0, 0, 1, 0};
    static int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}, {3, 7}, {4, 8}, {6, 9}, {9, 10}}; //정답 5

    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int res = Integer.MIN_VALUE;

    public static void main(String[] args) {
        graph = new ArrayList[info.length];
        visited = new boolean[info.length];

        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        ArrayList<Integer> nodeList = new ArrayList<>();
        visited[0] = true;
        nodeList.add(0);

        dfs(0, 0, 0, nodeList);

        System.out.println(res);
    }

    private static void dfs(int num, int wolf, int sheep, ArrayList<Integer> nodeList) {
        if (info[num] == 0) sheep += 1;
        else wolf += 1;

        if (wolf >= sheep) return;

        res = Math.max(sheep, res);

        ArrayList<Integer> nextList = new ArrayList<>(nodeList);
        nextList.remove((Integer) num);

        for (int i = 0; i < graph[num].size(); i++) {
            if (visited[graph[num].get(i)]) continue;

            nextList.add(graph[num].get(i));
        }

        for (int i = 0; i < nextList.size(); i++) {
            visited[nextList.get(i)] = true;
            dfs(nextList.get(i), wolf, sheep, nextList);
            visited[nextList.get(i)] = false;
        }
    }
}
