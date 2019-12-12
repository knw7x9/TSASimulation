package edu.isu.cs2235.structures;

/**
 * An interface for a double-ended Queue ADT
 *
 * @author Isaac Griffith
 * @param <E> The type of element stored in this Deque
 */
public interface Deque<E> extends Queue<E> {

    /**
     * @return The value of the last element of the deque (without removing it),
     * or null if the deque is empty.
     */
    E peekLast();

    /**
     * Inserts the given element into the front of the deque, unless the
     * provided value is null.
     *
     * @param element Element to be inserted to the front of the deque, nothing
     * happens if the value is null.
     */
    void offerFirst(E element);

    /**
     * @return The value of the last item in the Deque and removes that value
     * from the deque, if the deque was empty null is returned.
     */
    E pollLast();
}
