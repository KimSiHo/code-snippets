package me.bigmonkey.etc;

public class 조합구하기 {

    static int n, r;
    static int ch[] = new int[20];

    public static void main(String[] args) {
        n = 6; r = 4;
        dfs(0, 0);
    }

    private static void dfs(int start, int level) {
        if(level == r) {
            for (int j = 0; j < level; j++) {
                System.out.print(ch[j] + " ");
            }
            System.out.println();
        }
        else {
            for (int i = start; i < n; i++) {
                ch[level] = i;
                dfs(i + 1, level + 1);
            }
        }
    }
}
