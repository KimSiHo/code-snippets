package me.bigmonkey.kakao.winter2019;

import java.util.ArrayDeque;
import java.util.Deque;

/*
* 첫 번째 for 루프 (moves 배열)는 딱히 인덱스가
* 없으니 그냥 for(:) 문으로 하는 것이 더 좋은 것 같음
* for (int move : moves)
* */

public class 크레인인형뽑기 {
    static int[][] board = {{0,0,0,0,0},{0,0,1,0,3},{0,2,5,0,1},{4,2,4,4,2},{3,5,1,3,1}};
    static int[] moves = {1,5,3,5,1,2,1,4};
    static Deque<Integer> basket = new ArrayDeque<>();
    static int answer = 0;

    public static void main(String[] args) {
        int boardSize = board.length;

        for (int i = 0; i < moves.length; i++) {
            int selCol = moves[i] - 1;

            for (int j = 0; j < boardSize; j++) {
                if (board[j][selCol] != 0) {
                    int selDoll = board[j][selCol];
                    postProccess(selDoll);
                    board[j][selCol] = 0;
                    break;
                }
            }
        }

        System.out.println(answer);
    }

    private static void postProccess(int selDoll) {
        if(basket.isEmpty()) basket.add(selDoll);
        else {
            if(basket.peek() == selDoll) {
                basket.pop();
                answer += 2;
            }
            else basket.push(selDoll);
        }
    }
}
