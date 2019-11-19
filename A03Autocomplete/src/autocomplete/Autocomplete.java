package autocomplete;

import java.util.Arrays;

import edu.princeton.cs.algs4.Merge;

/**
 * Autocomplete is an immutable data type that provides autocomplete
 * functionality for a given set of string and weights.
 * 
 * @author kim and chantel!
 *
 */
public class Autocomplete {
	// FIELDS
	private Term[] t;

	/**
	 * Initializes the data structure from the given array of terms.
	 * 
	 * @param terms
	 */
	public Autocomplete(Term[] terms) {
		if (terms == null) {
			throw new java.lang.NullPointerException();
		}

		t = new Term[terms.length];

		// fill this.terms with terms stuff
		for (int i = 0; i < terms.length; i++) {
			t[i] = terms[i];
		}

		// sort terms with stable sort
		Merge.sort(t);
	}

	/**
	 * Returns all terms that start with the given prefix in descending order of
	 * weight.
	 * 
	 * @param prefix
	 * @return
	 */
	public Term[] allMatches(String prefix) {
		if (prefix == null) {
			throw new java.lang.NullPointerException();
		}

		int start = BinarySearchDeluxe.firstIndexOf(t, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
		int end = BinarySearchDeluxe.lastIndexOf(t, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

		Term[] aMatches = new Term[1 + end - start];

		for (int i = 0; start <= end; i++) {
			aMatches[i] = t[start++];
		}
		Arrays.sort(aMatches, Term.byReverseWeightOrder());

		return aMatches;
	}

	/**
	 * Returns the number of terms that start with the given prefix.
	 * 
	 * @param prefix
	 * @return
	 */
	public int numberOfMatches(String prefix) {
		if (prefix == null) {
			throw new java.lang.NullPointerException();
		}

		int start = BinarySearchDeluxe.firstIndexOf(t, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
		int end = BinarySearchDeluxe.lastIndexOf(t, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));

		return end - start + 1;
	}

	public static void main(String[] args) {
		Term[] terms = { new Term("bean", 4), new Term("bean", 5), new Term("banana", 2), new Term("bacon", 6), new Term("bean", 1)};
		Autocomplete test = new Autocomplete(terms);
		
		System.out.print("number of matchs of bean: ");
		System.out.println(test.numberOfMatches("bean"));
		
		System.out.println("\nlist of terms starting with b: descending order");
		Term[] temp = test.allMatches("B");
		for (Term term : temp) {
			System.out.println(term);
		}
	}
}
