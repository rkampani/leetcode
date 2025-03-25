package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Week18 {

    /**
     * Q1 Asteroid Collision
     * Input: asteroids = [5,10,-5]
     * Output: [5,10]
     * Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
     */
    public int[] asteroidCollision(int[] asteroids) {

        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < asteroids.length; i++) {
            int asteriod = asteroids[i];
            while (!stack.isEmpty() && asteriod < 0 && stack.peek() > 0) {
                int differnce = asteriod + stack.peek();
                if (differnce < 0) {
                    stack.pop();
                } else if (differnce > 0) {
                    asteriod = 0;
                } else {
                    stack.pop();
                    asteriod = 0;
                    break;
                }
            }
            if (asteriod > 0) {
                stack.push(asteriod);
            }
        }
        int[] result = new int[stack.size()];
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }

        return result;
    }

    /**
     * Q2  Kth Largest Element in an Array
     * Given an integer array nums and an integer k, return the kth largest element in the array.
     * <p>
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     * Input: nums = [3,2,1,5,6,4], k = 2
     * Output: 5
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue();
        for (int num : nums) {
            priorityQueue.offer(num);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        return priorityQueue.peek();
    }

    /**
     * Maximal Square
     * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int maxSideLength = 0;
        int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
        for (int row = 1; row <= matrix.length; row++) {
            for (int col = 1; col <= matrix[0].length; col++) {
                if (matrix[row - 1][col - 1] == '1') {  // Only proceed if matrix[row-1][col-1] is '1'
                    dp[row][col] = Math.min(Math.min(dp[row - 1][col], dp[row][col - 1]), dp[row - 1][col - 1]) + 1;
                    maxSideLength = Math.max(maxSideLength, dp[row][col]);
                }
            }
        }
        return maxSideLength * maxSideLength;
    }

    /**
     * Q4 Rotate Image
     * You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
     */
    public void rotate(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            int left = 0;
            int right = matrix[0].length - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
        System.out.println("Q4 rotateArray90Degree " + Arrays.deepToString(matrix));
    }

    /**
     * Q5:
     Binary Tree Zigzag Level Order Traversal
     *
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;
        while(!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>();

            // Process all nodes at the current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();

                if (leftToRight) {
                    currentLevel.add(node.val);
                } else {
                    currentLevel.add(0, node.val);  // Insert at the beginning for reverse order
                }

                // Add children of the current node to the queue for the next level
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            // After processing the current level, toggle the direction for the next level
            result.add(currentLevel);
            leftToRight = !leftToRight;
        }
        return result;
    }
    public static void main(String[] args) {

        Week18 week18 = new Week18();
        System.out.println("Q1 asteroidCollision " + Arrays.toString(week18.asteroidCollision(new int[]{5, 10, -5})));
        System.out.println("Q1 asteroidCollision " + Arrays.toString(week18.asteroidCollision(new int[]{8, -8})));

        System.out.println("Q2 findKthLargest " + week18.findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        System.out.println("Q2 findKthLargest " + week18.findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));

        char[][] matrix = {
                {'1', '0', '1', '0', '0'},
                {'1', '0', '1', '1', '1'},
                {'1', '1', '1', '1', '1'},
                {'1', '0', '0', '1', '0'}
        };
        System.out.println("Q3 maximalSquare " + week18.maximalSquare(matrix));
        int[][] matrix1 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        week18.rotate(matrix1);
        // Example usage:
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        System.out.println("Q5 zigzagLevelOrder "+week18.zigzagLevelOrder(root));
    }
}
