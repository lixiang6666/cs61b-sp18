package synthesizer;

public interface BoundedQueue <T> extends Iterable<T>{
    int capacity();
    int fillCourt();
    void enqueue(T x);
    T dequeue();
    T peek();

    default boolean isEmpty(){
        return fillCourt() == 0;
    }

    default boolean isFull(){
        return capacity() == fillCourt();
    }
}
