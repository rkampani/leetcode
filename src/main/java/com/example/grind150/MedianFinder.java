package com.example.grind150;

import java.util.PriorityQueue;

public class MedianFinder {

    private PriorityQueue<Integer> lowerHalf;
    // Min heap for upper half (larger numbers)
    private PriorityQueue<Integer> upperHalf;

    public MedianFinder() {
        // Initialize max heap (reverseOrder for max heap)
        lowerHalf = new PriorityQueue<>((a, b) -> b - a);
        // Initialize min heap (natural order)
        upperHalf = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // First add to lowerHalf
        lowerHalf.offer(num);

        // Balance the heaps: move largest from lower to upper
        upperHalf.offer(lowerHalf.poll());

        // Maintain size property: lowerHalf can have at most one more element
        if (lowerHalf.size() < upperHalf.size()) {
            lowerHalf.offer(upperHalf.poll());
        }
    }

    public double findMedian() {
        // If equal size, average of middle two elements
        if (lowerHalf.size() == upperHalf.size()) {
            return (lowerHalf.peek() + upperHalf.peek()) / 2.0;
        }
        // If unequal, median is top of lowerHalf (which has more elements)
        return lowerHalf.peek();
    }
}
