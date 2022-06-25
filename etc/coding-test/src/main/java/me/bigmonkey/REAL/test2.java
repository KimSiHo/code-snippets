package me.bigmonkey.REAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class test2 {

    static int N = 5;
    static int K = 4;
    static int[][] paths = {{1,5,1,1},{1,2,4,3},{1,3,3,2},{2,5,2,1},{2,4,2,3},{3,4,2,2}};
    // 1번 공항에서 출발해서 k 번 공항에 도착!

    static int ansTime = Integer.MAX_VALUE;
    static int ansMileage = Integer.MAX_VALUE;
    static boolean[] visited;
    static List<DestInfo>[] nodeInfos;

    public static void main(String[] args) {
        nodeInfos = new ArrayList[N +1]; // 그래프를 저장할 list 배열 생성
        visited = new boolean[N +1];
        for (int i = 1; i <= N; i++) {
            nodeInfos[i] = new ArrayList();
        }

        for (int[] path : paths) { // 양방향으로 도착 정보 생성
            int start = path[0];
            DestInfo destInfo = new DestInfo(path[1], path[2], path[3]);
            nodeInfos[start].add(destInfo);

            start = path[1];
            destInfo = new DestInfo(path[0], path[2], path[3]);
            nodeInfos[start].add(destInfo);
        }

        visited[1]=true;
        dfs(1, 0, 0);
        int[] ansArr = {ansTime, ansMileage};
        System.out.println(Arrays.toString(ansArr));
    }

    private static void dfs(int node, int time, int mileage) {
        if(time > ansTime) return;

        if(node == K) {
            if(time < ansTime) {
                ansTime = time;
                ansMileage = mileage;
            } else if(time == ansTime && mileage > ansMileage) {
                ansMileage = mileage;
            }
        }

        List<DestInfo> destInfos = nodeInfos[node];
        for (DestInfo destInfo : destInfos) {
            if(notVisited(visited[destInfo.node])) {
                visited[destInfo.node] = true;
                dfs(destInfo.node, time + destInfo.time, mileage + destInfo.mileage);
                visited[destInfo.node] = false;
            }
        }
    }

    private static boolean notVisited(boolean b) {
        return !b;
    }

    static class DestInfo {
        int node;
        int time;
        int mileage;

        public DestInfo(int node, int time, int mileage) {
            this.node = node;
            this.time = time;
            this.mileage = mileage;
        }
    }
}
