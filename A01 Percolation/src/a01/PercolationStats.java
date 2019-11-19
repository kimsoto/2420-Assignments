package a01;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Performs independent computational experiments on an N by N grid.
 * Using this data, it can calculate the mean, standard deviation,
 * and a 95% confidence interval for the percolation threshold.
 * 
 * @author Kim Soto and Jeff Ostler
 *
 */
public class PercolationStats {
	
	private double[] stats; //Array filled with open spaces divided by N*N.
	private Percolation p; 
	
	/**
	 * Perform T independent experiments on an N-by-N grid.
	 * @param N size of grid
	 * @param T Number of test runs
	 */
	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0) 
            throw new IllegalArgumentException("N <= 0 or T <= 0");
		
		stats = new double[T];
		for(int i = 0; i < T; i++) {
			p = new Percolation(N);
			double openCount = 0;
			
			while(!p.percolates()){
				int x = StdRandom.uniform(N);
				int y = StdRandom.uniform(N);
				
				if(!p.isOpen(x,y)) {
					p.open(x, y);
					openCount++;
				}
			}
			stats[i] = openCount / (N*N);
		}
	}
	
	/**
	 * Sample mean of percolation threshold.
	 * @return Mean of the data inside stats.
	 */
	public double mean() {
		return StdStats.mean(stats); 
	}
	
	/**
	 * Sample standard deviation of percolation threshold.
	 * @return Standard deviation inside stats.
	 */
	public double stddev() {
		return StdStats.stddev(stats); 
	}
	
	/**
	 * Low endpoint of 95% confidence interval.
	 * @return Low endpoint.
	 */
	public double confidenceLow() {
		double low = 0.0;
		for(double el: stats) {
			low = mean() - (1.96 * stddev())/ Math.sqrt(el);
		}
		return low; 
	}
	
	/**
	 * High endpoint of 95% confidence interval.
	 * @return High endpoint.
	 */
	public double confidenceHigh() {
		double high = 0.0;
		for(double el: stats) {
			high = mean() + (1.96 * stddev())/ Math.sqrt(el);
		}
		return high; 
	}

}
