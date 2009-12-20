package com.quui.algorithms.string_matching;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.junit.Test;

/**
 * Introduction
 * 
 * A basic (without any good-suffix-shift rule) implementation of the
 * [http://en.wikipedia.org/wiki/Boyer-Moore_string_search_algorithm Boyer-Moore
 * string matching algorithm], with right-to-left scan and a standard
 * bad-character-shift rule. This algorithm has a typical sub-linear runtime
 * (see Gusfield, p. 17). <p/>
 * 
 * It can be extended using a refined version of the bad-character-shift rule
 * which improves efficiency for small alphabets, e.g. for usage in
 * bioinformatics (see Gusfield, p. 18) and by the strong good-suffix rule for
 * provable worst-case linear runtime (see Gusfield, p. 20). <p/>
 * 
 * As an alternative for even faster matching (dependent on the pattern length,
 * not the text length, after linear-time preprocessing) consider suffix-tree
 * based algorithms (see Gusfield, p. 89).
 */
public class BoyerMoore {

	/** Matching */

	public List<Integer> match(String pattern, String text) {
		List<Integer> matches = new Vector<Integer>();
		/* <<lengths>>= */
		/* We match a pattern of length $n$ in a text of length $m$: */
		int m = text.length();
		int n = pattern.length();
		/* =<<lengths>> */
		/* <<preprop_call>>= */
		/*
		 * Preprocess the pattern for the right-to-left-scan and
		 * bad-character-shift rules by finding the right-most positions of all
		 * characters in the pattern:
		 */
		Map<Character, Integer> rightMostIndexes = preprocessForBadCharacterShift(pattern);
		/* =<<preprop_call>> */
		/* <<align_start>>= */
		/*
		 * We align p and t, starting on index 0 (meaning the beginning of the
		 * pattern is aligned with position 0, i.e. the beginning, of the text),
		 * and shift p to the left, until we reach the end of t:
		 */
		int alignedAt = 0;
		while (alignedAt + (n - 1) < m) {
			/* =<<preprop_call>> */
			/* <<loop>>= */
			/*
			 * On each aligned position, we scan the pattern from right to left,
			 * comparing the aligned characters at the current position in the
			 * text $x$ and at the current position in the pattern $y$:
			 */
			for (int indexInPattern = n - 1; indexInPattern >= 0; indexInPattern--) {
				int indexInText = alignedAt + indexInPattern;
				char x = text.charAt(indexInText);
				char y = pattern.charAt(indexInPattern);
				/* =<<loop>> */
				/* <<break>>= */
				/*
				 * If the pattern is longer than the text, we have no match
				 * here:
				 */
				if (indexInText >= m)
					break;
				/* =<<break>> */
				/* <<mismatch>>= */
				/* In the case of a mismatch, we do the shifting: */
				if (x != y) {
					/* =<<mismatch>> */
					/* <<get_index>>= */
					/*
					 * We first retrieve the right-most index of the mismatching
					 * text-character in the pattern:
					 */
					Integer r = rightMostIndexes.get(x);
					/* =<<get_index>> */
					/* <<big_skip>>= */
					/*
					 * If the mismatching character in the text is not in the
					 * pattern we can shift until we are aligned behind the
					 * mismatch-position, resulting in sub-linear runtime, as
					 * this will result in some characters never being
					 * inspected:
					 */
					if (r == null) {
						alignedAt = indexInText + 1;
					}
					/* =<<big_skip>> */
					/* <<small_skip>>= */
					/*
					 * Else we shift the pattern to the right until the
					 * right-most occurrence of $x$ in the pattern is under the
					 * mismatch position in the text (if this shift is a forward
					 * shift, i.e. to the right), as this is the next possible
					 * place where an occurrence of the pattern can begin in the
					 * text:
					 */
					else {
						int shift = indexInText - (alignedAt + r);
						alignedAt += shift > 0 ? shift : alignedAt + 1;
					}
					/* =<<small_skip>> */
					break;
				}
				/* <<match>>= */
				/*
				 * If the characters are equal and the pattern has been scanned
				 * completely from right to left, we have a match at the
				 * currently aligned position in the text. We store the match
				 * and shift the pattern one position to the right:
				 */
				else if (indexInPattern == 0) {
					matches.add(alignedAt);
					alignedAt++;
				}
				/* =<<match>> */
			}
		}
		return matches;
	}

	/** Preprocessing */
	private Map<Character, Integer> preprocessForBadCharacterShift(
			String pattern) {
		/* <<preprop>>= */
		/*
		 * For each character in the string to preprocess, we store its
		 * right-most position by scanning the string from right to left,
		 * storing the character as a key and its position as a value in a
		 * hash-map, if it is not in the map already:
		 */
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = pattern.length() - 1; i >= 0; i--) {
			char c = pattern.charAt(i);
			if (!map.containsKey(c)) {
				map.put(c, i);
			}
		}
		/* =<<preprop>> */
		return map;
	}

	/** Usage */
	@Test public void main() {
		/* <<usage>>= */
		/*
		 * A bit of basic testing: match 'ana' in 'bananas', print the matches
		 * found and simulate a simple unit test.
		 */
		List<Integer> matches = new BoyerMoore().match("ana", "bananas");
		for (Integer integer : matches) {
			System.out.println("Match at: " + integer);
		}
		System.out.println((matches.equals(Arrays.asList(1, 3)) ? "OK"
				: "Failed"));
		/* =<<usage>> */
	}
}
