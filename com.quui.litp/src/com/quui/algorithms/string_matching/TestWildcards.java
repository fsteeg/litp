package com.quui.algorithms.string_matching;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

/**
 * Tests for {@link Wildcards}
 * 
 * @author Fabian Steeg (fsteeg)
 */
public class TestWildcards {

    @Test
    public void testGetLongestCommonExtension1() {
        String a = "abcdezas";
        String b = "abcdefghj";
        int res = SimpleLongestCommonExtension.longestCommonExtension(a, 1, b, 0);
        assertEquals(0, res);
    }

    @Test
    public void testGetLongestCommonExtension2() {
        String a = "abcdezas";
        String b = "abcdefghj";
        int res = SimpleLongestCommonExtension.longestCommonExtension(a, 0, b, 0);
        assertEquals(5, res);
    }

    @Test
    public void testGetLongestCommonExtension4() {
        int res = SimpleLongestCommonExtension.longestCommonExtension("abcdezas", 0, "zsdabcdefghj", 3);
        assertEquals(5, res);
    }

    @Test
    public void testGetMatches() {
        Collection<String> results = Wildcards.getMatches("abentbananaend bend", "ben*");
        assertEquals(Arrays.asList("bent", "bend"), results);
    }
}
