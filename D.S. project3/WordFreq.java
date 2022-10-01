import java.util.*;

public class WordFreq{ 

private String word;
private int numOfFreq;
	WordFreq(String w){
		word=w;
		numOfFreq=1;
	}

	public String key() { 
		return word; 
	}

	public int getNum() { 
		return numOfFreq; 
	}

	public void setNum(int k) { 
		numOfFreq=k; 
	}	

	public String toString(){
		return "Word : " + key() + " Word Frequency: " + getNum();
	}
}
