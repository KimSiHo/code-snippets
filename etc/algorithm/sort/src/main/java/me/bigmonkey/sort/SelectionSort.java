package me.bigmonkey.sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class SelectionSort {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("hi selection");
        int a[] = new int[150000];

        Scanner scan = new Scanner(new File("array.txt"));

        int cnt = 0;
        while (scan.hasNext()) {
            a[cnt++] = scan.nextInt();
        }
        System.out.println(Arrays.toString(a));

        long startTime = System.currentTimeMillis();
        selection_sort(a, a.length);
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("걸린 시간 : " + estimatedTime/1000.0 + " seconds");

        System.out.println(Arrays.toString(a));
    }

    private static void selection_sort(int[] a, int size) {
        for(int i = 0; i < size - 1; i++) {
            int min_index = i;

            // 최솟값을 갖고있는 인덱스 찾기
            for(int j = i + 1; j < size; j++) {
                if(a[j] < a[min_index]) {
                    min_index = j;
                }
            }

            // i번째 값과 찾은 최솟값을 서로 교환
            swap(a, min_index, i);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    
}
