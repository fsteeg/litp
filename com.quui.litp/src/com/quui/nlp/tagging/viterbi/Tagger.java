package com.quui.nlp.tagging.viterbi;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * This is a minimal, exemplaric
 * [http://en.wikipedia.org/wiki/Part-of-speech_tagging part of speech tagger]
 * using a [http://en.wikipedia.org/wiki/Hidden_Markov_model hidden markov
 * model], implemented using the [http://en.wikipedia.org/wiki/Viterbi_algorithm
 * viterbi algorithm], a [http://en.wikipedia.org/wiki/Dynamic_programming
 * dynamic programming] algorithm. The tagger is
 * [http://en.wikipedia.org/wiki/Machine_learning trained] with an untagged
 * minimal text corpus, generating matrices A (Kupiec) and B (pseudo-random).
 */
public class Tagger {
    String tagset;

    String corpus;

    Map<String, String> lexicon;

    String lexiconString;

    /** Sample usage: Tag a given string of words. */
    @Test
    public void testTagger() {
        System.out
                .println(tag("layers that fly that food . that fly sneaks ."));
    }

    private void initData() {
        lexicon = new HashMap<String, String>();

        /**
         * Kupiec: words with common category-sets form a category of their own,
         * here 'vn' for word that can be v and n. Better for small
         * trainingsets, since they are then distinguished from the pure members
         * of the single classes. Consider Jelinek as an alternative. Consult
         * Manning & Schütze (1999) for further reference. The lemmata are
         * collected in a blank-separated string used by the HMM (also
         * internally the order). Finally, we initialize the tagset and the
         * untagged corpus.
         */
        lexicon.put("fly", "vn");
        lexicon.put("layers", "vn");
        lexicon.put("sneaks", "v");
        lexicon.put("food", "n");
        lexicon.put("that", "det,rp");
        lexicon.put(".", "period");

        StringBuffer lexBuf = new StringBuffer();
        for (String word : lexicon.keySet()) {
            lexBuf.append(word + " ");
        }
        lexiconString = lexBuf.toString();

        tagset = "det rp vn v n period";
        corpus = "that fly layers. that fly sneaks. that, that sneaks food layers. that fly layers that food. layers.";
    }

    /**
     * Tags the words in the given string using the HMM. Returns the input
     * string with the POS tag appended to each word. Creates an HMM for the
     * input, given the tagset, lexicon, matrices A and B (which are generated).
     * Adds the POS tag for every word in the input and returns that tagged
     * string using the most probable tag set for the input.
     */
    public String tag(String input) {
        initData();
        ViterbiHMM hmm = new ViterbiHMM(tagset, lexiconString, input, lexicon,
                corpus, true);
        String[] in = input.split(" ");
        String[] re = hmm.mostProbableSequence().split(" ");
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < in.length; i++) {
            buf.append(in[i] + ":" + re[i] + " ");
        }
        return buf.toString();
    }

    /**
     * References: Chris Manning and Hinrich Schütze (1999), ''Foundations of
     * Statistical Natural Language Processing'', MIT Press. Cambridge, MA.
     * 
     * [[Category:Viterbi|Java]] [[Category:HMM|Java]] [[Category:POS
     * tagging|Java]]
     */
}