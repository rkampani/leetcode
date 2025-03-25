package com.example.grind150;

import java.util.HashMap;
import java.util.Map;

public class UnionFind {

    private Map<String, String> parentMap;
    // Parent array for storing the parent index of each disjoint set (union-find)
    private int[] parent;
    public UnionFind() {
        parentMap = new HashMap<>();
    }


    public String find(String email) {
        if (!parentMap.containsKey(email)) {
            parentMap.put(email, email);
        }
        if (!parentMap.get(email).equals(email)) {
            // Path compression - Directly attaching email node to its root parent
            parentMap.put(email, find(parentMap.get(email)));
        }
        return parentMap.get(email);
    }

    // Union two emails into the same group
    public void union(String email1, String email2) {
        String root1 = find(email1);
        String root2 = find(email2);
        if (!root1.equals(root2)) {
            parentMap.put(root1, root2);  // Union operation
        }
    }
}
