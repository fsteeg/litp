package com.quui.algorithms.string_matching;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

/**
 * Tests for {@link KMismatch}
 * 
 * @author Fabian Steeg (fsteeg)
 */
public class TestKMismatch extends TestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * Test for {@link KMismatch}
     */
    public void testGetLongestCommonExtension() {
        String a = "abcdezas";
        String b = "abcdefghj";
        int res = SimpleLongestCommonExtension.longestCommonExtension(a, 1, b, 0);
        assertEquals(0, res);

        a = "abcdezas";
        b = "abcdefghj";
        res = SimpleLongestCommonExtension.longestCommonExtension(a, 0, b, 0);
        assertEquals(5, res);

        a = "abcdezas";
        b = "zsdabcdefghj";
        res = SimpleLongestCommonExtension.longestCommonExtension(a, 0, b, 3);
        assertEquals(5, res);
    }

    /**
     * Test for {@link KMismatch}
     */
    public void testGetMismatches() {
        Collection<String> results = KMismatch.getMatches("abentbananaend", "bend", 2);
        assertEquals(Arrays.asList("bent", "bana", "aend"), results);
    }

    

}
