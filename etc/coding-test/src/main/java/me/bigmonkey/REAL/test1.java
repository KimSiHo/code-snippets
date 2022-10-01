package me.bigmonkey.REAL;

import java.util.HashSet;
import java.util.Set;

public class test1 {

    /*public static int[] T = {0, 0, 0, 0, 2, 3, 3};
    public static int[] A = {2, 5, 6};*/

    public static int[] T = {0, 0, 1, 2};
    public static int[] A = {1, 2};

    public static void main(String[] args)  {

        Set<Integer> toLearnSkill = new HashSet<>();

        for (int skill : A) {
            // 삽입이 성공하면
            int toLearn = skill;
            while(toLearnSkill.add(toLearn)) {
                toLearn = T[toLearn];
            }
        }

        System.out.println(toLearnSkill.size());
    }
}
