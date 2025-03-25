package com.example.grind150;

import java.util.Arrays;

public class Week7 {

    /**
     * Q1: Coin Change
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];

        Arrays.fill(dp, amount + 1);
        dp[0] = 0;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                dp[currentAmount] = Math.min(dp[currentAmount], dp[currentAmount - coin] + 1);
            }
        }
        return dp[amount] >= amount + 1 ? -1 : dp[amount];
    }

    /**
     * Q3: Product of Array Except Self
     */
    public int[] productExceptSelf(int[] nums) {

        if (nums == null) return nums;
        int[] result = new int[nums.length];
        int leftProduct = 1;

        for (int i = 0; i < nums.length; i++) {
            result[i] = leftProduct;
            leftProduct *= nums[i];
        }

        int rightProduct = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        return result;
    }

    /**
     * Q4:Validate Binary Search Tree
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     * <p>
     * A valid BST is defined as follows:
     * <p>
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTHelper(TreeNode node, long min, long max) {
        if (node == null) {
            return true;
        }

        // Check if the current node's value is within the valid range
        if (node.val <= min || node.val >= max) {
            return false;
        }

        // Recursively validate the left and right subtrees, adjusting the min and max bounds
        return isValidBSTHelper(node.left, min, node.val) &&
                isValidBSTHelper(node.right, node.val, max);
    }


    /**
     * Q6:
     * Number of Islands
     */
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }

        int rowLength = grid.length;
        int colLength = grid[0].length;
        int noOfIslands = 0;
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                if (grid[row][col] == '1') {
                    noOfIslands++;
                    dfsNnumIslands(row, col, grid);
                }
            }
        }
        return noOfIslands;
    }

    private void dfsNnumIslands(int row, int col, char[][] grid) {
        // Mark the current cell as visited by setting it to '0'
        grid[row][col] = '0';

        // Array to facilitate the exploration of adjacent directions (up, right, down,
        // left)
        int[] directions = { -1, 0, 1, 0, -1 };

        // Explore all 4 adjacent directions
        for (int k = 0; k < 4; k++) {
            int newRow = row + directions[k];
            int newCol = col + directions[k + 1];
            // Check boundaries and if the adjacent cell is part of an island
            if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == '1') {
                // Continue DFS exploration for the adjacent cell
                dfsNnumIslands(newRow, newCol, grid);
            }
        }
    }

    public static void main(String[] args) {
        Week7 week7 = new Week7();
        TrieNode trie = new TrieNode();
        trie.insert("apple");
        System.out.println("Q1 TrieNode.search " + trie.search("apple"));
        System.out.println("Q1 TrieNode.search " + trie.search("app"));
        System.out.println("Q1 TrieNode.search " + trie.searchPrefix("app"));
        trie.insert("app");
        System.out.println("Q1 TrieNode.search " + trie.search("apple"));

        System.out.println("Q2 coinChange " + week7.coinChange(new int[]{1, 2, 5}, 11));
        System.out.println("Q2 coinChange " + week7.coinChange(new int[]{2}, 3));
        System.out.println("Q2 coinChange " + week7.coinChange(new int[]{1}, 0));

        System.out.println("Q3 productExceptSelf " + Arrays.toString(week7.productExceptSelf(new int[]{1, 2, 3, 4})));
        System.out.println("Q3 productExceptSelf " + Arrays.toString(week7.productExceptSelf(new int[]{-1, 1, 0, -3, 3})));
        System.out.println("Q3 productExceptSelf " + Arrays.toString(week7.productExceptSelf(new int[]{1})));

        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println("Q4 MinStack.getMin " + minStack.getMin());
        minStack.pop();
        System.out.println("Q4 MinStack.getMin " + minStack.top());
        System.out.println("Q4 MinStack.getMin " + minStack.getMin());

        TreeNode root = new TreeNode(2);
        root.left = new TreeNode(1);
        root.right = new TreeNode(3);
        System.out.println("Q5 isValidBST " + week7.isValidBST(root));

        char[][] grid = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}
        };

        System.out.println("Q6 numIslands " + week7.numIslands(grid));

    }
}
