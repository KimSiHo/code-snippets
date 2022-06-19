package me.bigmonkey.naver;

public class 네이버_기출_1 {
    /*static int[][] scores = {{100, 90, 98, 88, 65}, {50, 45, 99, 85, 77}, {47, 88, 95, 80, 67}, {61, 57, 100, 80, 65}, {24, 90, 94, 75, 65}};*/
    /*static int[][] scores = {{50, 90}, {50, 87}};*/
    static int[][] scores = {{70, 49, 90}, {68, 50, 38}, {73, 31, 100}};

    public static void main(String[] args) {
        int numOfStudents = scores.length;
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < numOfStudents; i++) {

            int myScore = 0;
            int sum = 0;
            int maxScore = Integer.MIN_VALUE;
            int minScore = Integer.MAX_VALUE;
            boolean maxUnique = true;
            boolean minUnique = true;

            for (int j = 0; j < numOfStudents; j++) {
                if(j == i) myScore = scores[j][i];
                if(scores[j][i] == maxScore) maxUnique = false;
                if(scores[j][i] == minScore) minUnique = false;
                if(scores[j][i] > maxScore) {
                    maxScore = scores[j][i];
                    maxUnique = true;
                }
                if(scores[j][i] < minScore) {
                    minScore = scores[j][i];
                    minUnique = true;
                }

                sum += scores[j][i];
            }

            double average = 0;
            if(maxUnique && myScore == maxScore) {
                sum -= myScore;
                average = (double)sum / (numOfStudents - 1);
            }
            else if(minUnique && myScore == minScore) {
                sum -= myScore;
                average = (double)sum / (numOfStudents - 1);
            }
            else average = (double)sum / numOfStudents;

            if(average >= 90) ans.append('A');
            else if(average >= 80 && average < 90) ans.append('B');
            else if(average >= 70 && average < 80) ans.append('C');
            else if(average >= 50 && average < 70) ans.append('D');
            else if (average < 50) ans.append('F');
            else System.out.println("error occured!");
        }

        System.out.println(ans.toString());
    }
}
