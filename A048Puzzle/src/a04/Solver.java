package a04;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Provides a data type that shows a solution for an n-size puzzle by using A* algorithm. 
 * 
 * @author Deokhee Kang and Kim Soto
 *
 */
public final class Solver {

	/**
	 * Represents a search node of the game (tree).
	 * 
	 * @author Deokhee Kang and Kim Soto
	 * 
	 */
	private final static class SearchNode implements Comparable<SearchNode> {
		final SearchNode previous;
		final Board value;
		final int moves;
		final int priority;
			
		/**
		 * Constructs a search node of the game tree
		 *  
		 * @param board
		 * @param previous
		 */
		private SearchNode(Board board, SearchNode previous) {
			int weight = 1; // expected to return minimum moves when weight == 1, bigger leads to faster response but not to return the minimum moves
							
			this.value = board;
			this.previous = previous;
			
			if (previous == null) {
				this.moves = 0;
			} else {
				this.moves = previous.moves + 1;
			}

			//this.priority = (board.hamming() * weight) + moves;
			this.priority = (board.manhattan() * weight) + moves;
		}
		
		@Override
		public int compareTo(SearchNode other) {
			return this.priority - other.priority;
		}
	}

	private static SearchNode result;

	/**
	 * Find a solution to the initial board.
	 * 
	 * @param initial
	 */
	public Solver(Board initial) {
		//checks the exceptions
		if (initial.equals(null))
			throw new NullPointerException("null board is not allowed");
		if (!initial.isSolvable())
			throw new IllegalArgumentException("problem must be solvable");
		
		//creates the game tree
		SearchNode root = new SearchNode(initial, null);
		MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
		minPQ.insert(root);
		
		// search and find a solution
    	while(!minPQ.isEmpty()) {
    		SearchNode current = minPQ.delMin();
    		if(current.value.isGoal()) {
    			result = current;
    			break;
    		}
    		
			for (Board board : current.value.neighbors()) {
    			SearchNode x = new SearchNode(board, current);
    			if (current.previous != null) {
//    				if ((x.priority == current.priority) 
  					if ((x.priority < current.priority)     						
    					|| board.equals(current.previous.value)) {
//    					System.out.println(board.toString());    				
    					// don't add neighbors that are same as its grand parent board					
    						continue;
    				} //end if
    			}//end if
    			
    			// insert a neighbor
//    			System.out.println(board.toString());
//    			System.out.println(x.priority);
    			minPQ.insert(x);
			}//end for
    	}//end while
	}

	/**
	 * Minimum number of moves to solve initial board.
	 * @return
	 */
	public int moves() {
		return result.moves;
	}

	/**
	 * Sequence of boards in a shortest solution.
	 * @return
	 */
	public Iterable<Board> solution() {
		Stack<Board> solution = new Stack<>();

		while (result != null) {
			solution.push(result.value);
			result = result.previous;
		}
		
		return solution;
	}

	/**
	 * Solves a slider puzzle.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create initial board from file
    	String filename = "puzzle28.txt";
        In in = new In(System.getProperty("user.dir") + "\\src\\a04\\" + filename);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();

        Board initial = new Board(blocks);

        // check if puzzle is solvable; if so, solve it and output solution
        if (initial.isSolvable()) {
        	Stopwatch timer = new Stopwatch();
        	Solver solver = new Solver(initial);
            double time = timer.elapsedTime();
            StdOut.printf("%-25s %7d %8.2f\n", filename, solver.moves(), time);
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        // if not, report unsolvable
        else {
            StdOut.println("Unsolvable puzzle");
        }
	}
}
