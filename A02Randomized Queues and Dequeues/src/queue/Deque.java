package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generalization of a stack and a queue that supports
 * inserting and removing items from either the front or the back of the data structure.
 * @author Kim Soto, Chris Christoffersen
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
	
	//FIELDS
	private int itemsInQueue; //Number of items in queue
	private Node head;
	private Node tail;
	
	/**
	 * Constructs an empty deque.
	 */
	public Deque() {
		head = null;
		tail = null;
		itemsInQueue = 0;
	}
	
	/**
	 * Checks if the deque is empty.
	 * @return True if deque is empty, false if it contains elements.
	 */
	public boolean isEmpty() {
		return itemsInQueue == 0;
	}

	/**
	 * Returns the number of items in the deque.
	 * @return int value of size.
	 */
	public int size() {
		return itemsInQueue;
	}

	/**
	 * Inserts an item at the front of queue.
	 * @param item
	 */
	public void addFirst(Item item) { 
		//If client attempts to add a null item.
		if(item == null) {
			throw new NullPointerException("Item is null!");
		}
		
		Node tempNode = new Node();
		tempNode.itemNode = item;
		
		if(isEmpty()) {
			head = tempNode;
			tail = tempNode;
		}
		else {
			tempNode.next = head;
			head = tempNode;
		}
		
		itemsInQueue++;
	}

	/**
	 * Inserts an item at the end of queue.
	 * @param item
	 */
	public void addLast(Item item) {
		//If client attempts to add a null item.
		if(item == null) {
			throw new NullPointerException("Item is null!");
		}
		
		Node tempNode = new Node();
		tempNode.itemNode = item;
		
		if(isEmpty()) {
			tail = tempNode;
			head = tempNode;
		}
		else {
			tail.next = tempNode;
			tail = tempNode;
		}
		
		itemsInQueue++;
	}

	/**
	 * Deletes and returns an item at the front.
	 * @return First item in queue.
	 */
	public Item removeFirst() { 
		//If client attempts to remove from an empty deque.
		if(isEmpty()) {
			throw new NoSuchElementException("Cannot remove further, deque is empty!");
		}

		Node current;
		if(itemsInQueue == 1) {
			current = head;
			head = null;
			tail = null;
			itemsInQueue--;
			return current.itemNode;
		}
		
		current = head.next;
		head = current;
		itemsInQueue--;

		return current.itemNode;
	}

	/**
	 * Deletes and returns an item at the end.
	 * @return Last item in queue.
	 */
	public Item removeLast() { 
		//If client attempts to remove from an empty deque.
		if(isEmpty()) {
			throw new NoSuchElementException("Cannot remove further, deque is empty!");
		}
		
		Node current = head;
		if(itemsInQueue == 1) {
			head = null;
			tail = null;
			itemsInQueue--;
			return current.itemNode;
		}
		
		while(current.next != tail) {
			current = current.next;
		}
		current.next = null;
		tail = current;
		itemsInQueue--;
		
		return current.itemNode;
	}
	
	/**
	 * toString method to print out the items in Deque.
	 */
//	@Override
//	public String toString() {
//		StringBuilder sb = new StringBuilder();
//		Node current = head;
//		
//		while(current != null) {
//			sb.append(current.itemNode).append(" ");
//			current = current.next;
//		}
//		
//		return sb.toString();
//	}

	/**
	 * Returns an iterator over items in order from front to end.
	 */
	public Iterator<Item> iterator() {
		return new DequeClassIterator();
	}
	
	private class DequeClassIterator implements Iterator<Item>{
		Node current;
		
		DequeClassIterator(){
			current = head;
		}
		@Override
		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new NoSuchElementException();
		}
		@Override
		public Item next() {
			if(current == null) {
				throw new NoSuchElementException();
			}
			Item item = current.itemNode;
			current = current.next;
			return item;
		}
	}
	
	/**
	 * Node that stores the item and a single reference to the next node.
	 */
	private class Node {
		private Item itemNode;
		private Node next;
	}

	/**
	 * Unit testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Deque<String> deque = new Deque<>();
		
		//addFirst
		deque.addFirst("test");
		deque.addFirst("a");
		deque.addFirst("is");
		deque.addFirst("this");
		System.out.println("testing addFirst: " + deque);
		
		//addLast
		deque.addLast(":)");
		System.out.println("\ntesting addLast: " + deque);
		
		//removeFirst
		deque.removeFirst();
		System.out.println("\ntesting removeFirst: " + deque);
		
		//removeLast
		deque.removeLast();
		System.out.println("\ntesting removeLast: " + deque);
		
		//test Iterator
		Deque<String> deque2 = new Deque<>();
		deque2.addFirst("c");
		deque2.addFirst("b");
		deque2.addFirst("a");
		deque2.addFirst("z");
		deque2.addFirst("q");
		
		System.out.println("\ntesting iterator:");
		for(String el: deque2) {
			System.out.print(el + " ");
		}
	}
}