package com.example.grind150;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Week20 {

    /**
     * Q1:
     */
    public int minMeetingRooms(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        int minMeetingRoom = 0;
        // Step 1: Sort the start times and end times
        int[] startTimes = new int[intervals.length];
        int[] endTimes = new int[intervals.length];

        for (int i = 0; i < intervals.length; i++) {
            startTimes[i] = intervals[i][0];
            endTimes[i] = intervals[i][1];
        }

        Arrays.sort(startTimes);
        Arrays.sort(endTimes);

        // Step 2: Use two pointers to track the meeting start and end times
        int startPointer = 0;
        int endPointer = 0;
        int roomsNeeded = 0;

        // Step 3: Iterate through the meetings
        while (startPointer < intervals.length) {
            // If the current meeting starts after the earliest meeting ends, we can reuse the room
            if (startTimes[startPointer] >= endTimes[endPointer]) {
                endPointer++;  // Reuse the room
            } else {
                roomsNeeded++;  // We need a new room
            }
            startPointer++;  // Move to the next meeting
        }

        return roomsNeeded;

    }

    /**
     * Q2Reverse Integer
     */
    public int reverse(int x) {
        long ans = 0;

        while (x != 0) {
            ans = ans * 10 + x % 10;
            x /= 10;
        }

        return (ans < Integer.MIN_VALUE || ans > Integer.MAX_VALUE) ? 0 : (int) ans;
    }

    /**
     * Set Matrix Zeroes
     * Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
     * Output: [[1,0,1],[0,0,0],[1,0,1]]
     */
    public void setZeroes(int[][] matrix) {
        if (matrix == null) return;
        ;
        Set<Integer> rowSet = new HashSet<>();
        Set<Integer> colSet = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowSet.add(i);
                    colSet.add(j);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (rowSet.contains(i) || colSet.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
        System.out.println("Q3 setZeroes " + Arrays.deepToString(matrix));
    }

    /**
     * Q4: Reorder List
     * Input: head = [1,2,3,4]
     * Output: [1,4,2,3]
     * Input: head = [1,2,3,4,5]
     * Output: [1,5,2,4,3]
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return; // No need to reorder if the list is empty or contains a single element
        }

        // Step 1: Find the middle of the list using slow and fast pointers
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // Step 2: Reverse the second half of the list
        ListNode secondHalf = slow.next;
        slow.next = null;  // Split the list into two halves
        secondHalf = reverseList(secondHalf);

        // Step 3: Merge the two halves
        ListNode firstHalf = head;
        while (secondHalf != null) {
            ListNode temp1 = firstHalf.next;
            ListNode temp2 = secondHalf.next;

            firstHalf.next = secondHalf;
            secondHalf.next = temp1;

            firstHalf = temp1;
            secondHalf = temp2;
        }

        System.out.println("Q4 reorderList");
        ListNode.printList(head);
    }

    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode nextNode = current.next;
            current.next = prev;
            prev = current;
            current = nextNode;
        }
        return prev;
    }

    /**
     * Q5 Cheapest Flights Within K Stops
     * There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei]
     * indicates that there is a flight from city fromi to city toi with cost pricei.
     * <p>
     * You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
     */
    public int findCheapestPriceByBFS(int n, int[][] flights, int src, int dst, int K) {

        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] flight : flights) {
            graph.putIfAbsent(flight[0], new ArrayList<>());
            graph.get(flight[0]).add(new int[]{flight[1], flight[2]});  // [destination, price]
        }
        // Step 2: BFS with queue, (city, stops, cost)
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{src, 0, 0});  // Starting at src with 0 stops and 0 cost

        // Create a 2D array to store the minimum cost to reach a city with a certain number of stops
        int[][] dist = new int[n][K + 2];  // We need K+1 stops, but also account for K+1 and K+2
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);  // Initialize with a large value
        }

        dist[src][0] = 0;  // Starting point, 0 cost at 0 stops
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int city = current[0];
            int stops = current[1];
            int cost = current[2];

            // If we exceed K stops, we stop exploring this path
            if (stops > K) continue;

            // Explore all the neighbors (flights) from the current city
            for (int[] neighbor : graph.getOrDefault(city, new ArrayList<>())) {
                int nextCity = neighbor[0];
                int price = neighbor[1];

                // If visiting the neighbor leads to a cheaper cost with fewer stops, add it to the queue
                if (cost + price < dist[nextCity][stops + 1]) {
                    dist[nextCity][stops + 1] = cost + price;
                    queue.offer(new int[]{nextCity, stops + 1, cost + price});
                }
            }
        }
        // Step 3: Find the minimum cost to reach the destination within K stops
        int minCost = Integer.MAX_VALUE;
        for (int i = 0; i <= K + 1; i++) {
            minCost = Math.min(minCost, dist[dst][i]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] prices = new int[n]; // minimum sum of edges in path
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0; // starting point
        // Only k stops allowed or nodes between the source and destination
        for (int i = 0; i < k + 1; i++) {
            // array for tracking the minimum sum between the edge weights
            int[] temp = Arrays.copyOf(prices, n);
            // finding the paths in the given edges
            for (int[] fl : flights) {
                int source = fl[0], destination = fl[1], price = fl[2];
                int currentCost = prices[source] + price;
                if (prices[source] != Integer.MAX_VALUE &&
                        currentCost < temp[destination])
                    temp[destination] = currentCost;
            }
            /*
             * Copying data from temp where the prices were kept temporarily
             * for calculating min prices
             */
            prices = temp;
        }
        // returning if there is path from source to destination
        return prices[dst] != Integer.MAX_VALUE ? prices[dst] : -1;
    }

    public static void main(String[] args) {
        Week20 week20 = new Week20();

        // Test cases
        int[][] intervals1 = {
                {0, 30},
                {5, 10},
                {15, 20}
        };
        System.out.println("Q1 minMeetingRooms " + week20.minMeetingRooms(intervals1));  // Output: 2

        int[][] intervals2 = {
                {7, 10},
                {2, 4}
        };
        System.out.println("Q1 minMeetingRooms " + week20.minMeetingRooms(intervals2));  // Output: 1

        System.out.println("Q2 ReverseInteger " + week20.reverse(20));
        System.out.println("Q2 ReverseInteger " + week20.reverse(1203));
        System.out.println("Q2 ReverseInteger " + week20.reverse(9015));
        int[][] matrix = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        week20.setZeroes(matrix);

        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        week20.reorderList(head);

        int n = 3;
        int[][] flights = {
                {0, 1, 100},  // Flight from city 0 to city 1 with price 100
                {1, 2, 100},  // Flight from city 1 to city 2 with price 100
                {0, 2, 500}   // Flight from city 0 to city 2 with price 500
        };
        int src = 0;
        int dst = 2;
        int K = 1;

        System.out.println("Q5 findCheapestPrice " + week20.findCheapestPrice(n, flights, src, dst, K));
        System.out.println("Q5 findCheapestPrice " + week20.findCheapestPriceByBFS(n, flights, src, dst, K));
    }
}
