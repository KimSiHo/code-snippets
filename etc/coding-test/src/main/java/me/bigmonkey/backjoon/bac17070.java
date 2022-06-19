package me.bigmonkey.backjoon;

public class bac17070 {

    /*static int n = 3;
    static int[][] graph = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}; // 정답 1*/

    /*static int n = 4;
    static int[][] graph = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}}; // 정답 3*/

    /*static int n = 5;
    static int[][] graph = {{0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}; // 정답 0*/

    static int n = 6;
    static int[][] graph = {{0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}}; // 정답 13

    static int res = 0;
    public static void main(String[] args) {

        dfs(0, 1, 0);

        System.out.println(res);
    }

    private static void dfs(int x, int y, int mode) {
        if(x == n - 1&& y == n - 1) {
            res++; return;
        }

        int xx, yy;
        if(mode == 0) {
            xx = x;
            yy = y + 1;
            if(yy < n && graph[xx][yy] == 0) dfs(xx, yy, 0);

            xx = x + 1;
            yy = y + 1;
            if(xx < n && yy < n && graph[xx][yy] == 0 && graph[xx - 1][yy] == 0 && graph[xx][yy - 1] == 0) dfs(xx, yy, 2);

        } else if(mode == 1) {
            xx = x + 1;
            yy = y;
            if(xx < n && graph[xx ][yy] == 0) dfs(xx, yy, 1);

            xx = x + 1;
            yy = y + 1;
            if(xx < n && yy < n && graph[xx][yy] == 0 && graph[xx - 1][yy] == 0 && graph[xx][yy - 1] == 0) dfs(xx, yy, 2);

        } else if(mode == 2) {
            xx = x;
            yy = y + 1;
            if(yy < n && graph[xx][yy] == 0) dfs(xx, yy, 0);

            xx = x + 1;
            yy = y;
            if(xx < n && graph[xx][yy] == 0) dfs(xx, yy, 1);

            xx = x + 1;
            yy = y + 1;
            if(xx < n && yy < n && graph[xx][yy] == 0 && graph[xx - 1][yy] == 0 && graph[xx][yy - 1] == 0) dfs(xx, yy, 2);

        }
    }
}
