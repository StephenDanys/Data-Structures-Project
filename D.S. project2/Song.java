import java.util.*;
import java.lang.*;

public class Song implements Comparable<Song> {
    private int id;
    private int likes;
    private String title;

    //constructors
    public Song (int likes) {
        this.likes=likes;
        this.id=0;
        this.title=null;
    }
    public Song(int id, String title, int likes) {
        this.id=id;
        this.likes=likes;
        this.title=title;
    }
   
    //getters
    public int getID(){ return id;}
    public int getLikes() {return likes;}
    public String getTitle(){return title;}

    //toString
    public String toString(){
        return "Title: "+ title + " ,Likes: " + likes;
    }
    @Override
    //compareTo
    public int compareTo(Song s) {
    	if(this.getLikes() < s.getLikes()){
			return -1;
		}
		else if(this.getLikes() == s.getLikes()){
			if (this.getTitle().compareTo(s.getTitle()) >0) return 1;
			else if (this.getTitle().compareTo(s.getTitle()) ==0) return 0;
			else return -1;
		}
		else{
			return 1;
		}
	}
}