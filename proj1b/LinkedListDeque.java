public class LinkedListDeque<Item> implements Deque<Item>{
    /**22.7.4  doubly linked Node
     *CS61B projest 1
     * writen by lx*/
    private class Node{
        public Item first;
        public Node next;
        public Node prev;
        public Node(Item first){
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
            this.addLast((Item) other.get(i));
        }
    }
    @Override
    public void addFirst(Item item) {
        Node ptr = sentinel.next;
        sentinel.next = new Node(item);
        ptr.prev = sentinel.next;
        sentinel.next.next = ptr;
        sentinel.next.prev = sentinel;
        size += 1;
    }
    @Override
    public void addLast(Item item){
        Node ptr = sentinel.prev;
        sentinel.prev = new Node(item);
        ptr.next = sentinel.prev;
        sentinel.prev.next = sentinel;
        sentinel.prev.prev = ptr;
        size += 1;
    }

    @Override
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public void printDeque(){
        Node ptr = sentinel;
        for (int i = 0; i < size; i++){
            System.out.print(ptr.next.first + " ");
        }
        System.out.println();
    }

    @Override
    public Item removeFirst(){
        if (sentinel.next == sentinel){
            return null;
        }else{
            Item t = sentinel.next.first;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return t;
        }
    }

    @Override
    public Item removeLast(){
        if (sentinel.next == sentinel){
            return null;
        }else{
            Item t = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return t;
        }
    }

    @Override
    public Item get(int index){
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

    public Item getRecursive(int index){
        Node ptr = sentinel.next;
        return(helperGetRecursive(index, ptr));
    }
    /*in order not to modify sentinel*/
    private Item helperGetRecursive(int index, Node nodes){
        if ((index > size - 1)||(index < 0)){
            return null;
        }else if(index == 0){
            return nodes.first;
        }else{
            return helperGetRecursive(index - 1, nodes.next);
        }
    }
}

