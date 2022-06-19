package me.bigmonkey.kakao.kakao2021;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class 메뉴_리뉴얼 {

    /*static String[] orders = {"ABCFG", "AC", "CDE", "ACDE", "BCFG", "ACDEH"};
    static int[] course = {2, 3, 4};  // 정답 ["AC", "ACDE", "BCFG", "CDE"]*/

    /*static String[] orders = {"ABCD", "ABCD", "ABCD"};
    static int[] course = {2, 3, 4};  // 정답 ['AB', 'ABC', 'ABCD', 'ABD', 'AC', 'ACD', 'AD', 'BC', 'BCD', 'BD', 'CD']*/

    static String[] orders = {"ABCDE", "AB", "CDAB", "ABDE", "XABYZ", "ABXYZ", "ABCD", "ABCDE",
        "ABCDE", "ABCDE", "AB", "AB", "AB", "AB", "AB", "AB", "AB", "AB", "AB", "AB"};
    static int[] course = {2};  // 정답 ['AB']

    static Map<String, Integer> map = new HashMap<>();
    static boolean[] ch = new boolean[11];
    static int[] countCourse = new int[11];

    public static void main(String[] args) {
        
        List<String> ret = new ArrayList<>();

        for (String order : orders) {
            char[] chars = order.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);

            dfs(0, 0, sorted.length(), sorted);
        }

        for (String s : map.keySet()) {
            if (map.get(s) >= 2 && map.get(s) > countCourse[s.length()]) countCourse[s.length()] = map.get(s);
        }

        for (String s : map.keySet()) {
            int menuCount = s.length();
            boolean flag = false;
            for (int i : course) {
                if (i == menuCount) flag = true;
            }
            if (flag && map.get(s) == countCourse[s.length()]) ret.add(s);

        }

        String[] temp = ret.toArray(new String[ret.size()]);
        Arrays.sort(temp);
        System.out.println(Arrays.toString(temp));
    }

    private static void dfs(int start, int length, int size, String sorted) {

        if (length >= 2) {
            String key = "";
            for (int i = 0; i < sorted.length(); i++) {
                if (ch[i] == true) {
                    key += sorted.charAt(i);
                }
            }
            Integer orDefault = map.getOrDefault(key, 0);
            map.put(key, orDefault + 1);
        }

        for (int i = start; i < size; i++) {
            ch[i] = true;
            dfs(i + 1, length + 1, size, sorted);
            ch[i] = false;
        }
    }
}
