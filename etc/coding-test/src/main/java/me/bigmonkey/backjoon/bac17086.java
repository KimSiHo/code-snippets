package me.bigmonkey.backjoon;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

// 맞긴 맞았는데 효율은.. // 그냥 상어의 배열을 갖고 있다가 0인 좌표마다 상어의 배열로 전체 순회..
public class bac17086 {

    /*public static int m = 5;
    public static int n = 4;
    static int[][] arr = {{0,0,1,0},{0,0,0,0},{1,0,0,0},{0,0,0,0},{0,0,0,1}}; // 정답 2*/

    public static int m = 7;
    public static int n = 4;
    static int[][] arr = {{0,0,0,1},{0,1,0,0},{0,0,0,0},{0,0,0,1},{0,0,0,0},{0,1,0,0},{0,0,0,1}}; // 정답 2
    static boolean[][] visited = new boolean[m][n];

    public static final int SHARK = 1;

    static int dx[] = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int dy[] = {-1, 0, 1, 1, 1, 0, -1, -1};

    public static void main(String[] args) {
        int ret = -1;
        Queue<Pos> queue = new LinkedList<>();

        for (int x = 0; x < m; x++) {

            for (int y = 0; y < n; y++) {
                if(arr[x][y] == SHARK) continue;
                queue.add(new Pos(x, y, 0));
                visited = new boolean[m][n];
                visited[x][y] = true;

                while(hasItem(queue)) {
                    Pos poll = queue.poll();
                    if(arr[poll.x][poll.y] == SHARK) {
                        if(poll.dis > ret) {
                            ret = poll.dis;
                        }
                        queue.clear();
                        break;
                    }
                    for (int i = 0; i < dx.length; i++) {
                        int xx = poll.x + dx[i];
                        int yy = poll.y + dy[i];
                        if(xx >= 0 && xx < m && yy >= 0 && yy < n && !visited[xx][yy]) {
                            queue.add(new Pos(xx, yy, poll.dis + 1));
                            visited[xx][yy] = true;
                        }
                    }
                }
            }
        }

        System.out.println(ret);

    }

    private static boolean hasItem(Queue<Pos> queue) {
        return !queue.isEmpty();
    }

    static class Pos {
        int x;
        int y;
        int dis;

        public Pos(int x, int y, int dis) {
            this.x = x;
            this.y = y;
            this.dis = dis;
        }
    }


}
