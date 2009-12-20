package com.quui.nlp.tagging.viterbi;

import java.text.NumberFormat;

/**
 * As other dynamic programming algorithms, the viterbi algorithms uses a table
 * or matrix for storing results and for computing results for subproblems. This
 * requires various queries on the table. Methods for these queries are gathered
 * in a viterbi matrix tools class.
 */
public class ViterbiMatrixTools {
    /** Compute the sum of the values in a given column: */
    static double sumForCol(int i, double[][] matrix) {
        double sum = 0;
        for (int j = 0; j < matrix.length; j++) {
            sum = sum + matrix[j][i];
        }
        return sum;
    }

    /** Compute the sum of the values in a given row: */
    static double sumForRow(int i, double[][] matrix) {
        double sum = 0;
        for (int j = 0; j < matrix[i].length; j++) {
            sum = sum + matrix[i][j];
        }
        return sum;
    }

    /** Find the maximum value in a given column: */
    static double maximimumForCol(int i, double[][] matrix) {
        double maxValue = 0.0;
        for (int j = 0; j < matrix.length; j++) {
            maxValue = Math.max(maxValue, matrix[j][i]);
        }
        return maxValue;
    }

    /**
     * Find the index of the maximum value in a given column of a matrix of
     * doubles:
     */
    static int indexOfMaximimumForCol(int i, double[][] matrix) {
        int maxIndex = -1;
        double maxValue = -1.0;
        for (int j = 0; j < matrix.length; j++) {
            if (matrix[j][i] > maxValue) {
                maxIndex = j;
                maxValue = matrix[j][i];
            }
        }
        return maxIndex;
    }

    /**
     * Find the index of the maximum value in a given column of a matrix of
     * integers:
     */
    static int indexOfMaximimumForCol(int i, int[][] matrix) {
        int maxIndex = -1;
        int maxValue = -1;
        for (int j = 0; j < matrix.length; j++) {
            if (matrix[j][i] > maxValue) {
                maxIndex = j;
                maxValue = matrix[j][i];
            }
        }
        return maxIndex;
    }

    /**
     * Print a matrix of doubles, filling up the cells with blanks for readable
     * output:
     */
    static void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                String myString = NumberFormat.getInstance().format(
                        matrix[i][j]);
                if (myString.length() < 5) {
                    for (int k = myString.length(); k < 5; k++) {
                        myString = myString + " ";
                    }
                }
                System.out.print(myString + "   ");
            }
            System.out.println();
        }
    }

    /** Print a matrix of integers: */
    static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] >= 0)
                    System.out.print(" ");
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println();
        }
    }

}