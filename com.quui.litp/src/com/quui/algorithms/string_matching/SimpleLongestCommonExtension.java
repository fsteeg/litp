package com.quui.algorithms.string_matching;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Simple computation of the '''longest common extension''' ('''lce''') of two
 * given strings, beginning at two given indices. The '''lce''' computation is
 * part of different algorithms in
 * [http://en.wikipedia.org/wiki/Fuzzy_string_searching inexact string matching]
 * (Gusfield 1999:196). This simple implementation has a runtime complexity of
 * O(n). Computation in O(1) is possible through constant-time retrieval of
 * '''lowest common ancestors''' ('''lca''') in
 * [http://en.wikipedia.org/wiki/Suffix_tree suffix trees] (after linear-time
 * preprocessing). See Gusfield 1999:181 for further reference on constant time
 * '''lca''' retrival and Gusfield 1999:196 for constant time '''lce'''
 * computation using the '''lca'''.
 */

public class SimpleLongestCommonExtension {
    /**
     * This method takes four parameters: t1 and t2 (the two strings) and two
     * indices i1 and i2 (the index to start at in t1 and t2). It returns the
     * length of the longest common extension starting at i1 in t1 and at i2 in
     * t2. The method simply iterates over the given strings, starting at the
     * given indices. It compares the characters, counts equal characters and
     * returns that number as soon as the characters differ.
     */
    public static int longestCommonExtension(String t1, int i1, String t2,
            int i2) {
        int res = 0;
        for (int i = i1; i < t1.length() && i2 < t2.length(); i++, i2++) {
            if (t1.charAt(i) == t2.charAt(i2))
                res++;
            else
                return res;
        }
        return res;
    }

    /** A JUnit 4 unit test to demonstrate the functionality: */

    @Test
    public void testGetLongestCommonExtension() {
        int res = longestCommonExtension("zsdabcdefghj", 3, "abcdezas", 0);
        assertEquals(5, res);
    }

    /**
     * References: Gusfield, Dan (1999), '''Algorithms on Strings, Sequences and
     * Trees'''. Cambridge: University Press.
     */

}