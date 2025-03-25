package com.example.grind150;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {
    // Using a Map where each key is a string and its value is a TreeMap that
    // associates timestamps with values
    private Map<String, TreeMap<Integer, String>> keyTimeValueMap = new HashMap<>();

    public void set(String key, String value, int timestamp) {
        keyTimeValueMap.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp){
        if (!keyTimeValueMap.containsKey(key)) {
            return "";
        }
        TreeMap<Integer, String> timeValueMap = keyTimeValueMap.get(key);
        Integer closestTimestamp = timeValueMap.floorKey(timestamp);
        return closestTimestamp == null ? "" : timeValueMap.get(closestTimestamp);
    }
}
