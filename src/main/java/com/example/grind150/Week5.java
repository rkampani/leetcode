package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import static com.example.grind150.Utils.log;

public class Week5 {

    /**
     * Q1: Reverse Bits : use a bit manipulation technique
     * basic idea is to iterate through all the bits of the number and build the reversed number by shifting and
     * adding bits in the correct order.
     * Reverse bits of a given 32 bits unsigned integer.
     * Input: n = 00000010100101000001111010011100
     * Output:    964176192 (00111001011110000010100101000000)
     */
    public int reverseBits(int n) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            ans <<= 1;  // Shift result left to make space for the next bit
            ans |= (n & 1);  // Extract the LSB (Least significant bit) of n and add it to result
            n >>>= 1;  // Logical shift right to process the next bit of n
        }
        return ans;
    }

    /**
     * Q2: Subtree of Another Tree
     * Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same
     * structure and node values of subRoot and false otherwise.
     * <p>
     * A subtree of a binary tree tree is a tree that consists of a node in tree and all of this node's descendants.
     * The tree tree could also be considered as a subtree of itself.
     *
     * @param root
     * @param subRoot
     * @return
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return false;
        }
        if (isIdentical(root, subRoot)) {
            return true;
        }
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isIdentical(TreeNode node, TreeNode node2) {

        if (node == null && node2 == null) return true;
        if (node == null || node2 == null) return false;
        if (node.val != node2.val) {
            return false;
        }
        return isIdentical(node.left, node2.left) && isIdentical(node.right, node2.right);
    }

    /**
     * Q3:Squares of a Sorted Array
     * Given an integer array nums sorted in non-decreasing order, return an array of the squares of each number
     * sorted in non-decreasing order.
     * <p>
     * Input: nums = [-4,-1,0,3,10]
     * Output: [0,1,9,16,100]
     */
    public int[] sortedSquares(int[] nums) {
        if (nums == null) return nums;
        for (int i = 0; i < nums.length; i++) {
            int temp = nums[i];
            nums[i] = temp * temp;
        }
        Arrays.sort(nums);
        return nums;
    }

    public int[] sortedSquaresTwoPointers(int[] nums) {
        int length = nums.length; // Store the length of the input array
        int[] sortedSquares = new int[length];
        int k = length - 1;
        int start = 0;
        int end = length - 1;
        while (start <= end) {
            int startSquare = nums[start] * nums[start];
            int endSquare = nums[end] * nums[end];

            if (startSquare > endSquare) {
                sortedSquares[k] = startSquare;
                start++;
            } else {
                sortedSquares[k] = endSquare;
                end--;
            }
            k--;
        }
        return sortedSquares;
    }

    /**
     * Q4: Maximum Subarray
     * Given an integer array nums, find the subarray with the largest sum, and return its sum.
     */
    public int maxSubArray(int[] nums) {
        if (nums == null) {
            return -1;
        }

        int currentSume = nums[0];
        int globalMaxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSume = Math.max(nums[i], currentSume + nums[i]);
            globalMaxSum = Math.max(currentSume, globalMaxSum);
        }
        return globalMaxSum;
    }

    /**
     * Q5:Insert Interval
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (newInterval == null) return intervals;
        Arrays.sort(intervals, Comparator.comparing(a -> a[0]));
        List<int[]> temp = new ArrayList<>();
        int[] newInterval1 = newInterval;
        for (int[] current : intervals) {

            int start = current[0];
            int end = current[1];
            int newEnd = newInterval1[1];
            int newStart = newInterval1[0];
            if (newEnd < start) {
                temp.add(newInterval1);  // Add the newInterval to the result list
                newInterval1 = current;  // Now, set newInterval to the current interval
            } else if (newStart > end) {
                temp.add(current);
            } else {
                newInterval1[0] = Math.min(newInterval1[0], current[0]);  // Adjust start of newInterval
                newInterval1[1] = Math.max(newInterval1[1], current[1]);
            }
        }
        int[][] result = new int[temp.size()][2];
        temp.toArray(result);
        return result;
    }

    /**
     * Q6: 011 Matrix
     * You are given a matrix of size m x n where each element of the matrix is either 0 or 1
     * . For each cell in the matrix, you need to calculate the shortest distance to the nearest cell that contains 0
     */
    public int[][] updateMatrix(int[][] matrix) {

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[0][0];
        }

        Queue<int[]> queue = new LinkedList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.offer(new int[]{i, j});
                } else {
                    matrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        int[] directions = {-1, 0, 1, 0, -1};

        while (!queue.isEmpty()) {
            int[] position = queue.poll();
            int currentRow = position[0];
            int currentCol = position[1];
            for (int k = 0; k < 4; k++) {
                int newRow = currentRow + directions[k];
                int newCol = currentCol + directions[k + 1];

                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
                        && matrix[newRow][newCol] == Integer.MAX_VALUE) {
                    matrix[newRow][newCol] = matrix[currentRow][currentCol] + 1;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
        return matrix;
    }

    /**
     * Q7: K Closest Points to Origin
     * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k,
     * return the k closest points to the origin (0, 0).
     * The distance between two points on the X-Y plane is the Euclidean distance
     * Formulae :: (i.e., √(x1 - x2)2 + (y1 - y2)2).
     * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
     */
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> priorityQueue =
                new PriorityQueue<>((a, b) -> (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1]));

        for (int[] point : points) {
            priorityQueue.offer(point);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        int[][] result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = priorityQueue.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        Week5 week5 = new Week5();

        System.out.println("Q1: reverseBits " + week5.reverseBits(43261596));
        System.out.println("Q1: reverseBits " + week5.reverseBits(429496723));


        /**
         *          //root = [3,4,5,1,2], subRoot = [4,1,2]
         *                               3
         *         //                 /   \
         *         //                4     5
         *         //               / \
         *         //              1   2
         */
        TreeNode t = new TreeNode(3);
        t.left = new TreeNode(4);
        t.right = new TreeNode(5);
        t.left.left = new TreeNode(1);
        t.left.right = new TreeNode(2);

        TreeNode s = new TreeNode(4);
        s.left = new TreeNode(1);
        s.right = new TreeNode(2);

        System.out.println("Q2: isSubtree " + week5.isSubtree(t, s));

        log("Q3: sortedSquares ", week5.sortedSquares(new int[]{-4, -1, 0, 3, 10}));
        log("Q3: sortedSquares ", week5.sortedSquares(new int[]{-7, -3, 2, 3, 11}));
        log("Q3: sortedSquaresTwoPointers ", week5.sortedSquaresTwoPointers(new int[]{-4, -1, 0, 3, 10}));
        log("Q3: sortedSquaresTwoPointers ", week5.sortedSquaresTwoPointers(new int[]{-7, -3, 2, 3, 11}));


        System.out.println("Q4: maxSubArray " + week5.maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
        System.out.println("Q4: maxSubArray " + week5.maxSubArray(new int[]{5, 4, -1, 7, 8}));
        System.out.println("Q4: maxSubArray " + week5.maxSubArray(new int[]{1}));

        int[][] intervals = {{1, 3}, {5, 7}, {8, 10}, {15, 18}};
        int[] newInterval = {6, 9};
        int[][] result = week5.insert(intervals, newInterval);
        System.out.println("Q5: Inserted intervals: " + Arrays.deepToString(result));
        // Expected output: [[1, 3], [5, 10], [15, 18]]
        int[][] intervals2 = {{1, 2}, {3, 5}, {6, 7}, {8, 10}};
        int[] newInterval2 = {4, 8};
        int[][] result2 = week5.insert(intervals2, newInterval2);
        System.out.println("Q5: Inserted intervals: " + Arrays.deepToString(result2));

        int[][] mat1 = {
                {0, 0, 0},
                {0, 1, 0},
                {0, 0, 0}
        };
        int[][] mat2 = {
                {0, 1, 0},
                {1, 1, 1},
                {0, 1, 0}
        };
        int[][] mat3 = {
                {0, 0, 0},
                {0, 1, 0},
                {1, 1, 1}
        };
        System.out.println("Q6 updateMatrix " + Arrays.deepToString(week5.updateMatrix(mat1)));
        System.out.println("Q6 updateMatrix " + Arrays.deepToString(week5.updateMatrix(mat2)));
        System.out.println("Q6 updateMatrix " + Arrays.deepToString(week5.updateMatrix(mat3)));

        int[][] points = {{1, 3}, {2, -2}};
        int[][] points1 = {{3, 3}, {5, -1}, {-2, 4}};
        System.out.println("Q7 kClosest" + Arrays.deepToString(week5.kClosest(points, 1)));
        System.out.println("Q7 kClosest" + Arrays.deepToString(week5.kClosest(points1, 2)));

    }
}
