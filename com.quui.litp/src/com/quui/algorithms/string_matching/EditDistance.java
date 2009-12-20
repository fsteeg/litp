package com.quui.algorithms.string_matching;

import org.junit.Test;

/**
 * Simple (no weights, no recorded edit transcript) DP-based computation of the
 * edit distance (also known as
 * [http://en.wikipedia.org/wiki/Levenshtein_distance Levenshtein distance]) of
 * two given strings s_1 and s_2 of lengths n and m with a time complexity of
 * O(nm), the time to fill the DP table. See Gusfield, p. 215 for details and
 * extensions.
 */
public class EditDistance {
	public int compute(String s1, String s2) {

		/* <<init>>= */
		/*
		 * We compute the edit distance of two strings using the standard DP
		 * approach: we find D(n,m) by computing all combinations of D(i,j) for
		 * i and j smaller than n,m. Initialize the table of height s1.length+1
		 * and width s2.length+1:
		 */
		int[][] dp = new int[s1.length() + 1][s2.length() + 1];
		/* =<<init>> */
		/* <<nested_loop>>= */
		/* We fill each row from left to right: */
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[i].length; j++) {
				/* =<<nested_loop>> */
				/* <<base_conditions>>= */
				/*
				 * Fill the table with the initial values, based on the base
				 * conditions: D(i,0)=1 and D(0,j)=1, resulting in row 0 filled
				 * with values from 0 to i-1 and column 0 with values 0 to j-1:
				 */
				dp[i][j] = i == 0 ? j : j == 0 ? i : 0;
				/* =<<base_conditions>> */
				/* <<recurrence>>= */
				/*
				 * For the other values, perform the bottom-up simulation of the
				 * recurrence relation, the actual DP computation:
				 */
				if (i > 0 && j > 0) {
					/* =<<recurrence>> */
					/* <<match>>= */
					/*
					 * The best case: match, so we can take the diagonal value
					 * without further cost (we compare the strings at index-1
					 * since the table is one position larger than the strings
					 * in height and width)
					 */
					if (s1.charAt(i - 1) == s2.charAt(j - 1))
						dp[i][j] = dp[i - 1][j - 1];
					/* =<<match>> */
					/* <<mismatch>>= */
					/*
					 * Else, we pick set the new optimal sub-solution to
					 * MIN(DP[i][j-1]+1,DP[i-1][j-1]+1,DP[i-1][j]+1), i.e. to
					 * the minimum result of the replace (diagonal), insert
					 * (horizontal), and delete (vertical) operations.
					 */
					else
						dp[i][j] = Math.min(dp[i][j - 1] + 1, Math.min(
								dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1));
					/* =<<mismatch>> */
				}
			}
		}
		/* <<result>>= */
		/*
		 * The result is stored in the bottom right cell of the DP table: the
		 * edit distance for pair n,m: D(n,m).
		 */
		return dp[s1.length()][s2.length()];
		/* =<<result>> */
	}

	@Test public void main() {
		/* <<usage>>= */
		/* Compute the edit distance for some samples, including empty strings: */
		EditDistance distance = new EditDistance();
		System.out.println(distance.compute("vintner", "writers"));
		System.out.println(distance.compute("vintners", "writers"));
		System.out.println(distance.compute("vintners", ""));
		System.out.println(distance.compute("", ""));
		/* =<<usage>> */
	}
}
