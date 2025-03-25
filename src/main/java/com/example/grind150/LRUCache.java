package com.example.grind150;

import java.util.HashMap;

public class LRUCache {

    private final int capacity;
    private HashMap<Integer, DoubleLinkedListNode> cache;
    private DoubleLinkedListNode head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();

        // Create dummy head and tail nodes to simplify operations
        head = new DoubleLinkedListNode(0, 0);
        tail = new DoubleLinkedListNode(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            DoubleLinkedListNode node = cache.get(key);
            // Move the accessed node to the front
            addNodeToFront(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // If the key exists, update the value and move to front
            DoubleLinkedListNode node = cache.get(key);
            node.value = value;
            addNodeToFront(node);
        } else {
            // If the key doesn't exist, add a new node
            if (cache.size() == capacity) {
                // Remove the least recently used (LRU) item
                DoubleLinkedListNode lru = tail.prev;
                cache.remove(lru.key);
                removeNode(lru);
            }
            DoubleLinkedListNode newNode = new DoubleLinkedListNode(key, value);
            cache.put(key, newNode);
            addNodeToFront(newNode);
        }
    }

    private void removeNode(DoubleLinkedListNode node) {

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void addNodeToFront(DoubleLinkedListNode node) {

        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }
}
