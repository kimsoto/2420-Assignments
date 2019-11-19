package autocomplete;

import java.util.Comparator;

/**
 * Binary search finds the index of the first or last key given that a sorted
 * array contains more than one key equal to the search key.
 * 
 * @author kim and chantel!
 *
 */
public class BinarySearchDeluxe {

	/**
	 * Returns the index of the first key in a[] that equals the search key or -1 if
	 * no such key exists.
	 * 
	 * @param a
	 * @param key
	 * @param comparator
	 * @return
	 */
	public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new java.lang.NullPointerException();
		}

		int begin = 0;
		int end = a.length - 1;

		while (begin <= end) {
			int middle = begin + (end - begin) / 2;

			if (comparator.compare(a[begin], key) == 0) {
				return begin;
			} else if (comparator.compare(key, a[middle]) < 0) {
				end = middle - 1;
			} else if (comparator.compare(key, a[middle]) > 0) {
				begin = middle + 1;
			} else if (comparator.compare(a[middle - 1], a[middle]) == 0) {
				end = middle - 1;
			} else {
				return middle;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the last key in a[] that equals the search key or -1 if
	 * no such key exists
	 * 
	 * @param a
	 * @param key
	 * @param comparator
	 * @return
	 */
	public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
		if (a == null || key == null || comparator == null) {
			throw new java.lang.NullPointerException();
		}

		int begin = 0;
		int end = a.length - 1;

		while (begin <= end) {
			int middle = begin + (end - begin) / 2;

			if (comparator.compare(a[end], key) == 0) {
				return end;
			} else if (comparator.compare(key, a[middle]) > 0) {
				begin = middle + 1;
			} else if (comparator.compare(key, a[middle]) < 0) {
				end = middle - 1;
			} else if (comparator.compare(a[middle + 1], a[middle]) == 0) {
				begin = middle + 1;
			} else
				return middle;
		}
		return -1;
	}

	/**
	 * test unit.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Term term = new Term("test", 3);
		Term term2 = new Term("test", 4);
		Term term3 = new Term("se", 1);
		Term term4 = new Term("s", 1);
		Term[] termTest = { term, term2, term3, term4 };
		Comparator<Term> weight = Term.byReverseWeightOrder();

		System.out.println(BinarySearchDeluxe.firstIndexOf(termTest, term3, weight));
	}
}
