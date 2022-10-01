import java.util.* ;

import javax.swing.DefaultBoundedRangeModel;

import java.io.*;

class ST{
	private class TreeNode{ // private node class
		WordFreq item;
		TreeNode l;
		TreeNode r;
		int number;

		TreeNode(WordFreq item){
			this.item=item;
		}
	}
	private TreeNode head; //root
	private int distinct=0; //distinct words
	private int total=0; //total words
	private DoublyLinkedList<String> stopwords=new DoublyLinkedList<String>();//stopwords list 
	private int maxFr;//number that holds the maximun frequency of the tree
	private TreeNode maxFrNode; //node that has the WordFreq with the maxFr
	//Constructors
	 

	public ST(){
		maxFr=0;
	}

	//search

	private WordFreq searchR(TreeNode h, String v) {
		if (h == null) return null;
		if (v.equals(h.item.key())) return h.item;
		if (v.compareTo(h.item.key())<0) return searchR(h.l, v);
		else return searchR(h.r, v); 
	}

	WordFreq search(String key) { 
		WordFreq n= searchR(head, key); 
		if(n==null){
			System.out.println("Doesn't exist"); 
			return null;
		}
		if(n.getNum()>getMeanFrequency()) insertRoot(n); //we call this special insert to rotate the seached item to the root

		return n;
	}
	public void insert(WordFreq item){ 
       	update(item.key());
	} 

	public void update(String w){
		TreeNode n = head;
       	TreeNode p = null;
		int result = 0;
		total++;
        while (n != null) {
			
            // Compare element with the element in the current subtree
            result = w.compareTo(n.item.key());
            if (result == 0){ //if this word is on the tree
				n.item.setNum(n.item.getNum()+1);
            	if(maxFr<n.item.getNum()) maxFr=n.item.getNum(); maxFrNode=n;
            	return;
            }
			p = n;
			p.number++;
            n = result < 0 ? n.l : n.r;
		}
		//if it's a new word
        // Create and connect a new node
        TreeNode node = new TreeNode(new WordFreq(w));
        // The new node must be a left child of p
        if (result < 0) {
            p.l = node;
        }
        // The new node must be a right child of p
        else if (result > 0) {
            p.r = node;
        }
        // The tree is empty; root must be set
        else {
            head = node;
		}
		++distinct;
	}

	private TreeNode insertT(TreeNode h, WordFreq x) { //insert with rotations
		if (h == null) return new TreeNode(x);
		if (x.key().compareTo(h.item.key())<0) {
			h.l = insertT(h.l, x); 
			h = rotR(h); } 
		else {
			h.r = insertT(h.r, x);
			h = rotL(h); 
		}
		return h; 
	}

	public void insertRoot(WordFreq x) { //insert at root
		head = insertT(head, x); 
	}	
	//rotations

	private TreeNode rotR(TreeNode h) {
		TreeNode x = h.l; h.l = x.r; x.r = h; return x; 
	}

	private TreeNode rotL(TreeNode h) {
		TreeNode x = h.r; h.r = x.l; x.l = h; return x; 
	}
	//counter

	private int countR(TreeNode h){
		if(h==null) return 0;
		return 1+countR(h.l)+countR(h.r);
	}

	public void count(TreeNode h){
		
		h.number=countR(h);
	}

	public int getNumber(TreeNode h){
		count(h);
		return h.number;
	}

	int getFrequency(String w){ 
		WordFreq word= search(w);
		if(word==null)return 0;
		return word.getNum();
	} 

	public WordFreq getMaximumFrequency(){
        return maxFrNode.item;
    } 

	void addStopWord(String w){
		stopwords.add(w.toLowerCase());
	} 
	void removeStopWord(String w){
		ListIterator<String> iterator = stopwords.iterator();
		while(iterator.hasNext()){
			if((iterator.next()).equals(w)){
				iterator.remove();
				System.out.println("word removed");
				return;
			}
		}
		
	}

	int getDistinctWords(){ return distinct;}

	int getTotalWords() { return total; }

	void load(String filename){
		BufferedReader reader = null;
        String line;
		File f=null;
		boolean exists=true;
		ListIterator<String> iterator =stopwords.iterator();
		String kay;
		int nub=stopwords.size();
			
		try{
			f = new File(filename);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }
		
        try {
            reader = new BufferedReader(new FileReader(f));
	    } catch (FileNotFoundException e) {
	        System.err.println("Error opening file!");
	    }
	    try {
	        line = reader.readLine();
	        line=line.trim();
	        line = line.replaceAll("'", ""); //using regex to make the text in the right form(without numbers or special symbols)
	        line=line.replaceAll("[0-9]","");
	        line=line.replaceAll("\\p{Punct}", "");
	    	while (line != null) {
	    		    String[]currentLine = line.split("\\W+");
    			        for(String s:currentLine){ //for each word
    			    
    			        	for(int j=0;j<nub;j++){
    			        		while(iterator.hasNext()){ //if it is not a stopword
									kay=iterator.next();
									if(kay.equals(s)){
										exists=false;
										break;
									}
								}
								while(iterator.hasPrevious()){ //we use 2nd loop to return the iterator to the start
									kay=iterator.previous();
								}
							}
    			            if(exists){
    				        	insert(new WordFreq(s));
    				    	}
    				    
    				    	exists=true;

    			        }
    				   line = reader.readLine(); 
    			    }
	    
		} catch (IOException e) {
	        System.out.println("Error reading line.");
	    }
	    try {
	        reader.close();
	    } catch (IOException e) {
	        System.err.println("Error closing file.");
	    }	
	} 

	//remove
 	
	public WordFreq remove(String key) {
    	TreeNode removed = remove(head, key);
   		if (removed != null){
        	return removed.item;
    	}
   		return null;
	}


	private TreeNode remove(TreeNode n, String key) {
		if (n == null) {
			return null;
		}
		else if (n.item.key().compareTo(key) < 0){
			n.r = remove(n.r, key);
		}
		else if (n.item.key().compareTo(key) > 0){
			n.l = remove(n.l, key);
		}
		else{
			if (n.r != null){
            WordFreq successor = leftMost(n.r).item;
            n.item = successor;
            n.r = remove(n.r, successor.key()); 
			}
			else{
				n = n.l;
			}
		}
		return n;
	}
	
	private TreeNode leftMost(TreeNode n) {
		if (n.l == null){
			return n;
		}
		else{
			return leftMost(n.l);
		}
	}
	public int getMeanFrequency(){
		if(distinct==0){
			System.out.println("Distinct words 0");
			return 0;
		}
		return total/distinct;
	}

	public void printAlphabetically(PrintStream stream){
		printLeftToRight(head,stream);
	}
	//inorder printing
	public void printLeftToRight(TreeNode node, PrintStream stream) {
    	if(node != null) {
        	if(node.l != null) {
        	    printLeftToRight(node.l,stream);
        	}
        	stream.print(node.item+"\n");
        	if(node.r != null) {
        	    printLeftToRight(node.r,stream);
        	}
    	}
	}
	private int compareTo(TreeNode k1,TreeNode k2){
		if (k1.item.getNum()<k2.item.getNum()) return -1;
		else if(k1.item.getNum()==k2.item.getNum()){
			return k1.item.key().compareTo(k2.item.key());
		}
		else return 1;

	}
	//we use this to insert WordFreqs to a doublyLinked List
	private void traverseR(TreeNode node,DoublyLinkedList<WordFreq> a) {
		if(node == null) return;
		traverseR(node.l,a);
		a.add(node.item);
        traverseR(node.r,a);
	}
		
	public void printByFrequency(PrintStream stream) {
		int min =maxFr;
		int k=0;
		WordFreq kay;
		DoublyLinkedList<WordFreq> nodes = new DoublyLinkedList<WordFreq>();//contains all the TreeNodes of the tree
		traverseR(head,nodes);
		DoublyLinkedList<WordFreq> freqs = new DoublyLinkedList<WordFreq>();//will be filled with the sorted items of nodes
		ListIterator<WordFreq> NodeIterator = nodes.iterator();//with the use of its iterator
		int big= nodes.size();
		for(int i=0;i<big;i++){
			min=maxFr;
			while(NodeIterator.hasNext()){
				kay=NodeIterator.next();
				k=kay.getNum();
				if(k<min) min=k;
			}
			while(NodeIterator.hasPrevious()){
				kay=NodeIterator.previous();
				if(kay.getNum()==min){
					freqs.add(kay);
					NodeIterator.remove();
				}
			}
		}

		stream.println(freqs);
	}
    public static void main(String args[]){
		//for(TreeNode k:newTree.Freaks) System.out.println(k.item);
		//newTree.printByFrequency(System.out);
    }
}