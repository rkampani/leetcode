package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class Week8 {

    /**
     * Rotting Oranges
     * You are given an m x n grid where each cell can have one of three values:
     * <p>
     * 0 representing an empty cell,
     * 1 representing a fresh orange, or
     * 2 representing a rotten orange.
     * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
     * <p>
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
     */
    public int orangesRotting(int[][] grid) {

        if (grid == null || grid.length == 0) {
            return 0;
        }
        Queue<int[]> rottenQue = new ArrayDeque<>();
        int freshOrangesCount = 0;
        // Traverse the grid to find all rotten oranges and count fresh oranges
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 2) {
                    rottenQue.offer(new int[]{row, col});
                } else if (grid[row][col] == 1) {
                    freshOrangesCount++;
                }
            }
        }
        // If there are no fresh oranges, return 0 because nothing needs to be rotted
        if (freshOrangesCount == 0) {
            return 0;
        }
        // Directions to move in the grid: (up, down, left, right)
        int[] directions = {-1, 0, 1, 0, -1};
        int minutes = 0;

        while (!rottenQue.isEmpty()) {
            int size = rottenQue.size();
            boolean rotted = false;
            for (int i = 0; i < size; i++) {
                int[] currentGrid = rottenQue.poll();
                // Check all 4 directions
                for (int k = 0; k < 4; k++) {
                    int newRow = currentGrid[0] + directions[k];
                    int newCol = currentGrid[1] + directions[k + 1];
                    // Check boundaries and if the new cell is a fresh orange
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length && grid[newRow][newCol] == 1) {
                        grid[newRow][newCol] = 2;
                        rottenQue.offer(new int[]{newRow, newCol});
                        freshOrangesCount--;
                        rotted = true;
                    }
                }
            }
            // Increment time only if some oranges rotted in this level of BFS
            if (rotted) {
                minutes++;
            }
        }
        // If there are still fresh oranges left, return -1
        return freshOrangesCount == 0 ? minutes : -1;
    }

    /**
     * Q2.Search in Rotated Sorted Array
     * There is an integer array nums sorted in ascending order (with distinct values).
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     * Input: nums = [4,5,6,7,0,1,2], target = 3
     * Output: -1
     * Example 3:
     * <p>
     * Input: nums = [1], target = 0
     * Output: -1
     */
    public int search(int[] nums, int target) {
        if (nums == null) return -1;

        int start = 0;
        int end = nums.length - 1;
        int size = nums.length - 1;

        while (start < end) {

            int mid = start + (end - start) / 2;
            if (nums[0] <= nums[mid]) {
                if (nums[0] <= target && target <= nums[mid]) {
                    end = mid;

                } else {
                    start = mid + 1;
                }

            } else {

                if (nums[mid] < target && target < nums[size]) {
                    start = mid + 1;
                } else {
                    end = mid;
                }
            }
        }
        return nums[start] == target ? start : -1;
    }

    /**
     * Q3.Combination Sum
     * Given an array of distinct integers candidates and a target integer target,
     * return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
     * <p>
     * The same number may be chosen from candidates an unlimited number of times.
     * Two combinations are unique if the frequency of at least one of the chosen numbers is different.
     * <p>
     * The test cases are generated such that the number of unique combinations that sum up to target is less
     * than 150 combinations for the given input.
     * Input: candidates = [2,3,6,7], target = 7
     * Output: [[2,2,3],[7]]
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        backtrackCombinationSum(candidates, target, 0, result, new ArrayList<>());
        return result;
    }

    private void backtrackCombinationSum(int[] candidates, int target, int start, List<List<Integer>> result, ArrayList<Integer> current) {

        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            current.add(candidates[i]);
            backtrackCombinationSum(candidates, target - candidates[i], i, result, current);
            current.remove(current.size() - 1);
        }
    }

    /**
     * Q3:Permutations
     * Given an array nums of distinct integers, return all the possible permutations. You can return the answer in any order.
     * Example 1:
     * <p>
     * Input: nums = [1,2,3]
     * Output: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * Example 2:
     * <p>
     * Input: nums = [0,1]
     * Output: [[0,1],[1,0]]
     * Example 3:
     * <p>
     * Input: nums = [1]
     * Output: [[1]]
     */

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null) return Collections.emptyList();
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        backtrackPermutation(nums, result, new ArrayList<>(), visited);
        return result;
    }

    private void backtrackPermutation(int[] nums, List<List<Integer>> result, ArrayList<Integer> current, boolean[] visited) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < nums.length; i++) {

            if (!visited[i]) {
                visited[i] = true;
                current.add(nums[i]);
                backtrackPermutation(nums, result, current, visited);
                current.remove(current.size() - 1);
                visited[i] = false;
            }
        }
    }

    /**
     * Q4.Merge Intervals
     * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
     * and return an array of the non-overlapping intervals that cover all the intervals in the input.
     * Example 1:
     * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
     * Output: [[1,6],[8,10],[15,18]]
     * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
     * Example 2:
     * Input: intervals = [[1,4],[4,5]]
     * Output: [[1,5]]
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null) return null;

        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> mergedIntervals = new ArrayList<>();

        mergedIntervals.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int start = intervals[i][0];
            int end = intervals[i][1];

            int[] lastInterval = mergedIntervals.get(mergedIntervals.size() - 1);
            int prevEnd = lastInterval[1];
            if (prevEnd > start) {
                lastInterval[1] = Math.max(end, prevEnd);
            } else {
                mergedIntervals.add(intervals[i]);
            }
        }
        return mergedIntervals.toArray(new int[0][0]);
    }

    /**
     * Q6: Lowest Common Ancestor of a Binary Tree
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
     * <p>
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between
     * two nodes p and q as the lowest node in T that has both p and q as descendants
     * (where we allow a node to be a descendant of itself).”
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        // Recursively find the LCA in the left and right subtrees
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        // If both left and right are non-null, the current root is the LCA
        if (left != null && right != null) {
            return root;
        }

        return left != null ? left : right;
    }

    public static void main(String[] args) {
        Week8 week8 = new Week8();

        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };
        int[][] grid1 = {{0, 2}};
        int[][] grid3 = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };
        System.out.println("Q1 orangesRotting " + week8.orangesRotting(grid));
        System.out.println("Q1 orangesRotting " + week8.orangesRotting(grid1));
        System.out.println("Q1 orangesRotting " + week8.orangesRotting(grid3));

        System.out.println("Q2 searchInRotatedSortedArray " + week8.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        System.out.println("Q2 searchInRotatedSortedArray " + week8.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));
        System.out.println("Q2 searchInRotatedSortedArray " + week8.search(new int[]{1}, 0));

        System.out.println("Q2 combinationSum " + week8.combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println("Q2 combinationSum " + week8.combinationSum(new int[]{2, 3, 5}, 8));
        System.out.println("Q2 combinationSum " + week8.combinationSum(new int[]{2}, 1));

        System.out.println("Q3 permute " + week8.permute(new int[]{1, 2, 3}));
        System.out.println("Q3 permute " + week8.permute(new int[]{0, 1}));
        System.out.println("Q3 permute " + week8.permute(new int[]{1}));

        int[][] interval = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        int[][] interval1 = {{1, 4}, {4, 5}};
        System.out.println("Q4 mergeInterval " + Arrays.deepToString(week8.merge(interval)));
        System.out.println("Q4 mergeInterval " + Arrays.deepToString(week8.merge(interval1)));

        //[3,5,1,6,2,0,8,null,null,7,4],
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(5);
        root.right = new TreeNode(1);
        root.left.left = new TreeNode(6);
        root.left.right = new TreeNode(2);
        root.right.left = new TreeNode(0);
        root.right.right = new TreeNode(8);
        root.left.right.left = new TreeNode(7);
        root.left.right.right = new TreeNode(4);

        TreeNode p = root.left;  // Node 5
        TreeNode q = root.left.right.right;  // Node 4
        TreeNode lca = week8.lowestCommonAncestor(root, p, q);
        System.out.println("Q6 lowestCommonAncestor " + p.val + " and " + q.val + " is: " + lca.val);
        System.out.println("Q6 lowestCommonAncestor " );
        TreeNode.printInOrder(lca);


        TreeNode p1 = root.left;  // Node 5
        TreeNode q1 = root.right;  // Node 1
        TreeNode lca1 = week8.lowestCommonAncestor(root, p1, q1);
        System.out.println("Q6 lowestCommonAncestor " + p1.val + " and " + q1.val + " is: " + lca1.val);
        System.out.println("Q6 lowestCommonAncestor " );
        TreeNode.printInOrder(lca1);

    }
}
