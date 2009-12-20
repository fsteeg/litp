package com.quui.data_structures.stack;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple [http://en.wikipedia.org/wiki/Stack_%28data_structure%29 Stack] in
 * Java, implemented as an object-oriented, recursive data structure, a
 * [http://en.wikipedia.org/wiki/Linked_list#Singly-linked_list singly linked
 * list]. Note that Java already provides a Stack class.
 * 
 * The Stack consists of two classes: the stack, which has a head element and
 * the element, which has a next element, visualized by the following UML class
 * diagram.
 * 
 * An element has a value and a next element and is constructed with a value:
 */
class Element {
    Object value;

    Element next;

    Element(Object value) {
        this.value = value;
    }
}

/**
 * The stack has a head element and three methods: push, for adding to the
 * stack, pop, for retrieving and removing items and empty to test if the stack
 * is empty.
 */
public class Stack {
    Element head;

    /**
     * Push: add an element at the beginning (prepend), by setting the head to
     * the new element and the next element to the old head:
     */
    public void push(Object element) {
        Element newElement = new Element(element);
        if (this.empty()) {
            this.head = newElement;
            this.head.next = null;
        } else {
            newElement.next = this.head;
            this.head = newElement;
        }
    }

    /**
     * Pop: return the reference to the first element in the stack, removing it
     * from the stack by setting the head to the next element:
     */
    public Object pop() {
        Object result = null;
        if (!this.empty()) {
            result = this.head.value;
            this.head = this.head.next;
        }
        return result;
    }

    /**
     * Empty: return true if the stack is empty, indicated by an empty head.
     */
    public boolean empty() {
        if (this.head == null)
            return true;
        else
            return false;
    }

    /**
     * Sample usage: push some elements onto the stack. Pop them from the stack
     * while it isn't empty. The effect of this is that the order is reversed
     * (LIFO).
     */
    @Test
    public void testStack() {

        Stack stack = new Stack();
        List<String> elements = Arrays.asList("first", "second", "third","fourth");
        for (String e : elements) {
            stack.push(e);
        }
        List<Object> result = new ArrayList<Object>();
        while (!stack.empty()) {
            result.add(stack.pop());
        }
        assertEquals(Arrays.asList("fourth", "third", "second", "first"),result);

    }
    /**
     * [[Category:Stack|Java]] [[Category:Singly linked list|Java]]
     * [[Category:Programming language:Java]] [[Category:Environment:Portable]]
     * 
     * 
     * 
     */
}