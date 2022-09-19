package me.bigmonkey.naver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

public class 네이버_기출_3 {

    public static Integer[] prices = {4, 5, 6, 7, 8};
    public static int d = 4;
    public static int k = 3;
    static int ch[] = new int[prices.length];

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList(prices);
        IntSummaryStatistics collect = ints.stream().collect(Collectors.summarizingInt(Integer::intValue));

        int max = collect.getMax();
        int min = collect.getMin();
        int res = -1;

        boolean isProcess = true;
        if (max - min <= d) {
            res = getAvg(ints);
            isProcess = false;
        }

        if (isProcess) {
            ArrayList<Integer> intsCopy = new ArrayList<>(ints);
            intsCopy.remove(max);
            intsCopy.remove(min);
            IntSummaryStatistics collect2 = intsCopy.stream().collect(Collectors.summarizingInt(Integer::intValue));
            int max1 = collect2.getMax();
            int min1 = collect2.getMin();
            if (max1 - min1 <= d) {
                res = getAvg(intsCopy);
                isProcess = false;
            }
        }

        if (isProcess) {
            dfs(0, 0);

        }

        if (isProcess) {
            int size = ints.size();

            if(isEven(size)) {
                int mid = size / 2;
                Integer integer = ints.get(mid);
                Integer integer1 = ints.get(mid - 1);
                if(integer < integer1) {
                    res = integer;
                } else {
                    res = integer1;
                }

            } else {
                int mid = size /2;
                res = ints.get(mid);
            }

            isProcess =false;
        }

        System.out.println("res = " + res);
    }

    private static void dfs(int start, int level) {
        if(level == k) {
            List<Integer> temp = new ArrayList<>();
            for (int j = 0; j < level; j++) {
                //temp.add()
            }

        }
        else {
            for (int i = start; i < prices.length; i++) {
                ch[level] = i;
                dfs(i + 1, level + 1);
            }
        }
    }

    private static boolean isEven(int size) {
        return size % 2 == 0;
    }

    private static int getAvg(List<Integer> ints) {
        int sum = ints.stream().mapToInt(Integer::intValue).sum();
        return sum / ints.size();
    }
}
