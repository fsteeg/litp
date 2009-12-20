package com.quui.data_structures.suffix_tree.stripped;

/* <<imports>>= */
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

/* =<<import>> */

/**
 * A simple, intuitive algorithm for constructing a suffix tree by first
 * constructing a simple suffix tree (a suffix trie), which is then transformed
 * to a suffix tree, with a runtime complexity of O(n^2). For construction of
 * suffix trees in linear runtime see e.g. the Ukkonen algorithm. This algorithm
 * is implemented as an object oriented, recursive data structure, as
 * illustrated by the UML diagram.
 */

/* <<sample_usage>>= */
/**
 * Sample usage for the suffix tree: Create a compact suffix tree for an input
 * text and export it as a text usable with GraphViz, using a few attributes to
 * style the generated graph. See the image for the result when rendered with
 * GraphViz.
 */
public class SuffixTree {
    @Test
    public void sampleUsage() {
        CompactSuffixTree tree = new CompactSuffixTree(new SimpleSuffixTree(
                "bananas"));
        String properties = "rankdir=LR; node[shape=box fillcolor=gray95 style=filled]\n";
        System.out.println("digraph {\n" + properties + tree.root + "}");
    }
}

/* =<<sample_usage>> */
class CompactSuffixTree extends AbstractSuffixTree {
    /* <<constructor_compact>>= */
    /**
     * Both trees represent the same text (superclass), but this one has compact
     * nodes but no compact labels, for illustrative reasons. Actually, a suffix
     * tree should contain indices as labels.
     * 
     * @param simpleSuffixTree:
     *            The simple suffix tree that should be made compact.
     * @param verbose:
     *            Flag to indicate if verbose output should be displayed.
     */
    public CompactSuffixTree(SimpleSuffixTree simpleSuffixTree) {
        super(simpleSuffixTree.text);
        super.root = compactNodes(simpleSuffixTree.root, 0);
    }

    /* =<<constructor_compact>> */
    /* <<compact_notes>>= */
    /**
     * Makes a simple suffix tree compact by removing inner nodes with exactly
     * one child node.
     * 
     * @param node:
     *            The root node of the simple suffix tree to make compact.
     * @param nodeDepth:
     *            The current node depth.
     * @return The root node of the compact suffix tree.
     * 
     * adjust the node depth while making the tree compact
     * 
     * remove all inner nodes with exactly one child node * set the new longer
     * label * set the new string depth * skip the grandchild by setting the
     * grandchild's children as the child's children
     * 
     * for the others, continue
     * 
     */
    private SuffixTreeNode compactNodes(SuffixTreeNode node, int nodeDepth) {
        node.nodeDepth = nodeDepth;
        for (SuffixTreeNode child : node.children) {
            while (child.children.size() == 1) {
                SuffixTreeNode grandchild = child.children.iterator().next();
                child.incomingEdge.label = child.incomingEdge.label + ", "
                        + grandchild.incomingEdge.label;
                child.stringDepth = child.stringDepth
                        + grandchild.incomingEdge.label.length();
                child.children = grandchild.children;
            }
            child = compactNodes(child, nodeDepth + 1);
        }
        return node;
    }
    /* =<<compact_notes>> */
}

/**
 * An abstract superclass for the simple suffix trie and the compact suffix
 * tree. Has a root node, which recursively contains children nodes.
 */
abstract class AbstractSuffixTree {
    /* <<attributes_abstract>>= */
    /**
     * The attributes of the AbstractSuffixTree: the text represented by this
     * tree, the root node of this tree and the size of the input alphabet.
     */
    String text = null;

    SuffixTreeNode root = null;

    int inputAlphabetSize = -1;

    /* =<<attributes_abstract>> */

    /* <<constructor_abstract>>= */
    /**
     * Constructor for the AbstractSuffixTree, with one param, text: The text to
     * be represented by this tree, a terminating "$" is appended if not already
     * present.
     */
    AbstractSuffixTree(String text) {
        if ((text.length() > 0 && text.charAt(text.length() - 1) == '$')) {
            this.text = text;
        } else {
            this.text = text + "$";
        }
    }

    /* =<<constructor_abstract>> */

}

/**
 * Construction of a simple suffix trie. The trie is later made compact and
 * thereby transformed to a suffix tree.
 */
class SimpleSuffixTree extends AbstractSuffixTree {
    /* <<constructor_simple>>= */
    /**
     * A simple suffix trie ist constructed with one param, text: the text to be
     * represented by the suffix tree, a terminating "$" is appended if not
     * already present.
     */
    public SimpleSuffixTree(String text) {
        super(text);
        constructTree();
    }

    /* =<<constructor_simple>> */

    /* <<construct_tree>>= */
    /**
     * Create the root node and insert all suffixes into this tree.
     */
    private void constructTree() {
        super.root = new SuffixTreeNode();
        int pathCounter = 0;
        char[] s = super.text.toCharArray();
        for (int i = 0; i < s.length; i++) {
            List<String> suffixList = new ArrayList<String>();
            for (int k = i; k < s.length; k++) {
                suffixList.add(s[k] + "");
            }
            pathCounter++;
            super.root.addSuffix(suffixList, pathCounter);
        }

    }
    /* =<<construct_tree>> */
}

class SuffixTreeNode {
    /* <<attributes_node>>= */
    /**
     * The incoming edge of this node. Every node in a tree has one incoming
     * edge, except for the root, which has none.
     * 
     * The depth of this node, that is, how many edges are on a path from the
     * root node to this node (not the string depth).
     * 
     * The label of this node. Currently for leafs only.
     * 
     * The collection of nodes, the immediate children of this node.
     * 
     * The parent node of this node.
     * 
     * The string depth of this node.
     * 
     * Attributes for saving the tree as dot.
     */
    SuffixTreeEdge incomingEdge = null;

    int nodeDepth = -1;

    int label = -1;

    Collection<SuffixTreeNode> children = null;

    SuffixTreeNode parent = null;

    int stringDepth;

    int id = 0;

    public static int c;

    /* =<<attributes_abstract>> */

    /* <<constructor_node>>= */
    /**
     * Constructor for a node with a parent, that is, for any node except the
     * root node.
     * 
     * @param parent:
     *            The parent node of this node.
     * @param incomingLabel:
     *            The label for the incoming edge of this node.
     * @param depth:
     *            The node depth to be assigned to this node.
     * @param label:
     *            The label for this node (Nodes are currently labeled with leaf
     *            numbers).
     */
    public SuffixTreeNode(SuffixTreeNode parent, String incomingLabel,
            int depth, int label, int id) {
        children = new ArrayList<SuffixTreeNode>();
        incomingEdge = new SuffixTreeEdge(incomingLabel, label);
        this.nodeDepth = depth;
        this.label = label;
        this.parent = parent;
        this.stringDepth = parent.stringDepth + incomingLabel.length();
        this.id = id;
    }

    /**
     * Empty constructor, for the root node.
     */
    public SuffixTreeNode() {
        children = new ArrayList<SuffixTreeNode>();
        nodeDepth = 0;
        this.label = 0;
    }

    /* =<<constructor_node>> */
    /* <<add_suffix>>= */
    /**
     * @param suffix:
     *            The suffix to insert into the suffix tree, will be inserted
     *            behind the maximum prefix of the suffix found in the tree.
     * @param pathIndex:
     *            The path index for labeling the leaf at the end of the path of
     *            the suffix added.
     * 
     * recursivly find the node to insert at
     * 
     * insert new nodes
     * 
     * 
     */
    public void addSuffix(List<String> suffix, int pathIndex) {
        SuffixTreeNode insertAt = this;
        insertAt = search(this, suffix);
        insert(insertAt, suffix, pathIndex);
    }

    /* =<<add_suffix>> */

    /* <<search>>= */
    /**
     * @param startNode:
     *            The node in which to start the search.
     * @param suffix:
     *            The suffix that is intended to be inserted into the tree.
     * @return Returns the node in which to enter the suffix, that is the node
     *         in which the path to the maximum prefix of the new suffix ends.
     * 
     * The suffix size should never be 0, as a terminating "$" is always
     * appended to the text, but if that wasn't the case, and the text would be
     * something like "dacdac", and right where the Exception is thrown we would
     * return the startNode, we would have an invalid suffix tree, where one
     * path would end in an inner node.
     */
    private SuffixTreeNode search(SuffixTreeNode startNode, List<String> suffix) {
        if (suffix.size() == 0) {
            throw new IllegalArgumentException(
                    "Empty suffix. Probably no valid simple suffix tree exists for the input.");
        }
        Collection<SuffixTreeNode> children = startNode.children;
        for (SuffixTreeNode child : children) {
            if (child.incomingEdge.label.equals(suffix.get(0))) {
                suffix.remove(0);
                if (suffix.isEmpty()) {
                    return child;
                }
                return search(child, suffix);
            }
        }
        return startNode;
    }

    /* =<<search>> */
    /* <<insert>>= */
    /**
     * 
     * Skip the maximum prefix that has already been found, for every remaining
     * char, enter a node into the tree.
     * 
     * @param insertAt:
     *            The node into which the suffix should be entered.
     * @param suffix:
     *            The suffix to enter into the node (not all will be used, see
     *            inline comment).
     * @param pathIndex:
     *            the path index (for labels of leafs).
     */
    private void insert(SuffixTreeNode insertAt, List<String> suffix,
            int pathIndex) {
        for (int j = 0; j < suffix.size(); j++) {
            SuffixTreeNode child = new SuffixTreeNode(insertAt, suffix.get(j)
                    + "", insertAt.nodeDepth + 1, pathIndex, id);
            insertAt.children.add(child);
            insertAt = child;
        }
    }

    /* =<<insert>> */
    /* <<dot_export>>= */

    /**
     * Recursively exports the node and all children as dot, using indentation
     * to visualize node depth in the text file.
     * 
     * @return Returns the tree as a dot string (www.graphviz.org), the body
     *         only (see sample usage above).
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        String incomingLabel = this.isRoot() ? "" : this.incomingEdge.label;
        for (int i = 1; i <= this.nodeDepth; i++)
            result.append("\t");
        if (this.isRoot()) {
            c = 1;
            this.id = 1;
        } else {
            this.id = c;
            result.append(this.parent.id + " -> ");
            result.append(this.id + "[label=\"" + incomingLabel + "\"];\n");
        }
        for (SuffixTreeNode child : children) {
            child.parent.id = this.id;
            c++;
            child.id = c;
            result.append(child.toString());
        }
        return result.toString();
    }

    /* =<<dot_export>> */
    /* <<helper_methods>>= */
    /**
     * @return true if this node is the root node.
     * @return true if this node is a leaf.
     */
    public boolean isRoot() {
        /** the root node has no parent */
        return this.parent == null;
    }

    public boolean isLeaf() {
        /** a leaf node has no children */
        return children.size() == 0;
    }
    /* =<<helper_methods>> */

}

/* <<edge>>= */
class SuffixTreeEdge {

    /**
     * The label of this edge.
     * 
     * The index where the branch this label belongs to starts in the text
     * represented by the tree. That is the number that the leaf at the end of
     * this branch will be labeled with (the index in the immediate children of
     * the tree's root node).
     * 
     * @param label:
     *            The label for this edge.
     * @param branchIndex:
     *            The index where the branch this label belongs to starts in the
     *            text represented by the tree.
     */
    String label = null;

    @SuppressWarnings("unused")
    private int branchIndex = -1;

    public SuffixTreeEdge(String label, int branchIndex) {
        this.label = label;
        this.branchIndex = branchIndex;
    }

}
/* =<<edge>> */