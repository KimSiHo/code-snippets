package me.bigmonkey.REAL;

public class test1 {

    public static int[] grade = {3, 2, 3, 6, 4, 5};
    public static int ans = Integer.MAX_VALUE;

    public static void main(String[] args) {
        dfs(0, 0);
        System.out.println("ans = " + ans);
    }

    private static void dfs(int index, int sum) {
        if (index == grade.length - 1) {
            if(grade[index - 1] > grade[index]) {
                sum += grade[index] - grade[index - 1];
            }
            if (sum < ans) {
                ans = sum;
            }
            return;
        }


        if (grade[index] > grade[index + 1]) {
            int dis = grade[index + 1] - grade[index];
            grade[index] -= dis;
            dfs(index + 1, sum += dis);
            grade[index] += dis;
        } else if (index > 0 && grade[index - 1] > grade[index]) {
            int dis = grade[index] - grade[index - 1];
            grade[index] += dis;
            dfs(index + 1, sum += dis);
            grade[index] -= dis;
        } else {
            dfs(index + 1, sum);
        }

    }
}
