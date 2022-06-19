package me.bigmonkey.programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* 배열은 건드리면 안되고 그냥 max 값을 구해야 됨!
* */
public class 모의고사 {
    private static int[] answers = {1, 2, 3, 4, 5};


    public static void main(String[] args) {
        int one[] = {1, 2, 3, 4, 5};
        int two[] = {2, 1, 2, 3, 2, 4, 2, 5};
        int three[] = {3, 3, 1, 1, 2, 2, 4, 4, 5, 5};
        int[] numOfAnswers = {0, 0, 0};

        for (int i = 0; i < answers.length; i++) {
            int answer = answers[i];

            if(answer == one[i % one.length]) numOfAnswers[0]++;
            if(answer == two[i % two.length]) numOfAnswers[1]++;
            if(answer == three[i % three.length]) numOfAnswers[2]++;
        }

        int maxScore = Math.max(numOfAnswers[0], Math.max(numOfAnswers[1], numOfAnswers[2]));

        List<Integer> answerList = new ArrayList<>();
        for (int i = 0; i < numOfAnswers.length; i++) {
            if(numOfAnswers[i] == maxScore) answerList.add(i + 1);
        }
        answerList.stream().mapToInt(Integer::intValue).toArray();
        System.out.println(Arrays.toString(answerList.toArray()));

    }
}
