package com.example.grind150;

import java.util.Stack;

public class Week24 {

    /**
     * Largest Rectangle in Histogram
     * Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, return the area of the largest rectangle in the histogram.
     * <p>
     * Input: heights = [2,1,5,6,2,3]
     * Output: 10
     * Explanation: The above is a histogram where width of each bar is 1.
     * The largest rectangle is shown in the red area, which has an area = 10 units.
     */

    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;

        // Loop through all bars in the histogram
        for (int i = 0; i <= n; i++) {

            int h = (i == n) ? 0 : heights[i];

            // While the current bar is shorter than the bar at the index on top of the stack
            while (!stack.isEmpty() && h < heights[stack.peek()]) {
                int height = heights[stack.pop()]; // Pop the top bar
                //Width is determined by the indices: current index
                //i minus the index of the previous smaller bar.
                int width = stack.isEmpty() ? i : i - stack.peek() - 1; // Calculate width
                maxArea = Math.max(maxArea, height * width); // Calculate area
            }

            stack.push(i); // Push the current index onto the stack
        }
        return maxArea;
    }

    /**
     * Binary Tree Maximum Path Sum
     * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them.
     * A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.
     */
    int dfsMmaxPathSum = 0;

    public int maxPathSum(TreeNode root) {
        dfsMmaxPathSum(root);
        return dfsMmaxPathSum;
    }

    private int dfsMmaxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //  Recur for left and right subtrees If negative, we ignore the left path
        int leftMax = Math.max(dfsMmaxPathSum(root.left), 0);
        // If negative, we ignore the left path
        int rightMax = Math.max(dfsMmaxPathSum(root.right), 0);
        int currentPathSum = root.val + leftMax + rightMax;
        dfsMmaxPathSum = Math.max(dfsMmaxPathSum, currentPathSum);
        return currentPathSum;
    }

    /**
     *
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0;
        int right = m;
        while (left <= right) {
            // Partition point for nums1
            int partition1 = (left + right) / 2;
            // Partition point for nums2
            int partition2 = (m + n + 1) / 2 - partition1;
            //maxLeftX, minRightX, maxLeftY, and minRightY are the elements on the left and right of the partitions in nums1 and nums2.
            // If the partition is at the boundary of the array, we use Integer.MIN_VALUE or Integer.MAX_VALUE as placeholders.
            // If partitionX is 0 it means there is no element on the left side
            // If partitionX is m it means there is no element on the right side
            // Edge cases: if partition is at start/end
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];

            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];

            // Check if we found the correct partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // If total length is odd
                if ((m + n) % 2 == 1) {
                    return Math.max(maxLeft1, maxLeft2);
                }
                // If total length is even- // Even total length: median is the average of two middle elements
                return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
            }
            // Adjust partition points,If we are too far right on nums1, move left
            else if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            }
            else {
                //// If we are too far left on nums1, move right
                left = partition1 + 1;
            }
        }
        return 0.0; // Should never reach here if inputs are valid
    }
    public static void main(String[] args) {
        Week24 week24 = new Week24();

        int[] heights1 = {2, 1, 5, 6, 2, 3};
        System.out.println("Q1 largestRectangleArea " + week24.largestRectangleArea(heights1)); // Output: 10

        // Test case 2
        int[] heights2 = {2, 4};
        System.out.println("Q1 largestRectangleArea " + week24.largestRectangleArea(heights2)); // Output: 4


        // Test case 1: Example tree
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);

        System.out.println("Q2 Maximum Path Sum: " + week24.maxPathSum(root)); // Expected output: 6 (path: 2 -> 1 -> 3)

        // Test case 2: Tree with negative values
        TreeNode root2 = new TreeNode(-10);
        root2.left = new TreeNode(9);
        root2.right = new TreeNode(20);
        root2.right.left = new TreeNode(15);
        root2.right.right = new TreeNode(7);
        System.out.println("Q2 Maximum Path Sum: " + week24.maxPathSum(root2));

        FrequencyStack freqStack = new FrequencyStack();
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(5);
        freqStack.push(7);
        freqStack.push(4);
        freqStack.push(5);

        System.out.println("Q3 FrequencyStack.pop " +freqStack.pop()); // returns 5
        System.out.println("Q3 FrequencyStack.pop "+freqStack.pop()); // returns 7
        System.out.println("Q3 FrequencyStack.pop "+freqStack.pop()); // returns 5
        System.out.println("Q3 FrequencyStack.pop "+freqStack.pop()); // returns 7

        // Test cases
        int[] nums1 = {1, 3};
        int[] nums2 = {2};
        System.out.println("Q4 findMedianSortedArrays " +week24.findMedianSortedArrays(nums1, nums2));  // Output: 2.0

        int[] nums1_2 = {1, 2};
        int[] nums2_2 = {3, 4};
        System.out.println("Q4 findMedianSortedArrays " +week24.findMedianSortedArrays(nums1_2, nums2_2));  // Output: 2.5

        int[] nums1_3 = {0, 0};
        int[] nums2_3 = {0, 0};
        System.out.println("Q4 findMedianSortedArrays " +week24.findMedianSortedArrays(nums1_3, nums2_3));  // Output: 0.0

        int[] nums1_4 = {};
        int[] nums2_4 = {1};
        System.out.println("Q4 findMedianSortedArrays " +week24.findMedianSortedArrays(nums1_4, nums2_4));  // Output: 1.0

    }
}
