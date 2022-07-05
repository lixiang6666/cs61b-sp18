import java.awt.*;

public class LinkedListDeque<T> {
    /**22.7.4  doubly linked list
     *CS61B projest 1 a
     * writen by lx*/
    private class List{
        public T first;
        public List rest;
        public List before;

        public List(T first, List rest, List before) {
            this.first = first;
            this.rest = rest;
            this.before = before;
        }
        public List(T first){
            this.first = first;
            this.rest = null;
            this.before = null;
        }
    }
    private List sentinel;
    private int size;

    public LinkedListDeque(){
        size = 0;
        sentinel = new List(null);
    }

    public LinkedListDeque(LinkedListDeque other){
        this.size = other.size;
        this.sentinel = new List(null);
        List ptr = other.sentinel;
        for (int i = 0; i < size; i++){
            this.addFirst(ptr.rest.first);
            ptr = ptr.rest;
        }
    }

    public void addFirst(T item){
        sentinel.rest = new List(item, sentinel.rest, sentinel);
        size = size + 1;
    }

    public void addLast(T item){
        sentinel.before = new List(item, sentinel, sentinel.before);
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
        List ptr = sentinel;
        for (int i = 0; i < size; i++){
            System.out.print(ptr.rest.first + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        if (sentinel.rest == null){
            return null;
        }else{
            T t = sentinel.rest.first;
            sentinel.rest = sentinel.rest.rest;
            return t;
        }
    }

    public T removeLast(){
        if (sentinel.before == null){
            return null;
        }else{
            T t = sentinel.before.first;
            sentinel.before = sentinel.before.before;
            return t;
        }
    }

    public T get(int index){
        if (index > size - 1){
            return null;
        }else{
            List ptr = sentinel.rest;
            while(index > 0){
                ptr = ptr.rest;
                index -= 1;
            }
            return ptr.first;
        }
    }
}
