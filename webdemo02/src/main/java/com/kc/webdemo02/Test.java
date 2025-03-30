package com.kc.webdemo02;

import java.util.Arrays;

/**
 * @author KCWang
 * @version 1.0
 * @date 2025/3/21 下午4:44
 */
public class Test {

    public static  int heaplength = 0;

    public static void main(String[] args) {

        System.out.println("a");
        int[] ints = {4, 2, 7, 8, 1};

        heapfy(ints);
        System.out.println(Arrays.toString(ints));
    }

    public static void heapfy(int[] arry){
        heaplength=arry.length;
        if(heaplength<2){
            return;
        }

        for (int i = heaplength-1; i >=0 ; i--) {
            heap(arry,i);
        }

        for (int i = heaplength; i > 0; i--) {
            swap(arry,0,i-1);
            heaplength--;
            heap(arry,0);
        }


    }


    private static void heap(int[] arry,int idx){
        int left=idx*2+1, right =idx*2+2, lagest=idx;
        if(left<heaplength && arry[left]>arry[lagest]){
            lagest=left;
        }
        if(right<heaplength&& arry[right]>arry[lagest]){
            lagest=right;
        }
        if(idx!=lagest){
            swap(arry,lagest,idx);
            heap(arry,lagest);
        }
    }

    private static void swap(int[] arry,int idx1,int idx2){
        int tep = arry[idx2];
        arry[idx2]=arry[idx1];
        arry[idx1]=tep;
    }


}





