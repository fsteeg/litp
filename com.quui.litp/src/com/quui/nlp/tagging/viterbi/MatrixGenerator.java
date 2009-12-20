package com.quui.nlp.tagging.viterbi;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Generates the matrix with word-tag transition-probabilities (Matrix B).
 * 
 */
public class MatrixGenerator {
    private Map<String, String> lexicon;

    private Map<String, Integer> freq;

    private String corpus;

    private double[][] result;

    private String tagset;

    public MatrixGenerator(Map<String, String> lexicon, String corpus,
            String tagset) {
        this.corpus = corpus;
        this.lexicon = lexicon;
        this.tagset = tagset;
        countLexems();
        System.out.println("Tagset: " + tagset);
        System.out.println();
        System.out.println("Corpus: " + corpus);
        System.out.println();
        System.out.println("Frequencies:");
        System.out.println();
        printMap(freq);
    }

    /**
     * Create the matrix for word-tag-transitions (Matrix B, via Jelinek). Learn
     * transition probabilities from the corpus.
     */
    public double[][] createMatrixB() {
        String[] l = new String[lexicon.keySet().size()];
        String[] t = tagset.split(" ");
        int i = 0;
        for (Iterator<?> iter = lexicon.keySet().iterator(); iter.hasNext(); i++) {
            String element = (String) iter.next();
            l[i] = element;
        }
        /**
         * Initialize the matrix depending of the number of possible pos tags
         * for a given word by first retrieving the possible parts of speech for
         * the given word from the lexicon.
         */
        result = new double[l.length][t.length];
        for (int j = 0; j < result.length; j++) {
            Arrays.fill(result[j], 0.0);
            for (int k = 0; k < result[j].length; k++) {
                String categories = ((String) lexicon.get(l[j]));
                if (categories.indexOf(t[k]) != -1) {
                    result[j][k] = 1.0 / categories.split("\\,").length;
                }
            }
        }
        /**
         * Now adjust the values by computing the divider of every value ''a''
         * in the matrix and other value ''b'' in the same column as a.
         */
        for (int j = 0; j < result.length; j++) {
            for (int k = 0; k < result[j].length; k++) {
                double a = result[j][k]
                        * (double) ((Integer) freq.get(l[j])).intValue();
                double b = 0.0;
                for (int index = 0; index < result.length; index++) {
                    b = b
                            + result[index][k]
                            * (double) ((Integer) freq.get(l[index]))
                                    .intValue();
                }
                result[j][k] = a / b;
            }
        }
        System.out.println();
        System.out
                .println("Matrix B (word-tag, lexicon and tags ordered as printed above, learned from corpus):");
        ViterbiMatrixTools.printMatrix(result);
        return result;
    }

    /**
     * Create a matrix filled with pseudo-random values. The method takes one
     * argument, diff: the variability for the random numbers. Adjusting this
     * value changes how the matrix is filled. It returns a random-filled
     * matrix, for tag-tag-transitions probabilities (Matrix A).
     */
    public double[][] createMatrixA(double diff) {
        String[] t = tagset.split(" ");
        double[][] res = new double[t.length][t.length];
        int cols = res[0].length;
        double average = 1.0 / (double) cols;
        for (int i = 0; i < res.length; i++) {
            Arrays.fill(res[i], average);
        }
        Random r = new Random();
        for (int i = 0; i < res.length; i++) {
            double var = 1.0;
            while (var > diff)
                var = r.nextDouble();
            int pos = r.nextInt(res[i].length);
            boolean plus = true;
            for (int j = 0; j < res[i].length; j++) {
                // if not even, skip one random position
                if (res[i].length % 2 != 0 && j == pos)
                    continue;
                if (plus && res[i][j] - var > 0) {
                    res[i][j] = res[i][j] + var;
                    plus = false;
                } else if (res[i][j] - var > 0) {
                    res[i][j] = res[i][j] - var;
                    plus = true;
                }
            }
        }
        System.out.println();
        System.out.println("Matrix A (tag-tag, pseudo-random-generated):");
        ViterbiMatrixTools.printMatrix(res);
        System.out.println();
        return res;
    }

    /**
     * Print the keys and values of a map to system out.
     */
    private void printMap(Map<String, ?> map) {
        for (Iterator<?> iter = map.keySet().iterator(); iter.hasNext();) {
            String element = (String) iter.next();
            System.out.println(element + ": " + map.get(element));
        }
    }

    /**
     * Count word frequencies.
     */
    private void countLexems() {
        freq = new HashMap<String, Integer>();
        StringTokenizer tok = new StringTokenizer(corpus, " \t\n.,", true);
        while (tok.hasMoreTokens()) {
            String rec = tok.nextToken();
            if (lexicon.containsKey(rec)) {
                if (freq.containsKey(rec)) {
                    freq.put(rec, freq.get(rec) + 1);
                } else {
                    freq.put(rec, 1);
                }
            }
        }
    }
}