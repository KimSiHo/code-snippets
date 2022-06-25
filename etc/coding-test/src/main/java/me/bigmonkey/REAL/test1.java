package me.bigmonkey.REAL;

public class test1 {

    /*static String[][] map3d = {{"XXXXX", "OOSXO", "OOXOO"}, {"XEOOO", "OXXXO", "OOOOX"}}; // 정답 13*/
    /*static String[][] map3d = {{"OOOOO", "OOOOO", "OOEOO", "OOOOO", "OOOOO"}, {"OOOOO", "OXXXO", "OXXXO", "OXXXO", "OOOOO"}, {"OOOOO", "OOOOO", "OOSOO", "OOOOO", "OOOOO"}}; // 정답 6*/
    static String[][] map3d = {{"SOXX", "OOXX"}, {"XXOO", "XXOE"}}; // 정답 -1

    // 이동 좌표 변수 선언 : 위 아래, 앞 뒤, 좌 우 순
    static int dx[] = {0, 0, 0, 0, -1, 1};
    static int dy[] = {0, 0, -1, 1, 0, 0};
    static int dz[] = {1, -1, 0, 0, 0, 0};

    // 그래프 저장할 3차원 변수, 각 x y z 길이, start end 변수 선언
    static int ans = Integer.MAX_VALUE;
    static Pos end, start ;
    static int xDis, yDis, zDis;
    static int[][][] maps;
    static boolean[][][] visited;

    public static void main(String[] args) {
        zDis = map3d.length;             // z 길이
        yDis = map3d[0].length;          // y 길이
        xDis = map3d[0][0].length();     // x 길이
        maps = new int[xDis][yDis][zDis];
        visited = new boolean[xDis][yDis][zDis];

        for (int z = 0; z < zDis; z++) {
            String[] strings = map3d[z];
            int zz = zDis - 1 - z;      // 그래프 모형을 시뮬레이션 하기 위해 변수 설정

            for (int y = 0; y < strings.length; y++) {
                String str = strings[y];
                int yy = strings.length - 1 - y;    // 그래프 모형을 시뮬레이션 하기 위해 변수 설정

                for (int x = 0; x < str.length(); x++) {
                    char sel = str.charAt(x);
                    switch (sel) {
                        case 'X':
                            maps[x][yy][zz] = -1;
                            break;

                        case 'O':
                            maps[x][yy][zz] = 1;
                            break;

                        case 'S':
                            start = new Pos(x, yy, zz);
                            maps[x][yy][zz] = 1;
                            break;

                        case 'E':
                            maps[x][yy][zz] = 1;
                            end = new Pos(x, yy, zz);
                            break;
                    }
                }
            }
        }

        dfs(start.x, start.y, start.z, 0);
        if(ans == Integer.MAX_VALUE) {
            System.out.println(-1);
            return;
        }
        System.out.println("ans = " + ans);

    }

    private static void dfs(int x, int y, int z, int count) {
        if(x < 0 || x >= xDis || y < 0 || y >= yDis || z < 0 || z >= zDis) return; // 3차원 그래프 범위 초과
        if(visited[x][y][z]) return;
        if(maps[x][y][z] == -1) return; // 이동 불가능
        if(count > ans) return; // 최소 조건 만족 X

        if(x == end.x && y == end.y && z == end.z) {
            if(count < ans) {
                ans = count;
            }
            return;
        }

        visited[x][y][z] = true;
        for (int i = 0; i < dx.length; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];
            int zz = z + dz[i];
            dfs(xx, yy, zz, count + 1);
        }
    }

    static class Pos{
        int x;
        int y;
        int z;

        public Pos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

