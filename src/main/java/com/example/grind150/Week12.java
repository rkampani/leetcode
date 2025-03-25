package com.example.grind150;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class Week12 {

    /**
     * Q2 Kth Smallest Element in a BST
     * Given the root of a binary search tree, and an integer k,
     * return the kth smallest value (1-indexed) of all the values of the nodes in the tree.
     * Input: root = [3,1,4,null,2], k = 1
     * Output: 1
     */
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) return -1;

        Deque<TreeNode> stack = new ArrayDeque<>();

        while (root != null || !stack.isEmpty()) {
            // Reach the leftmost node of the current subtree
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            // Process leftmost node
            root = stack.pop();
            // Decrement k and if it becomes 0, it means we found our k-th smallest
            if (--k == 0) {
                return root.val;
            }
            // Move to right subtree to continue the process
            root = root.right;
        }
        return 0;
    }

    /**
     * Q3 Daily Temperatures
     * Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i] is the number of days you have to wait after the ith day to get a warmer temperature.
     * If there is no future day for which this is possible, keep answer[i] == 0 instead.
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) return null;
        int[] result = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();

        for (int currentIndex = 0; currentIndex < temperatures.length; currentIndex++) {
            //current temperature is greater than the temperature at the top index of the stack
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[currentIndex]) {
                int prevIndex = stack.pop();
                result[prevIndex] = currentIndex - prevIndex;
            }
            stack.push(currentIndex);
        }
        return result;
    }

    /**
     * Q4: House Robber
     * Input: nums = [1,2,3,1]
     * Output: 4
     * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
     * Total amount you can rob = 1 + 3 = 4.
     */
    public int rob(int[] nums) {
        // f represents the max profit we can get from the previous house
        int prevNoRob = 0;
        // g represents the max profit we can get if we rob the current house
        int prevRob = 0;

        // Iterate over all the houses in the array
        for (int currentHouseValue : nums) {
            // Store max profit of robbing/not robbing the previous house
            int tempPrevNoRob = Math.max(prevNoRob, prevRob);

            // If we rob the current house, we cannot rob the previous one
            // hence our current profit is previous house's no-rob profit + current house
            // value
            prevRob = prevNoRob + currentHouseValue;

            // Update the previous no-rob profit to be the best of robbing or not robbing
            // the last house
            prevNoRob = tempPrevNoRob;
        }

        // Return the max profit we can get from the last house,
        return Math.max(prevNoRob, prevRob);
    }

    public int robb(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int prev2 = 0; // dp[i-2]
        int prev1 = 0; // dp[i-1]

        for (int num : nums) {
            int current = Math.max(prev1, prev2 + num);  // Max of robbing or not robbing current house
            prev2 = prev1;  // Move prev1 to prev2
            prev1 = current; // Move current to prev1
        }

        return prev1; // The last computed value is the answer
    }

    /**
     * Q5 Gas Station
     * Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
     * Output: 3
     */

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalSurplus = 0;
        int currentSurplus = 0;
        int startingPoint = 0;

        for (int i = 0; i < gas.length; i++) {
            totalSurplus += gas[i] - cost[i];
            currentSurplus += gas[i] - cost[i];
            if (currentSurplus < 0) {
                currentSurplus = 0;
                startingPoint = i + 1;
            }
        }
        return totalSurplus >= 0 ? startingPoint : -1;
    }

    /**
     * Q6 Next Permutation
     * Input: nums = [1,2,3]
     * Output: [1,3,2]
     */
    public void nextPermutation(int[] nums) {
        // Step 1: Find the first decreasing element from the end
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Step 2: Find the smallest element greater than nums[i] from the right
            int j = nums.length - 1;
            while (nums[j] <= nums[i]) {
                j--;
            }
            // Step 3: Swap nums[i] and nums[j]
            swap(nums, i, j);
        }

        // Step 4: Reverse the sequence after index i
        reverse(nums, i + 1);

    }
    // Helper method to swap elements
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // Helper method to reverse the array from index i
    private void reverse(int[] nums, int i) {
        int left = i, right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
    public static void main(String[] args) {
        Week12 week12 = new Week12();

        LRUCache cache = new LRUCache(2);
        cache.put(1, 1); // Cache is {1=1}
        cache.put(2, 2); // Cache is {1=1, 2=2}
        System.out.println("Q1 lruCache " + cache.get(1));  // Returns 1, Cache is {2=2, 1=1}
        cache.put(3, 3); // LRU key was 2, evicts key 2, Cache is {1=1, 3=3}
        System.out.println("Q1 lruCache " + cache.get(2));  // Returns -1 (not found)
        cache.put(4, 4); // LRU key was 1, evicts key 1, Cache is {3=3, 4=4}
        System.out.println("Q1 lruCache " + cache.get(1));  // Returns -1 (not found)
        System.out.println("Q1 lruCache " + cache.get(3));  // Returns 3, Cache is {4=4, 3=3}
        System.out.println("Q1 lruCache " + cache.get(4));  // Returns 4, Cache is {3=3, 4=4}


        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        System.out.println("Q2 kthSmallest " + week12.kthSmallest(root, 3));

        System.out.println("Q3 dailyTemperatures " + Arrays.toString(week12.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        System.out.println("Q3 dailyTemperatures " + Arrays.toString(week12.dailyTemperatures(new int[]{30, 40, 50, 60})));
        System.out.println("Q3 dailyTemperatures " + Arrays.toString(week12.dailyTemperatures(new int[]{30, 60, 90})));

        System.out.println("Q4 houseRobber " + week12.rob(new int[]{1, 2, 3, 1}));
        System.out.println("Q4 houseRobber " + week12.rob(new int[]{1, 2, 3, 1}));
        System.out.println("Q4 houseRobberbb " + week12.robb(new int[]{1, 2, 3, 1}));
        System.out.println("Q4 houseRobberbb " + week12.robb(new int[]{2, 7, 9, 3, 1}));

        System.out.println("Q5 canCompleteCircuit " + week12.canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));
        System.out.println("Q5 canCompleteCircuit " + week12.canCompleteCircuit(new int[]{2, 3, 4}, new int[]{3, 4, 3}));


        int[] nums1 = {1, 2, 3};
        week12.nextPermutation(nums1);
        System.out.println("Q6 nextPermutation " + java.util.Arrays.toString(nums1)); // Output: [1, 3, 2]

        // Example 2
        int[] nums2 = {3, 2, 1};
        week12.nextPermutation(nums2);
        System.out.println("Q6 nextPermutation " + java.util.Arrays.toString(nums2)); // Output: [1, 2, 3]

        // Example 3
        int[] nums3 = {1, 1, 5};
        week12.nextPermutation(nums3);
        System.out.println("Q6 nextPermutation " + java.util.Arrays.toString(nums3));
    }
}
