package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Week11 {

    /**
     * Q1:Letter Combinations of a Phone Number
     */
    private static final String[] digitToChars = {
            "", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    public List<String> letterCombinations(String digits) {

        if (digits == null) return Collections.emptyList();

        List<String> result = new ArrayList<>();
        backtrackLetterCombinations(digits, result, 0, new StringBuilder());
        return result;
    }

    private void backtrackLetterCombinations(String digits, List<String> result, int index, StringBuilder currentPath) {

        if (index == digits.length()) {
            result.add(currentPath.toString());
            return;
        }
        int digitIndex = digits.charAt(index) - '0';
        String letters = digitToChars[digitIndex];
        for (int i = 0; i < letters.length(); i++) {

            currentPath.append(letters.charAt(i));
            backtrackLetterCombinations(digits, result, index + 1, currentPath);
            currentPath.deleteCharAt(currentPath.length() - 1);
        }
    }

    /**
     * Q2 Word Search
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * <p>
     * The word can be constructed from letters of sequentially adjacent cells,
     * where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
     */
    public boolean exist(char[][] board, String word) {
        if (word == null || board == null) return false;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (dfsWordSearchExists(board, word, row, col, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfsWordSearchExists(char[][] board, String word, int row, int col, int index) {
        // Base case: If we have matched all characters of the word
        if (index == word.length()) {
            return true;
        }
        // Boundary Base Case
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != word.charAt(index)) {
            return false;
        }
        // Mark the current cell as visited by replacing it with a temporary character
        char temp = board[row][col];
        board[row][col] = '#';
        // Explore all four directions: up, down, left, right
        boolean isfound = dfsWordSearchExists(board, word, row + 1, col, index + 1)
                || dfsWordSearchExists(board, word, row - 1, col, index + 1)
                || dfsWordSearchExists(board, word, row, col + 1, index + 1)
                || dfsWordSearchExists(board, word, row, col - 1, index + 1);
        // Restore the cell's original value after backtracking
        board[row][col] = temp;
        return isfound;
    }

    /**
     * Q3: Find All Anagrams in a String
     * Given two strings s and p, return an array of all the start indices of p's anagrams in s. You may return the answer in any order.
     * Input: s = "cbaebabacd", p = "abc"
     * Output: [0,6]
     * Input: s = "abab", p = "ab"
     * Output: [0,1,2]
     */
    public List<Integer> findAnagrams(String s, String p) {
        if (p == null) return Collections.emptyList();
        int[] pCount = new int[26];
        int[] windowCount = new int[26];
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) {
            return result;
        }
        // Initialize pCount with the frequency of characters in p &  Initialize the first window
        for (int i = 0; i < p.length(); i++) {
            pCount[p.charAt(i) - 'a']++;
            windowCount[s.charAt(i) - 'a']++;
        }

        // If the first window is an anagram, add the index 0
        if (Arrays.equals(pCount, windowCount)) {
            result.add(0);
        }

        for (int i = p.length(); i < s.length(); i++) {
            // Include the new character in the window
            windowCount[s.charAt(i) - 'a']++;
            // Remove the character at the left end of the window
            windowCount[s.charAt(i - p.length()) - 'a']--;
            // Check if the window is an anagram of p
            if (Arrays.equals(pCount, windowCount)) {
                result.add(i - p.length() + 1);
            }
        }
        return result;
    }

    /*
     * Q4; Minimum Height Trees
     * A tree is an undirected graph in which any two vertices are connected by exactly one path.
     *  In other words, any connected graph without simple cycles is a tree.
     * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // Edge case: if there is only one node, the root is the only node
        if (n == 1) {
            return Arrays.asList(0);
        }

        // Step 1: Build the graph
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new HashSet<>());
        }

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        // Step 2: Initialize leaves (nodes with only one neighbor)
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (graph.get(i).size() == 1) {
                leaves.add(i);
            }
        }
        // Step 3: Iteratively remove leaves until we have one or two nodes left
        int remainingNodes = n;
        while (remainingNodes > 2) {
            int leafCount = leaves.size();
            remainingNodes -= leafCount;

            // Remove all leaves
            for (int i = 0; i < leafCount; i++) {
                int leaf = leaves.poll();
                for (int neighbor : graph.get(leaf)) {
                    graph.get(neighbor).remove(leaf);
                    // If a neighbor becomes a leaf, add it to the queue
                    if (graph.get(neighbor).size() == 1) {
                        leaves.add(neighbor);
                    }
                }
            }
        }

        return new ArrayList<>(leaves);
    }

    /**
     * Q5:Task Scheduler
     * You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n.
     * Each CPU interval can be idle or allow the completion of one task. Tasks can be completed in any order,
     * but there's a constraint: there has to be a gap of at least n intervals between two tasks with the same label.
     * <p>
     * Return the minimum number of CPU intervals required to complete all tasks.
     */
    public int leastInterval(char[] tasks, int n) {
        // Counts of each task where index 0 represents 'A', 1 represents 'B', and so
        // on.
        int[] taskCounts = new int[26];
        // Maximum frequency among the tasks
        int maxFrequency = 0;

        // Loop over the tasks to count them and find the task with maximum frequency.
        for (char task : tasks) {
            // Convert the task from char type to an index for our count array
            int index = task - 'A';
            // Increment the count for this task
            taskCounts[index]++;
            // Update the maximum frequency
            maxFrequency = Math.max(maxFrequency, taskCounts[index]);
        }

        // Count how many tasks have the maximum frequency
        int maxFrequencyTasks = 0;
        for (int count : taskCounts) {
            if (count == maxFrequency) {
                maxFrequencyTasks++;
            }
        }

        final int maxFreqTaskOccupy = (maxFrequency - 1) * (n + 1);
        // Calculate the minimum length of the task schedule
        // Each block of tasks includes the cooldown period followed by the most
        // frequent task itself
        // Then, add the number of tasks with maximum frequency to cover the last one
        // without tailing idle time
        int minScheduleLength = Math.max(tasks.length, maxFreqTaskOccupy + maxFrequencyTasks);

        return minScheduleLength;
    }

    public static void main(String[] args) {
        Week11 week11 = new Week11();

        System.out.println("Q1 letterCombinations " + week11.letterCombinations("23"));
        System.out.println("Q1 letterCombinations " + week11.letterCombinations("1"));
        System.out.println("Q1 letterCombinations " + week11.letterCombinations(""));

        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        System.out.println("Q2 WordSearchExists " + week11.exist(board, "ABCCED"));
        System.out.println("Q2 WordSearchExists " + week11.exist(board, "ABCB"));
        System.out.println("Q2 WordSearchExists " + week11.exist(board, "SEE"));

        System.out.println("Q3 findAnagrams " + week11.findAnagrams("cbaebabacd", "abc"));
        System.out.println("Q3 findAnagrams " + week11.findAnagrams("abab", "abc"));
        System.out.println("Q3 findAnagrams " + week11.findAnagrams("abab", "ab"));

        int[][] edges = {{1, 0}, {1, 2}, {3, 2}};
        int[][] edges2 = {{1, 0}, {1, 2}, {1, 3,}};
        int[][] edges1 = {{3, 0}, {3, 1}, {3, 2}, {3, 4}, {5, 4}};
        System.out.println("Q4 findMinHeightTrees " + week11.findMinHeightTrees(4, edges));
        System.out.println("Q4 findMinHeightTrees " + week11.findMinHeightTrees(6, edges1));
        System.out.println("Q4 findMinHeightTrees " + week11.findMinHeightTrees(4, edges2));

        System.out.println("Q5 leastTaskSchedularInterval " + week11.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 2));
        System.out.println("Q5 leastTaskSchedularInterval " + week11.leastInterval(new char[]{'A', 'C', 'A', 'B', 'D', 'B'}, 1));
        System.out.println("Q5 leastTaskSchedularInterval " + week11.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 3));
    }
}
