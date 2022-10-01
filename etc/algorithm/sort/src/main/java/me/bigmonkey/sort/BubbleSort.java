package me.bigmonkey.sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class BubbleSort {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hi bubble");
        int a[] = new int[150000];

        Scanner scan = new Scanner(new File("array.txt"));

        int cnt = 0;
        while (scan.hasNext()) {
            a[cnt++] = scan.nextInt();
        }
        System.out.println(Arrays.toString(a));

        long startTime = System.currentTimeMillis();
        bubble_sort(a, a.length);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("걸린 시간 : " + estimatedTime/1000.0 + " seconds");

        System.out.println(Arrays.toString(a));
    }

    private static void bubble_sort(int[] a, int size) {
        // round는 배열 크기 - 1 만큼 진행됨
        for(int i = 1; i < size; i++) {

            // 각 라운드별 비교횟수는 배열 크기의 현재 라운드를 뺀 만큼 비교함
            for(int j = 0; j < size - i; j++) {

                // 현재 원소가 다음 원소보다 클 경우, 서로 원소의 위치를 교환한다
                if(a[j] > a [j + 1]) {
                    swap(a, j, j + 1);
                }
            }
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
