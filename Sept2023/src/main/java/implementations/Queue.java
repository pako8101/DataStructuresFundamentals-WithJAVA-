package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;

public class Queue<E> implements AbstractQueue<E> {
    private Queue.Node<E> head;
    private int size;

    private static class Node<E> {
        private E value;
        private Queue.Node<E> next;

        Node(E element) {
            this.value = element;
        }
    }

    public Queue() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public void offer(E element) {

        Queue.Node<E> toInsert = new Node<>(element);
        if (this.isEmpty()) {
            this.head = toInsert;
            this.size++;
            return;
        }
        Node<E> current = this.head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = toInsert;
        this.size++;
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        Node<E> first = this.head;
        this.head = first.next;

        this.size--;

        return first.value;
    }

    private void ensureNonEmpty() {
        if (this.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.head.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Queue.Node<E> current = head;

            @Override
            public boolean hasNext() {

                return current != null;
            }

            @Override
            public E next() {
               E value = current.value;
                current = current.next;
                return value;
            }
        };
    }
}
