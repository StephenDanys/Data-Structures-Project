import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//generic Top_k
public class Top_k {
     // read from text file to fill up 
     public static void main(String args[]) 
     { 
        Scanner in = new Scanner(System.in);
        //readaing from text
        System.out.println("Please input path to file: ");
        String filePath=in.nextLine();
        int cap=loadFirstTime(filePath);//songay capacity
        Song[] coll= new Song[cap];
		coll=loadFile(filePath,cap);
        //creating new Top_k object
        Top_k ob = new Top_k(); 
        ob.sort(coll, 0, cap-1); 
        //coll is now sorted
		//print(coll);
        System.out.println("How many of the best recomended songs would you like to see? ");
        int k=in.nextInt();
		if(k>coll.length) k=coll.length;
        for(int i=0;i<k;i++ )
            System.out.println(coll[i].getTitle());
     } 
    // Merges two subsongays of song[]. 
    // First subsongay is song[l..m] 
    // Second subsongay is song[m+1..r] 
	static void print(Song[] a){ for(int i=0;i<a.length;i++) System.out.println(a[i]); }
    static void merge(Song[] song, int l, int m, int r) 
    { 
        // Find sizes of two subsongays to be merged 
        int n1 = m - l + 1; 
        int n2 = r - m; 
  
        /* Create temp songays */
        Song[] L = new Song[n1]; 
        Song[] R = new Song[n2]; 
  
        /*Copy data to temp songays*/
        for (int i=0; i<n1; ++i) 
            L[i] = song[l + i]; 
        for (int j=0; j<n2; ++j) 
            R[j] = song[m + 1+ j]; 
  
        /* Merge the temp songays */
        // Initial indexes of first and second subsongays 
        int i = 0, j = 0; 
        // Initial index of merged subsongy songay 
        int k = l; 
        while (i < n1 && j < n2) 
        { 
            if ((L[i]).compareTo( R[j])>0) 
            { 
                song[k] = L[i]; 
                i++; 
            } 
            else
            { 
                song[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
  
        /* Copy remaining elements of L[] if any */
        while (i < n1) 
        { 
            song[k] = L[i]; 
            i++; 
            k++; 
        } 
  
        /* Copy remaining elements of R[] if any */
        while (j < n2) 
        { 
            song[k] = R[j]; 
            j++; 
            k++; 
        } 
    } 
    // Main function that sorts song[l..r] using 
    // merge() 
    static void sort(Song[] song, int l, int r) 
    { 
        if (l < r) 
        { 
            // Find the middle point 
            int m = (l+r)/2; 
            // Sort first and second halves 
            sort(song, l, m); 
            sort(song , m+1, r); 
            // Merge the sorted halves 
            merge(song, l, m, r); 
        } 
    }
	public static int loadFirstTime(String data){ //reading file so we can initialize array
		File f = null;
		BufferedReader reader2 = null;
		String line;
		int counter=0;
		try {
			f = new File(data);
		} catch (NullPointerException e) {
			System.err.println("File not found.");
		}
		try {
			reader2 = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			System.err.println("Error opening file!");
		}
		try{
			line=reader2.readLine();
			while(line != null){
				counter++;
				line=reader2.readLine();
			}
		}
		
		catch (IOException e) {
			System.out.println	("Error reading line ...");
		}
	  
		try {
			reader2.close();
		}catch (IOException e) {
        System.err.println("Error closing file.");
		}
		return counter;
	}
	public static Song[] loadFile(String data,int counter) { //using regex to read the file
        File f = null;
        BufferedReader reader = null;
        String line;
	    int beginning=0;
	    int end=0;
	    int i=0;
	    String name;
	    Pattern intsOnly = Pattern.compile("\\d+");
        try {
            f = new File(data);
            } catch (NullPointerException e) {
                System.err.println("File not found.");
            }

	    	try {
                reader = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                System.err.println("Error opening file!");
            }
	    	Song[] songs= new Song[counter];
	    	counter=0;
            try {
                line = reader.readLine();
                while (line != null) {
	    			line=line.trim();
	    			beginning=line.indexOf(' ');
	    			end=line.lastIndexOf(' ');
	    			name=line.substring(beginning,end);
	    			Matcher makeMatch = intsOnly.matcher(line);
	    			while (makeMatch.find()) {
                        if(i%2!=0){     //every 2 numbers that are read are stored as likes, else for id's
                            beginning=Integer.valueOf(makeMatch.group());
                        }else{
                            end=Integer.valueOf(makeMatch.group());
                        }
                        i++;
                    }
	    			Song newSong= new Song(end,name,beginning);
	    			songs[counter]=newSong;
	    			counter++;
	    			line = reader.readLine();
	    		}
         } //try
     
         catch (IOException e) {
               System.out.println	("Error reading line ...");
         }
         try {
               reader.close();
         }
         catch (IOException e) {
               System.err.println("Error closing file.");
         }
	     return songs;
    }
}