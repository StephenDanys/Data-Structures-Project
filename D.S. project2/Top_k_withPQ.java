import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.omg.CORBA.portable.ValueFactory;

public class Top_k_withPQ {
    public static void main(String args[]){
        int k=0;
        try{
            k=Integer.parseInt(args[0]);
        }
        catch(NumberFormatException e){
            System.err.println("wrong args");
        }
        //reading text from args, using reqular expresions(see more at Top_k) 
		File f = null;
        BufferedReader reader = null;
        String line;
		int beginning=0;
		int end=0;
		int i=0;
		String name;
		Pattern intsOnly = Pattern.compile("\\d+");
        try {
            f = new File(args[1]);
        } catch (NullPointerException e) {
            System.err.println("File not found.");
        }

		try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
		PQ queue= new PQ(2*k);
		
        try {
            line = reader.readLine();
            while (line != null) {
					line=line.trim();
					beginning=line.indexOf(' ');
					end=line.lastIndexOf(' ');
					name=line.substring(beginning,end);
					Matcher makeMatch = intsOnly.matcher(line);
					while (makeMatch.find()) {
                        if(i%2!=0){     //every 2 numbers that are read are ID of songs, the rest are likes
                            beginning=Integer.valueOf(makeMatch.group());
                        }else{
                            end=Integer.valueOf(makeMatch.group());
                        }
                        i++;
                    }
					/**
					* If size=k ,then remove the one with least priority
					* the one with least priority holds the biggest possition at heap
					* biggest possition at heap= max value of IdArray
					* This method finds the max value of PQ IdArray, and returns it's IdArray possition, aka song's ID
					*/
					Song newbie =new Song(end,name,beginning); //newbie is the song we read from text
					if(queue.size()==k){
                        //finding song with min likes, and save it's heap position at pos
                        //using sequential searching
                        int min=queue.getSong(1).getLikes();
                        int pos=1;
                        for(int j=2;j<=queue.size();j++){
                          if(queue.getSong(j).getLikes()<min){
                            min=queue.getSong(j).getLikes();
                            pos=j;
                            } else if (queue.getSong(j).getLikes()==min){
                                if(queue.getSong(j).getTitle().compareTo(queue.getSong(pos).getTitle())<0) pos=j;
                            }
                        }
                        //old man is a reference to queue's song with the least likes(or )
                        Song oldMan= queue.getSong(pos);
						if (newbie.compareTo(oldMan)>0){
							queue.remove(oldMan.getID());
							queue.insert(newbie);
						} 
							
					} else 
						queue.insert(newbie);
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
        

        //ending file, print Queue
        System.out.println("The "+k+" best song are:");
        queue.print();
    }
}