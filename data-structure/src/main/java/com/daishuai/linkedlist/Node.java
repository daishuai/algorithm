package com.daishuai.linkedlist;

import org.apache.commons.lang3.ArrayUtils;

public class Node {

    int value;

    Node next;

    public Node() {}

    public Node(int value, Node next) {
        this.value = value;
        this.next = next;
    }

    public static Node buildNode(int[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return null;
        }
        Node head = null;
        Node tail = null;
        for (int i : array) {
            if (head == null) {
                head = new Node(i, null);
                tail = head;
            } else {
                tail.next = new Node(i, null);
                tail = tail.next;
            }
        }
        return head;
    }

    public static void printNode(Node node) {
        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
