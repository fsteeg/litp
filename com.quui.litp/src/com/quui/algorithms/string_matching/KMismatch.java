package com.quui.algorithms.string_matching;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * An implementation of string matching with a defined number of at most ''k''
 * mismatches based on ''longest common extension'' (''lce'') computation as
 * described in Gusfield 1999:200. This is a form of inexact string matching,
 * which allows no insertions or deletions but only matches and mismatches. When
 * used with a ''lce'' computation with
 * [http://en.wikipedia.org/wiki/Suffix_tree suffix trees], it has a runtime
 * complexity of ''O(km)'', where m is the length of the text. This
 * implementation uses a [[Longest common extension (Java)|simple computation]]
 * of the ''lce''.
 */
public class KMismatch {
    /**
     * The approach is very similar to [[Matching with wildcards (Java)|matching
     * with wildcards]]: For every position in the text, up to ''k'' lce queries
     * are executed, and if the lce reaches the end of the pattern, then more
     * than k mismatches are needed to match. As at most ''k'' lce computations
     * are required the runtime complexity is ''O(km)'', where k is the length
     * of the text (Gusfield 1999:200). The method takes three parameters, the
     * text, the pattern and the number of allowed mismatches. It retuns a
     * collection of strings, the matching substrings. The algorithm consists of
     * four steps:
     */
    public static Collection<String> getMatches(String t, String p, int k) {
        Collection<String> result = new ArrayList<String>();
        for (int i = 0; i < t.length(); i++) {
            // Step 1: Set j to 1 and h to i and count to 0.
            int j = 0;
            int h = i;
            int count = 0;
            int n = p.length();
            while (true) {
                // Step 2: Compute the length L of the longest common extension
                // starting at positions j of P and h of T:
                int L = SimpleLongestCommonExtension.longestCommonExtension(p, j,
                        t, h);
                // Step 3: If j + L = n + 1, then a k-mismatch of P occurs in T
                // starting at i (in fact, only count mismatches occur); stop.
                if (j + 1 + L == n + 1) {
                    result.add(t.substring(i, i + n));
                    break;
                }
                // Step 4: If count >= k, then increment count by one, set j to
                // j + L + 1, set h to h + L + 1 and go to step 2. If count = k
                // + 1, then a k-mismatch of P does not occur stating at i;
                // stop.
                else if (count < k) {
                    count++;
                    j = j + L + 1;
                    h = h + L + 1;
                } else if (count == k) {
                    break;
                }
            }

        }
        return result;
    }

    /** A JUnit 4 unit test to demonstrate the usage: */
    @Test
    public void testGetMismatches() {
        Collection<String> results = getMatches("abentbananaend", "bend", 2);
        assertEquals(Arrays.asList("bent", "bana", "aend"), results);
    }
}
