package a04;

import edu.princeton.cs.algs4.Queue;

/**
 * Creates an immutable board that is used to solve an 8 puzzle.
 * 
 * @author Kim Soto and Deokhee Kang
 *
 */
public final class Board {
	private final int N; // size
	private final int[] tiles;	
	private final int xOfBlank;
	private final int yOfBlank;

	/**
	 * Constructs a board from an N-by-N array of blocks where blocks[i][j] = block
	 * in row i, column j.
	 * 
	 * @param blocks
	 */
	public Board(int[][] blocks) {
		N = blocks.length;
		//tiles = new int[N][N];
		tiles = new int[N*N];
		int tempX = 0;
		int tempY = 0;

		// Transfers rows and columns from blocks into tiles.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tiles[xyTo1d(i,j)] = blocks[i][j];
				if (tiles[xyTo1d(i,j)] == 0) {
					tempX = i;
					tempY = j;
				}
			}
		}
		
		//stores the location of the blank(0)
		xOfBlank = tempX;
		yOfBlank = tempY;
	}

	/**
	 * Board size N
	 * 
	 * @return size
	 */
	public int size() {
		return N;
		//return tiles.length; // confused since it 8 or 15 puzzle  
	}

	/**
	 * Number of blocks out of place.
	 * 
	 * @return blocks not in place.
	 */
	public int hamming() {
		int notInPlace = 0;
		int position = 1;

		for (int i = 0; i < tiles.length; i++) { // row
			// If block is not blank space and is not in correct position.
			if (tiles[i] != 0 && tiles[i] != position) {
				notInPlace++;
			}

			position++;
		}
		
		return notInPlace;
	}

	/**
	 * Sum of Manhattan distances between blocks and goal.
	 * 
	 * @return
	 */
	public int manhattan() {
		int sumDistances = 0;
		int position = 1;

		for (int i = 0; i < N; i++) { // row
			for (int j = 0; j < N; j++) { // column
				if (tiles[xyTo1d(i,j)] != 0 && tiles[xyTo1d(i,j)] != position) {
					int row = (tiles[xyTo1d(i,j)] - 1) / N;
					int column = (tiles[xyTo1d(i,j)] - 1) % N;
					sumDistances += Math.abs(row - i) + Math.abs(column - j);
				}

				position++;
			}
		}
		
		return sumDistances;
	}

	/**
	 * Checks if board is the goal board.
	 */
	public boolean isGoal() {
		return hamming() == 0;
	}

	/**
	 * Checks if the board is solvable.
	 * 
	 * @return
	 */
	public boolean isSolvable() {
		int inversions = 0;
		
		// count the number of inversions 	
		for (int n = 1; n <= N * N - 1; n++) {
			idvInversions: for (int i = 0; i < tiles.length; i++) {
				if (tiles[i] > n) {
//					System.out.printf("inverse pair [%d, %d]\n", tiles[i], n);
					inversions++;
				} else if (tiles[i] == n) {
					break idvInversions;
				}
			}
		}
		
//		System.out.printf("inversions: %d\n", inversions);
		
		// depens of the board's size
		if (N % 2 == 0) {// even
//			System.out.printf("blank row: %d\n", xOfBlank);
			return (inversions + xOfBlank) % 2 != 0;
		} else {// odd
			return inversions % 2 == 0;
		}
	}

	/**
	 * Returns all neighboring boards.
	 * 
	 * @return
	 */
	public Iterable<Board> neighbors() {
		Queue<Board> neighbors = new Queue<>();

		// swipe with top pane
		if (xOfBlank > 0) {
			neighbors.enqueue(new Board(swipe(xOfBlank - 1, yOfBlank)));
		}

		// swipe with bottom pane
		if (xOfBlank < N - 1) {
			neighbors.enqueue(new Board(swipe(xOfBlank + 1, yOfBlank)));
		}

		// swipe with left pane
		if (yOfBlank > 0) {
			neighbors.enqueue(new Board(swipe(xOfBlank, yOfBlank - 1)));
		}

		// swipe with right pane
		if (yOfBlank < N - 1) {
			neighbors.enqueue(new Board(swipe(xOfBlank, yOfBlank + 1)));
		}
	
		return neighbors;
	}

	/**
	 * Swaps number in the tile.
	 * 
	 * @param row
	 * @param column
	 * @return copy of Board with numbers switched.
	 */
	private int[][] swipe(int row, int column) {
		int[][] copy = copyBoard();

		copy[xOfBlank][yOfBlank] = copy[row][column];
		copy[row][column] = 0;

		return copy;
	}

	/**
	 * Returns a copy of Board.
	 * 
	 * @param tiles
	 * @return a Duplication of tiles
	 */
	private int[][] copyBoard() {
		int[][] copy = new int[N][N];

		for (int i = 0; i < tiles.length; i++) {
			copy[i / N][i % N] = tiles[i];
		}

		return copy;
	}
	
	/**
	 * Return 1 dimensional location of i, j
	 * 
	 * @param i row
	 * @param j column
	 * @return 1 dimensional location  i, j 
	 */
	private int xyTo1d(int x, int y) {
		return (N * x) + y;
	}
		
	/**
	 * Checks if the board is equal to y.
	 * @return true or false
	 */
	@Override
	public boolean equals(Object y) {
		if (y == this)
			return true;
		if (y == null)
			return false;
		if (y.getClass() != this.getClass())
			return false;

		Board that = (Board) y;
		
		if (that.size() != this.size())
			return false;
		
		for (int i = 0; i < tiles.length; i++) {
			if (this.tiles[i] != that.tiles[i]) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Returns String representation of this board.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(N + "\n");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				s.append(String.format("%2d ", tiles[xyTo1d(i,j)]));
			}
			s.append("\n");
		}

		return s.toString();
	}
}
