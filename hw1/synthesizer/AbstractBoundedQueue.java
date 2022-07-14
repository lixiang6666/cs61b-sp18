package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCourt;
    protected int capacity;
    public int capacity(){
        return capacity;
    }
    public int fillCourt(){
        return fillCourt;
    }

}
