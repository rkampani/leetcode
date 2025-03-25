package com.example.grind150;

import com.sun.org.apache.bcel.internal.classfile.Code;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Week22 {

    /**
     * Non-overlapping Intervals
     * Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals
     * you need to remove to make the rest of the intervals non-overlapping.
     */
    public int eraseOverlapIntervals(int[][] intervals) {

        Arrays.sort(intervals, Comparator.comparingInt((a) -> a[0]));
        int lastInterval = intervals[0][1];
        int overlap = 0;
        for (int i = 1; i < intervals.length; i++) {
            int startInteveal = intervals[i][0];

            if (startInteveal < lastInterval) {
                overlap++;
            } else {
                lastInterval = intervals[i][1];
            }
        }
        return overlap;
    }

    /**
     * Q2: Minimum Window Substring
     * Given two strings s and t of lengths m and n respectively,
     * return the minimum window substring of s such that every character in t (including duplicates) is included in the window.
     * If there is no such substring, return the empty string "
     * <p>
     * Input: s = "ADOBECODEBANC", t = "ABC"
     * Output: "BANC"
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // Step 1: Count characters in t
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }
        // Step 2: Sliding window initialization
        int left = 0, right = 0;
        int required = targetMap.size();  // Number of unique characters in t we need to match
        int formed = 0;  // Number of unique characters in current window that match t
        Map<Character, Integer> windowMap = new HashMap<>();
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;

        // Step 3: Expand the window
        while (right < s.length()) {
            char c = s.charAt(right);
            windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);

            // If the current character's count in windowMap matches the count in targetMap
            if (windowMap.getOrDefault(c, 0).equals(targetMap.getOrDefault(c, 0))) {
                formed++;
            }
            // Step 4: Try to shrink the window
            while (left <= right && formed == required) {
                c = s.charAt(left);

                // Update result if this window is smaller
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }

                // Shrink the window from the left
                windowMap.put(c, windowMap.get(c) - 1);
                if (windowMap.getOrDefault(c, 0) < targetMap.getOrDefault(c, 0)) {
                    formed--;
                }

                left++;
            }

            // Expand the window by moving right
            right++;
        }

        // Step 5: Return the result
        return minLen == Integer.MAX_VALUE ? "" :
                s.substring(minLeft, minLeft + minLen);
    }

    /**
     * Trapping Rain Water
     * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
     * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * Output: 6
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int trappedWater = 0;
        while (left < right) {
            // Update the maximum height seen from left and right
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);

            // If leftMax is smaller, process left side
            if (leftMax < rightMax) {
                trappedWater += leftMax - height[left];
                left++;
            }
            // If rightMax is smaller or equal, process right side
            else {
                trappedWater += rightMax - height[right];
                right--;
            }
        }

        return trappedWater;
    }

    public static void main(String[] args) {
        Week22 week22 = new Week22();

        int[][] intervals = {{1, 2}, {2, 3}, {3, 4}, {1, 3}};
        int[][] intervals1 = {{1, 2}, {1, 2}, {1, 2}};
        int[][] intervals2 = {{1, 2}, {2, 3}};
        System.out.println("Q1 Non-overlapping Intervals " + week22.eraseOverlapIntervals(intervals));
        System.out.println("Q1 Non-overlapping Intervals " + week22.eraseOverlapIntervals(intervals1));
        System.out.println("Q1 Non-overlapping Intervals " + week22.eraseOverlapIntervals(intervals2));

        System.out.println("Q2 minWindow " + week22.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println("Q2 minWindow " + week22.minWindow("a", "aa"));
        System.out.println("Q2 minWindow " + week22.minWindow("a", "a"));

        System.out.println("Q3 TrappedWater " + week22.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println("Q3 TrappedWater " + week22.trap(new int[]{4, 2, 0, 3, 2, 5}));

        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);

        Codec codec = new Codec();
        codec.serialize(root);
        String serialized = codec.serialize(root);
        System.out.println("Q4 Codec-Serialized tree: " + serialized);
        TreeNode deserializedRoot = codec.deserialize(serialized);
        System.out.println("Q4 Codec-Deserialized tree root value: " + deserializedRoot.val);

        MedianFinder mf = new MedianFinder();
        mf.addNum(5);
        System.out.println("Q5 findMedian: " + mf.findMedian());  // 5.0
        mf.addNum(10);
        System.out.println("Q5 findMedian: " + mf.findMedian());  // 7.5
        mf.addNum(3);
        System.out.println("Q5 findMedian: " + mf.findMedian());  // 5.0
        mf.addNum(7);
        System.out.println("Q5 findMedian: " + mf.findMedian());
    }
}
