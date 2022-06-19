package me.bigmonkey.backjoon;

import java.util.Scanner;

public class bac1062 {

    /*static int n = 3;
    static int k = 6;
    static String[] word = {"antarctica", "antahellotica", "antacartica"};
    static boolean[] visited = new boolean[26]; // 정답 2*/

    /*static int n = 2;
    static int k = 3;
    static String[] word = {"antaxxxxxxxtica", "antarctica"};
    static boolean[] visited = new boolean[26]; // 정답 0*/

    static int n = 9;
    static int k = 8;
    static String[] word = {"antabtica", "antaxtica", "antadtica", "antaetica", "antaftica", "antagtica", "antahtica", "antajtica", "antaktica"};
    static boolean[] visited = new boolean[26]; // 정답 3

    static int answer = 0;

    public static void main(String[] args) {

        if (k < 5) {
            System.out.println(answer);
            return;
        } else if (k == 26) {
            System.out.println(n);
            return;
        }

        visited['a' - 'a'] = true;
        visited['n' - 'a'] = true;
        visited['t' - 'a'] = true;
        visited['i' - 'a'] = true;
        visited['c' - 'a'] = true;

        dfs(0, 0);

        System.out.println(answer);
    }


    private static void dfs(int start, int len) {
        if(len == k-5) {
            int temp = 0;
            for (String s : word) {
                boolean canLearn = true;
                for (int j = 0; j < s.length(); j++) {
                    if(visited[s.charAt(j) - 'a']) continue;
                    else {
                        canLearn = false;
                        break;
                    }
                }
                if(canLearn) temp++;
            }
            answer = Math.max(answer, temp);
            return;
        }

        for (int j = start; j < 26; j++) {
            if(!visited[j]) {
                visited[j] = true;
                dfs(j+1, len+1);
                visited[j] = false;
            }
        }
    }
}
