package com.quui.nn.perceptron;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.junit.Test;

/**
 * A simple, illustrative implementation of a
 * [http://en.wikipedia.org/wiki/Artificial_neural_network#Single-layer_perceptron
 * single-layered] [http://en.wikipedia.org/wiki/Perceptron perceptron] in Java.
 * When a pattern is impressed on the perceptron the activation of the network
 * is adjusted according to an activiation formula and a given bias value. To
 * adjust the way the perceptron reacts to a given input, a learning algorithm
 * (the delta rule) is implemented to adjust the weights connecting the neurons
 * of the perceptron, which are initially set to 0. This rule keeps adjusting
 * the weights until the resulting output for a given input corresponds to a
 * supplied correct output, resulting in a perceptron trained to react to a
 * certain perception in a certain way.
 */

public class Perceptron {

    /**
     * Attributes and constructor of the perceptron: the input values and the
     * teaching output are hard coded as integer matrices representing the
     * desired activation for a given set of inputs. The number of input and
     * output neurons and the number of patterns depend on these matrices. The
     * weights of the connections between neurons in the perceptron are
     * represented in a matric of doubles. The constructor initializes the
     * weights with the given sizes of input and output neurons.
     */

    int[][] patterns = { { 0, 0, 0, 0 }, { 0, 0, 0, 1 }, { 0, 0, 1, 0 },
            { 0, 0, 1, 1 }, { 0, 1, 0, 0 }, { 0, 1, 0, 1 }, { 0, 1, 1, 0 },
            { 0, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 1 } };

    int[][] teachingOutput = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 }, { 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 } };

    int numberOfInputNeurons = patterns[0].length;

    int numberOfOutputNeurons = teachingOutput.length;

    int numberOfPatterns = patterns.length;

    double[][] weights;

    public Perceptron() {
        weights = new double[numberOfInputNeurons][numberOfOutputNeurons];
    }

    /**
     * The delta rule is the learning algorithm for adjusting the weights of the
     * perceptron given an input value, a desired output and an untrained
     * perceptron. The output of the perceptron is compared to the desired
     * output and weights are adjusted if an error is found, where an error is a
     * difference between the actual output and the expected output (the
     * teaching output).
     */

    public void deltaRule() {
        boolean allCorrect = false;
        boolean error = false;
        double learningFactor = 0.2;
        while (!allCorrect) {
            error = false;
            for (int i = 0; i < numberOfPatterns; i++) {
                int[] output = setOutputValues(i);
                for (int j = 0; j < numberOfOutputNeurons; j++) {
                    if (teachingOutput[i][j] != output[j]) {
                        for (int k = 0; k < numberOfInputNeurons; k++) {
                            weights[k][j] = weights[k][j] + learningFactor
                                    * patterns[i][k]
                                    * (teachingOutput[i][j] - output[j]);
                        }
                    }
                }
                for (int z = 0; z < output.length; z++) {
                    if (output[z] != teachingOutput[i][z])
                        error = true;
                }
            }
            if (!error) {
                allCorrect = true;
            }
        }
    }

    /**
     * Imresses a pattern on the input layer and sets the output layer for the
     * applied pattern. Takes one parameter: the index of the pattern to apply.
     * Returns an int array: the resulting output. Depending on the result of
     * the activation bias computation a neuron is activated or deactivated by
     * setting the value of the corresponding position to 1 or 0.
     */
    int[] setOutputValues(int patternNo) {
        double bias = 0.7;
        int[] result = new int[numberOfOutputNeurons];
        int[] toImpress = patterns[patternNo];
        for (int i = 0; i < toImpress.length; i++) {
            for (int j = 0; j < result.length; j++) {
                double net = weights[0][j] * toImpress[0] + weights[1][j]
                        * toImpress[1] + weights[2][j] * toImpress[2]
                        + weights[3][j] * toImpress[3];
                if (net > bias)
                    result[j] = 1;
                else
                    result[j] = 0;
            }
        }
        return result;
    }

    /**
     * Method for printing a matrix of doubles formatting the values to one
     * decimal digit (using DecimalFormat) to get a readable output of weights,
     * representing the connections of neurons in the perceptron.
     */
    public void printMatrix(double[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                NumberFormat f = NumberFormat.getInstance();
                if (f instanceof DecimalFormat) {
                    DecimalFormat decimalFormat = ((DecimalFormat) f);
                    decimalFormat.setMaximumFractionDigits(1);
                    decimalFormat.setMinimumFractionDigits(1);
                    System.out.print("(" + f.format(matrix[i][j]) + ")");
                }
            }
            System.out.println();
        }
    }

    /** Demonstration of the usage in a JUnit 4 unit test (requires Java 5). */
    @Test
    public void testPerceptron() {
        Perceptron p = new Perceptron();
        System.out.println("Weights before training: ");
        p.printMatrix(p.weights);
        p.deltaRule();
        System.out.println("Weights after training: ");
        p.printMatrix(p.weights);
    }
}
