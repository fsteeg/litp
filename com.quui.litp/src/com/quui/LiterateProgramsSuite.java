package com.quui;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.quui.algorithms.binary_search.BinarySearch;
import com.quui.algorithms.permutations.TestPermutationsWithRepetition;
import com.quui.algorithms.quicksort.Quicksort;
import com.quui.algorithms.string_matching.BoyerMoore;
import com.quui.algorithms.string_matching.EditDistance;
import com.quui.algorithms.string_matching.SimpleLongestCommonExtension;
import com.quui.algorithms.string_matching.TestKMismatch;
import com.quui.algorithms.string_matching.TestWildcards;
import com.quui.data_structures.binary_tree.BinaryTree;
import com.quui.data_structures.stack.Stack;
import com.quui.data_structures.suffix_tree.stripped.SuffixTree;
import com.quui.data_structures.visited_tree.Tree.TreeClient;
import com.quui.nlp.tagging.viterbi.Tagger;
import com.quui.nn.perceptron.Perceptron;

/**
 * Main test suite for the Literate Programs tests.
 * @author Fabian Steeg (fsteeg)
 */
@RunWith( Suite.class )
@Suite.SuiteClasses( { 
BinarySearch.class,
BinaryTree.class,
TestPermutationsWithRepetition.class,
Quicksort.class,
BoyerMoore.class,
EditDistance.class,
TestKMismatch.class,
TestWildcards.class,
SimpleLongestCommonExtension.class,
Stack.class,
SuffixTree.class,
TreeClient.class,
Tagger.class,
Perceptron.class
} )
public final class LiterateProgramsSuite { }
