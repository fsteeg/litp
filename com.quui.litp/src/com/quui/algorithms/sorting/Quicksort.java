package com.quui.algorithms.sorting;

/** <<import>>= Import the JUnit 4 test annotation and the static assertEquals method. */
import org.junit.Assert;
import org.junit.Test;

/**
 * An implementation of the Quicksort algorithm. The array to be sorted can be
 * of any type that extends Comparable, making it usable e.g. with Java's
 * referencial numeric types like Float, Double, Integer etc. <p/>
 * 
 * As common for Quicksort implementations, the algorithm is implemented using a
 * Divide-and-Conquer strategy. The basic idea is to build two partitions which
 * are sorted at all times, until these two partitions together are the complete
 * array. The array is therefore conquered from the back and from the front,
 * dividing the array into two partitions, one starting from the beginning and
 * one from the end.
 */
public class Quicksort {

    static <T extends Comparable<? super T>> void quicksort(T[] array) {
        /**
         * Start conquering the array from the beginning (0) and from the end
         * (array.length - 1), thereby dividing it into two partitions.
         */
        quicksort(array, 0, array.length - 1);
    }

    /** The recursive method. */
    static <T extends Comparable<? super T>> void quicksort(T[] array,
            int left0, int right0) {
        /** <<initialize>>= Initialize variables. */
        int left, right;
        T pivot, temp;
        left = left0;
        right = right0 + 1;
        /**
         * This implementation uses the first element in the array as the pivot
         * element, which results in Quicksort's worst case runtime behaviour of
         * $O(n^2)$ for already sorted arrays. The elements to be swapped are
         * searched in do-while loops, elements are only swapped if left >
         * right, resulting in an ascending order.
         */
        pivot = array[left0];
        /** <<process>>= Process the remaining interval step-by-step. */
        do {
            /**
             * <<search_from_left>>= Search an element > current in the
             * partition from left to right.
             */
            do left++; while (left < array.length && array[left].compareTo(pivot) < 0);
            /**
             * <<search_from_right>>= Search an element < current in the
             * partition from right to left.
             */
            do right--; while (array[right].compareTo(pivot) > 0);
            /**
             * <<swap>>= At this point, if there is anything to be sorted left,
             * we have found two values, which, if swapped would ensure that the
             * two partitions are sorted again.
             */
            if (left < right) {
                temp = array[left];
                array[left] = array[right];
                array[right] = temp;
            }
        }
        /**
         * When there is no longer an interval between the already sorted
         * partitions, we are done here.
         */
        while (left <= right);
        /** Copy the smallest value to the beginning. */
        temp = array[left0];
        array[left0] = array[right];
        array[right] = temp;
        /** Keep going for the remaining intervals, if any. */
        if (left0 < right)
            quicksort(array, left0, right);
        if (left < right0)
            quicksort(array, left, right0);
    }

    /** <<test_integer>>= Test the sorting with Java's Integer type. */
    @Test
    public void integerSorting() {
        Integer[] array = new Integer[] { 5, 3, 4, 2, 1 };
        quicksort(array);
        Integer[] correct = new Integer[] { 1, 2, 3, 4, 5 };
        Assert.assertArrayEquals(correct, array);

    }

    /** <<test_float>>= Test the sorting with Java's Float type. */
    @Test
    public void floatSorting() {
        Float[] array = new Float[] { 1.8F, 3.6F, 4F, 5F, 2F };
        quicksort(array);
        Float[] correct = new Float[] { 1.8F, 2F, 3.6F, 4F, 5F };
        Assert.assertArrayEquals(correct, array);
    }
    
    /** <<test_float>>= Test the sorting with Java's String type. */
    @Test
    public void stringSorting() {
        String[] array = new String[] {"Batman", "Spiderman", "Anthony", "Zoolander"};
        quicksort(array);
        String[] correct = new String[] {"Anthony", "Batman",  "Spiderman",  "Zoolander"};
        Assert.assertArrayEquals(correct, array);
    }
}
