package com.daishuai;

import java.util.Arrays;

public class HeapSort {

    public static void main(String[] args) {
        int[] array = {8,6,3,4,1,2,6,7,9};
        heapSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void heapSort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            headInsert(array, i);
        }
        int heapSize = array.length;
        while (heapSize > 0) {
            swap(array, 0, heapSize - 1);
            heapSize --;
            heapIfy(array, 0, heapSize);
        }
    }

    public static void headInsert(int[] array, int index) {
        int parentIndex = (index - 1) / 2;
        while (array[index] > array[parentIndex]) {
            swap(array, index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    public static void heapIfy(int[] array, int index, int heapSize) {
        int leftChild = 2 * index + 1;
        // 存在左孩子
        while (leftChild < heapSize) {
            // 右孩子索引
            int rightChild = leftChild + 1;
            // 右孩子存在且大于左孩子
            int largest = rightChild < heapSize && array[rightChild] > array[leftChild] ? rightChild : leftChild;
            largest = array[index] > array[largest] ? index : largest;
            if (largest == index) {
                break;
            }
            swap(array, largest, index);
            index = largest;
            leftChild = 2 * index + 1;
        }
    }

    public static void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}
