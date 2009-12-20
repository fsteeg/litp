/**
 * Created on 16.11.2004
 */
package com.quui.algorithms.permutations;

import java.util.List;
import java.util.Vector;

/**
 * Generate all
 * [http://en.wikipedia.org/wiki/Combinatorics#Permutation_with_repetition
 * permutations with repetition] of a given length for a given alphabet,
 * implemented using a [http://en.wikipedia.org/wiki/Dynamic_programming dynamic
 * programming] strategy. The number of permutations with repetition is
 * <math>n^l</math> where ''n'' is the length of the alphabet and ''l'' the
 * length of the permutations to generate. Note that as part of the dynamic
 * programming approach, a ''l'' * <math>n^l</math> matrix is created.
 */
public class PermutationsWithRepetition {

    private String a;

    private int n;
    
    public PermutationsWithRepetition(String a, int n) {
        this.a = a;
        this.n = n;
    }

    public List<String> getVariations() {
        int l = a.length();
        int permutations = (int) Math.pow(l, n);
        char[][] table = new char[permutations][n];
        /**
         * We create a matrix for all possible permutations, with each row being
         * one permutation. For every column in the table, we compute (length
         * pow column index). For instance, for a length of 3 we get 1, 3, 9,
         * 27... We then, for every position in the table, fill in that computed
         * number of elements in every column with the char in the alphabet at
         * the position of the current column.
         */
        for (int x = 0; x < n; x++) {
            int t2 = (int) Math.pow(l, x);
            for (int p1 = 0; p1 < permutations;) {
                for (int al = 0; al < l; al++) {
                    for (int p2 = 0; p2 < t2; p2++) {
                        table[p1][x] = a.charAt(al);
                        p1++;
                    }
                }
            }
        }
        /**
         * Read the result from the matrix: Add a string to the list of
         * permutations for every row in the table:
         */
        List<String> result = new Vector<String>();
        for (int i = 0; i < table.length; i++) {
            result.add(new String(table[i]));
        }
        return result;
    }

}

