package src.main.java;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("add First with null");
        }
        Node newFirst = new Node(item);
        newFirst.next = first;
        first = newFirst;
        if (first.next != null) {
            first.next.previous = first;
        }
        else {
            // it's the first node in a deque
            last = first;
        }
        ++size;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("add Last with null");
        }
        Node newLast = new Node(item);
        newLast.previous = last;
        last = newLast;
        if (last.previous != null) {
            last.previous.next = last;
        }
        else {
            // it's the first node in a deque
            first = last;
        }
        ++size;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) {
            throw new NoSuchElementException("remove first on a empty deque");
        }
        Node previousFirst = first;
        first = previousFirst.next;
        if (first == null) {
            // the deque is empty
            last = null;
        }
        else {
            // clear the reference to removed node
            first.previous = null;
        }
        --size;
        return previousFirst.value;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException("remove last on a empty deque");
        }
        Node previousLast = last;
        last = previousLast.previous;
        if (last == null) {
            // the deque is empty
            first = null;
        }
        else {
            // clear the reference to removed node
            last.next = null;
        }
        --size;
        return previousLast.value;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        edu.princeton.cs.algs4.StdOut
                .printf("New deque with size - %s is empty - %s\n", deque.size(), deque.isEmpty());
        deque.addFirst("First");
        deque.addFirst("Second");
        deque.addLast("Third");
        edu.princeton.cs.algs4.StdOut
                .printf("Add 3 elements now size - %s, is empty - %s\n", deque.size(),
                        deque.isEmpty());
        Iterator<String> iterator = deque.iterator();
        edu.princeton.cs.algs4.StdOut
                .printf("Get iterator and has next is %s\n", iterator.hasNext());
        edu.princeton.cs.algs4.StdOut.printf("iterator next is %s\n", iterator.next());
        edu.princeton.cs.algs4.StdOut
                .printf("Remove first - %s now size %s\n", deque.removeFirst(), deque.size());
        edu.princeton.cs.algs4.StdOut
                .printf("Remove last - %s now size %s\n", deque.removeLast(), deque.size());
    }

    private class Node {
        private final Item value;
        private Node next;
        private Node previous;

        Node(Item nodeValue) {
            this.value = nodeValue;
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node next = first;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("next on when there is no next");
            }
            Item item = next.value;
            next = next.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove is forbidden!");
        }
    }
}
