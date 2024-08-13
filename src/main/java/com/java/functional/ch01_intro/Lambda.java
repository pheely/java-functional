/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.java.functional.ch01_intro;

import java.util.Arrays;
import java.util.List;

public class Lambda {
    public void totalOfDiscountedPrices() {
        final List<Integer> prices = Arrays.asList(10, 30, 17, 20, 18, 45, 12);
        double total =
            prices.stream()
                .filter(p -> p > 20)
                .mapToDouble(p -> p * 0.9)
                .sum();
        System.out.printf("Total of discounted prices: %.2f%n", total);
    }

    public static void main(String[] args) {
        new Lambda().totalOfDiscountedPrices();
    }
}
