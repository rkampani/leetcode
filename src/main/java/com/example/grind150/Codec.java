package com.example.grind150;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Codec {

    private static final String NULL_SYMBOL = "#";
    private static final String SEPARATOR = ",";

    public String serialize(TreeNode root) {
        if (root == null) return null;
        StringBuilder stringBuilder = new StringBuilder();
        serializePreOrder(root, stringBuilder);
        return stringBuilder.toString();
    }

    private void serializePreOrder(TreeNode node, StringBuilder stringBuilder) {

        if (node == null) {
            stringBuilder.append(NULL_SYMBOL).append(SEPARATOR);
            return;
        }
        stringBuilder.append(node.val).append(SEPARATOR);
        serializePreOrder(node.left, stringBuilder);
        serializePreOrder(node.right, stringBuilder);
    }

    public TreeNode deserialize(String data) {
        if(data == null) return null;
        List<String> nodesList = new LinkedList<>(Arrays.asList(data.split(SEPARATOR)));
        return deserializePreOrder(nodesList);

    }

    private TreeNode deserializePreOrder(List<String> nodesList) {
        String value = nodesList.remove(0);
        if (NULL_SYMBOL.equals(value)) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(value));
        node.left = deserializePreOrder(nodesList);
        node.right = deserializePreOrder(nodesList);
        return node;
    }
}
