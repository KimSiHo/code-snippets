package me.bigmonkey.backjoon;

import java.util.LinkedList;


//백준에서 시간 초과나는 이유가 visited 배열을 타임 배열로 선언하고 한번에 이동하는 k라는 개념을
// 이용해서 이미 방문한 적이 있으면 continue가 아니고 break를 사용해서 loop를 줄이는 것 같은데.. 거기까지는 일단 보지 말자 그리고 이게 로직은 맞다!
public class bac16930 {

    /*static int n = 3;
    static int m = 4;
    static int k = 4;
    static int startX = 1;
    static int startY = 1;
    static int endX = 3;
    static int endY = 1;
    static int[][] graph = {{0,0,0,0},{1,1,1,0},{0,0,0,0}}; // 정답 3*/

    static int n = 3;
    static int m = 4;
    static int k = 1;
    static int startX = 1;
    static int startY = 1;
    static int endX = 3;
    static int endY = 1;
    static char[][] graph = {{'.', '.', '.' , '.'}, {'#', '#', '#' , '.'},{'.' , '.', '.', '.'}}; // 정답 8

/*    static int n = 2;
    static int m = 2;
    static int k = 1;
    static int startX = 1;
    static int startY = 1;
    static int endX = 2;
    static int endY = 2;
    static char[][] graph = {{'.', '#'},{'#', '.'}}; // 정답 -1*/

    public static void main(String[] args) {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, -1, 0, 1};

        boolean[][] visited = new boolean[n + 1][m + 1];
        LinkedList<Pos> queue = new LinkedList<>();

        queue.add(new Pos(startX, startY, 0));

        while (!queue.isEmpty()) {
            Pos cur = queue.pop();
            if(cur.x == endX && cur.y == endY) {
                System.out.println(cur.time);
                return;
            }

            for (int i = 0; i < 4; i++) {

                for (int j = 1; j <= k; j++) {
                    int xx = dx[i] * j;
                    int yy = dy[i] * j;

                    xx += cur.x;
                    yy += cur.y;

                    if(xx < 1 || xx > n) break;
                    if(yy < 1 || yy > m) break;
                    if(graph[xx - 1][yy - 1] == '#') break;
                    if(visited[xx][yy]) continue;
                    visited[xx][yy] = true;
                    queue.add(new Pos(xx, yy, cur.time + 1));
                }
            }
        }

        System.out.println(-1);
    }

    static class Pos {
        int x;
        int y;
        int time;

        public Pos(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }
}
