package kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

/**
 * Represents a Symbol Table that is implemented using a 2D tree. 
 * It is a BST with points in the nodes, using the x- and 
 * y-coordinates of the points as keys in strictly alternating 
 * sequence, starting with the x-coordinates.
 * 
 * @author Kim Soto & Kyla Kunz
 *
 */
public class KDTreeST<Value> {
	private int size;
	private Node root;

	/**
	 * Node data type representing a Node in a 2d-tree.
	 * 
	 * @author Kyla and Kim
	 *
	 */
	private class Node {
		private Point2D p;
		private Value value;
		private RectHV rect;
		private Node leftBottom;
		private Node rightTop;

		public Node(Point2D p, Value value, RectHV rect) {
			this.p = p;
			this.value = value;
			this.rect = rect;
		}
	}

	/**
	 * Constructs an empty symbol table of points.
	 */
	public KDTreeST() {
		size = 0;
	}

	/**
	 * Checks if the symbol table is empty.
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Number of points.
	 * 
	 * @return
	 */
	public int size() {
		return size;
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

		root = put(p, val, root, true);
	}

	private Node put(Point2D p, Value value, Node x, boolean vertical) {
		if (x == null) {
			size++;
			return new Node(p, value, createRect(p, x, vertical));
		}
		double compare = compareGraph(p, x, vertical);

		if (x.p.equals(p)) {
			x.value = value;
		}
		
		if (compare < 0) {
			x.leftBottom = put(p, value, x.leftBottom, !vertical);
		} else {
			x.rightTop = put(p, value, x.rightTop, !vertical);
		}

		return x;
	}

	/**
	 * Creates new RectHV depending on vertical and axis of the graph.
	 * 
	 * @param p
	 * @param x
	 * @param vertical
	 * @return new RectHV or null
	 */
	private RectHV createRect(Point2D p, Node x, boolean vertical) {
		if (x == null) {
			return new RectHV(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
		}

		double compare = compareGraph(p, x, !vertical);

		if (vertical && compare >= 0) {
			return new RectHV(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.rect.ymax());
		}
		if (vertical && compare < 0) {
			return new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
		}

		if (!vertical && compare >= 0) {
			return new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
		}
		if (!vertical && compare < 0) {
			return new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		}

		return null;
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

		return get(p, root, true);
	}

	private Value get(Point2D p, Node x, boolean vertical) {
		if (x == null) {
			return null;
		}

		double compare = compareGraph(p, x, vertical);

		if (compare < 0) {
			return get(p, x.leftBottom, !vertical);
		} else if (compare > 0) {
			return get(p, x.rightTop, !vertical);
		}

		return x.value;
	}

	/**
	 * Compares a node to a point depending on the x and y axis.
	 * 
	 * @param p
	 * @param x
	 * @return
	 */
	private double compareGraph(Point2D p, Node x, boolean vertical) {
		if (vertical) {
			return p.x() - x.p.x();
		}

		return p.y() - x.p.y();
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

		return get(p) != null;
	}

	/**
	 * All points in the symbol table.
	 * 
	 * @return
	 */
	public Iterable<Point2D> points() {
		Queue<Point2D> points = new Queue<>(); // queue containing all points
		Queue<Node> nodes = new Queue<>(); // queue containing all nodes
		Node tempNode = root;

		nodes.enqueue(tempNode);
		while (!nodes.isEmpty()) {
			tempNode = nodes.dequeue();
			points.enqueue(tempNode.p);

			if (tempNode.leftBottom != null) {
				nodes.enqueue(tempNode.leftBottom);
			}
			if (tempNode.rightTop != null) {
				nodes.enqueue(tempNode.rightTop);
			}
		}

		return points;
	}

	/**
	 * All points that are inside the rectangle.
	 * 
	 * @param rect
	 * @return
	 */
	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new NullPointerException();
		}

		Queue<Point2D> points = new Queue<>();
		range(rect, points, root);

		return points;
	}

	private void range(RectHV rect, Queue<Point2D> points, Node x) {
		if (rect == null || !rect.intersects(x.rect)) {
			if (rect.contains(x.p)) {
				points.enqueue(x.p);
			}

			// recursively calling for left and right.
			range(rect, points, x.leftBottom);
			range(rect, points, x.rightTop);
		}
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

		return nearest(p, root, root.p);
	}
	
	private Point2D nearest(Point2D p, Node x, Point2D nearest) {
		if(x == null) {
			return nearest;
		}
		
		if(p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearest)) {
			nearest = x.p;
		}
		
		if(x.leftBottom != null && x.leftBottom.rect.contains(p)) {
			nearest = nearest(p, x.leftBottom, nearest);
			nearest = nearest(p, x.rightTop, nearest);
		}
		else {
			nearest = nearest(p, x.rightTop, nearest);
			nearest = nearest(p, x.leftBottom, nearest);
		}
		
		return nearest;
	}

	/**
	 * Unit testing of methods.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Point2D[] points = { new Point2D(1, 0), new Point2D(1, 1), new Point2D(1, 3) };
		KDTreeST<String> kdTree = new KDTreeST<>();

		for (Point2D el : points) {
			kdTree.put(el, el.toString());
		}

		StdOut.println("Size: " + kdTree.size);
		StdOut.println("isEmpty: " + kdTree.isEmpty());

		kdTree.put(new Point2D(2.0, 5.0), "Weee");
		kdTree.put(new Point2D(2.0, 4.0), "Newwww");
		kdTree.put(new Point2D(6.0, 2.0), "Pointts");
		
		StdOut.println("New points added.");
		StdOut.println("Size: " + kdTree.size);
		
		StdOut.println("get (6.0, 2.0): " + kdTree.get(new Point2D(6.0, 2.0)));
		StdOut.println("Contains (1.0, 0.0): " + kdTree.contains(new Point2D(1.0, 0.0)));
		StdOut.println();
		
		for (Point2D el : kdTree.points()) {
			StdOut.print(el + " ");
		}
		
	}

}
