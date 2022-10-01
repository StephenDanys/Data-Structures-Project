import java.util.Comparator;
//class SongComparator uses compare to prioritize songs with more likes 
class SongComparator implements Comparator {
	public int compare(Object a, Object b) {
		return ((Comparable)a).compareTo(b);
	}
}