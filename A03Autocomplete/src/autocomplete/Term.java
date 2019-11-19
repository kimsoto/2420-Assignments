package autocomplete;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Represents an autocomplete term: a string query and an 
 * associated real-valued weight.
 * 
 * @author kim and chantel!
 *
 */
public class Term implements Comparable<Term> {
	//FIELDS
	private String query;
	private double weight;
	
	/**
	 * Initializes a term with the given query string and weight.
	 * @param query
	 * @param weight
	 */
	public Term(String query, double weight) {
		if(query == null) 
			throw new java.lang.NullPointerException();
		
		if(weight < 0) 
			throw new java.lang.IllegalArgumentException();
		
		this.query = query;
		this.weight = weight;
	}

	/**
	 * Compares the terms in descending order by weight.
	 * @return new CompareByReverseWeightOrder()
	 */
	public static Comparator<Term> byReverseWeightOrder(){
		return new CompareByReverseWeightOrder();
	}
	
	/**
	 * Class that compares terms by reverse order.
	 * 
	 * @author 
	 *
	 */
	private static class CompareByReverseWeightOrder implements Comparator<Term>{

		@Override
		public int compare(Term o1, Term o2) {
			if(o2.weight < o1.weight) {
				return -1;
			}
			if(o2.weight > o1.weight) {
				return 1;
			}
			return 0;
		}
		
	}

	/**
	 * Compares the terms in lexicographic order using only the first
	 * r characters of each query
	 * @param r
	 * @return
	 */
	public static Comparator<Term> byPrefixOrder(int r){
		if(r < 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		return new CompareByPrefixOrder(r);
	}
	
	/**
	 * Class that compares terms by prefix order.
	 * 
	 * @author 
	 *
	 */
	private static class CompareByPrefixOrder implements Comparator<Term>{
		private int r;
		
		public CompareByPrefixOrder(int r) {
			this.r = r;
		}
		
		@Override
		public int compare(Term o1, Term o2) {
			String p1 = o1.query.substring(0, r);
			String p2 = o2.query.substring(0, r);
			
			if(o1.query.length() < r) {
				p1 = o1.query;
			}
			if(o2.query.length() < r) {
				p2 = o2.query;
			}
			
			return p1.compareToIgnoreCase(p2);
		}
		
	}

	/**
	 * Compares the terms in lexicographic order by query.
	 */
	public int compareTo(Term that) {
		return this.query.compareTo(that.query);
	}

	/**
	 * Returns a string representation of the term in following format:
	 *  weight	query
	 */
	@Override
	public String toString() {
		return weight + "\t" + query;
	}
	
	/**
	 * for testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Term[] terms = {new Term("dog", 3), new Term("dogcatcher", 5), new Term("do", 5.5)};
		
		System.out.println("Terms:");
		for(Term t: terms) {
			System.out.print(t + ", ");
		}
		System.out.println();
		
		
		System.out.println("\nTerms by reverse weight:");
		Arrays.sort(terms, Term.byReverseWeightOrder());
		for(Term t: terms) {
			System.out.print(t + ", ");
		}
		System.out.println();
		
		System.out.println("\nTerms by prefix:");
		Arrays.sort(terms, Term.byPrefixOrder(2));
		for(Term t: terms) {
			System.out.print(t + ", ");
		}
	
	}
}
