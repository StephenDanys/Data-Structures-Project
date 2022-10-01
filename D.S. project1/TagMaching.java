import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.io.*;
import java.util.*;

public class TagMaching
{
    public static void main( String args[] ){
        //path included in cmd
        File f= null;
        try {
            System.out.println(args[0]);
            f = new File(args[0]);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }
        //reading file
        int runner; //runs throught each line
        int mp1=0; //matches the position of "<"  
        int mp2=0; //matches the position of ">"
        String sub;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(f)); 
            String line=reader.readLine(); //reads next line
            StringStackImpl <String> Stack1= new StringStackImpl<String>();
            while (line != null) { //while there is a next line, meaning the file has not ended 
                for (runner=0; runner<line.length() ;runner++){ //loop running throught each line
                    if( (line.charAt(runner))=='<') { //if we find "<"
                        mp1= runner;
                    }
                    if ((line.charAt(runner))=='>'){ // if we find ">"
                        mp2= runner;
                    
                        if (mp1<mp2) { //means we found a tag

                            sub= line.substring(mp1+1 ,mp2); //create substring, using matching positions
                            if(ReconTag(sub)) { //opening tag
                                Stack1.push(sub); // go inside the stack
                            } else { // if is closing 
                                sub=sub.substring(1); // take the index of the tag with out "/"
                                if (sub.equals(Stack1.peek())) Stack1.pop(); //if the closing tag matches the last opening tag, then pop that from the stack
                            } 
                            mp1=0; //reseting mp's, for the next time we will need them
                            mp2=0;
                        }
                    }
                }
                line=reader.readLine(); //read next line, to continue loop
            }

            if(Stack1.isEmpty()) { //program's output, and exit
                System.out.println("This HTML file has matching tags");
            } else {
                System.out.println("This HTML file doesn't have matching tags");
            }
            reader.close();
        } catch (IOException e) { //exeption
            System.err.println("Error opening / reading file!");
        }
    }
   


   //ReconTagMethod, recognises whether a tag is opening or closing 
    public static boolean ReconTag(String subS) {
        if(subS.startsWith("/")) return false; // it's a closing tag
        else return true; // it's an oppening tag
    }
}