package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Deque;
import edu.isu.cs2235.structures.LinkedDeque;
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
public class LinkedDequeTest {

    private Deque<Integer> fixture;

    public LinkedDequeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        fixture = new LinkedDeque<>();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of peek method, of class LinkedDeque.
     */
    @Test
    public void testPeekLast() {
        assertTrue(fixture.isEmpty());

        assertNull("PeekLast not returning null for an empty deque", fixture.peekLast());
    }

    @Test
    public void testPeekLast_2() {
        fixture.offer(1);
        assertEquals("PeekLast not correctly getting value of Last element", new Integer(1), fixture.peekLast());
        assertEquals("Size is incorrectly changed after a peekLast", 1, fixture.size());
    }

    @Test
    public void testPeekLast_3() {
        fixture.offer(1);
        fixture.offer(0);
        assertEquals("PeekLast not looking at last element", new Integer(0), fixture.peekLast());
        assertEquals("Size is incorrectly changed after a peekLast", 2, fixture.size());
    }

    /**
     * Test of offer method, of class LinkedDeque.
     */
    @Test
    public void testOfferFirst() {
        assertTrue(fixture.isEmpty());

        fixture.offerFirst(null);
        assertTrue("OfferFirst allows adding nulls", fixture.isEmpty());
    }

    @Test
    public void testOfferFirst_2() {
        fixture.offerFirst(0);
        assertEquals("OfferFirst not adding as first element", new Integer(0), fixture.peek());
    }

    @Test
    public void testOfferFirst_3() {
        fixture.offerFirst(0);
        fixture.offerFirst(1);
        assertEquals("OfferFirst not adding as first element", new Integer(1), fixture.peek());

    }

    /**
     * Test of poll method, of class LinkedDeque.
     */
    @Test
    public void testPollLast() {
        assertTrue(fixture.isEmpty());

        assertNull("Null not returned when pollLasting from an empty deque", fixture.pollLast());
    }

    @Test
    public void testPollLast_2() {
        fixture.offer(0);
        assertEquals("not correctly polling the last element", new Integer(0), fixture.pollLast());
        assertTrue("size is not correctly updated after a pollLast", fixture.isEmpty());
    }

    @Test
    public void testPollLast_3() {
        fixture.offer(1);
        fixture.offer(2);
        assertEquals("Not correctly polling the last element", new Integer(2), fixture.pollLast());
        assertEquals("size is not correctly updated after a pollLast", 1, fixture.size());
    }

    /**
     * Test of size method, of class LinkedDeque.
     */
    @Test
    public void testSize() {
        assertEquals("size not initialized to zero", 0, fixture.size());
    }

    @Test
    public void testSize_2() {
        fixture.offer(1);
        assertEquals("Size not updated correctly on offer", 1, fixture.size());
    }

    @Test
    public void testSize_3() {
        assertEquals(0, fixture.size());
        fixture.offer(null);
        assertEquals("Size modified on offer of null element", 0, fixture.size());
    }

    /**
     * Test of isEmpty method, of class LinkedDeque.
     */
    @Test
    public void testIsEmpty() {
        assertTrue("Deque not initialized to empty state on creation", fixture.isEmpty());
    }

    @Test
    public void testIsEmpty_2() {
        fixture.offer(null);
        assertTrue("Deque size updated after offer of null", fixture.isEmpty());
    }

    @Test
    public void testIsEmpty_3() {
        fixture.offer(0);
        assertFalse("Deque not updating size correctly", fixture.isEmpty());
    }

    /**
     * Test of offer method, of class LinkedDeque.
     */
    @Test
    public void testOffer() {
        assertTrue(fixture.isEmpty());

        fixture.offer(null);
        assertTrue("Offer allows adding nulls", fixture.isEmpty());
    }

    @Test
    public void testOffer_2() {
        fixture.offer(0);
        assertEquals("Offer not adding as first element", new Integer(0), fixture.peek());
    }

    @Test
    public void testOffer_3() {
        fixture.offer(0);
        fixture.offer(1);
        assertEquals("Offer not adding as first element", new Integer(0), fixture.peek());

    }

    /**
     * Test of peek method, of class LinkedDeque.
     */
    @Test
    public void testPeek() {
        assertTrue(fixture.isEmpty());

        assertNull("Peek not returning null for an empty deque", fixture.peek());
    }

    @Test
    public void testPeek_2() {
        fixture.offer(1);
        assertEquals("Peek not correctly getting value of first element", new Integer(1), fixture.peek());
        assertEquals("Size is incorrectly changed after a peek", 1, fixture.size());
    }

    @Test
    public void testPeek_3() {
        fixture.offer(1);
        fixture.offer(0);
        assertEquals("Peek not looking at first element", new Integer(1), fixture.peek());
        assertEquals("Size is incorrectly changed after a peek", 2, fixture.size());
    }

    /**
     * Test of poll method, of class LinkedDeque.
     */
    @Test
    public void testPoll() {
        assertTrue(fixture.isEmpty());

        assertNull("Null not returned when polling from an empty deque", fixture.poll());
    }

    @Test
    public void testPoll_2() {
        fixture.offer(0);
        assertEquals("not correctly polling the first element", new Integer(0), fixture.poll());
        assertTrue("size is not correctly updated after a poll", fixture.isEmpty());
    }

    @Test
    public void testPoll_3() {
        fixture.offer(1);
        fixture.offer(2);
        assertEquals("Not correctly polling the first element", new Integer(1), fixture.poll());
        assertEquals("size is not correctly updated after a poll", 1, fixture.size());
    }

    /**
     * Test of transfer method, of class LinkedDeque.
     */
    @Test
    public void testTransfer() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        Deque<Integer> other = new LinkedDeque<>();
        fixture.transfer(other);
        assertTrue("Deque on which transfer is called should be empty after the transfer", fixture.isEmpty());
        assertFalse("Other deque should not be empty after transfer", other.isEmpty());

        for (int i = other.size(); i >= 1; i--) {
            assertEquals("Order in transfer to deque is not reversed", new Integer(i), other.poll());
        }
    }

    @Test
    public void testTransfer_2() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        Deque<Integer> other = null;
        fixture.transfer(other);
        assertFalse("Deque on which transfer was called is empty, when transfer to deque is null", fixture.isEmpty());
    }

    /**
     * Test of reverse method, of class LinkedDeque.
     */
    @Test
    public void testReverse() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        assertFalse(fixture.isEmpty());
        fixture.reverse();
        assertFalse("Deque on which reverse is called was empty after reverse", fixture.isEmpty());

        for (int i = fixture.size(); i >= 1; i--) {
            assertEquals("Deque order not reversed after reverse call", new Integer(i), fixture.poll());
        }
    }

    /**
     * Test of merge method, of class LinkedDeque.
     */
    @Test
    public void testMerge() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        Deque<Integer> other = new LinkedDeque<>();
        for (int i = 1; i <= 10; i++) {
            other.offer(i);
        }

        assertNotNull(other);
        assertFalse(other.isEmpty());
        fixture.merge(other);
        assertEquals("Deque size not correct after a merge", 20, fixture.size());
        for (int j = 1; j <= 2; j++) {
            for (int i = 1; i <= 10; i++) {
                assertEquals("Deque does not correctly include merged components at its bottom, while preserving the correct order of both deques", new Integer(i), fixture.poll());
            }
        }
    }

    @Test
    public void testMerge_2() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        fixture.merge(null);
        assertEquals("Deque size not preserved on a merge of a null deque", 10, fixture.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals("Deque original order not preserved on a merge of a null deque", new Integer(i), fixture.poll());
        }
    }

    @Test
    public void testMerge_3() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        fixture.merge(new LinkedDeque<Integer>());
        assertEquals("Deque size not preserved on a merge with an empty deque", 10, fixture.size());
        for (int i = 1; i <= 10; i++) {
            assertEquals("Deque order not preserved on a merge with an empty deque", new Integer(i), fixture.poll());
        }
    }

    @Test
    public void testMerge_4() {
        for (int i = 1; i <= 10; i++) {
            fixture.offer(i);
        }

        Deque<Integer> other = new LinkedDeque<>();
        for (int i = 1; i <= 10; i++) {
            other.offer(i);
        }

        assertNotNull(other);
        assertFalse(other.isEmpty());
        fixture.merge(other);
        assertFalse("Merge empties from queue", other.isEmpty());
	for (int i = 1; i <= 10; i++)
	    assertEquals("Other queue order not restored", new Integer(i), other.poll());
    }

    /**
     * Test of the printList method, for class SinglyLinkedList
     */
    @Test
    public void testPrintDeque() {
        for (int i = 0; i < 5; i++) {
            fixture.offer(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);

        fixture.printQueue();

        System.out.flush();
        System.setOut(old);

        String output = baos.toString();
        assertEquals("Did not print correctly", "0" + System.lineSeparator() + "1" + System.lineSeparator() + "2" + System.lineSeparator() + "3" + System.lineSeparator() + "4", output.trim());

        assertFalse("Deque is empty after printing", fixture.isEmpty());

    }

}
