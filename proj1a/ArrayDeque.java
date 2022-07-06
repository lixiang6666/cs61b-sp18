public class ArrayDeque<T> {
    private T[] array;
    private int arrsize;
    private int size;
    private double usageratio;
    private int refactor;
    
    public ArrayDeque(){
        size = 0;
        usageratio = 0.25;
        refactor = 2;
        array =(T [])new Object[arrsize];
    }

    public ArrayDeque(ArrayDeque other){
        size = other.size;
        array = (T [])new Object[other.size];
        for (int i = 0; i < size; i++){
            array[i] = (T)other.get(i);
        }
    }

    public void lagerSize(){
        T[] rearray =(T[])new Object[arrsize * refactor];
        System.arraycopy(array, 0,rearray,0, size);
        arrsize *= refactor;
        array = rearray;
    }

    public void reduceSize(){
        T[] rearray = (T[])new Object[arrsize / refactor];
        System.arraycopy(array, 0,rearray,0,size);
        array = rearray;
        arrsize /= refactor;
    }

    public void addFirst(T item){
        if (size + 1 > arrsize) {
            lagerSize();
        }
            System.arraycopy(array, 0,array,1, size);
            array[0] = item;
            size += 1;
    }

    public void addLast(T item){
        if (size + 1 > arrsize) {
            lagerSize();
        }
        array[size] = item;
        size += 1;
    }

    public boolean isEmpty(){
        if (size == 0){
            return true;
        }else{
            return false;
        }
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        for (int i=0; i<size; i++){
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public T removeFirst(){
        T firstitem = array[0];
        T[] rearray  = (T[]) new Object[arrsize];
        System.arraycopy(array,1,rearray,1,size -1);
        array = rearray;
        size -= 1;
        while((double)size/arrsize < usageratio){
            reduceSize();
        }
        return firstitem;
    }

    public T removeLast(){
        T lastitem = array[size - 1];
        array[size - 1] = null;
        size -= 1;
        while((double)size/arrsize < usageratio){
            reduceSize();
        }
        return lastitem;
    }

    public T get(int index){
        return array[index];
    }

}
