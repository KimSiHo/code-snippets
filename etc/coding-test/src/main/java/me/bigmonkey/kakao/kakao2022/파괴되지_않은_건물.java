package me.bigmonkey.kakao.kakao2022;

/*// 로직은 맞는데, 효율성이.. (누적합으로 풀기)
public class 파괴되지_않은_건물 {

*//*    static int[][] board = {{5,5,5,5,5}, {5,5,5,5,5}, {5,5,5,5,5}, {5,5,5,5,5}};
    static int[][] skill = {{1,0,0,3,4,4}, {1,2,0,2,3,2}, {2,1,0,3,1,2}, {1,0,1,3,3,1}}; // 정답 10*//*

    static int[][] board = {{1,2,3}, {4,5,6}, {7,8,9}};
    static int[][] skill = {{1,1,1,2,2,4}, {1,0,0,1,1,2}, {2,2,0,2,0,100}}; // 정답 6

    public static void main(String[] args) {
        int rowLength = board.length;
        int colLength = board[0].length;

        int[] flatArray = new int[rowLength*colLength];
        for (int i = 0; i < rowLength; i++) {

            for (int j = 0; j < colLength; j++) {
                flatArray[i*colLength + j] = board[i][j];
            }
        }

        for (int[] input : skill) {

            int colEnd = Math.max(input[2], input[4]);
            int colStart = Math.min(input[2], input[4]);
            int size = input[5];
            int mode = input[0];
            int start = input[1];
            int end = input[3];

            for (int i = start; i <= end; i++) {
                int pos = colLength * i;

                for (int j = colStart; j <= colEnd; j++) {
                    if(mode == 1) flatArray[pos + j] -= size;
                    else flatArray[pos + j] += size;
                }
            }
        }

        int cnt = 0;
        for (int i = 0; i < flatArray.length; i++) {
            if(flatArray[i] > 0) cnt++;
        }

        System.out.println(cnt);
    }

}*/

// 효율성 ver > 상하 좌우 둘다 누적.. 일단 패스..
public class 파괴되지_않은_건물 {

   /* static int[][] board = {{5,5,5,5,5}, {5,5,5,5,5}, {5,5,5,5,5}, {5,5,5,5,5}};
    static int[][] skill = {{1,0,0,3,4,4}, {1,2,0,2,3,2}, {2,1,0,3,1,2}, {1,0,1,3,3,1}}; // 정답 10*/

    static int[][] board = {{1,2,3}, {4,5,6}, {7,8,9}};
    static int[][] skill = {{1,1,1,2,2,4}, {1,0,0,1,1,2}, {2,2,0,2,0,100}}; // 정답 6

    public static void main(String[] args) {
        int rowLength = board.length;
        int colLength = board[0].length;

        int[][] tempArray = new int[rowLength][colLength + 1];

        for (int[] input : skill) {

            int colEnd = Math.max(input[2], input[4]);
            int colStart = Math.min(input[2], input[4]);
            int size = input[5];
            int mode = input[0];
            int start = input[1];
            int end = input[3];

            for (int i = start; i <= end; i++) {

                if(mode == 1) {
                    tempArray[i][colStart] -= size;
                    tempArray[i][colEnd + 1] += size;
                } else {
                    tempArray[i][colStart] += size;
                    tempArray[i][colEnd + 1] -= size;
                }
            }
        }

        int cnt = 0;
        for (int i = 0; i < rowLength; i++) {
            int temp = 0;
            for (int j = 0; j < colLength; j++) {
                temp += tempArray[i][j];
                int calc = board[i][j] + temp;

                if(calc > 0) cnt ++;
            }
        }

        System.out.println(cnt);
    }

}
