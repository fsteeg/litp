package com.quui.algorithms.sorting

/** 
 * Simple generic insertion sort in Scala, using lists and pattern matching.
 * 
 * @author Fabian Steeg (fsteeg@gmail.com)
 */
object ScalaInsertionSort {
  def main(args : Array[String]) : Unit = {
      println("Testing insertion sort in " + getClass.getName)
      /* Two simple test cases: sorting an unsorted list should yield a sorted list: */
      require(sort(2 :: 1 :: 6 :: 4 :: Nil) == 1 :: 2 :: 4 :: 6 :: Nil)
      require(sort("c" :: "a" :: "d" :: Nil) == "a" :: "c" :: "d" :: Nil)
  }
  
  /* Sorting a list yields a sorted list by matching... */
  def sort[T <% Ordered[T]](list: List[T]): List[T] = list match {
      /* ...the empty list: is already sorted */
      case Nil => Nil
      /* ...any non-empty list: insert the head at the correct position in the sorted tail */
      case head :: tail => insert(head, sort(tail))
  }
  
  /* Inserting an element into a list at the correct position yields a list by matching... */
  def insert[T <% Ordered[T]](elem:T, list: List[T]): List[T] = list match {
      /* ...an empty list: a list containing only the element */
      case Nil => elem :: list
      /* ...a list with a head that is larger than the element: prepend the element */
      case head :: tail if (elem <= head) => elem :: list
      /* ...any other list: insert the element into the tail, prepend the head */
      case head :: tail => head :: insert(elem, tail)
  }
}
