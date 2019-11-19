package queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Takes an integer k, and reads in a sequence of strings from
 * StdIn and prints out k of them uniformly at random.
 * @author Kim Soto and Chris Christoffersen
 *
 */
public class Subset {

	public static void main(String[] args) {
		//int from command line.
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

		//adds strings from command line into randomizedQueue.
		while (!StdIn.isEmpty()) {
		randomizedQueue.enqueue(StdIn.readString());
		}

		//prints out strings randomly depending on k.
		for (int i = 0; i < k; i++) {
			StdOut.println(randomizedQueue.dequeue());
		}
	}
}