package com.daishuai.binarytree;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class BinaryTree {

    public static void main(String[] args) {

        String binaryTreeStr = "5_3_6_#_#_3_#_#_9_5_#_#_4_7_#_#_9_#_#";
        //String binaryTreeStr = "5_3_6_#_2_#_#_3_#_#_9_5_#_#_4_#_9_#_#";

        // 二叉树的反序列化
        Node node = preorderDeserializeBinaryTree(binaryTreeStr);
        // 二叉树的序列化

        // 1、二叉树的宽度优先遍历
        //widthFirstTraversal(node);
        // 2、二叉树的深度优先遍历（二叉树的先序优先遍历）

        // 3、二叉树的中序遍历（递归和非递归方式）
        // 3.1、递归方式实现
        //inorderTraversalRecursion(node);
        // 3.2、非递归方式实现
        //inorderTraversalNonRecursion(node);
        // 4、二叉树的先序遍历（递归和非递归方式）
        // 4.1、递归方式实现
        //preorderTraversalRecursion(node);
        // 4.2 非递归方式实现
        //preorderTraversalNonRecursion(node);
        // 5、二叉树的后序遍历（递归和非递归方式）
        // 5.1、递归方式实现
        //postorderTraversalRecursion(node);
        // 5.2、非递归方式实现
        //postorderTraversalNonRecursion1(node);
        //postorderTraversalNonRecursion2(node);
        // 6、判断一棵二叉树是否为搜索二叉树：左树比他小，右树比他大

        // 7、判断一棵二叉树是否为完全二叉树

        // 8、判断一棵二叉树是否为满二叉树

        // 9、判断一棵二叉树是否为平衡二叉树

        // 10、计算二叉树的宽度

        // 11、计算二叉树的深度
    }

    /**
     * 二叉树的宽度优先遍历
     *
     * @param root  根节点
     */
    public static void widthFirstTraversal(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.println(node.value);
            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    /**
     * 先序反序列化二叉树
     *
     * @param binaryTreeStr 序列化后的二叉树
     * @return  二叉树根节点
     */
    public static Node preorderDeserializeBinaryTree(String binaryTreeStr) {
        if (StringUtils.isBlank(binaryTreeStr)) {
            return null;
        }
        String[] values = binaryTreeStr.split("_");
        Queue<String> queue = new LinkedList<>(Arrays.asList(values));
        return reconstructPreOrder(queue);
    }

    private static Node reconstructPreOrder(Queue<String> queue) {
        String value = queue.poll();
        if (value == null || "#".equals(value)) {
            return null;
        }
        Node node = new Node();
        node.value = Integer.parseInt(value);
        node.left = reconstructPreOrder(queue);
        node.right = reconstructPreOrder(queue);
        return node;
    }

    /**
     * 递归方式实现二叉树的先序遍历
     *
     * @param node  二叉树根节点
     */
    public static void preorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.value);
        preorderTraversalRecursion(node.left);
        preorderTraversalRecursion(node.right);
    }

    /**
     * 非递归方式实现先序遍历
     *
     * @param root  二叉树根节点
     */
    public static void preorderTraversalNonRecursion(Node root) {
        if (root == null) {
            return;
        }
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.value);
            if (node.right != null) {
                stack.add(node.right);
            }
            if (node.left != null) {
                stack.add(node.left);
            }
        }
    }

    /**
     * 递归方式实现二叉树的后续遍历
     *
     * @param node  二叉树根节点
     */
    public static void postorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        postorderTraversalRecursion(node.left);
        postorderTraversalRecursion(node.right);
        System.out.println(node.value);
    }

    /**
     * 非递归方式实现二叉树的后序遍历1
     *
     * @param root  二叉树的根节点
     */
    public static void postorderTraversalNonRecursion1(Node root) {
        if(root == null) {
            return;
        }
        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();
        stack1.add(root);
        while (!stack1.isEmpty()) {
            Node node = stack1.pop();
            stack2.add(node);
            if (node.left != null) {
                stack1.add(node.left);
            }
            if (node.right != null) {
                stack1.add(node.right);
            }
        }
        while (!stack2.isEmpty()) {
            System.out.println(stack2.pop().value);
        }
    }

    /**
     * 非递归方式实现二叉树后序遍历2
     *
     * @param root  二叉树根节点
     */
    public static void postorderTraversalNonRecursion2(Node root) {
        if (root == null) {
            return;
        }
        Map<Node, Node> parentNodeMap = new HashMap<>();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        while (root.left != null || root.right != null || !stack.isEmpty()) {
            if (root.left != null || root.right != null) {
                if (root.left != null) {
                    stack.add(root.left);
                    parentNodeMap.put(root.left, root);
                    root = root.left;
                } else {
                    stack.add(root.right);
                    root = root.right;
                }
            } else {
                Node node = stack.pop();
                System.out.println(node.value);
                Node parentNode = parentNodeMap.get(node);
                if (parentNode == null || parentNode.right == null) {
                    continue;
                }
                root = parentNode.right;
                stack.add(root);
            }
        }
    }

    /**
     * 递归方式实现二叉树中序遍历
     *
     * @param node  二叉树根节点
     */
    public static void inorderTraversalRecursion(Node node) {
        if (node == null) {
            return;
        }
        inorderTraversalRecursion(node.left);
        System.out.println(node.value);
        inorderTraversalRecursion(node.right);
    }

    /**
     * 非递归方式实现中序遍历
     *
     * @param root  二叉树根节点
     */
    public static void inorderTraversalNonRecursion(Node root) {
        Stack<Node> stack = new Stack<>();
        if (root == null) {
            return;
        }
        while (!stack.isEmpty() || root != null) {
            if (root != null) {
                stack.add(root);
                root = root.left;
            } else {
                root = stack.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }

    /**
     * 判断一棵二叉树是否为搜索二叉树
     *
     * @param root  二叉树根节点
     * @return  true or false
     */
    public static boolean isSearchBinaryTree(Node root) {

        return false;
    }

    /**
     * 判断一棵二叉树是否为完全二叉树
     * 1、左子树为null，右子树不为null 返回false
     * 2、宽度优先遍历，第一次遇到左右子树不全的，下面所有节点的左右子树都为null
     *
     * @param root  二叉树根节点
     * @return  true or false
     */
    public static boolean isCompleteBinaryTree(Node root) {

        return false;
    }

    /**
     * 判断一棵二叉树是否为完全二叉树
     *
     * @param root  二叉树根节点
     * @return  true or false
     */
    public static boolean isFullBinaryTree(Node root) {

        return false;
    }

    /**
     * 判断一棵树是否为平衡二叉树
     * 1、左右子树高度差的绝对值不超过1
     * @param root  二叉树根节点
     * @return  true or false
     */
    public static boolean balancedBinaryTree(Node root) {

        return false;
    }
}
