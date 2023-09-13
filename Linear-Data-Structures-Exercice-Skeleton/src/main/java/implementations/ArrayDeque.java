package implementations;

import interfaces.Deque;

import java.util.Iterator;

public class ArrayDeque<E> implements Deque<E> {
    private final int DEFAULT_CAPACITY = 7;
    private int head;
    private int tail;
    private int size;
    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];
        int middle = DEFAULT_CAPACITY / 2;
        this.tail = this.head = middle;
    }


    @Override
    public void add(E Element) {
        addLast(Element);
    }

    private Object[] grow() {
        int newSize = this.elements.length * 2 + 1;

        Object[] newElements = new Object[newSize];
        int middle = newSize / 2;

        int begin = middle = this.size / 2;
        int index = this.head;
        for (int i = begin; index <= this.tail; i++) {
            newElements[i] = this.elements[index++];
        }
        this.head = begin;
        this.tail = this.head + this.size - 1;

        return newElements;
    }

    @Override
    public void offer(E element) {

        addLast(element);
    }

    @Override
    public void addFirst(E element) {

        if (this.size == 0) {
            this.addLast(element);
        } else {
            if (this.head == 0) {
                this.elements = grow();
            }
            this.elements[--this.head] = element;
            this.size++;
        }

    }

    @Override
    public void addLast(E element) {
        if (this.size == 0) {
            this.elements[this.tail] = element;
        } else {
            if (this.tail == this.elements.length - 1) {
                this.elements = grow();
            }
            this.elements[++this.tail] = element;
        }
        this.size++;
    }

    @Override
    public void push(E element) {
        addFirst(element);

    }

    @Override
    public void insert(int index, E element) {
        int realIndex = this.head + index ;
        this.ensureIndex(realIndex);
        if (realIndex - this.head<this.tail - realIndex){
            insertAndShiftLeft(realIndex,element);
        }else {
            insertAndShiftRight(realIndex,element);
        }


    }

    private void insertAndShiftRight(int index,E element) {
        E lastElement = this.getAt(this.tail);
        for (int i = this.tail; i > index; i--) {
            this.elements[i] = this.elements[i-1];
        }
        this.elements[index] = element;
        this.addLast(lastElement);
    }

    private void insertAndShiftLeft(int index, E element) {
        E firstElement = this.getAt(this.head);
        for (int i = this.head; i < index; i++) {
            this.elements[i] = this.elements[i+1];
        }
        this.elements[index] = element;
        this.addFirst(firstElement);
    }

    @Override
    public void set(int index, E element) {

    }

    @Override
    public E peek() {
        if (this.size != 0) {
            return this.getAt(this.head);


        }
        return null;
    }

    @SuppressWarnings("unckecked")
    private E getAt(int index) {
        return (E) this.elements[this.head];
    }

    @Override
    public E poll() {

        return removeFirst();
    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public E get(int index) {
        int realIndex = this.head + index;
        this.ensureIndex(realIndex);

        return this.getAt(realIndex);
    }

    @Override
    public E get(Object object) {

        for (int i = this.head; i <= this.tail; i++) {
            if (this.elements[i].equals(object)) {
                return this.getAt(i);
            }
        }
        return null;
    }

    @Override
    public E remove(int index) {
        int realIndex = this.head + index;
        ensureIndex(realIndex);

        return null;
    }

    private void ensureIndex(int realIndex) {
        if (realIndex < this.head || realIndex > this.tail) {
            throw new IndexOutOfBoundsException("Index out of bounds for index: " + (realIndex - this.head));
        }
    }

    @Override
    public E remove(Object object) {
        return null;
    }

    @Override
    public E removeFirst() {
        if (!isEmpty()) {
            E element = this.getAt(this.head);
            this.elements[this.head] = null;
            this.head++;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public E removeLast() {
        if (!isEmpty()) {
            E element = this.getAt(this.tail);
            this.elements[this.tail--] = null;
            this.size--;
            return element;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {

    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = head;

            @Override
            public boolean hasNext() {
                return index != tail;
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
