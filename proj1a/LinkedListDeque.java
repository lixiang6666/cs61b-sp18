import java.awt.*;

public class LinkedListDeque<T> {
    /**22.7.4  doubly linked Node
     *CS61B projest 1 a
     * writen by lx*/
    private class Node{
        public T first;
        public Node next;
        public Node prev;
        public Node(T first){
            this.first = first;
            this.next = null;
            this.prev = null;
        }
    }
    private Node sentinel;
    private int size;

    public LinkedListDeque(){
        size = 0;
        sentinel = new Node(null);
    }

    public LinkedListDeque(LinkedListDeque other){
        this.size = other.size;
        this.sentinel = new Node(null);
        for (int i = 0; i < size; i++){
            this.addLast((T) other.get(i));
        }
    }

    public void addFirst(T item){
        Node ptrNext = sentinel.next;
        sentinel.next = new Node(item);
        sentinel.next.next = ptrNext;
        sentinel.next.prev = sentinel;
        size = size + 1;
    }

    public void addLast(T item){
        Node ptrPrev = sentinel.prev;
        sentinel.prev = new Node(item);
        sentinel.prev.next = sentinel;
        sentinel.prev.prev = ptrPrev;
        size = size + 1;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else {
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node ptr = sentinel;
        for (int i = 0; i < size; i++){
            System.out.print(ptr.next.first + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if (sentinel.next == null){
            return null;
        }else{
            T t = sentinel.next.first;
            sentinel.next = sentinel.next.next;
            size -= 1;
            return t;
        }
    }

    public T removeLast(){
        if (sentinel.next == null){
            return null;
        }else{
            T t = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
            return t;
        }
    }

    public T get(int index){
        if (index > size - 1){
            return null;
        }else{
            Node ptr = sentinel.next;
            while(index > 0){
                ptr = ptr.next;
                index -= 1;
            }
            return ptr.first;
        }
    }

    public T getRecursive(int index){
        if (index > size - 1){
            return null;
        }else if(index  == 0){
            return sentinel.next.first;
        }else{
            LinkedListDeque ptr = new LinkedListDeque(this);
            ptr.removeFirst();
            index -= 1;
            return (T)ptr.getRecursive(index);
        }
    }
}
