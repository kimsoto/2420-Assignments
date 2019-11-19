package kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

/**
 * Represents a Symbol Table data type using a red black binary 
 * search tree, and 2D points to represent the values in the tree.
 * 
 * @author Kyla Kunz & Kim Soto
 *
 */
public class PointST<Value> {
	private RedBlackBST<Point2D, Value> points;

	/**
	 * Constructs an empty symbol table of points.
	 */
	public PointST() {
		points = new RedBlackBST<Point2D, Value>();
	}

	/**
	 * Checks if the symbol table is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return points.isEmpty();
	}

	/**
	 * Number of points.
	 * 
	 * @return
	 */
	public int size() {
		return points.size();
	}

	/**
	 * Associate the value val with point p.
	 * 
	 * @param p
	 * @param val
	 */
	public void put(Point2D p, Value val) {
		if (p == null) {
			throw new NullPointerException();
		}
		if (val == null) {
			throw new NullPointerException();
		}

		points.put(p, val);
	}

	/**
	 * Value associated with point p.
	 * 
	 * @param p
	 * @return
	 */
	public Value get(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}

		return points.get(p);
	}

	/**
	 * Checks if the symbol table contains point P.
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}

		return points.contains(p);
	}

	/**
	 * All points in the symbol table.
	 * 
	 * @return all keys in redblack bst.
	 */
	public Iterable<Point2D> points() {
		return points.keys();
	}

	/**
	 * All points that are inside the rectangle.
	 * 
	 * @param rect
	 * @return queue containing all points.
	 */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new NullPointerException();
		}

		Queue<Point2D> queue = new Queue<>();

		for (Point2D el : points.keys()) {
			if (rect.contains(el)) {
				queue.enqueue(el);
			}
		}

		return queue;
	}

	/**
	 * A nearest neighbor to point P; null if symbol table is empty.
	 * 
	 * @param p
	 * @return
	 */
	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new NullPointerException();
		}

		Point2D nearest = points.max();

		for (Point2D el : points.keys()) {
			if (el.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
				nearest = el;
			}
		}

		return nearest;
	}

	/**
	 * Unit testing of methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		PointST<Point2D> table = new PointST<>();
		StdOut.println("isEmpty: " + table.isEmpty());

		table.put(new Point2D(2.0, 5.0), new Point2D(3.0, 5.0));
		table.put(new Point2D(3.0, 4.0), new Point2D(2.0, 8.0));
		table.put(new Point2D(6.0, 2.0), new Point2D(1.0, 7.0));
		table.put(new Point2D(5.0, 7.0), new Point2D(4.0, 2.0));
		table.put(new Point2D(1.0, 2.0), new Point2D(5.0, 6.0));
		

		StdOut.println("isEmpty: " + table.isEmpty());
		StdOut.println("size: " + table.size());

		StdOut.println("get (6.0, 2.0): " + table.get(new Point2D(6.0, 2.0)));
		StdOut.println("Contains (3.0, 6.0): " + table.contains(new Point2D(3.0, 6.0)));
		StdOut.println();
		
		StdOut.println("Nearest to (5.0, 4.0): " + table.nearest(new Point2D(1.0, 1.0)));

		for (Point2D el : table.points()) {
			StdOut.print(el + " ");
		}
	}

}
