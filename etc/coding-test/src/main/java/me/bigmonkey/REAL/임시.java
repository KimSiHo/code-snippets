package me.bigmonkey.REAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class 임시 {

    public static void main(String[] args) {

        boolean[] visited = new boolean[500];
        List<Integer> ret = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            ret.add(random.nextInt(100));
        }

        System.out.println(ret.toString());
    }



}
