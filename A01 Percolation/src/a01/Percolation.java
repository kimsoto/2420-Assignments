package a01;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * TODO: Models a percolation system using an N by N grid of sites.
 * 
 * @author Jeff Ostler and Kim Soto
 *
 */
public class Percolation {
	private boolean[][] grid;
	private int size;
	private int top; //Virtual top.
	private int bottom; //Virtual bottom.
    private int openSites;
	private WeightedQuickUnionUF wqu; 

	/**
	 * Creates NbyN grid, with all sites blocked.
	 * @param N
	 */
	public Percolation(int N) {
		if(N <= 0) throw new IndexOutOfBoundsException(N + "is less than 0");
		grid = new boolean[N][N];
		size = N;
		top = size * size + 1;
		bottom = top + 1;
		wqu = new WeightedQuickUnionUF(bottom + 1);
	}
	
	/**
	 * Open site (row i, column j) if it is not open already.
	 * @param i
	 * @param j
	 */
	public void open(int i, int j) {
		if((i < 0 || i >= size) || ((i < 0 || i >= size))) 
			throw new IndexOutOfBoundsException("Cannot open site: index is out of range\nsize: " + size);
		
		// Open site (row i, column j) if it is not open already.
		//1) Validate i and j.
		wqu.find(i);
		//2) Mark the site as open.
		if(!isOpen(i, j)) {			
			openSites++;
			grid[i][j] = true;
			//Connect virtual top to actual top.
			if(i == 0) {
				wqu.union(getSite(i, j), top);
			}
		//Fill the open sites.
		//If not top then fill above.
		if(i != 0) {
			if(isOpen(i-1,j)) {
				wqu.union(getSite(i-1,j), getSite(i,j));
			}				
		}
		//If not bottom then fill below.
		if(i != size-1) {
			if(isOpen(i+1,j)) {
				wqu.union(getSite(i+1,j), getSite(i,j));				
			}
		}
		//If not left side then fill left.
		if(j != 0) {
			if(isOpen(i,j-1)) {
				wqu.union(getSite(i,j-1), getSite(i,j));
			}	
		}
		//If not right side then fill right.
		if(j != size - 1) {
			if(isOpen(i,j+1)) {
				wqu.union(getSite(i,j+1), getSite(i,j));
			}			
		}
		//Prevent backwash.
		if(i == size-1) {
			if(!(wqu.connected(top,bottom))) {
				wqu.union(getSite(i,j), bottom);
			}
		}
		}
	} 
	
	/**
	 * Checks whether site (row i, column j) is open.
	 * @param i
	 * @param j
	 * @return True if site is open, false otherwise.
	 */
	public boolean isOpen(int i, int j) {
		if((i < 0 || i >= size) || ((i < 0 || i >= size))) 
			throw new IndexOutOfBoundsException("Cannot check if site is open: index is out of range\nsize " + size);
		return grid[i][j]; 
	}
	
	/**
	 * Checks whether site (row i, column j) is full.
	 * @param i
	 * @param j
	 * @return True if site is full, false otherwise.
	 */
	public boolean isFull(int i, int j) {
		if((i < 0 || i >= size) || ((i < 0 || i >= size))) 
			throw new IndexOutOfBoundsException("Cannot check if site is full: index is out of range\nsize " + size);
		return wqu.connected(top, getSite(i,j));
	}
	
	/**
	 * Decides whether the system percolates.
	 * @return True if system percolates, false otherwise.
	 */
	public boolean percolates() {
		return wqu.connected(top, bottom); 
	}

	/**
	 * AUTO-GENERATED: Called by PercolationVisualizer to return number of open sites.
	 * @return Int representing open sites.
	 */
	@SuppressWarnings("unused")
	private String numberOfOpenSites() {
		return String.valueOf(openSites);
	}
	
	/**
	 * Returns 1-dimensional site id.
	 * @param i row
	 * @param j column
	 * @return Site id based on row and column.
	 */
	private int getSite(int i, int j) {	
		return size * i + j;
	}
}
