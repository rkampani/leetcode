package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Week10 {

    /**
     * Q1: Spiral Matrix
     * Given an m x n matrix, return all elements of the matrix in spiral order.
     * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * Output: [1,2,3,6,9,8,7,4,5]
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;
        List<Integer> result = new ArrayList<>();
        while (top <= bottom && left <= right) {


            // Traverse from left to right along the top row
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;

            // Traverse from top to bottom along the right column
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            if (top <= bottom) {
                // Traverse from right to left along the bottom row
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;
            }

            if (left <= right) {
                // Traverse from bottom to top along the left column
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }

        return result;
    }

    /**
     * Q2:Subsets
     * Given an integer array nums of unique elements, return all possible subsets (the power set).
     * <p>
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     */
    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        backtrackSubsets(nums, 0, result, new ArrayList<>());
        return result;
    }

    private void backtrackSubsets(int[] nums, int start, List<List<Integer>> result, ArrayList<Integer> current) {
        result.add(new ArrayList<>(current));

        for (int i = start; i < nums.length; i++) {
            current.add(nums[i]);
            backtrackSubsets(nums, i + 1, result, current);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Q3:  Binary Tree Right Side View
     * Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes you can see ordered from top to bottom.
     * <p>
     * Input: root = [1,2,3,null,5,null,4]
     * Output: [1,3,4]
     */
    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) return Collections.emptyList();
        List<Integer> result = new ArrayList<>();

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {

            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode currentNode = queue.poll();

                if (i == levelSize - 1) {
                    result.add(currentNode.val);
                }
                if (currentNode.left != null) {
                    queue.add(currentNode.left);
                }

                if (currentNode.right != null) {
                    queue.add(currentNode.right);
                }

            }
        }
        return result;
    }

    /**
     * Q4: Longest Palindromic Substring
     * Longest Palindromic Substring
     * Input: s = "babad"
     * Output: "bab"
     * Explanation: "aba" is also a valid answer.
     */
    public String longestPalindrome(String s) {
        if (s == null) return null;

        String longestPalindrome = "";
        for (int centre = 0; centre < s.length(); centre++) {
            String str = longestPalindrome(s, centre, centre);
            if (str.length() > longestPalindrome.length()) {
                longestPalindrome = str;
            }
            String str1 = longestPalindrome(s, centre, centre + 1);
            if (str1.length() > longestPalindrome.length()) {
                longestPalindrome = str1;
            }
        }
        return longestPalindrome;
    }

    private String longestPalindrome(String string, int left, int right) {
        while (left >= 0 && right < string.length() && string.charAt(left) == string.charAt(right)) {
            left--;
            right++;
        }
        return string.substring(left + 1, right);
    }

    /**
     * Q5: Unique Path
     * There is a robot on an m x n grid. The robot is initially located
     * at the top-left corner (i.e., grid[0][0]). The robot tries to move to the bottom-right corner (i.e., grid[m - 1][n - 1]).
     * The robot can only move either down or right at any point in time.
     * Input: m = 3, n = 7
     * Output: 28
     */
    public int uniquePaths1DDP(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        //Starting from row 1 and column 1, there is only one way to traverse if either one of the conditions is true.
        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                dp[col] = dp[col] + dp[col - 1];
            }
        }
        return dp[n - 1];
    }

    public int uniquePaths2DDP(int m, int n) {
        int[][] dp = new int[m][n];
        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                //Starting from row 1 and column 1, there is only one way to traverse if either one of the conditions is true.
                if (row == 0 || col == 0)
                    dp[row][col] = 1;
                else
                    dp[row][col] = dp[row][col - 1] + dp[row - 1][col];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * Q6: Construct Binary Tree from Preorder and Inorder Traversal
     * Given two integer arrays preorder and inorder where preorder is the preorder traversal of a binary tree
     * and inorder is the inorder traversal of the same tree, construct and return the binary tree.
     *
     * We can use the properties of preorder and inorder traversals to construct the binary tree:
     *
     * Preorder Traversal gives us the root of the tree as the first element.
     * Inorder Traversal gives us the left and right subtrees of the root:
     * The elements before the root in the inorder traversal are in the left subtree.
     * The elements after the root in the inorder traversal are in the right subtree.
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null && inorder == null) return null;

        HashMap<Integer, Integer> inorderIndexMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexMap.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, inorderIndexMap, 0, 0, inorder.length - 1);

    }

    private TreeNode buildTreeHelper(int[] preorder, HashMap<Integer, Integer> inorderIndexMap, int preStart,
                                     int inStart, int inEnd) {
        // Base case: If there are no elements to process
        if (preStart >= preorder.length || inStart > inEnd) {
            return null;
        }
        // The first element in preorder is the root of the tree
        int rootVal = preorder[preStart];
        TreeNode root = new TreeNode(rootVal);

        int rootIndex = inorderIndexMap.get(rootVal); // Get root index from map
        int leftSize = rootIndex - inStart;

        root.left = buildTreeHelper(preorder, inorderIndexMap, preStart + 1, inStart, rootIndex - 1);
        root.right = buildTreeHelper(preorder, inorderIndexMap, preStart + leftSize + 1, rootIndex + 1, inEnd);

        return root;
    }

    /**
     * Q7: Container With Most Water
     * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
     * <p>
     * Find two lines that together with the x-axis form a container, such that the container contains the most water.
     * <p>
     * Return the maximum amount of water a container can store.
     * Input: height = [1,8,6,2,5,4,8,3,7]
     * Output: 49
     */
    public int maxArea(int[] height) {
        if (height == null) {
            return -1;
        }
        int left = 0;
        int right = height.length - 1;
        int area = 0;
        while (left < right) {

            int width = right - left;
            int currentArea = width * Math.min(height[left], height[right]);
            area = Math.max(currentArea, area);
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return area;
    }

    public static void main(String[] args) {
        Week10 week10 = new Week10();
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] matrix2 = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
        System.out.println("Q1 spiralOrder " + week10.spiralOrder(matrix));
        System.out.println("Q1 spiralOrder " + week10.spiralOrder(matrix2));

        System.out.println("Q2 subSets " + week10.subsets(new int[]{1, 2, 3}));
        System.out.println("Q2 subSets " + week10.subsets(new int[]{0}));

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        System.out.println("Q3 rightSideView " + week10.rightSideView(root));

        System.out.println("Q4 longestPalindrome " + week10.longestPalindrome("babad"));
        System.out.println("Q4 longestPalindrome " + week10.longestPalindrome("cbbd"));

        System.out.println("Q5 uniquePaths1DDP " + week10.uniquePaths1DDP(3, 7));
        System.out.println("Q5 uniquePaths1DDP " + week10.uniquePaths1DDP(3, 2));
        System.out.println("Q5 uniquePaths2DDP " + week10.uniquePaths2DDP(3, 7));
        System.out.println("Q5 uniquePaths2DDP " + week10.uniquePaths2DDP(3, 2));

        // Example Input
        int[] preorder = {3, 9, 20, 15, 7};
        int[] inorder = {9, 3, 15, 20, 7};

        System.out.println("Q6 buildTree-Root of the tree " + week10.buildTree(preorder, inorder).val);

        System.out.println("Q7 maxArea " + week10.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println("Q7 maxArea " + week10.maxArea(new int[]{1, 1}));
    }
}
