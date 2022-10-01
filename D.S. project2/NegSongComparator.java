import java.util.Comparator;
//class SongComparator uses compare to prioritize songs with less likes 
class NegSongComparator implements Comparator {
	public int compare(Object a, Object b) {
		return -((Comparable)a).compareTo(b);
	}
}