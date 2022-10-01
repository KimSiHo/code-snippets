package me.bigmonkey.REAL;

public class test2 {

    public static int A[] = {3, 2, 3, 2, 3};

    public static void main(String[] args) {
        int ans = Integer.MIN_VALUE;
        boolean evenInit = false;
        boolean oddInit = false;
        int odd = Integer.MIN_VALUE;
        int even = Integer.MIN_VALUE;
        int size = 0;

        for (int pos = 0; pos < A.length; ) {
            if(!evenInit || !oddInit) {
                if(pos % 2 == 0) {
                    even = A[pos];
                    evenInit = true;
                } else {
                    odd = A[pos];
                    oddInit = true;
                }
                pos++;
                size++;
                if(size > ans) ans = size;
                continue;
            }

            if(isEven(pos) && A[pos] == even) {
                pos++; size++;
            } else if (pos % 2 == 1 && A[pos] == odd) {
                pos++; size++;
            } else {
                pos = pos - 2 + 1;
                size = 0;
                evenInit = false;
                oddInit = false;
                continue;
            }

            if(size > ans) ans = size;
        }

        System.out.println("ans = " + ans);
    }

    private static boolean isEven(Integer pos) {
        return pos % 2 == 0;
    }
}