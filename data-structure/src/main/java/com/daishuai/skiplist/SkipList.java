package com.daishuai.skiplist;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

/**
 * @author admin
 * @version 1.0.0
 * @description 跳表
 * @createTime 2022-08-21 18:30:00
 */
public class SkipList<T> {

    private SkipNode<T> head;

    private int highLevel;

    private final Random random;

    private int size;

    private final static int MAX_LEVEL = 32;

    public SkipList() {
        this.head = new SkipNode<>(Integer.MIN_VALUE, null);
        this.random = new Random();
    }

    public SkipNode<T> search(int key) {
        SkipNode<T> temp = head;
        while (temp != null) {
            if (temp.key == key) {
                return temp;
            } else if (temp.right == null || temp.right.key > key) {
                temp = temp.down;
            } else {
                temp = temp.right;
            }
        }
        return null;
    }

    public void delete(int key) {
        SkipNode<T> temp = head;
        boolean exists = false;
        while (temp != null) {
            if (temp.right == null || temp.right.key > key) {
                temp = temp.down;
            } else if (temp.right.key == key) {
                exists = true;
                temp.right = temp.right.right;
                temp = temp.down;
            } else {
                temp = temp.right;
            }
        }
        size = exists ? size + 1 : size;
    }


    public void insert(int key, T value) {
        SkipNode<T> existNode = this.search(key);
        if (existNode != null) {
            existNode.value = value;
            return;
        }
        size ++;
        SkipNode<T> temp = head;
        Stack<SkipNode<T>> stack = new Stack<>();
        while (temp != null) {
            if (temp.right == null || temp.right.key > key) {
                stack.push(temp);
                temp = temp.down;
            } else {
                temp = temp.right;
            }
        }
        int level = 0;
        SkipNode<T> preNode;
        SkipNode<T> downNode = null;
        do {
            if (stack.isEmpty()) {
                // new line, change the head node
                preNode = new SkipNode<>(Integer.MIN_VALUE, null);
                preNode.down = head;
                head = preNode;
            } else {
                preNode = stack.pop();
            }
            level ++;
            SkipNode<T> insertNode = new SkipNode<>(key, value);
            insertNode.right = preNode.right;
            insertNode.down = downNode;
            downNode = insertNode;
            preNode.right = insertNode;
            highLevel = Math.max(highLevel, level);
        } while (level < MAX_LEVEL && random.nextDouble() > 0.5);
    }

    public int size() {
        return this.size;
    }

    public void print() {
        ArrayList<SkipNode<T>> heads = new ArrayList<>();
        SkipNode<T> temp = head;
        while (temp != null) {
            heads.add(temp);
            temp = temp.down;
        }
        SkipNode<T> lastLevelHead = heads.get(heads.size() - 1);
        SkipNode<T> nextNode;
        SkipNode<T> lastLevelLastNode;
        for (SkipNode<T> firstNode : heads) {
            System.out.print("head->");
            nextNode = firstNode.right;
            lastLevelLastNode = lastLevelHead.right;
            while (lastLevelLastNode != null) {
                if (nextNode != null && nextNode.key == lastLevelLastNode.key) {
                    String str = String.format("%03d->", nextNode.key);
                    System.out.print(str);
                    nextNode = nextNode.right;
                } else {
                    System.out.print("---->");
                }
                lastLevelLastNode = lastLevelLastNode.right;
            }
            System.out.println("tail");
        }
    }

    private static class SkipNode<T> {

        int key;

        T value;

        SkipNode<T> right;

        SkipNode<T> down;

        public SkipNode (int key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
