package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

/**
 * A structure that inserts items into an array and removes items 
 * uniformly at random from items in the array.
 * 
 * @author Kim Soto, Chris Christoffersen
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	//FIELDS
	private Item[] itemArray;
	private int size;
	
	/**
	 * Constructs an empty randomized queue.
	 */
	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		itemArray = (Item[]) new Object[2];
		size = 0;
	}

	/**
	 * Checks if the queue is empty.
	 * @return True if queue is empty, false if it contains elements.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of items on the queue.
	 * @return
	 */
	public int size() {                  
		return size;
	}

	/**
	 * Adds item into queue.
	 * @param item
	 */
	public void enqueue(Item item) {
		//If client attempts to add a null item.
		if(item == null) {
			throw new NullPointerException("Item is null!");
		}
		
		//double size of array
		if(size == itemArray.length) {
			resize(2 * itemArray.length);
		}
		itemArray[size++] = item;
	}

	/**
	 * Deletes and returns a random item.
	 * @return
	 */
	public Item dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		int n = StdRandom.uniform(size);
		Item random = itemArray[n];
		
		if(n != size - 1) {
			itemArray[n] = itemArray[size - 1];
		}
		itemArray[--size] = null; //prevent loitering
		
		//half size of array when 1/4 full
		if(size >= 1 && size == itemArray.length /4) {
			resize(itemArray.length / 2);
		}
		
		return random; 
	}

	/**
	 * Returns a random item, but does not delete it.
	 * @return
	 */
	public Item sample() { 
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		
		return itemArray[StdRandom.uniform(size)];
	}

	/**
	 * Returns an independent iterator over items in random order.
	 */
	public Iterator<Item> iterator(){
		return new RandomizedQueueIterator();
	}
	
	private class RandomizedQueueIterator implements Iterator<Item>{
		private int s = size;
		private Item[] queue;
		
		@SuppressWarnings("unchecked")
		public RandomizedQueueIterator(){
			queue = (Item[]) new Object[s];
			for(int i = 0; i < s; i++) {
				queue[i] = itemArray[i];
			}
		}
		
		@Override
		public boolean hasNext() {
			return s > 0;
		}
		
		public void remove() {
			throw new NoSuchElementException();
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			int index = StdRandom.uniform(s);
			Item temp = queue[index];
			queue[index] = queue[--s];
			queue[s] = null;
			
			return temp;
		}
	}
	
	/**
	 * Resize array to avoid thrashing.
	 * @param n
	 */
	@SuppressWarnings("unchecked")
	private void resize(int n) {
		Item[] newItemArray = (Item[]) new Object[n];
		
		for(int i = 0; i < size; i++) {
			newItemArray[i] = itemArray[i];
		}
		
		itemArray = newItemArray;
	}

	/**
	 * Unit testing.
	 * @param args
	 */
	public static void main(String[] args) { 
		RandomizedQueue<String> test = new RandomizedQueue<>();
		
		test.enqueue("a");
		test.enqueue("b");
		test.enqueue("c");
		test.enqueue("d");
		
		//iterator testing
		System.out.println("Queue Iterator: ");
		for(String el: test) {
			System.out.print(el + " ");
		}
		System.out.println();
		
		//testing other methods
		System.out.printf("\nis Empty: %b  size: %d%n", test.isEmpty(), test.size());
		System.out.println("\ntest sample: " + test.sample());
		System.out.println("\ntesting dequeue: " + test.dequeue());
	}
}