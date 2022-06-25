package me.bigmonkey.kakao.kakao2022;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

// 다 푼거나 마찬가지다.. 다음에는 문제 좀 꼼꼼히 읽자..
public class 주차_요금_계산 {

    public static final String IN = "IN";
    public static final String OUT = "OUT";

    static String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT", "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN", "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};
    static int[] fees = {180, 5000, 10, 600};

    public static void main(String[] args) {
        int i = LocalTime.of(5, 34).toSecondOfDay();

        Map<String, Integer> recordMap = new HashMap<>();
        Map<String, Integer> totalFees = new HashMap<>();

        for (String record : records) {
            String[] infos = record.split(" ");
            int seconds = parseToSeconds(infos[0]);
            if (infos[2].equals(IN)) {
                recordMap.put(infos[1], seconds);
            }
            if(infos[2].equals(OUT)) {
                int inputTime = recordMap.get(infos[1]);
                int sumFee = calcFee(inputTime, seconds);
                recordMap.remove(infos[1]);
                totalFees.put(infos[1], totalFees.getOrDefault(infos[1], 0) + sumFee);
            }
        }

        int endTime = LocalTime.of(23, 59).toSecondOfDay();
        for (String key : recordMap.keySet()) {
            int inTime = recordMap.get(key);
            totalFees.put(key, totalFees.getOrDefault(key, 0) + calcFee(inTime, endTime));
        }

        List<Integer> ret = new ArrayList<>();
        String[] mapKeys = totalFees.keySet().toArray(new String[0]);
        Arrays.sort(mapKeys);
        for (String mapKey : mapKeys) {
            ret.add(totalFees.get(mapKey));
        }

        System.out.println(ret.toString());
    }

    private static int calcFee(int inTime, int outTime) {
        int ret = 0;
        int duration = outTime - inTime;
        if(duration > fees[0] * 60) {
            ret += fees[1];
            duration -= fees[0] * 60;
        } else {
            return fees[1];
        }

        while(duration > 0) {
            duration -= fees[2] * 60;
            ret += fees[3];
        }

        return ret;
    }

    private static int parseToSeconds(String info) {
        String[] timeInfos = info.split(":");
        return LocalTime.of(Integer.parseInt(timeInfos[0]), Integer.parseInt(timeInfos[1])).toSecondOfDay();
    }
}