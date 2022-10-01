import java.io.PrintStream;
import java.util.NoSuchElementException; 

public class IntQueueImpl <Gene> implements IntQueue <Gene> {// we are using generics, so the IntQueue can be used for any type of data, not just Integer
   
    protected Node First; //first node
    protected Node Last; // last node
    protected int Size;
    
    //inner class Node 
    private class Node
    {
        Gene item; 
        Node next; //pointing from first to last
    }
    
    //No- argument constructor
    public IntQueueImpl() {
        First=null;
        Last=null;
        Size=0;
    }
    
    //methods 
    public boolean isEmpty(){
        return Size==0;
    }

    public void put(Gene data){
        Node oldLast= Last; //save the data of previous last
        Last = new Node(); //creaating a new Node, ment to be Last 
        Last.item= data; // new Node has new data
        Last.next= null;
        if (isEmpty()) First=Last;//matching 
        else oldLast.next=Last; //matching 
        Size++;
    }

    public Gene get() throws NoSuchElementException{
        if(Size==0) throw new NoSuchElementException();
        else { 
            Gene gene=First.item;   //creates a new Gene Object, with the data from the first
            First=First.next; // removes first from Queue
            Size--;
            return gene; //returns Gene Object
        }
    }

    public Gene peek() throws NoSuchElementException{
        if(Size==0) throw new NoSuchElementException();
        else return First.item; //returns an Gene Object with the data of the First Node, without removing it from the list
    }
	
	public void setFirstData(Gene data) throws NoSuchElementException {
        if(Size==0) throw new NoSuchElementException();
		else First.item=data;    //changes the info of the First node
	}

    public void printQueue(PrintStream stream){
        Node curr= First; //runner that runs throught the Queue
        stream=new PrintStream(System.out);
        while(curr!=null) {
            stream.println(curr.item); //using the runner to print 
            curr=curr.next;
        }
    }

    public int size(){
        return Size;
    }
}