package com.daishuai.binaryheap;

import java.util.Arrays;

/**
 * @author admin
 * @version 1.0.0
 * @description 二叉堆-最大堆
 * @createTime 2022-09-09 18:56:12
 */
public class BinaryHeap {

    public static void main(String[] args) {
        int[] array = {5,2,1,9,8,10,3,6,7,1,5,2};
        BinaryHeap maxHeap = new BinaryHeap(array);
        int[] sortedArray = new int[array.length];
        int heapSize = maxHeap.size();
        for (int i = 0; i < heapSize; i++) {
            sortedArray[i] = maxHeap.delete();
        }
        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(sortedArray));
    }

    private Integer[] values;

    private int size;

    public BinaryHeap() {
        this.values = new Integer[8];
        this.size = 0;
    }

    public BinaryHeap(int size) {
        this.values = new Integer[size];
        this.size = 0;
    }

    public BinaryHeap(int[] array) {
        this.values = new Integer[8];
        this.size = 0;
        for (int value : array) {
            this.add(value);
        }
    }

    public void add(int value) {
        // 判断是否需要扩容
        if (size >= values.length) {
            this.grow();
        }
        values[size] = value;
        size++;
        if (size == 1) {
            return;
        }
        int parent;
        int child = size - 1;
        // 左孩子 是 2n, 右孩子是2n+1
        if ((size & 1) == 0) {
            // 偶数
            parent = size / 2 - 1;
        } else {
            parent = (size - 1) / 2 - 1;
        }
        while (parent >= 0 && values[child] > values[parent]) {
            // 交换
            int temp = values[child];
            values[child] = values[parent];
            values[parent] = temp;
            child = parent;
            if (((child + 1) & 1) == 0) {
                // 偶数
                parent = (child + 1) / 2 - 1;
            } else {
                parent = child / 2 - 1;
            }
        }
    }

    public Integer delete() {
        if (size == 0) {
            return null;
        }
        Integer deleteValue = values[0];
        values[0] = values[size - 1];
        values[size - 1] = null;
        size --;
        if (size == 1) {
            return deleteValue;
        }
        int parent = 0;
        int leftChild = 1;
        int rightChild = 2;
        while (leftChild < size) {
            int rightValue = values[rightChild] == null ? 0 : values[rightChild];
            if (values[leftChild] > values[parent] && values[leftChild] >= rightValue) {
                // 左孩子大于父节点，且大于右孩子
                int temp = values[leftChild];
                values[leftChild] = values[parent];
                values[parent] = temp;
                parent = leftChild;
            } else if (rightValue > values[parent] && rightValue > values[leftChild]) {
                values[rightChild] = values[parent];
                values[parent] = rightValue;
                parent = rightChild;
            } else {
                return deleteValue;
            }
            leftChild = (parent << 1) + 1;
            rightChild = (parent << 1) + 2;
        }
        return deleteValue;
    }

    public int size() {
        return this.size;
    }

    /**
     * 扩容
     */
    private void grow() {
        values = Arrays.copyOf(values, values.length << 1);
    }

    public void print() {
        System.out.println(Arrays.toString(values));
    }
}
