package com.quui.algorithms.binary_search;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * An implementation of [http://en.wikipedia.org/wiki/Binary_search binary
 * search] in Java. Note that this implementation is for illustrative reasons,
 * Java provides static binary search methods in the Arrays and Collections
 * classes.
 */
public class BinarySearch {
    /**
     * Search for an element in a list of elements. Returns the index of the
     * element if found, or, if not found, the negative index where the element
     * should be inserted. The method takes two arguments: The value to search
     * and the list of values to search in. The input list must be sorted in
     * ascending order prior to searching, else the result of this method is
     * undefined. It also must contain at least one element. Through Java
     * generics, the method can be used with any elements that extend
     * Comparable, like the Java number objects or strings, making Java 5 a
     * requirement.
     */
    static <T extends Comparable<? super T>> int search(T value, List<T> values) {

        int n = values.size();
        int mid, left, right;
        T midVal;
        left = 0;
        right = n - 1;
        /**
         * The interval between 0 and left is empty or contains only values <
         * value. The interval from right to n-1 is empty or contains only
         * values > value. The remaining search interval is from left to
         * right. The part left and right of that interval has been
         * searched.
         */
        do {
            mid = (left + right) / 2;
            midVal = values.get(mid);
            if (value.compareTo(values.get(mid)) < 0)
                right = mid - 1;
            else if (value.compareTo(values.get(mid)) > 0)
                left = mid + 1;
            else
                return mid;
        } while (left <= right);
        /**
         * Now left > right and the value was not found in the list. As the
         * remaining searching interval is empty now, there is an interval i
         * [0,n-1] with two disjunct intervals i1 [0,left] and i2 [rigth, n-1]
         * with the following attributes: all indices in i1 have values < value,
         * all indices in i2 have values > value and i1 merged with i2 equals i.
         * 
         * Case a) value < midVal & right = mid-1. From the first part follows
         * that mid is in interval [right, n-1] and from the second part follows
         * that mid is at the left edge of the interval, so mid is the smalles
         * index with list value > value. So in case a) an insertion must happen
         * on index mid.
         * 
         * Case b) value > midVal & left = mid+1. From the first part follows
         * that mid is in interval [0, left]. From the second part follows that
         * mid is at the right edge of the interval. This means mid is the
         * largest index with a list value < value, so in case b) we need to
         * insert at mid + 1.
         */
        return (value.compareTo(midVal) < 0) ? -mid : -(mid + 1);
    }

    /**
     * Illustration of the functionality using a JUnit 4 unit test:
     */
    @Test
    public void testStringSearch() {
        assertEquals(-1, BinarySearch.search("Billy", Arrays.asList("Anny",
                "Emmy", "Grammy")));
    }

    @Test
    public void testIntegerSearch() {
        assertEquals(-1, BinarySearch.search(2, Arrays.asList(1, 3, 4)));
    }
}