package me.bigmonkey.programmers;

public class N_Queen {

    public static final int N = 4;  // 정답 2
    static int ans = 0;
    static int[] arr;

    public static void main(String[] args) {

        // 각 열의 행을 기록
        arr = new int[N + 1];

        // 1열 부터 시작
        dfs(1);

        System.out.println("ans = " + ans);
    }

    private static void dfs(int col) {
        if (col == N + 1) {
            ans++;
            return;
        }

        for (int row = 1; row <= N; row++) {
            arr[col] = row;

            // 해당 열의 row 가 valid 하면 다음 열로 넘어간다
            if (isValid(col)) {
                dfs(col + 1);
            }
        }
    }

    private static boolean isValid(int col) {
        for (int i = 1; i < col; i++) {
            if (arr[col] == arr[i]) {
                return false;
            }
            if (Math.abs(col - i) == Math.abs(arr[col] - arr[i])) {
                return false;
            }
        }

        return true;
    }
}
