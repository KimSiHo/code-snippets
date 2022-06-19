package me.bigmonkey.naver;

import java.util.LinkedList;

public class 라인_기출_1 {

    /*static int cony = 11;
    static int brown = 2; // answer 5*/

    /*static int cony = 11;
    static int brown = 1; // answer 6*/

    static int cony = 6;
    static int brown = 3; // answer 4

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        if(brown - 1 >= 0 && brown - 1 <= 20000) list.add(brown - 1);
        if(brown + 1 >= 0 && brown + 1 <= 20000) list.add(brown + 1);
        if(brown * 2 >= 0 && brown * 2 <= 20000) list.add(brown * 2);

        for (int i = 1; ; i++) {

            cony += i;

            if(cony  > 200000) {
                System.out.println("-1");
                return;
            }
            LinkedList<Integer> tempList = new LinkedList<>();

            while (!list.isEmpty()) {
                Integer poll = list.poll();
                if (poll == cony) {
                    System.out.println(i);
                    return;
                }

                if(poll - 1 >= 0 && poll - 1 <= 20000) tempList.add(poll - 1);
                if(poll + 1 >= 0 && poll + 1 <= 20000) tempList.add(poll + 1);
                if(poll * 2 >= 0 && poll * 2 <= 20000) tempList.add(poll * 2);
            }

            list = tempList;

        }
    }

}
