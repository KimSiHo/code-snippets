package me.bigmonkey.kakao.kakao2022;

import java.util.Arrays;

public class 양궁대회 {



   /* static int[] result = new int[11];
    static int[] ansArr = new int[11];
    static int ans = 0;

    public static void main(String[] args) {

        dfs(10, 0);
        if (ans == 0) {
            System.out.println("[-1]");
            return;
        }
        System.out.print("[");
        for (int i = 0; i <= 10; i++) {
            System.out.print(ansArr[i] + ",");
            if (i == 10) System.out.print(ansArr[10]);
        }
        System.out.print("]");
    }

    private static void dfs(int start, int total) {
        if(total > n) return;
        if(total == n) {
            int sumOfApache = 0;
            int sumOfLion = 0;
            for (int i = 0; i <= 10; i++) {
                if(info[i] == 0 && result[i] == 0) continue;
                if(info[i] >= result[i]) sumOfApache += 10-i;
                else if(result[i] > info[i]) sumOfLion += 10-i;
            }

            if(sumOfLion - sumOfApache > ans) {
                ans = sumOfLion - sumOfApache;
                for (int i = 0; i <= 10; i++) {
                    ansArr[i] = result[i];
                }
            }
            return;
        }


        for (int i = start; i >= 0; i--) {

            int cnt = info[i] + 1;
            result[i] = cnt;
            total += cnt;
            dfs(i - 1, total);
            result[i] = 0;
            total -= cnt;
        }
    }*/

       static int n = 5;
    static int[] info = {2,1,1,1,0,0,0,0,0,0,0}; // 정답 [0,2,2,0,1,0,0,0,0,0,0]

    /*static int n = 1;
    static int[] info = {1,0,0,0,0,0,0,0,0,0,0}; // 정답 [-1]*/

    /*static int n = 9;
    static int[] info = {0,0,1,2,0,1,1,1,1,1,1}; // 정답 [1,1,2,0,1,2,2,0,0,0,0]*/

    static int[] res = { -1 };
    static int[] lion;
    static int max = -1000;

    public static void main(String[] args) {
        lion = new int[11];
        dfs(1);
        System.out.println(Arrays.toString(res));
        //return res;
    }


    public static void dfs(int cnt) {
        if(cnt == n+1) {
            int apeach_point = 0;
            int lion_point = 0;
            for(int i = 0; i <= 10; i++)
            {
                if(info[i] != 0 || lion[i] != 0) {
                    if(info[i] < lion[i])
                        lion_point += 10 - i;
                    else
                        apeach_point += 10 - i;
                }
            }
            if(lion_point > apeach_point) {
                if(lion_point - apeach_point >= max)
                {
                    res = lion.clone();
                    max = lion_point - apeach_point;
                }
            }
            return ;
        }
        for(int j = 0; j <= 10 && lion[j] <= info[j]; j++) {
            lion[j]++;
            dfs(cnt + 1);
            lion[j]--;
        }
    }
}
