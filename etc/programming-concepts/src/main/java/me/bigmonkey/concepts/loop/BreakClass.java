package me.bigmonkey.concepts.loop;

// for문과 while문 switch 문을 빠져나가는 데 사용, 주로 if문과 함께 쓰임
public class BreakClass {

    public static void main(String[] args) {
        while(true) {
            System.out.println(1);
            if(true) {
                System.out.println(2);
                break;
            }
            System.out.println(3);
        }
        System.out.println(4);


        for (int i = 0; i < 3; i++) {

            while(true) {
               break;
            }
            if(i == 1) break;
            System.out.println("i = " + i);
        }
    }
}
