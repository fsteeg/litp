package com.quui.data_structures.binary_tree;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

/**
 * A simple, pre-Generics binary search tree, implemented as an object-oriented, recursive
 * data structure in Java, for objects that extend Comparable. Note that Java
 * already provides efficient tree-based data structures, e.g. TreeSet.
 */

/**
 * A node in a binary tree has three attributes: left, right (both nodes again)
 * and value. The tree has one root node. This describes the binary tree data
 * structure, as represented in the UML class diagram below.
 */
class Node {

    /**
     * We have a constructor for an empty node with a value and a copy
     * constructor for creating a copy of a node.
     */

    public Node left;

    public Node right;

    public Comparable value;

    Node(Comparable value) {
        this.value = value;
    }

    Node(Node node) {
        this.left = node.left;
        this.right = node.right;
        this.value = node.value;
    }
}

public class BinaryTree {
    /**
     * We create a tree and add a few strings. We check if it's sorted and if
     * searching works.
     */
    @Test
    public void testTree() {
        BinaryTree tree = new BinaryTree();
        tree.add("Hanno");
        tree.add("Zacharias");
        tree.add("Berhard");
        Assert.assertArrayEquals(new Comparable[] { "Berhard", "Hanno", "Zacharias" }, tree
                .toArray());
        assertEquals(null, tree.get("Otto"));
        assertEquals("Hanno", tree.get("Hanno"));
    }

    Node root;

    int size = 0;

    int i;

    /**
     * Add an element into the tree. If the element is in the tree already, it
     * is not added. Searches the proper place to insert at recursivley.
     */
    public void add(Comparable element) {
        if (this.root == null && element != null) {
            this.root = new Node(element);
            this.size++;
        } else if (element != null) {
            this.root = insert(this.root, element);
        }
    }

    /**
     * The recursive insertion method. Takes two parameters: the node to search
     * from and the element to add. Returns the node including the inserted new
     * element.
     */
    private Node insert(Node node, Comparable value) {
        Node result = new Node(node);
        int compare = result.value.compareTo(value);
        /**
         * Value already in the tree, insert nothing (here, for a more
         * sophisticated tree, we could add the new element somewhere, count
         * frequencies etc).
         */
        if (compare == 0) {
            return result;
        }
        /** New value needs to be inserted in the left subtree: */
        if (compare > 0) {
            if (result.left != null) {
                result.left = insert(result.left, value);
            } else {
                result.left = new Node(value);
                this.size++;
            }
        }
        /** New value needs to be inserted in the right subtree: */
        else if (compare < 0) {
            if (result.right != null) {
                result.right = insert(result.right, value);
            } else {
                result.right = new Node(value);
                this.size++;
            }
        }

        return result;
    }

    /**
     * Return the value for key or null if the key is not in the tree.
     */
    public Comparable get(Comparable key) {
        if (this.root == null)
            return null;
        else
            return find(this.root, key);
    }

    /**
     * Recursively searches a value in the tree starting from a given node,
     * either in the ledft or in the right subtree. Returns the object found.
     * 
     */
    private Comparable find(Node node, Comparable searchValue) {
        int compareResult;
        Comparable result = null;
        if ((compareResult = node.value.compareTo(searchValue)) == 0) {
            return node.value;
        } else if (compareResult > 0) {
            if (node.left != null)
                return find(node.left, searchValue);
        } else {
            if (node.right != null)
                return find(node.right, searchValue);
        }
        return result;
    }

    /**
     * Creates an array from the tree and returns it.
     */
    public Comparable[] toArray() {
        i = 0;
        Comparable[] result = new Comparable[this.size];
        if (this.root != null)
            treeToArray(this.root, result);
        return result;
    }

    /**
     * Recursive preorder traversal of the tree, saving the values to the array.
     */
    private void treeToArray(Node node, Comparable[] goal) {
        if (node.left != null)
            treeToArray(node.left, goal);
        goal[i] = node.value;
        if (i == goal.length - 1)
            return;
        i++;
        if (node.right != null)
            treeToArray(node.right, goal);
        return;
    }
}