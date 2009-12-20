package com.quui.algorithms.string_matching;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

/**
 * An implementation of string matching with
 * [http://en.wikipedia.org/wiki/Wildcard_character wildcards], as described in
 * Gusfield 1999:199. When used with a ''longest common extension'' computation
 * with [http://en.wikipedia.org/wiki/Suffix_tree suffix trees], it allows for
 * searching a pattern with ''k'' wildcards in a text ''T'' of length ''m'' in
 * ''O(km)'' time (Gusfield 1999:199). This implementation uses a [[Longest
 * common extension (Java)|simple computation]] of the ''longest common
 * extension''.
 */
public class Wildcards {
    /**
     * The basic idea is to align the pattern at every position of the text,
     * computing the ''longest common extension'' and ensuring that mismatches
     * occur on positions with wildcards.
     * 
     * The matching method takes two arguments: The text and the pattern. The
     * [http://en.wikipedia.org/wiki/Asterisk asterisk] (*) denotes the wildcard
     * character. The method returns a collection of strings: the matches of the
     * pattern in the text.
     */
    public static Collection<String> getMatches(String t, String p) {
        Collection<String> result = new ArrayList<String>();
        for (int i = 0; i < t.length(); i++) {
            // Step 1: Set j to 1 and h to i.
            int j = 0;
            int h = i;
            int n = p.length();
            while (true) {
                // Step 2: Compute the length L of the longest common extension
                // starting at positions j of P and h of T:
                int L = SimpleLongestCommonExtension.longestCommonExtension(p,
                        j, t, h);
                // Step 3: if j + L = n + 1 then P occurs in T starting at i;
                // stop.
                if (j + 1 + L == n + 1) {
                    result.add(t.substring(i, i + n));
                    break;
                }
                // Step 4: Check if a wildcard occurs in position j + L of P or
                // position h + L in T. If so then set j to j + L + 1, set h to
                // h + L + 1, and then go to step 2. Else, P does not occur in T
                // starting at i; stop.
                if (((j + L) < p.length() && p.charAt(j + L) == '*')
                        || ((h + L) < t.length() && t.charAt(h + L) == '*')) {
                    j = j + L + 1;
                    h = h + L + 1;
                } else
                    break;
            }
        }
        return result;
    }

    /** A JUnit 4 test to demonstrate the usage: */

    @Test
    public void testGetMatches() {
        Collection<String> results = getMatches("abentbananaend bend", "ben*");
        assertEquals(Arrays.asList("bent", "bend"), results);
    }

    /**
     * References: Gusfield, Dan (1999), '''Algorithms on Strings, Sequences and
     * Trees'''. Cambridge: University Press.
     */
}
