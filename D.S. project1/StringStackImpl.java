import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.*;

public class StringStackImpl <Gene> implements StringStack <Gene> // we are using generics, so the StringStack can be used for any type of data, not just string
{
    protected Node Top; //the top node
    protected int Size;
   
    //inner class Node
    private class Node
    {
        Gene item;
        Node under; // like the "next" on a linkedlist
    }
    //constructors
    public StringStackImpl() //no-argument
    {
        Size= 0;
        Top = null;
    }
    //methods
    public boolean isEmpty() 
    {
        return Size==0;
    }
    public void push(Gene data) {
        Node oldTop = Top; //we are saving the old top in another variable 
        Top = new Node(); // creating a new Node for the new top
        Top.item = data;
        Top.under = oldTop; //connection
        Size++;
    }
    public Gene pop() throws NoSuchElementException{
        if(Size==0) throw new NoSuchElementException();
        else {Gene gene = Top.item; // creating an object with the same data, but without the "under" link
        Top=Top.under;
        Size--;
        return gene; 
    }
    }
    public Gene peek() throws NoSuchElementException{
        if(Size==0) throw new NoSuchElementException();
        else return Top.item;
    }
    public void printStack(PrintStream stream){
        Node curr= Top; //creating a variable, that runs through the Stack
        stream=new PrintStream(System.out);
        while(curr!=null) {
            stream.println(curr.item); //using the varriable to Print
            curr=curr.under;
        }
    }
    public int size() {
        return Size;
    }
}