package com.company;

import java.util.HashSet;
import java.util.Random;

public class Solution {

    private static final int MAX_SIZE = 100000;
    private static final int MAX_B = 110;

    public static void main(String[] args) {
        Solution solution = new Solution();
        TreeNode firstTree = solution.createFirstTree();

        solution.t2Sum(firstTree, 19);
        solution.t2Sum(firstTree, 40);
        TreeNode root = solution.createTree();
        int sum = solution.getRandom(MAX_B);
        System.out.println("B = " + sum);
        solution.t2Sum(root, sum);
    }

    TreeNode createFirstTree() {
        Tree tree = new Tree();
        tree.insertTreeNode(10);
        tree.insertTreeNode(9);
        tree.insertTreeNode(20);
        return tree.getRootTreeNode();
    }


    public int t2Sum(TreeNode A, int B) {
        // Пишите код тут
        HashSet<Integer> set = new HashSet<>();
        TreeNode firstNod = findFirstNod(A, B);
        if (firstNod == null) {
            System.out.println("No such pair exists");
            return 0;
        }
        if (searchNod(firstNod, B, set)) {
            return 1;
        }
        System.out.println("No such pair exists");
        return 0;
    }

    private boolean searchNod(TreeNode nod, int b, HashSet<Integer> set) {
        boolean result = false;
        if (set.contains(b - nod.getVal())) {
            System.out.println("Один из узлов равен = " + nod.getVal());
            return true;
        }
        set.add(nod.getVal());
        if (nod.getLeft() != null) {
            result = searchNod(nod.getLeft(), b, set);
        }
        if (!result && nod.getRight() != null) {
            result = searchNod(nod.getRight(), b, set);
        }
        return result;
    }

    TreeNode findFirstNod(TreeNode node, int B) {
        if (node.getVal() <= B) {
            return node;
        } else if (node.getLeft() != null) {
            return findFirstNod(node.getLeft(), B);
        }
        return null;
    }

    TreeNode createTree() {
        int maxSize = getRandom(MAX_SIZE);
        System.out.println(maxSize);
        Tree tree = new Tree();
        for (int i = 0; i <= maxSize; i++) {
            tree.insertTreeNode(getRandom(MAX_SIZE * 2));
        }
        tree.printTreeLight();
        return tree.getRootTreeNode();
    }

    int getRandom(int max) {
        Random rand = new Random();
        return rand.nextInt((max - 1) + 1) + 1;
    }
}
