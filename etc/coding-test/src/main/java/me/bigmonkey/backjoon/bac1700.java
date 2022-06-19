package me.bigmonkey.backjoon;

import java.util.ArrayList;

public class bac1700 {
    /*static int n = 2;
    static int k = 7;
    static int[] usage = {2, 3, 2, 3, 1, 2, 7};  //answer 2*/

/*    static int n = 3;
    static int k = 5;
    static int[] usage = {1, 3, 1, 2, 1};  //answer 0*/

    static int n = 2;
    static int k = 8;
    static int[] usage = {1, 2, 3, 4, 3, 4, 2, 2};  //answer 3

    public static void main(String[] args) {
        int cnt = 0;
        ArrayList<Integer> list = new ArrayList<>();

        int t = 0;
        for (; ; t++) {
            if(list.size() == n) break;

            if(!list.contains(usage[t])) list.add(usage[t]);
        }

        for (int i = t; i < usage.length; i++) {
            int cur = usage[i];
            if(list.contains(cur)) continue;

            else {

                int count = Integer.MIN_VALUE;
                int item = -1;
                for (int j = 0; j < list.size(); j++) {
                    int sel = list.get(j);
                    int temp = 0;
                    boolean find = false;

                    for (int l = i; l < usage.length; l++) {
                        temp++;
                        if(usage[l] == sel) {
                            find = true;
                            break;
                        }
                    }

                    if(find && temp > count) {
                        item = sel;
                        count = temp;
                    }
                    if(!find) {
                        item = sel;
                        break;
                    }

                }

                list.remove((Integer)item);
                list.add(cur);
                cnt++;
            }
        }

        System.out.println(cnt);
    }
}
