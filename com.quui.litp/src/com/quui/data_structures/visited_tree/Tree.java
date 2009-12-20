package com.quui.data_structures.visited_tree;

/* <<imports>>= */
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

/* =<<imports>> */

// visitor: extend operations on the data structure without messing with the
// structure itself:
public abstract class Tree<E> {
	// a visitor visits the actual elements of type E and recursive branches,
	// returns elements of type R
	/* <<visitor>>= */
	public interface Visitor<E, R> {
		public R leaf(E elt);

		public R branch(List<R> children);
	}

	public abstract <R> R visit(Visitor<E, R> v);

	/* =<<visitor>> */

	// Factory method for a leaf.
	/* <<leaf>>= */
	public static <T> Tree<T> leaf(final T e) {
		// return anonymous subclass for a leaf:
		return new Tree<T>() {
			@Override
			public <R> R visit(Visitor<T, R> v) {
				return v.leaf(e);
			}
		};
	}

	/* =<<leaf>> */

	// Factory method for a branch.
	/* <<branch>>= */
	public static <T> Tree<T> branch(final List<Tree<T>> children) {
		// return anonymous subclass for a branch:
		return new Tree<T>() {
			@Override
			public <R> R visit(Visitor<T, R> v) {
				List<R> result = new Vector<R>();
				for (Tree<T> tree : children) {
					result.add(tree.visit(v));
				}
				return v.branch(result);
			}
		};
	}

	/* =<<branch>> */

	// now we can add operations on the tree outside of it, in a client:
	public static class TreeClient {

		/* <<op_1>>= */
		public static <T> String toString(Tree<T> t) {
			// return an anonymous implementation of a visitor:
			return t.visit(new Tree.Visitor<T, String>() {
				public String leaf(T e) {
					return e.toString();
				}

				public String branch(List<String> children) {
					String result = "(";
					for (String c : children) {
						result += c + " ";
					}
					return result.trim() + ")";
				}
			});
		}

		/* =<<op_1>> */

		/* <<op_2>>= */
		public static <N extends Number> double sum(Tree<N> t) {
			// return an anonymous implementation of a visitor:
			return t.visit(new Tree.Visitor<N, Double>() {
				public Double leaf(N e) {
					return e.doubleValue();
				}

				public Double branch(List<Double> children) {
					Double res = 0.0;
					for (Double d : children) {
						res += d;
					}
					return res;
				}
			});
		}

		/* =<<op_2>> */

		/* <<main>>= */
		@SuppressWarnings( "unchecked" ) // for concise sample usage
        @Test public void main() {
			Tree<Integer> t = Tree.branch(Arrays.asList(Tree.branch(Arrays
					.asList(Tree.leaf(1), Tree.leaf(2))), Tree.leaf(3)));
			System.out.println(toString(t)); // --> ((1,2),3)
			System.out.println(sum(t)); // --> 6.0
		}
		/* =<<main>> */
	}

}