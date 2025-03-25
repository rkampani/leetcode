package com.example.grind150;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class FrequencyStack {
    // Map to store frequency of each element
    private Map<Integer, Integer> freqMap;
    // Map to store stacks of elements grouped by frequency
    private Map<Integer, Stack<Integer>> freqStackMap;
    // Track maximum frequency
    private int maxFreq;

    public FrequencyStack() {
        freqMap = new HashMap<>();
        freqStackMap = new HashMap<>();
        maxFreq = 0;
    }
    public void push(int val) {
        // Update frequency count
        int freq = freqMap.getOrDefault(val, 0) + 1;
        freqMap.put(val, freq);

        // Update max frequency
        maxFreq = Math.max(maxFreq, freq);

        // Add element to corresponding frequency stack
        freqStackMap.putIfAbsent(freq, new Stack<>());
        freqStackMap.get(freq).push(val);

    }


    public int pop() {
        // Get the stack with maximum frequency
        Stack<Integer> maxFreqStack = freqStackMap.get(maxFreq);
        int val = maxFreqStack.pop();

        // Update frequency map
        int newFreq = freqMap.get(val) - 1;
        freqMap.put(val, newFreq);

        // If stack is empty, remove it and decrease maxFreq
        if (maxFreqStack.isEmpty()) {
            freqStackMap.remove(maxFreq);
            maxFreq--;
        }

        return val;
    }
}
