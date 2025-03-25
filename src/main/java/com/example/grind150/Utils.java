package com.example.grind150;

import java.util.Arrays;

public class Utils {

    public static void log(String methodName, int[] result) {
        System.out.print(methodName);
        Arrays.stream(result).forEach(e -> System.out.print(" " + e));
        System.out.println();
    }

    public static void long2DArray(String methodName, int[][] image) {
        System.out.print(methodName);
        for (int[] row : image) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
