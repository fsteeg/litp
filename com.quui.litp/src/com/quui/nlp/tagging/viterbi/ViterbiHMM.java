package com.quui.nlp.tagging.viterbi;

import java.util.Arrays;
import java.util.Map;

/**
 * An HMM consists of an alphabet ''a'' a number of states ''n'', an observable
 * alphabet, an observation, the state transition probabilities matrix ''A''
 * (transitions from observable to hidden) and the observation probabilities
 * matrix ''B'' (transitions from hidden to hidden). What the HMM computes is
 * the most probable sequence of states for a given observation. The matrices
 * are generated, see below for details.
 */
abstract class AbstractHMM {
    protected String a;

    protected int n;

    protected String obsA;

    protected String obs;

    protected double[][] A;

    protected double[][] B;

    protected String mostProbableSequence;

    public AbstractHMM(String a, String obsA, String obs,
            Map<String, String> lexicon, String corpus) {
        MatrixGenerator gen = new MatrixGenerator(lexicon, corpus, a);
        this.a = a;
        this.n = obs.length();
        this.obsA = obsA;
        this.obs = obs;
        this.A = gen.createMatrixA(0.01);
        this.B = gen.createMatrixB();
    }

    public abstract String mostProbableSequence();
}

/**
 * A concrete HMM using the viterbi algorithm for POS tagging. The HMM has the
 * following attributes: ''a'', the hidden alphabet (the possible tags),
 * ''obsA'': the observable alphabet (the lemmata in the lexicon), ''obs'': the
 * observed sequence (a string containing words found in the lexicon), ''A'':
 * transitions from observable to hidden (probabilities for word to tag
 * transitions) and ''B'': transitions from hidden to hidden (probabilities for
 * tag to tag transitions). As part of the Viterbi algorithm this HMM has delta
 * and psi matrices. Finding the most probable sequence consists of two basic steps.
 */
public class ViterbiHMM extends AbstractHMM {

    private double[][] delta;

    private int[][] psi;

    private String[] hiddenAlphabet;

    private String[] observableAlphabet;

    private String[] observation;

    private boolean absoluteValues;

    public ViterbiHMM(String a, String obsA, String obs,
            Map<String, String> lexicon, String corpus, boolean absoluteValues) {
        super(a, obsA, ". " + obs, lexicon, corpus);
        this.absoluteValues = absoluteValues;
    }

    /**
     * Finding the most probable sequence consists of two basic steps:
     */
    public String mostProbableSequence() {
        init();
        induct();
        System.out.println("Delta:");
        ViterbiMatrixTools.printMatrix(delta);
        System.out.println();
        System.out.println("Psi:");
        ViterbiMatrixTools.printMatrix(psi);
        System.out.println();
        return getResult();
    }

    /**
     * 1. Initialize the matrices with their initial values: fill delta with 0.0
     * except for the starting 1.0. Fill psi with -1.
     */
    private void init() {
        hiddenAlphabet = a.split("\\s");
        observableAlphabet = obsA.split("\\s");
        observation = obs.split("\\s");
        delta = new double[hiddenAlphabet.length][observation.length];
        for (int i = 0; i < delta.length; i++) {
            Arrays.fill(delta[i], 0.0);
        }
        delta[delta.length - 1][0] = 1.0;
        psi = new int[hiddenAlphabet.length][observation.length - 1];
        for (int i = 0; i < psi.length; i++) {
            Arrays.fill(psi[i], -1);
        }
    }

    /**
     * 2. Compute delta, the matrix with probabilities. For each word in the
     * input, except the starting period (1), we compute the entry for delta for
     * every tag in the tagset (2). We lookup the word (3) and compute the
     * values for delta and psi (4).
     */
    private void induct() {
        // (1)
        for (int i = 1; i < observation.length; i++) {
            // (2)
            for (int j = 0; j < hiddenAlphabet.length; j++) {
                double prevTagMax = ViterbiMatrixTools.maximimumForCol(
                        i - 1, delta);
                // (3)
                int lexIndex = getIndex(observation[i], observableAlphabet);
                // (4)
                double prob = probForWordToTag(lexIndex + 1, j, B, A);
                double res = prevTagMax * prob;
                delta[j][i] = res;
                if (res > 0.0)
                    psi[j][i - 1] = ViterbiMatrixTools.indexOfMaximimumForCol(
                            i - 1, delta);
            }

        }
    }

    /**
     * Compute the probability for a transistion from a given (observable) word
     * to a given (hidden) tag, using the indices in the matrices.
     */
    private double probForWordToTag(int i, int j, double[][] b, double[][] a) {
        // delta has the leading period, therefore - 1 for previous:
        int prevIndex = ViterbiMatrixTools.indexOfMaximimumForCol(i - 1, delta);
        // b doesn have the leading p, therefore -1 for recent:
        if (absoluteValues)
            return (b[i - 1][j] / ViterbiMatrixTools.sumForCol(j, B))
                    * (A[prevIndex][j] / ViterbiMatrixTools.sumForRow(
                            prevIndex, A));
        else {
            return (b[i - 1][j]) * (A[prevIndex][j]);
        }
    }

    /**
     * Return the result after retrieving it from the psi matrix:
     */
    private String getResult() {
        String[] resultArray = new String[psi[0].length];
        int lastIndexInPsi = ViterbiMatrixTools.indexOfMaximimumForCol(
                delta[0].length - 1, delta);
        if (lastIndexInPsi == -1) {
            System.out.println("no tag-sequence found for input, exit.");
            System.exit(0);
        }
        int lastValueInPsi = psi[lastIndexInPsi][psi[0].length - 1];
        String lastTag = hiddenAlphabet[lastIndexInPsi];
        resultArray[resultArray.length - 1] = lastTag;
        // retrieve other tags:
        for (int i = psi[0].length - 2; i >= 0; i--) {
            resultArray[i] = hiddenAlphabet[lastValueInPsi];
            lastValueInPsi = psi[lastValueInPsi][i];
        }
        StringBuffer resultString = new StringBuffer();
        for (int i = 0; i < resultArray.length; i++) {
            resultString.append(resultArray[i]);
            if (i < resultArray.length - 1)
                resultString.append(" ");
        }
        return resultString.toString();
    }

    /**
     * Find the index of a string in a given array of strings. Returns the index
     * of the string in the array. If it wasn't found, we can't go on and
     * terminate here.
     */
    int getIndex(String string, String[] lexicon) {
        for (int i = 0; i < lexicon.length; i++) {
            if (string.equals(lexicon[i]))
                return i;
        }
        System.out.println("Word '" + string + "' not found in lexicon, exit.");
        System.exit(0);
        return -1;
    }

}