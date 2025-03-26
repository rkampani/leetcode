package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Week28 {

    /**
     * N-Queens
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
     *
     * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
     *
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        char[][] board = new char[n][n];
        // Initialize board with empty cells
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                board[row][col] = '.';
            }
        }
        backtrackSolveNQueensHelper(0, board, result, n);
        return result;
    }

    private void backtrackSolveNQueensHelper(int row, char[][] board, List<List<String>> result, int n) {
        // If all queens are placed, add the solution
        if (row == n) {
            result.add(generateBoard(board));
            return;
        }

        // Try placing a queen in each column of the current row
        for (int col = 0; col < n; col++) {
            // Check if placing a queen at (row, col) is valid
            if (isValidQueenPosition(row, col, board, n)) {
                // Place queen
                board[row][col] = 'Q';
                // Recur to place queens in the next row
                backtrackSolveNQueensHelper(row + 1, board, result, n);
                // Backtrack: remove queen
                board[row][col] = '.';
            }
        }
    }
    // Check if it's valid to place a queen at (row, col)
    private boolean isValidQueenPosition(int row, int col, char[][] board, int n) {
        // Check vertical (column)
        for (int i = 0; i < row; i++) {
            if (board[i][col] == 'Q') return false;
        }

        // Check upper-left diagonal
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') return false;
        }

        // Check upper-right diagonal
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i--, j++) {
            if (board[i][j] == 'Q') return false;
        }

        return true;
    }


    // Generate a board configuration based on the positions of the queens
    private List<String> generateBoard(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }
    /**
     * Smallest Range Covering Elements from K Lists
     * The problem to find the smallest range that includes at least one element from each of the K sorted lists. The range should be as small as possible.
     * You have k lists of sorted integers in non-decreasing order. Find the smallest range that includes at least one number from each of the k lists.
     *
     * We define the range [a, b] is smaller than range [c, d] if b - a < d - c or a < c if b - a == d - c.
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        // Initialize a min-heap
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        // Initialize the maximum element and the result
        int max = Integer.MIN_VALUE;
        int rangeStart = 0;
        int rangeEnd = Integer.MAX_VALUE;

        // Add the first element from each list to the min-heap
        for (int i = 0; i < nums.size(); i++) {
            int value = nums.get(i).get(0);
            minHeap.offer(new int[] { value, i, 0 });  // (value, list_index, element_index)
            max = Math.max(max, value);
        }

        // Iterate over the heap until we can't move forward
        while (true) {
            // Get the smallest element from the heap
            int[] minElement = minHeap.poll();
            int value = minElement[0];
            int listIndex = minElement[1];
            int elementIndex = minElement[2];

            // Calculate the current range
            if (max - value < rangeEnd - rangeStart) {
                rangeStart = value;
                rangeEnd = max;
            }

            // If we have reached the end of any list, break the loop
            if (elementIndex + 1 == nums.get(listIndex).size()) {
                break;
            }

            // Move the pointer for the list from which the element was taken
            int nextValue = nums.get(listIndex).get(elementIndex + 1);
            minHeap.offer(new int[] { nextValue, listIndex, elementIndex + 1 });
            max = Math.max(max, nextValue);  // Update the maximum value
        }

        return new int[] { rangeStart, rangeEnd };
    }
    public static void main(String[] args) {
        Week28 week28 = new Week28();

        // Test cases
        int n1 = 4;
        List<List<String>> solutions1 = week28.solveNQueens(n1);
        System.out.println("Q1 solveNQueens for N = " + n1 + ": " + solutions1);

        int n2 = 1;
        List<List<String>> solutions2 = week28.solveNQueens(n2);
        System.out.println("Q1 solveNQueens for N = " + n2 + ": " + solutions2);

        // Test case 1
        List<List<Integer>> nums1 = Arrays.asList(
                Arrays.asList(4, 10, 15, 24, 26),
                Arrays.asList(0, 9, 12, 20),
                Arrays.asList(5, 18, 22, 30)
        );
        int[] result1 = week28.smallestRange(nums1);
        System.out.println("Q2 Smallest Range: [" + result1[0] + ", " + result1[1] + "]");

        // Test case 2
        List<List<Integer>> nums2 = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );
        int[] result2 = week28.smallestRange(nums2);
        System.out.println("Q2 Smallest Range: [" + result2[0] + ", " + result2[1] + "]");
    }
}
