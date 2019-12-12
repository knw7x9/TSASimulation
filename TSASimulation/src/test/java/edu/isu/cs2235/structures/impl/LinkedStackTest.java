package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Stack;
import edu.isu.cs2235.structures.LinkedStack;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Isaac Griffith
 */
public class LinkedStackTest {

    private Stack<Integer> fixture;

    public LinkedStackTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        fixture = new LinkedStack<>();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of push method, of class LinkedStack.
     */
    @Test
    public void testPush() {
        assertTrue(fixture.isEmpty());

        fixture.push(null);
        assertTrue("Push allows adding nulls", fixture.isEmpty());
    }

    @Test
    public void testPush_2() {
        fixture.push(0);
        assertEquals("Push not adding as first element", new Integer(0), fixture.peek());
    }

    @Test
    public void testPush_3() {
        fixture.push(0);
        fixture.push(1);
        assertEquals("Push not adding as first element", new Integer(1), fixture.peek());

    }

    /**
     * Test of peek method, of class LinkedStack.
     */
    @Test
    public void testPeek() {
        assertTrue(fixture.isEmpty());

        assertNull("Peek not returning null for an empty stack", fixture.peek());
    }

    @Test
    public void testPeek_2() {
        fixture.push(1);
        assertEquals("Peek not correctly getting value of first element", new Integer(1), fixture.peek());
        assertEquals("Size is incorrectly changed after a peek", 1, fixture.size());
    }

    @Test
    public void testPeek_3() {
        fixture.push(1);
        fixture.push(0);
        assertEquals("Peek not looking at first element", new Integer(0), fixture.peek());
        assertEquals("Size is incorrectly changed after a peek", 2, fixture.size());
    }

    /**
     * Test of pop method, of class LinkedStack.
     */
    @Test
    public void testPop() {
        assertTrue(fixture.isEmpty());

        assertNull("Null not returned when popping from an empty stack", fixture.pop());
    }

    @Test
    public void testPop_2() {
        fixture.push(0);
        assertEquals("not correctly popping the first element", new Integer(0), fixture.pop());
        assertTrue("size is not correctly updated after a pop", fixture.isEmpty());
    }

    @Test
    public void testPop_3() {
        fixture.push(1);
        fixture.push(2);
        assertEquals("Not correctly popping the first element", new Integer(2), fixture.pop());
        assertEquals("size is not correctly updated after a pop", 1, fixture.size());
    }

    /**
     * Test of size method, of class LinkedStack.
     */
    @Test
    public void testSize() {
        assertEquals("size not initialized to zero", 0, fixture.size());
    }

    @Test
    public void testSize_2() {
        fixture.push(1);
        assertEquals("Size not updated correctly on push", 1, fixture.size());
    }

    @Test
    public void testSize_3() {
        assertEquals(0, fixture.size());
        fixture.push(null);
        assertEquals("Size modified on push of null element", 0, fixture.size());
    }

    /**
     * Test of isEmpty method, of class LinkedStack.
     */
    @Test
    public void testIsEmpty() {
        assertTrue("Stack not initialized to empty state on creation", fixture.isEmpty());
    }

    @Test
    public void testIsEmpty_2() {
        fixture.push(null);
        assertTrue("Stack size updated after push of null", fixture.isEmpty());
    }

    @Test
    public void testIsEmpty_3() {
        fixture.push(0);
        assertFalse("Stack not updating size correctly", fixture.isEmpty());
    }

    /**
     * Test of transfer method, of class LinkedStack.
     */
    @Test
    public void testTransfer() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        Stack<Integer> other = new LinkedStack<>();
        fixture.transfer(other);
        assertTrue("Stack on which transfer is called should be empty after the transfer", fixture.isEmpty());
        assertFalse("Other stack should not be empty after transfer", other.isEmpty());

        for (int i = 1; i <= other.size(); i++) {
            assertEquals("Order in transfer to stack is not reversed", new Integer(i), other.pop());
        }
    }

    @Test
    public void testTransfer_2() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        Stack<Integer> other = null;
        fixture.transfer(other);
        assertFalse("Stack on which transfer was called is empty, when transfer to stack is null", fixture.isEmpty());
    }

    /**
     * Test of reverse method, of class LinkedStack.
     */
    @Test
    public void testReverse() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        assertFalse(fixture.isEmpty());
        fixture.reverse();
        assertFalse("Stack on which reverse is called was empty after reverse", fixture.isEmpty());

        for (int i = 1; i <= fixture.size(); i++) {
            assertEquals("Stack order not reversed after reverse call", new Integer(i), fixture.pop());
        }
    }

    /**
     * Test of merge method, of class LinkedStack.
     */
    @Test
    public void testMerge() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        Stack<Integer> other = new LinkedStack<>();
        for (int i = 1; i <= 10; i++) {
            other.push(i);
        }

        assertNotNull(other);
        assertFalse(other.isEmpty());
        fixture.merge(other);
        assertEquals("Stack size not correct after a merge", 20, fixture.size());
        for (int j = 1; j <= 2; j++) {
            for (int i = 10; i > 0; i--) {
                assertEquals("Stack does not correctly include merged components at its bottom, while preserving the correct order of both stacks", new Integer(i), fixture.pop());
            }
        }
    }

    @Test
    public void testMerge_2() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        fixture.merge(null);
        assertEquals("Stack size not preserved on a merge of a null stack", 10, fixture.size());
        for (int i = 10; i > 0; i--) {
            assertEquals("Stack original order not preserved on a merge of a null stack", new Integer(i), fixture.pop());
        }
    }

    @Test
    public void testMerge_3() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        fixture.merge(new LinkedStack<Integer>());
        assertEquals("Stack size not preserved on a merge with an empty stack", 10, fixture.size());
        for (int i = 10; i > 0; i--) {
            assertEquals("Stack order not preserved on a merge with an empty stack", new Integer(i), fixture.pop());
        }
    }

    @Test
    public void testMerge_4() {
        for (int i = 1; i <= 10; i++) {
            fixture.push(i);
        }

        Stack<Integer> other = new LinkedStack<>();
        for (int i = 1; i <= 10; i++) {
            other.push(i);
        }

        assertNotNull(other);
        assertFalse(other.isEmpty());
        fixture.merge(other);
        assertFalse("From stack still empty after merge.", other.isEmpty());
	for (int i = 10; i >= 1; i--)
	    assertEquals("Other stack order and contents not preserved after merge", new Integer(i), other.pop());
    }

    /**
     * Test of the printList method, for class SinglyLinkedList
     */
    @Test
    public void testPrintStack() {
        for (int i = 4; i >= 0; i--) {
            fixture.push(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        fixture.printStack();

        System.out.flush();
        System.setOut(old);

        String output = baos.toString();
        assertEquals("Did not print correctly", "0" + System.lineSeparator() + "1" + System.lineSeparator() + "2" + System.lineSeparator() + "3" + System.lineSeparator() + "4", output.trim());

        assertFalse("Stack is empty after printing", fixture.isEmpty());

    }

}
