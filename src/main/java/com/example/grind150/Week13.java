package com.example.grind150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Week13 {

    /**
     * Q1  Valid Sudoku
     * Each row must contain the digits 1-9 without repetition.
     * Each column must contain the digits 1-9 without repetition.
     * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
     */

    public boolean isValidSudoku(char[][] board) {

        if (board == null) return false;
        Set<String> seen = new HashSet<>();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int num = board[row][col];
                if (board[row][col] == '.') {
                    String rowKey = "row" + row + num;
                    String colKey = "col" + row + num;
                    String boxKey = "box" + (row / 3) + (col / 3) + num;
                    if (seen.contains(rowKey) || seen.contains(colKey) || seen.contains(boxKey)) {
                        return false;
                    }
                    seen.add(rowKey);
                    seen.add(colKey);
                    seen.add(boxKey);
                }
            }
        }
        return true;
    }

    public boolean isValidSudokuu(char[][] board) {
        // Use three arrays of sets to track row, column, and subgrid.
        Set<String> rows = new HashSet<>();
        Set<String> cols = new HashSet<>();
        Set<String> subgrids = new HashSet<>();

        // Traverse through each cell in the board.
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char num = board[i][j];
                if (num == '.') continue;  // Skip empty cells.

                // Check row constraint: row[i] contains num.
                if (!rows.add("row" + i + num)) return false;

                // Check column constraint: column[j] contains num.
                if (!cols.add("col" + j + num)) return false;

                // Check subgrid constraint: subgrid (i / 3, j / 3) contains num.
                if (!subgrids.add("subgrid" + (i / 3) + (j / 3) + num)) return false;
            }
        }
        return true;  // Return true if all constraints are satisfied.
    }

    /**
     * Group Anagrams
     * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
     * <p>
     * Input: strs = ["eat","tea","tan","ate","nat","bat"]
     * <p>
     * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
     *
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {

        if (strs == null) return Collections.emptyList();

        Map<String, List<String>> keyToGroup = new HashMap<>();

        for (String st : strs) {

            char[] array = st.toCharArray();
            Arrays.sort(array);
            String key = String.valueOf(array);
            List<String> values = keyToGroup.getOrDefault(key, new ArrayList<>());
            values.add(st);
            keyToGroup.put(key, values);
        }
        return new ArrayList<>(keyToGroup.values());
    }

    /**
     * Q3: Maximum Product Subarray
     * Input: nums = [2,3,-2,4]
     * Output: 6
     * Explanation: [2,3] has the largest product 6
     */
    public int maxProduct(int[] nums) {
        if (nums == null) return -1;

        int maxProduct = nums[0];  // To handle the edge case where the array has only one element.
        int minProduct = nums[0];  // Track the minimum product (to handle negative numbers).
        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];

            // If num is negative, swap the max and min products
            // because multiplying by a negative number flips the sign
            if (num < 0) {
                int temp = maxProduct;
                maxProduct = minProduct;
                minProduct = temp;
            }

            // Update the max and min product considering the current number
            maxProduct = Math.max(num, maxProduct * num);
            minProduct = Math.min(num, minProduct * num);

            result = Math.max(maxProduct, result);
        }
        return result;
    }

    public List<List<Integer>> pacificAtlanticUsingDFS(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return Collections.emptyList();
        }
        int rowSize = heights.length;
        int colSize = heights[0].length;
        int width = heights[0].length;
        boolean[][] visitedPacific = new boolean[rowSize][colSize];
        boolean[][] visitedAtlantic = new boolean[rowSize][colSize];

        // Perform DFS from the edges for both Pacific and Atlantic oceans
        for (int row = 0; row < rowSize; row++) {
            // For Pacific, start from the left and top edges
            dfsPacificAtlantic(row, 0, visitedPacific, heights);
            // For Atlantic, start from the right and bottom edges
            dfsPacificAtlantic(row, colSize - 1, visitedAtlantic, heights);
        }

        for (int col = 0; col < colSize; col++) {
            // For Pacific, start from the top and left edges
            dfsPacificAtlantic(0, col, visitedPacific, heights);
            // For Atlantic, start from the bottom and right edges
            dfsPacificAtlantic(rowSize - 1, col, visitedAtlantic, heights);
        }
        // The result will be the intersection of pacificVisited and atlanticVisited
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                if (visitedAtlantic[i][j] && visitedPacific[i][j]) {
                    results.add(Arrays.asList(i, j));
                }
            }
        }

        return results;
    }

    private void dfsPacificAtlantic(int i, int j,boolean[][] visited, int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j]) {
            return;
        }

        visited[i][j] = true;

        // Directions: up, down, left, right
        int[] directions = {-1, 0, 1, 0, -1};

        for (int d = 0; d < 4; d++) {
            int ni = i + directions[d];
            int nj = j + directions[d + 1];

            if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && matrix[ni][nj] >= matrix[i][j] && !visited[ni][nj]) {
                dfsPacificAtlantic(ni, nj, visited, matrix);
            }
        }
    }

    public List<List<Integer>> pacificAtlanticUsingBFS(int[][] heights) {
        if (heights == null || heights.length == 0 || heights[0].length == 0) {
            return Collections.emptyList();
        }
        int height = heights.length;
        int width = heights[0].length;
        Deque<int[]> pacificQueue = new LinkedList<>();
        Deque<int[]> atlanticQueue = new LinkedList<>();

        Set<Integer> visitedPacific = new HashSet<>();
        Set<Integer> visitedAtlantic = new HashSet<>();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int cellIndex = row * width + col;
                if (row == 0 || col == 0) { //  Pacific Ocean's edges (Top and left)
                    pacificQueue.add(new int[]{row, col});
                    visitedPacific.add(cellIndex);
                } else if (row == height - 1 || col == width - 1) {
                    // Atlantic ocean (right and bottom edges)
                    atlanticQueue.add(new int[]{row, col});
                    visitedAtlantic.add(cellIndex);
                }
            }
        }

        bfsPacificAtlantic(pacificQueue, visitedPacific, heights, width);
        bfsPacificAtlantic(atlanticQueue, visitedAtlantic, heights, width);

        List<List<Integer>> results = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int cellIndex = row * width + col;
                // if a cell is reachable from both Pacific and Atlantic, add it to results
                if (visitedAtlantic.contains(cellIndex) && visitedPacific.contains(cellIndex)) {
                    results.add(Arrays.asList(row, col));
                }
            }
        }
        return results;
    }

    private void bfsPacificAtlantic(Deque<int[]> queue, Set<Integer> visited, int[][] heights, int width) {
        int[] directions = {-1, 0, 1, 0, 1};

        while (!queue.isEmpty()) {

            for (int i = queue.size(); i > 0; i--) {

                int[] currentCell = queue.poll();
                int currentRow = currentCell[0];
                int currentCol = currentCell[1];

                for (int k = 0; k < 4; k++) {

                    int newRow = currentRow + directions[k];
                    int newCol = currentCol + directions[k + 1];
                    int cellIndex = newRow * width + newCol;
                    if (newRow >= 0 && newRow < heights.length && newCol >= 0 && newCol < heights[0].length
                            && !visited.contains(cellIndex) && heights[newRow][newCol] >= heights[currentRow][currentCol]) {

                        queue.add(new int[]{newRow, newCol});
                        visited.add(cellIndex);
                    }
                }
            }
        }
    }

    /**
     * Q6  Remove Nth Node From End of List
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode first = dummy, second = dummy;
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        // Move both pointers until first reaches the end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        // Remove the N-th node from the end
        second.next = second.next.next;

        // Return the modified list (dummy.next points to the head)
        return dummy.next;
    }
    public static void main(String[] args) {
        Week13 week13 = new Week13();

        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '8', '.', '.', '.', '6', '3'},
                {'4', '.', '2', '1', '.', '8', '.', '2', '7'},
                {'7', '.', '.', '.', '4', '5', '9', '3', '.'},
                {'.', '6', '.', '4', '8', '.', '1', '9', '5'},
                {'.', '.', '9', '7', '3', '.', '.', '.', '8'},
                {'.', '4', '1', '9', '.', '2', '8', '.', '6'}
        };

        System.out.println("Q1 isValidSudoku " + week13.isValidSudoku(board));
        System.out.println("Q1 isValidSudokiu " + week13.isValidSudokuu(board));

        System.out.println("Q2 groupAnagrams " + week13.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        System.out.println("Q2 groupAnagrams " + week13.groupAnagrams(new String[]{"a"}));
        System.out.println("Q2 groupAnagrams " + week13.groupAnagrams(new String[]{}));

        System.out.println("Q3 maxProduct " + week13.maxProduct(new int[]{2, 3, -2, 4}));
        System.out.println("Q3 maxProduct " + week13.maxProduct(new int[]{-2, 0, -1}));


        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");


        System.out.println("Q4 WordDictionary.searchFullText " + wordDictionary.searchFullText("pad"));
        System.out.println("Q4 WordDictionary.searchFullText " + wordDictionary.searchFullText("bad"));
        System.out.println("Q4 WordDictionary.searchFullText " + wordDictionary.searchFullText(".ad"));
        System.out.println("Q4 WordDictionary.searchFullText " + wordDictionary.searchFullText("b.."));

        System.out.println("Q4 WordDictionary.search " + wordDictionary.search("pad"));
        System.out.println("Q4 WordDictionary.search " + wordDictionary.search("bad"));
        System.out.println("Q4 WordDictionary.search " + wordDictionary.search(".ad"));
        System.out.println("Q4 WordDictionary.search " + wordDictionary.search("b.."));


        int[][] matrix = {
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 19, 20},
                {21, 22, 23, 24, 25}
        };
        System.out.println("Q5 pacificAtlantic DFS " + (week13.pacificAtlanticUsingDFS(matrix)));
        System.out.println("Q5 pacificAtlantic BFS " + (week13.pacificAtlanticUsingBFS(matrix)));
        // Create a sample linked list: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        System.out.println("Q6 removeNthFromEnd ");
        ListNode.printList(week13.removeNthFromEnd(head, 2));
    }
}
