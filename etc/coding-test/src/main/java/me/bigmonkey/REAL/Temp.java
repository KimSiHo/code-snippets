package me.bigmonkey.REAL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Temp {

    public static String s = "John Doe, Peter Parker, Mary Jane Watson-Parker, James Doe";
    public static String C = "example";

    public static void main(String[] args) {
        Map<String, Integer> nameCountMap = new HashMap<>();
        StringBuilder ret = new StringBuilder();

        String[] split = s.split(", ");
        for (String name : split) {
            StringBuilder email = new StringBuilder();
            email.append("<");
            String[] nameArr = name.split(" ");
            if(nameArr.length == 2) {
                email.append(nameArr[0].charAt(0));
                email.append(process(nameArr[1]));
            } else {
                email.append(nameArr[0].charAt(0));
                email.append(nameArr[1].charAt(0));
                email.append(process(nameArr[2]));
            }

            if(nameCountMap.containsKey(email.toString())){
                Integer integer = nameCountMap.get(email.toString());
                nameCountMap.put(email.toString(), integer + 1);
                email.append(integer);
            } else {
                nameCountMap.put(email.toString(), 2);
            }
            email.append("@").append(C).append(".com").append(">").append(", ");

            ret.append(name).append(" ").append(email.toString().toLowerCase());
        }

        String trim = ret.deleteCharAt(ret.lastIndexOf(",")).toString().trim();

        System.out.println(trim);
    }

    private static String process(String s) {
        String s1 = s.replaceAll("-", "");
        if(s1.length() > 8) {
            return s1.substring(0, 8);
        }
        else return s1;
    }
}
