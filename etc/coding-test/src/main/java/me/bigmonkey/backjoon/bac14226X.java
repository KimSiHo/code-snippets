package me.bigmonkey.backjoon;

import java.util.LinkedList;


// 재시도 필요!
public class bac14226X {

    static int n = 2;

    public static void main(String[] args) {
        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[]{1, 0});

        int[][] time = new int[2000][2];
        time[1][0] = 1;

        while(!queue.isEmpty()) {
            int[] cur = queue.pop();
            if(cur[1] == 0) {
                if(time[cur[0]][0] > 1) continue;
            } else {
                if(time[cur[0]][1] > 1) continue;
            }

            // 클립보드에 데이터가 있으면
            if(cur[1] != 0) {
                int curCnt = cur[0] + cur[1];
                if(curCnt == n) {
                    System.out.println(time[cur[0]][1] + 1);
                    return;
                }
                if(curCnt < 0 || curCnt > 2000) continue;
                else {
                    queue.add(new int[]{curCnt, 0});
                    time[curCnt][0] = time[cur[0]][1] + 1;
                }

                if(cur[0] - 1 < 0) continue;
                else {
                    if(cur[0] - 1 == n) {
                        System.out.println(time[cur[0]][1] + 1);
                        return;
                    }
                    queue.add(new int[]{cur[0] - 1, cur[1]});
                    time[cur[0] - 1][1] = time[cur[0]][1] + 1;
                }
            }

            // 클립보드가 0이면
            else if(cur[1] == 0) {
                queue.add(new int[]{cur[0], cur[0]});
                time[cur[0]][1] = time[cur[0]][0] + 1;

                if(cur[0] - 1 < 0) continue;
                else {
                    if(cur[0] - 1 == n) {
                        System.out.println(time[cur[0]][0] + 1);
                        return;
                    }
                    queue.add(new int[]{cur[0] - 1, cur[1]});
                    time[cur[0] - 1][0] = time[cur[0]][0] + 1;
                }

            }
        }
    }

}
