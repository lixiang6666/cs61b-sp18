// TODO: Make sure to make this class a part of the synthesizer package
package synthesizer;
import synthesizer.AbstractBoundedQueue;

import javax.swing.text.html.HTMLDocument;
import java.sql.Array;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */

    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCourt = 0;
        rb = (T[]) new Object[capacity];
    }

    private class ArbIterator implements Iterator<T>{
        private int ptr;
        public ArbIterator(){
            ptr = first;
        }
        @Override
        public boolean hasNext(){
            return (ptr != last);
        }
        @Override
        public T next(){
            T returnItem = rb[ptr];
            if (ptr == capacity - 1){
                ptr = 0;
            }else{
                ptr += 1;
            }
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator(){
        return new ArbIterator();
    }
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */

    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (fillCourt == capacity){
            throw new RuntimeException("Ring buffer overflow");
        }else{
            if(last == capacity - 1){
                last = 0;
            }else if(fillCourt == 0){
                last = 0;
            }else{
                last += 1;
            }
            rb[last] = x;
            fillCourt += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (fillCourt == 0){
            throw new RuntimeException("Ring buffer underflow");
        }else{
            T item = rb[first];
            if(first == capacity - 1){
                first = 0;
            }else{
                first += 1;
            }
            fillCourt -= 1;
            return item;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
