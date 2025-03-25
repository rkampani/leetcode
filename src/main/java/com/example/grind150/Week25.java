package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class Week25 {

    /**
     * Longest Increasing Path in a Matrix
     * Given an m x n integers matrix, return the length of the longest increasing path in matrix.
     * <p>
     * From each cell, you can either move in four directions: left, right, up, or down.
     * You may not move diagonally or move outside the boundary (i.e., wrap-around is not allowed).
     * Input: matrix = [[9,9,4],[6,6,8],[2,1,1]]
     * Output: 4
     * Explanation: The longest increasing path is [1, 2, 6, 9].
     */
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int[][] cache = new int[m][n];
        // Directions: up, down, left, right
        int[] dirs = {-1, 0, 1, 0, -1};  // Corresponding to (row, col)

        // DFS + memoization to calculate the longest path starting from (i, j)
        int maxPath = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxPath = Math.max(maxPath, dfsLongestIncreasingPath(matrix, cache, i, j, dirs));
            }
        }

        return maxPath;
    }

    private int dfsLongestIncreasingPath(int[][] matrix, int[][] cache, int row, int col, int[] dirs) {
        // If already computed, return the memoized result
        if (cache[row][col] != 0) {
            return cache[row][col];
        }

        int m = matrix.length;
        int n = matrix[0].length;
        int maxLength = 1;  // Each cell has at least itself as a path

        // Explore the four possible directions
        for (int d = 0; d < 4; d++) {
            int newRow = row + dirs[d];
            int newCol = col + dirs[d + 1];

            // Check bounds and if the next cell has a larger value
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && matrix[newRow][newCol] > matrix[row][col]) {
                maxLength = Math.max(maxLength, 1 + dfsLongestIncreasingPath(matrix, cache, newRow, newCol, dirs));
            }
        }
        // Memoize the result for the current cell (row, col)
        cache[row][col] = maxLength;

        return maxLength;
    }

    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                // Pop the top of the stack for ')'
                stack.pop();

                // Check if the stack is empty after popping
                if (stack.isEmpty()) {
                    // If the stack is empty, push the current index onto the stack
                    stack.push(i);
                } else {
                    // Calculate the length of the current valid parentheses substring
                    maxLength = Math.max(maxLength, i - stack.peek());
                }
            }
        }
        return maxLength;
    }

    /**
     * Employee Free Time
     * You are given a list of intervals representing the working hours of employees.
     * Each employee's working hours are represented by a pair of integers, where each pair corresponds to the start
     * and end time of that employee's work shift.
     * <p>
     * The goal is to find the free time for all employees, i.e., periods of time when no employee is working.
     * You need to return the intervals during which no one is working.
     */
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        List<Interval> result = new ArrayList<>();

        // Step 1: Merge all intervals into one list
        List<Interval> allIntervals = new ArrayList<>();
        for (List<Interval> employeeSchedule : schedule) {
            for (Interval interval : employeeSchedule) {
                allIntervals.add(interval);
            }
        }
        // Step 2: Sort intervals by start time
        allIntervals.sort(Comparator.comparingInt(a -> a.start));

        // Step 3: Find free time intervals
        int prevEnd = allIntervals.get(0).end;

        for (int i = 1; i < allIntervals.size(); i++) {
            Interval current = allIntervals.get(i);

            // If there's a gap between previous end and current start, we found free time
            if (current.start > prevEnd) {
                result.add(new Interval(prevEnd, current.start));
            }

            // Update the previous end time to the max of previous end and current end
            prevEnd = Math.max(prevEnd, current.end);
        }

        return result;
    }

    public static void main(String[] args) {
        Week25 week25 = new Week25();
        // Test Case 1
        int[][] matrix1 = {
                {9, 9, 4},
                {6, 6, 8},
                {2, 1, 1}
        };
        System.out.println("Q1 longestIncreasingPath " + week25.longestIncreasingPath(matrix1)); // Output: 4

        // Test Case 2
        int[][] matrix2 = {
                {3, 4, 5},
                {3, 2, 6},
                {2, 2, 1}
        };
        System.out.println("Q1 longestIncreasingPath " + week25.longestIncreasingPath(matrix2)); // Output: 4

        // Test Case 3: Empty matrix
        int[][] matrix3 = {};
        System.out.println("Q1 longestIncreasingPath " + week25.longestIncreasingPath(matrix3)); // Output: 0

        System.out.println("Q2 longestValidParentheses " + week25.longestValidParentheses("(()())"));  // Output: 6
        System.out.println("Q2 longestValidParentheses " + week25.longestValidParentheses(")()())"));  // Output: 4
        System.out.println("Q2 longestValidParentheses " + week25.longestValidParentheses("()(()"));    // Output: 2
        System.out.println("Q2 longestValidParentheses " + week25.longestValidParentheses("()"));      // Output: 2
        System.out.println("Q2 longestValidParentheses " + week25.longestValidParentheses(""));        // Output: 0

        FileSystem fs = new FileSystem();

        // Create directories and files
        fs.createDirectory("/root/dir1");
        fs.createFile("/root/dir1/file1.txt");
        fs.createFile("/root/dir1/file2.txt");

        // Write to file
        fs.writeToFile("/root/dir1/file1.txt", "Hello, File1!");
        fs.writeToFile("/root/dir1/file2.txt", "Hello, File2!");

        // Read from file
        System.out.println("Q3 readFromFile " + fs.readFromFile("/root/dir1/file1.txt"));  // Output: Hello, File1!
        System.out.println("Q3 readFromFile " + fs.readFromFile("/root/dir1/file2.txt"));  // Output: Hello, File2!

        // List directory contents
        System.out.println("Q3 readFromFile Contents of dir1:");
        fs.listDirectoryContents("/root/dir1");

        // Delete a file
        fs.deleteFile("/root/dir1/file2.txt");

        // List directory contents after deletion
        System.out.println("Q3 readFromFile Contents of dir1 after deletion:");
        fs.listDirectoryContents("/root/dir1");

        // Delete a directory
        fs.deleteDirectory("/root/dir1");


        List<List<Interval>> schedule = new ArrayList<>();
        List<Interval> employee1 = Arrays.asList(new Interval(1, 3), new Interval(5, 6));
        List<Interval> employee2 = Arrays.asList(new Interval(2, 4));
        List<Interval> employee3 = Arrays.asList(new Interval(2, 3), new Interval(6, 8));

        schedule.add(employee1);
        schedule.add(employee2);
        schedule.add(employee3);

        List<Interval> result = week25.employeeFreeTime(schedule);

        // Output the free times
        for (Interval interval : result) {
            System.out.println("Q4 employeeFreeTime [" + interval.start + ", " + interval.end + "]");
        }
    }
}

class Interval {
    int start, end;

    Interval(int start, int end) {
        this.start = start;
        this.end = end;
    }
}
