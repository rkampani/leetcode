package com.example.grind150;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Week26 {


    /**
     * Word Search II
     * Given an m x n board of characters and a list of strings words, return all words on the board.
     * <p>
     * Each word must be constructed from letters of sequentially adjacent cells, where adjacent cells are
     * horizontally or vertically neighboring. The same letter cell may not be used more than once in a word.
     */
    public List<String> findWords(char[][] board, String[] words) {
        TrieNode trieNode = new TrieNode();
        for (int i = 0; i < words.length; i++) {
            trieNode.insert(words[i], i);
        }

        int rowLength = board.length;
        int colLength = board[0].length;
        List<String> foundWords = new ArrayList<>();
        for (int row = 0; row < rowLength; row++) {
            for (int col = 0; col < colLength; col++) {
                dfsFindWord(board, row, col, trieNode.trieNode, foundWords, words);
            }
        }
        return foundWords;
    }

    private void dfsFindWord(char[][] board, int row, int col, Trie node, List<String> foundWords, String[] words) {
        char character = board[row][col];
        Trie nextNode = node.children.get(character);

        // If no matching character in trie, return
        if (nextNode == null) {
            return;
        }

        // If we found a word
        if (nextNode.referenceIndex != -1) {
            foundWords.add(words[nextNode.referenceIndex]);
            nextNode.referenceIndex = -1; // Mark as found to avoid duplicates
        }

        // Mark current cell as visited
        board[row][col] = '#';

        // Four directions: up, right, down, left
        int[] directions = {-1, 0, 1, 0, -1};


        for (int k = 0; k < 4; k++) {
            int newRow = row + directions[k];
            int newCol = col + directions[k + 1];

            if (newRow >= 0 && newRow < board.length &&
                    newCol >= 0 && newCol < board[0].length &&
                    board[newRow][newCol] != '#') {
                dfsFindWord(board, newRow, newCol, nextNode, foundWords, words);
            }
        }
        board[row][col] = character;
    }

    /**
     * Alien Dictionary
     * You are given a list of words that are sorted lexicographically by the alien language's order.
     * <p>
     * Your task is to return the order of characters in the alien language.
     * <p>
     * For example, given the words ["wrt", "wrf", "er", "ett", "rftt"], the order of characters in the alien language can be determined as ["w", "r", "t", "e", "f"].
     */
    public String alienOrder(String[] words) {
        if (words == null) return null;

        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> indegree = new HashMap<>();
        // Initialize the graph and indegree map for all characters
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<>());
                indegree.putIfAbsent(c, 0);
            }
        }

        // Step 2: Add edges to the graph
        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int minLength = Math.min(word1.length(), word2.length());
            // Compare each character of word1 and word2
            for (int j = 0; j < minLength; j++) {
                char c1 = word1.charAt(j);
                char c2 = word2.charAt(j);
                if (c1 != c2) {
                    // Add an edge from c1 to c2
                    if (!graph.get(c1).contains(c2)) {
                        graph.get(c1).add(c2);
                        indegree.put(c2, indegree.get(c2) + 1);
                    }
                    break;
                }
            }
        }

        // Topological Sort using Kahn's Algorithm (BFS)
        Queue<Character> queue = new LinkedList<>();
        // Enqueue characters with indegree 0
        for (char c : indegree.keySet()) {
            if (indegree.get(c) == 0) {
                queue.offer(c);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            result.append(c);

            // Decrease the indegree of neighbors
            for (char neighbor : graph.get(c)) {
                indegree.put(neighbor, indegree.get(neighbor) - 1);
                if (indegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // If the result length is not equal to the number of unique characters, there was a cycle
        if (result.length() != indegree.size()) {
            return "";
        }

        return result.toString();
    }

    /**
     * Bus Routes
     * You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.
     * <p>
     * For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
     * Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
     * Output: 2
     * Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
     */
    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target)
            return 0;

        // Mapping of bus stops to buses
        Map<Integer, List<Integer>> stopToBuses = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int stop : routes[i]) {
                stopToBuses.putIfAbsent(stop, new ArrayList<>());  // {route: [buses]}
                stopToBuses.get(stop).add(i);  // Add the bus index to the stop
            }
        }
        // BFS initialization
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visitedStops = new HashSet<>();
        Set<Integer> visitedBuses = new HashSet<>();
        queue.offer(source);
        visitedStops.add(source);
        int busesTaken = 0;

        while (!queue.isEmpty()) {
            busesTaken++;
            int levelSize = queue.size();

            // Explore all the stops at the current level (one bus change)
            for (int i = 0; i < levelSize; i++) {
                int currentStop = queue.poll();
                // Check all buses passing through the current stop
                for (int busIndex : stopToBuses.get(currentStop)) {
                    // If we have already visited this bus route, skip it
                    if (visitedBuses.contains(busIndex)) continue;
                    // Mark this bus route as visited
                    visitedBuses.add(busIndex);

                    // Explore all the stops on this bus route
                    for (int stop : routes[busIndex]) {
                        if (stop == target) return busesTaken;  // We found the target stop
                        if (!visitedStops.contains(stop)) {
                            visitedStops.add(stop);
                            queue.offer(stop);
                        }
                    }
                }
            }
        }
        // If we exit the loop without finding the target
        return -1;  // No possible way to reach target
    }

    /**
     * Sliding Window Maximum
     * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to
     * the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.
     * <p>
     * Return the max sliding window.
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }
        int n = nums.length;
        int[] result = new int[n - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            // Remove elements that are out of this window
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove elements that are smaller than the current element
            // since they are not useful for future windows
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // Add the current element's index to the deque
            deque.offerLast(i);

            // The first k-1 elements do not have a complete window
            // so we start adding results after the first k-1 elements
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Week26 week26 = new Week26();

        char[][] board = {
                {'o', 'a', 'a', 'n'},
                {'e', 't', 'a', 'e'},
                {'i', 'h', 'k', 'r'},
                {'i', 'f', 'l', 'v'}
        };
        String[] words = {"oath", "pea", "eat", "rain"};
        System.out.println("Q1 Word Search II -findWords " + week26.findWords(board, words));

        String[] alienWords = {"wrt", "wrf", "er", "ett", "rftt"};
        System.out.println("Q2 Alien Dictionary order " + week26.alienOrder(alienWords));

        int[][] routes = {{1, 2, 7}, {3, 6, 7}};
        int source = 1, target = 6;
        System.out.println("Q3 numBusesToDestination " + week26.numBusesToDestination(routes, source, target));

        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        System.out.println("Q4 maxSlidingWindow " + week26.maxSlidingWindow(nums, k));

    }
}
