package com.quui.algorithms.string_matching;

import org.scalatest._

/**
 * Simple (no weights, no recorded edit transcript) DP-based computation of the edit distance (also 
 * known as [http://en.wikipedia.org/wiki/Levenshtein_distance Levenshtein distance]) of two given 
 * strings s_1 and s_2 of lengths n and m with a time complexity of O(n*m), the time to fill the DP 
 * table. See Gusfield, p. 215 for details and extensions.
 * 
 * @author Fabian Steeg (fsteeg@gmail.com)
 */
class ScalaEditDistance() {
/** 
 * @param s1 The first string
 * @param s2 The second string
 * @return The minimum number of delete, insert or replace operations required to transform 
 *         the first string to the second, or vice versa
 */
def distance(s1: String, s2: String): Int = {
    /*
     * We compute the edit distance of two strings using the standard DP approach: we find D(n,m) by 
     * computing all combinations of D(i,j) for i and j smaller than n,m. Initialize the table of 
     * height s1.length+1 and width s2.length+1:
     */
    val table = new Array[Array[Int]](s1.length + 1, s2.length + 1)
    
    /*
     * For every cell in the table, we compute the optimal solution to the subproblem of computing 
     * the edit distance for the first i characters in s1 and the first j characters in s2:
     */
    for (i <- table.indices; j <- table(i).indices) table(i)(j) = distance(table, i, j, s1, s2)
    
    /*
     * The result is stored in the bottom right cell of the DP table: the edit distance for pair n,m.
     */
    table(s1.length)(s2.length)
  }
  
  private def distance(table:Array[Array[Int]], i:Int, j:Int, s1:String, s2:String): Int = {
    /*
     * Fill the table with the initial values, based on the base conditions: D(i,0)=1 and D(0,j)=1, 
     * resulting in row 0 filled with values from 0 to i-1 and column 0 with values 0 to j-1:
     */
    if (i == 0) j else if (j == 0) i else {
      /*
       * For the other values, perform the bottom-up simulation of the recurrence relation, the 
       * actual DP computation, we try the possible operations:
       */
      
      /* 1. choice: delete in s1 (= insert in s2) */
      val del: Int = table(i - 1)(j) + 1
      
      /* 2. choice: insert in s1 (= delete in s2) */
      val ins: Int = table(i)(j - 1) + 1
      
      /* 3. choice: replace
       * a) the current chars match, we can take the diagonal value without further cost
       * b) the chars don't match: we actually do a replace and add 1
       */
      val rep: Int = table(i - 1)(j - 1) + (if(s1(i - 1) == s2(j - 1)) 0 else 1)
       
      /*
       * In the end, we pick the new optimal sub-solution MIN(DEL,INS,REP), i.e. to minimum of the 
       * replace (diagonal), insert(horizontal), and delete (vertical) operations:
       */
      List(del, ins, rep) min
    }
  }
}

object ScalaEditDistanceRunner extends Application {
    
  (new SpecEditDistance).execute
  
  class SpecEditDistance extends Spec {
      def edit: ScalaEditDistance = new ScalaEditDistance()
      println("Testing edit distance implementation " + edit.getClass.getName)
      /* Compute the edit distance for some samples, including empty strings: */
      expect(2) { edit.distance("ehe", "reh") }
      expect(2) { edit.distance("eber", "leder") }
      expect(0) { edit.distance("ehe", "ehe") }
      expect(0) { edit.distance("", "")}
      expect(1) { edit.distance("ehe", "eher") }
      expect(2) { edit.distance("he", "") }
      expect(2) { edit.distance("", "he") }
      expect(0) { edit.distance("nette rehe retten", "nette rehe retten") }
  }
}

