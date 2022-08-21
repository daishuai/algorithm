package com.daishuai.skiplist;

import java.util.Random;

/**
 * @author admin
 * @version 1.0.0
 * @description 跳表调试
 * @createTime 2022-08-21 21:13:07
 */
public class SkipListDemo {

    public static void main(String[] args) {
        Random random = new Random();
        SkipList<Integer> skipList = new SkipList<>();
        for (int i = 0; i < 1120; i++) {
            int key = random.nextInt(100);
            skipList.insert(key, key);
        }
        skipList.insert(30, 30);
        System.out.println("删除前");
        skipList.print();
        skipList.delete(30);
        System.out.println("删除后");
        skipList.print();
        System.out.println(skipList.size());
    }
}
