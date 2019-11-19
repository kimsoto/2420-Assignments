package autocomplete;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class AutoCompleteApp {

	public static void main(String[] args) {

		String filename = "lib/wiktionary.txt";
		In in = new In(filename);
		int N = in.readInt();
		Term[] terms = new Term[N];
		for (int i = 0; i < N; i++) {
			double weight = in.readDouble();
			in.readChar();
			String query = in.readLine();
			terms[i] = new Term(query, weight);
		}

		int k = 5;
		Autocomplete autocomplete = new Autocomplete(terms);
		while (StdIn.hasNextLine()) {
			String prefix = StdIn.readLine();
			Term[] results = autocomplete.allMatches(prefix);
			for (int i = 0; i < Math.min(k, results.length); i++)
				StdOut.println(results[i]);
		}
	}
}
