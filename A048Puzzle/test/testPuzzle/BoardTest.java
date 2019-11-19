package testPuzzle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import a04.Board;

/**
 * Test units for Board and Solver.
 * @author Kim Soto and Deokhee Kang
 *
 */
class BoardTest {

	Board board1 = new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 5}});
	Board board2 = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});
	Board goalBoard = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
	Board oddSizeSolvableBoard = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
	Board oddSizeUnsolvableBoard = new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {8, 5, 7}});
	Board evenSizeSolvableBoard = new Board(new int[][] {{1,2,3,4},{5,0,6,8},{9,10,7,11},{13,14,15,12}});
	Board evenSizeUnsolvableBoard = new Board(new int[][] {{1,2,3,4},{5,6,8,9},{0,10,7,11},{13,14,15,12}});
	//Duplicate boards to test equals.
	Board y1 = new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 5}});
	Board y2 = new Board(new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 8, 5}});

	@Test
	public void testManhattan_ReturnTwo() {
		int expect = 2;
		int actual = board1.manhattan();
		
		assertEquals(expect,actual);
	}
		
	@Test
	public void testManhattan_ReturnTen() {
		int expect = 10;
		int actual = board2.manhattan();
		
		assertEquals(expect,actual);
	}
	
	@Test
	public void testHamming_ReturnOne() {
		int expect = 1;
		int actual = board1.hamming();
		
		assertEquals(expect,actual);
	}
	
	@Test
	public void testHamming_ReturnFive() {
		int expect = 5;
		int actual = board2.hamming();
		
		assertEquals(expect,actual);
	}
	
	@Test
	public void testEquals_ReturnTrue() {
		boolean expect = true;
		boolean actual = board1.equals(y1);
		
		assertEquals(expect, actual);
	}
	
	@Test 
	public void testEquals_ReturnFalse() {
		boolean expect = false;
		boolean actual = board2.equals(y1);
		
		assertEquals(expect, actual);
	}
	
	@Test 
	public void testGoal_ReturnTrue() {
		boolean expect = true;
		boolean actual = goalBoard.isGoal();
		
		assertEquals(expect, actual);
	}
	
	@Test 
	public void testOddSolvableBoard_ReturnTrue() {
		boolean expect = true;
		boolean actual = oddSizeSolvableBoard.isSolvable();
		
		assertEquals(expect, actual);
	}

	@Test 
	public void testOddUnsolvableBoard_ReturnFalse() {
		boolean expect = false;
		boolean actual = oddSizeUnsolvableBoard.isSolvable();
		
		assertEquals(expect, actual);
	}
	
	@Test
	public void testEvenSolvable_ReturnTrue() {
		boolean expect = true;
		boolean actual = evenSizeSolvableBoard.isSolvable();
		
		assertEquals(expect, actual);
	}

	@Test
	public void testEvenUnsolvable_ReturnFalse() {
		boolean expect = false;
		boolean actual = evenSizeUnsolvableBoard.isSolvable();
		
		assertEquals(expect, actual);
	}
}
