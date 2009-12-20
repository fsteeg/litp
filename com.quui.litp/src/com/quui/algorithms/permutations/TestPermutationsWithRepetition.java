package com.quui.algorithms.permutations;

import java.util.List;

import org.junit.Test;

public class TestPermutationsWithRepetition {
    /**
     * Sample usage: generate all permutations with repetitions of length 3 for
     * an alphabet consisting of a,b and c:
     */
    @Test
    public void main() {
        PermutationsWithRepetition gen = new PermutationsWithRepetition("abc", 3);
        List<String> v = gen.getVariations();
        for (String s : v) {
            System.out.println(s);
        }
    }
}