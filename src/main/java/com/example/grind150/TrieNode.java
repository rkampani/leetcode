package com.example.grind150;

import java.util.HashMap;
import java.util.Map;

public class TrieNode {
    Trie trieNode;

    public TrieNode() {
        trieNode = new Trie();
    }

    public void insert(String word) {
        Trie node = this.trieNode;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new Trie());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    public void insert(String word, int index) {
        Trie node = this.trieNode;

        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new Trie());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
        node.referenceIndex = index;
    }
    public boolean searchPrefix(String prefix) {
        Trie node = searchWord(prefix);
        return node != null && node.isEndOfWord;
    }

    public boolean search(String word) {
        return searchWord(word) != null;
    }

    private Trie searchWord(String prefix) {

        Trie node = this.trieNode;

        for (char c : prefix.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return null;
            }
        }
        return node;
    }


}

class Trie {

    Map<Character, Trie> children;
    boolean isEndOfWord;
    int referenceIndex =-1;
    public Trie() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}
