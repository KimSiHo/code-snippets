package me.bigmonkey.kakao.kakao2022;

public class k진수에서_소수_개수_구하기 {

    /*static int n = 437674;
    static int k = 3; // 정답 3*/

    static int n = 110011;
    static int k = 10; // 정답 2

    static int cnt = 0;

    public static void main(String[] args) {
        String kStr = makeKStr(n, k);

        String temp = "";
        for (int i = 0; i < kStr.length(); i++) {
            if (kStr.charAt(i) != '0') {
                temp += kStr.charAt(i);
            }

            if (kStr.charAt(i) == '0' || i == kStr.length() - 1) {
                if (temp.length() == 0) {
                    continue;
                }

                boolean flag = false;
                if (isPrime(Long.valueOf(temp))) {
                    int size = temp.length();
                    if (i - size - 1 < 0) {
                        flag = true;
                    }
                    if (i == kStr.length() - 1 && kStr.charAt(i - size) == '0') {
                        flag = true;
                    }
                    if (i - size - 1 > 0 && kStr.charAt(i - size - 1) == '0') {
                        flag = true;
                    }
                    if (size == kStr.length()) {
                        flag = true;
                    }
                }
                if (flag) {
                    cnt++;
                }
                temp = "";
            }
        }

        System.out.println(cnt);
    }

    private static String makeKStr(int n, int k) {
        StringBuilder ret = new StringBuilder();
        while (n > 0) {
            ret.append(n % k);
            n /= k;
        }

        return ret.reverse().toString();
    }

    private static boolean isPrime(Long num) {
        if (num == 1) {
            return false;
        }

        long sqrt = (long) Math.sqrt(num);
        for (long i = 2; i <= sqrt; i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}
