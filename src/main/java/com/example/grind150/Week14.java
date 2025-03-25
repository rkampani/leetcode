package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Week14 {

    /**
     * Shortest Path to Get Food
     *
     * @param grid
     * @return
     */
    public int shortestPathToGetFood(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        // Visited array to avoid re-visiting cells
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {

                if (grid[row][col] == 'P') {
                    queue.offer(new int[]{row, col});
                    visited[row][col] = true;
                    break;
                }
            }
        }
        int[] dirs = {-1, 0, 1, 0, 1};
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cell = queue.poll();
                int r = cell[0], c = cell[1];

                // If we find the food 'F', return the number of steps
                if (grid[r][c] == 'F') {
                    return steps;
                }

                // Explore the 4 possible directions (up, right, down, left)
                for (int j = 0; j < 4; j++) {
                    int newRow = r + dirs[j];
                    int newCol = c + dirs[j + 1];

                    // Check if the new position is within bounds and not visited
                    if (newRow >= 0 && newRow < grid.length && newCol >= 0 && newCol < grid[0].length
                            && !visited[newRow][newCol] && grid[newRow][newCol] != 'X') {
                        visited[newRow][newCol] = true;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            // Increment the step count after finishing the level
            steps++;
        }
        return steps;
    }

    public int findDuplicate(int[] nums) {
        if (nums == null) return -1;
        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {

            if (!hashSet.add(num)) {
                return num;
            }
        }
        return -1;
    }

    /**
     * Top K Frequent Words
     * Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
     * Input: words = ["i","love","leetcode","i","love","coding"], k = 2
     * Output: ["i","love"]
     * Explanation: "i" and "love" are the two most frequent words.
     * Note that "i" comes before "love" due to a lower alphabetical order.
     * Example 2:
     * <p>
     * Input: words = ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4
     * Output: ["the","is","sunny","day"]
     * Explanation: "the", "is", "sunny" and "day" are the four most frequent words, with the number of occurrence being 4, 3, 2 and 1 respectively.
     */
    public List<String> topKFrequent(String[] words, int k) {

        if (words == null)
            return Collections.emptyList();

        Map<String, Integer> wordToCount = new HashMap<>();
        for (String word : words) {
            wordToCount.put(word, wordToCount.getOrDefault(word, 0) + 1);
        }

        PriorityQueue<String> priorityQueue = new PriorityQueue<>((word1, word2) -> {
            int freq = wordToCount.get(word1) - wordToCount.get(word2);
            return freq == 0 ? word2.compareTo(word1) : freq;
        });

        for (String word : wordToCount.keySet()) {
            priorityQueue.offer(word);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        LinkedList<String> result = new LinkedList<>();
        while (!priorityQueue.isEmpty()) {
            result.addFirst(priorityQueue.poll());
        }
        return result;
    }

    /**
     * Longest Increasing Subsequence
     * Given an integer array nums, return the length of the longest strictly increasing subsequence.
     * Input: nums = [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     */
    public int lengthOfLIS(int[] nums) {

        if (nums == null) return -1;
        int n = nums.length;
        int[] dp = new int[n];  // dp[i] will hold the length of the LIS ending at index i
        Arrays.fill(dp, 1);  // Initialize all dp values to 1, as each element is a subsequence of length 1

        // Loop over all elements in the array to find the LIS
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }

        return Arrays.stream(dp).max().getAsInt();
    }

    /**
     * Q5 : Graph Valid Tree
     * The graph must be connected: All nodes must be reachable from any other node.
     * The graph must have no cycles: It should have exactly n - 1 edges, where n is the number of nodes. This is because a tree with n nodes must have n - 1 edges.
     */
    public boolean validTree(int n, int[][] edges) {
        if (n - 1 != edges.length) {  // Condition 1: There must be exactly n - 1 edges
            return false;
        }
        // Create an adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.putIfAbsent(edge[0], new ArrayList<>());
            graph.putIfAbsent(edge[1], new ArrayList<>());
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // Visited array to keep track of visited nodes
        boolean[] visited = new boolean[n];

        // Perform DFS starting from node 0
        if (hasCycle(-1, 0, graph, visited)) {
            return false;  // If a cycle is detected, return false
        }

        // Ensure all nodes are visited (graph is connected)
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }

        return true;
    }

    private boolean hasCycle(int parent, int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
        // Mark the current node as visited
        visited[node] = true;

        // Explore all the neighbors of the current node
        for (int neighbor : graph.get(node)) {
            // Skip the parent node
            if (neighbor == parent) {
                continue;
            }
            // If the neighbor is already visited, we found a cycle
            if (visited[neighbor]) {
                return true;
            }
            // Recur for the neighbor
            if (hasCycle(node, neighbor, graph, visited)) {
                return true;
            }
        }

        return false;  // No cycle detected
    }

    /**
     * Q6: Course Schedule II
     * Input: numCourses = 2, prerequisites = [[1,0]]
     * Output: [0,1]
     * Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,2,1,3]
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {

        List<List<Integer>> graphs = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            graphs.add(new ArrayList<>());
        }
        int[] inDegree = new int[numCourses];
        for (int[] pre : prerequisites) {
            int course = pre[0];
            int preRequest = pre[1];
            graphs.get(course).add(preRequest);
            graphs.get(preRequest).add(course);
            ++inDegree[course];
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numCourses; ++i) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }
        int[] order = new int[numCourses];
        int courseCount = 0;

        while (!queue.isEmpty()) {

            int currentCourse = queue.poll();
            order[courseCount] = currentCourse;
            courseCount++;

            for (int neighbor : graphs.get(currentCourse)) {
                if (--inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }
        return courseCount == numCourses ? order : new int[0];
    }

    public static void main(String[] args) {
        Week14 week14 = new Week14();
        // Example Grid:
        // P -> person
        // F -> food
        // X -> wall
        char[][] grid = {
                {'X', 'X', 'X', 'X', 'X'},
                {'X', 'P', 'O', 'O', 'X'},
                {'X', 'X', 'O', 'F', 'X'},
                {'X', 'X', 'X', 'X', 'X'}
        };
        System.out.println("Q1 shortestPathToGetFood " + week14.shortestPathToGetFood(grid));
        System.out.println("Q2 findDuplicate " + week14.findDuplicate(new int[]{1, 2, 2, 3, 4}));
        System.out.println("Q2 findDuplicate " + week14.findDuplicate(new int[]{3, 1, 3, 4, 2}));
        System.out.println("Q2 findDuplicate " + week14.findDuplicate(new int[]{3, 3, 3, 3, 3}));


        System.out.println("Q3 topKFrequent " + week14.topKFrequent(new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2));
        System.out.println("Q3 topKFrequent " + week14.topKFrequent(new String[]{"the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"}, 4));

        System.out.println("Q4 lengthOfLIS " + week14.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        System.out.println("Q4 lengthOfLIS " + week14.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        System.out.println("Q4 lengthOfLIS " + week14.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));

        int[][] edges1 = {
                {0, 1}, {0, 2}, {0, 3}, {1, 4}
        };
        int[][] edges2 = {
                {0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 0}
        };
        int[][] edges3 = {{0, 1}, {1, 2}, {2, 3}};
        System.out.println("Q5 validTree-hasNoCycle " + week14.validTree(5, edges1));
        System.out.println("Q5 validTree-hasNoCycle " + week14.validTree(5, edges2));
        System.out.println("Q5 validTree-hasNoCycle " + week14.validTree(5, edges3));


        System.out.println("Q6 courseScheduleII-findOrder" + Arrays.toString(week14.findOrder(2, new int[][]{{0, 1}, {1, 0}})));
        System.out.println("Q6 courseScheduleII-findOrder" + Arrays.toString(week14.findOrder(2, new int[][]{{0, 1}})));
        System.out.println("Q6 courseScheduleII-findOrder" + Arrays.toString(week14.findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
    }
}
