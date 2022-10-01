import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Dynamic_Median {
    /**
     * 2 PQ's one for songs above the current Median, one for below
     * AboveMid uses a NegSongComparator, which places songs with the less likes at the top of the queue
     * initialized at 500 songs, so that resize is not nessecary
     */
    private static PQ aboveMid  = new PQ(500, new NegSongComparator());
    private static PQ belowMid  = new PQ(500, new SongComparator());
    /**
     * insert a song to the structure
     * if aboveMid queue is empty or the song is bigger that it's max, place it there, else at below
     * the 2 last if's are used to resize each queue, by shifting songs form one queue to the other
     * if they have the same size our median is located at AboveMid.max()
     * else, below is always bigger, at median is it's max()
     */
    public void insert (Song k) {
        if(aboveMid.size() == 0|| k.compareTo( aboveMid.max())>0){
            aboveMid.insert(k);
        }else {
            belowMid.insert(k);
        }
        
        if (belowMid.size() > (aboveMid.size() + 1)) {
            aboveMid.insert(belowMid.getMax());
        }
        if (aboveMid.size() > belowMid.size()){
            belowMid.insert(aboveMid.getMax());
        }
    }
    /**
     * @param counter determines where the median is. It also is the number of songs read, and insered in structure
     * in our program, it only uses the abovemid.max()
     * @return is the median
     */
    public Song showMedian(int counter) {
        if(counter%2==1) return belowMid.max();
        else return aboveMid.max();
    }

    public static void main(String[] args) {
        //reading text from args, using reqular expresions(see more at Top_k) 
        File f = null;
        Scanner in = new Scanner(System.in);
        BufferedReader reader = null;
        String line;
		int beginning=0;
		int end=0;
        int i=0;
        int lineCounter=0;//shows how many lines we read, and so how many songs we save(for this programm is capped to 500)
        String name;
		Pattern intsOnly = Pattern.compile("\\d+");
        //initialize path to file
        System.out.println("Give me text File: ");
        String filePath= in.nextLine();
        f = new File(filePath);
		try {
            reader = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException e) {
            System.err.println("Error opening file!");
        }
		Dynamic_Median queue= new Dynamic_Median();
        try {
            line = reader.readLine();
            while (line != null) {
                line=line.trim();
                lineCounter++;
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
				Song newbie =new Song(end,name,beginning);
                queue.insert(newbie);
                if(lineCounter%5==0){ //printing every 5 songs inserted
                    Song Mid=queue.showMedian(lineCounter);
                    System.out.println("Median = "+ Mid.getLikes()+", achieved by Song: "+Mid.getTitle());
                }
                line = reader.readLine();
                if( lineCounter>500) break;
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
    }
}