package com.example.grind150;

public class WordDictionary {

    Trie root;

    public WordDictionary() {
        this.root = new Trie();
    }

    public void addWord(String word) {
        Trie node = this.root;
        for (char ch : word.toCharArray()) {

            node.children.put(ch, new Trie());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
    }

    public boolean searchFullText(String word) {
        Trie node = this.root;
        for (char ch : word.toCharArray()) {
            if (node.children.get(ch) == null) {
                return false;
            }
            node = node.children.get(ch);
        }
        return true;
    }


    //bool search(word) Returns true if there is any string
    // in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.

    public boolean search(String word) {
        return searchWithInNode(word, root);
    }

    private boolean searchWithInNode(String word, Trie node) {

        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch == '.') {
                for (Trie child : node.children.values()) {
                    if (child != null && searchWithInNode(word.substring(i + 1), child)) {
                        return true;
                    }
                }
                return false;
            } else {
                if (node.children.get(ch) == null) {
                    return false;
                }
                node = node.children.get(ch);
            }
        }
        return node.isEndOfWord;
    }
}
