/*
 * PriorityQueue.java
 */
import java.util.Comparator;

public class PQ {
    /**
     * Array based heap representation
     */
    private Song[] heap;
    /**
     * The number of objects in the heap
     */
    private int size;
    /**
     * Comparator.
     */
    protected Comparator cmp;
    /**
     *IdArray will have 9999 positions. Since each song will have ID number at [1,9999] we will be storing it's heap position at at IDArray's ID'th postion
     IdArray[song.getID]=heap possition
     */
    protected int[] IdArray;
    //one argument constructor for a default pq
    public PQ(int capacity) {
        this(capacity, new SongComparator());
    }
    /**
     * Creates heap with a given capacity and comparator.
     * param capacity The capacity of the heap being created.
     * param cmp The comparator that will be used.
     * also initialize IdArray, with each position being 0
     */
    public PQ(int capacity, Comparator cmp) {
        if (capacity < 1) throw new IllegalArgumentException();
        this.heap = new Song[capacity+1];
        this.size = 0;
        this.cmp = cmp;
        IdArray=new int[10000];
        for (int i=0;i<10000;i++)
            IdArray[i]=0;
    }
    /**
     * Inserts an object in this heap.
     * throws IllegalStateException if heap capacity is exceeded.
     * param object The object to insert.
     */
    public void insert(Song object) {
        // Ensure object is not null
        if (object == null) throw new IllegalArgumentException();
        // Check available space
        if (size == heap.length*3/4) resize();
        // Place object at the next available position
        heap[++size] = object;
        IdArray[object.getID()]=size;//put heap pos in IDArray
        // Let the newly added object swim
        int pos =swim(size); // last position of previous heap[size]
        IdArray[heap[pos].getID()]=pos; //just to be sure

    }
    public int[] getIdArray(){
        return IdArray; //returns IdArray
    }
    public Song getSong(int i){
        return heap[i];//returns a song from queue
    }
    /**
     * return max object of heap without removing it
     */
    public Song max() {
        return heap[1];
    }
    /**
     * Removes the object at the root of this heap.
     * throws IllegalStateException if heap is empty.
     * return The object removed.
     */
    public Song getMax() {
        // Ensure not empty
        if (size == 0) throw new IllegalStateException();
        // Keep a reference to the root object
        Song object = heap[1];
        // Replace root object with the one at rightmost leaf
        if (size > 1) heap[1] = heap[size];
        // Dispose the rightmost leaf
        heap[size--] = null;
        // save new root's id
        // Sink the new root element
        int pos =sink(1); //returns last position of previous heap[1]
        if (size==0){ //if queue is empty just return the object
            return object;
        }else IdArray[heap[pos].getID()]=pos;
        // Return the object removed
        return object;
    }
    /**
     * remove an object from heap
     * @param i is it's ID, which will show object's heap position though IdArray
     * using same method as in getMax() to delete it from heap
     * using max() to get the max value
     */
    public void remove(int ID){
        int key= IdArray[ID]; //the object i want to remove is heap[key]
        heap[key]= new Song(this.max().getLikes()+1); //change the song into one that has more likes than root
        swim(key);//swim it to the root
        getMax();//remove it

    }
    /**
     * Shift up.
     */
    private int swim(int i) {
        while (i > 1) {  //if i root (i==1) return
            int p = i/2;  //find parent
            int result = cmp.compare(heap[i], heap[p]);  //compare parent with child
            if (result <= 0) return i;    //if child <= parent return
            swap(i, p);             //else swap and i=p
            i = p;
        }
        return i;
    }
    /**
     * Shift down.
     */
    private int sink(int i){
        int left = 2*i, right = left+1, max = left;
        // If 2*i >= size, node i is a leaf
        while (left <= size) {
            // Determine the largest children of node i
            if (right <= size) {
                max = cmp.compare(heap[left], heap[right]) < 0 ? right : left;
            }
            // If the heap condition holds, stop. Else swap and go on.
            if (cmp.compare(heap[i], heap[max]) >= 0) return i;
            swap(i, max);
            //after swaping position i belongs to max child
            i = max; left = 2*i; right = left+1; max = left;
        }
        return i;
    }
    
    /**
     * Interchanges two array elements.
     * also rearanges it's IdArray's positions
     */
    private void swap(int i, int j) {
        Song tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
        IdArray[heap[j].getID()]=j;
        IdArray[heap[i].getID()]=i;
    }
    //print songs
    public void print() {
        for (int i=1; i<=size; i++){
            System.out.print(heap[i].getTitle()+ " ");
            System.out.println();
        }
        System.out.println();
    }
    boolean empty(){
        return size == 0;
    }
    private void resize() {
        Song[] newArray;
		newArray = new Song[heap.length * 2];
		// copy elements that are logically in the Queue
		for (int i = 0; i<size; i++)
			newArray[i] = heap[i];
		heap = newArray;
    }
    public int size(){return size;}
}
