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
            /*initial as null before, which violated variance when size = 1 */
            this.next = this;
            this.prev = this;
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

    public void addFirst(T item) {
        Node ptr = sentinel.next;
        sentinel.next = new Node(item);
        ptr.prev = sentinel.next;
        sentinel.next.next = ptr;
        sentinel.next.prev = sentinel;
        size += 1;
    }
    public void addLast(T item){
        Node ptr = sentinel.prev;
        sentinel.prev = new Node(item);
        ptr.next = sentinel.prev;
        sentinel.prev.next = sentinel;
        sentinel.prev.prev = ptr;
        size += 1;
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
        if (sentinel.next == sentinel){
            return null;
        }else{
            T t = sentinel.next.first;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return t;
        }
    }

    public T removeLast(){
        if (sentinel.next == sentinel){
            return null;
        }else{
            T t = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
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
        Node ptr = sentinel.next;
        return(helperGetRecursive(index, ptr));
    }
    /*in order not to modify sentinel*/
    public T helperGetRecursive(int index, Node nodes){
        if ((index > size - 1)||(index < 0)){
            return null;
        }else if(index == 0){
            return nodes.first;
        }else{
            return helperGetRecursive(index - 1, nodes.next);
        }
    }
}
