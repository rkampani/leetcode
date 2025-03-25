package com.example.grind150;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Week19 {

    /**
     * Q1:  Path Sum III
     * Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values along the path equals targetSum.
     * <p>
     * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e., traveling only from parent nodes to child nodes).
     *
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0l, 1);
        return dfsPathSumIII(root, targetSum, prefixSumCount, 0l);
    }

    private int dfsPathSumIII(TreeNode node, int targetSum, Map<Long, Integer> prefixSumCount, long currentSum) {

        if (node == null) {
            return 0;
        }
        currentSum += node.val;
        // Count the number of valid paths that sum to targetSum
        int pathCount = prefixSumCount.getOrDefault(currentSum - targetSum, 0);
        // Add the current sum to the prefixSumCount map
        prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        // Recursively search left and right subtrees
        pathCount += dfsPathSumIII(node.left, targetSum, prefixSumCount, currentSum);
        pathCount += dfsPathSumIII(node.right, targetSum, prefixSumCount, currentSum);
        // After exploring the current node, revert the change to the map
        prefixSumCount.put(currentSum, prefixSumCount.get(currentSum) - 1);

        return pathCount;
    }

    /**
     * Q2 Pow(x, n)
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }
        return power(x, n);
    }

    private double power(double x, int n) {
        double result = 1.0;
        while (n != 0) {
            if (n % 2 == 1) {
                result *= x;
            }
            x = x * x;
            n = n / 2;
        }
        return result;
    }

    /**
     * Q3 Search a 2D Matrix
     * You are given an m x n integer matrix matrix with the following two properties:
     * <p>
     * Each row is sorted in non-decreasing order.
     * The first integer of each row is greater than the last integer of the previous row.
     * Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
     * Output: true
     */
    public boolean searchMatrix(int[][] matrix, int target) {

        int row = 0;
        int cols = matrix[0].length - 1;

        while (row < matrix.length && cols >= 0) {

            if (matrix[row][cols] < target) {
                row++;
            } else if (matrix[row][cols] > target) {
                cols--;
            } else if (matrix[row][cols] == target) {
                return true;
            }
        }
        return false;
    }

    /**Largest Number
     ****Given a list of non-negative integers nums, arrange them such that they form the largest number and return it.
     */
    public String largestNumber(int[] nums) {
        List<String> stringNumbers = new ArrayList<>();
        for(int num: nums) {
            stringNumbers.add(String.valueOf(num));
        }

        stringNumbers.sort((str1, str2) -> (str2 + str1).compareTo(str1 + str2));
        if ("0".equals(stringNumbers.get(0))) {
            return "0";
        }

        return String.join("", stringNumbers);
    }

    /**
     * Q5: Decode Ways
     * Input: s = "12"
     *
     * Output: 2
     *
     * Explanation:
     *
     * "12" could be decoded as "AB" (1 2) or "L" (12).
     */
    public int numDecodings(String s) {
        if (s == null || s.length() == 0 || s.charAt(0) == '0') {
            return 0;
        }
        int n = s.length();
        int[] dp = new int[n + 1];

        // Base case: one way to decode an empty string
        dp[0] = 1;

        // Base case: if the first character is valid (not '0')
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            // Check if the current single character forms a valid letter
            if (s.charAt(i - 1) != '0') {
                dp[i] += dp[i - 1];
            }

            // Check if the last two characters form a valid number between 10 and 26
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2];
            }
        }

        return dp[n];

    }
    public static void main(String[] args) {
        Week19 week19 = new Week19();
        // Example usage:
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        int targetSum = 8;

        System.out.println("Q1 pathSum " + week19.pathSum(root, targetSum));
        System.out.println("Q2 myPow " + week19.myPow(2, 10));
        System.out.println("Q2 myPow " + week19.myPow(2.1, 3));
        System.out.println("Q2 myPow " + week19.myPow(2, -2));

        int[][] matrix = {
                {1, 4, 7, 11},
                {2, 5, 8, 12},
                {3, 6, 9, 16},
                {10, 13, 14, 17}
        };
        int target = 5;
        System.out.println("Q3 searchMatrix " + week19.searchMatrix(matrix, target));

        System.out.println("Q4 largestNumber " +week19.largestNumber(new int[]{1,0,2}));
        System.out.println("Q4 largestNumber " +week19.largestNumber(new int[]{3,30,34,5,9}));
        System.out.println("Q4 largestNumber " +week19.largestNumber(new int[]{10,2}));

        System.out.println("Q5 numDecodings " +week19.numDecodings("12"));
        System.out.println("Q5 numDecodings " +week19.numDecodings("226"));
        System.out.println("Q5 numDecodings " +week19.numDecodings("12"));  // Output: 2
        System.out.println("Q5 numDecodings " +week19.numDecodings("226")); // Output: 3
        System.out.println("Q5 numDecodings " +week19.numDecodings("0"));   // Output: 0
        System.out.println("Q5 numDecodings " +week19.numDecodings("10"));  // Output: 1
        System.out.println("Q5 numDecodings " +week19.numDecodings("27"));  // Outp
    }
}
