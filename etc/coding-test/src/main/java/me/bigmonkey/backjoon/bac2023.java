package me.bigmonkey.backjoon;

public class bac2023 {

    static int n = 4;
    public static StringBuilder sb = new StringBuilder();
    
    public static void main(String[] args) {

        dfs("", 0);
        System.out.println(sb.toString());


    }

    private static void dfs(String s, int cnt) {
        if (cnt == n) {
            sb.append(s+'\n');
            return;
        }
        for(int i=1; i<=9; i++) {
            if(isPrime(Integer.parseInt(s+i))) {
                dfs(s+i,cnt+1);
            }
        }
    }

    private static boolean isPrime(int num) {
        if(num==1) return false;

        int sqrt=(int)Math.sqrt(num);
        for(int i=2; i<=sqrt; i++) {
            if(num%i==0) return false;
        }
        return true;
    }

}
