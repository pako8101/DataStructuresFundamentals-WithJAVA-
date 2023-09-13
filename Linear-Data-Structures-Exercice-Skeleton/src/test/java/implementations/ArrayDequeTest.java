package implementations;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testArrayDeque(){
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.offer(13);
        deque.offer(14);

        System.out.println(deque.poll());

    }

}