package testAutocomplete;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Before;
import org.junit.Test;
					
import autocomplete.Autocomplete;
import autocomplete.BinarySearchDeluxe;
import autocomplete.Term;

/**
 * J-unit tests on different methods in classes: Autocomplete, BinarySearchDeluxe, and Term.
 * 
 * @author Kimberly Soto and Chantel Tonks
 *
 */
public class AutoCompleteTest {
	
	//Terms declared so eclipse views them as the same objects so our tests pass.
	private Term termDogBig = new Term("dog", 5.5);
	private Term termDogCatcher = new Term("dogcatcher", 5);
	private Term termDogSmall = new Term("dog", 3);
	private Term termCat = new Term("cat", 6.0);

	private Term[] terms = { termDogBig, termDogCatcher, termDogSmall };
	private Term[] expectThree = { termCat, termDogBig, termDogCatcher,
			termDogSmall };
	private Autocomplete test = new Autocomplete(terms);

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testMatches_ReturnThree() {
		int expect = 3;
		int actual = test.numberOfMatches("dog");
		
		assertEquals(expect, actual);
	}

	@Test
	//fixed! just needed to use same terms in both arrays
	//eclipse saw the terms as different objects even though they were technically the same.
	public void testAllMatches() {
		Term[] expectTwo = { termDogBig, termDogCatcher, termDogSmall };
		Term[] actualTwo = test.allMatches("d");
		
		assertArrayEquals(expectTwo, actualTwo);
	}

	@Test
	public void testReverse() {
		Term[] actualThree = { termDogSmall, termDogCatcher, termDogBig,
				termCat };
		
		Arrays.sort(actualThree, Term.byReverseWeightOrder());
		assertArrayEquals(expectThree, actualThree);
	}

	@Test
	public void testPrefix() {
		Term[] actualThree = { termCat, termDogBig, termDogCatcher,
				termDogSmall };
		
		Arrays.sort(expectThree, Term.byPrefixOrder(2));
		Arrays.sort(actualThree, Term.byPrefixOrder(2));
		assertArrayEquals(expectThree, actualThree);
	}
	
	@Test
	public void testFirstIndex_ExpectZero() {
		Comparator<Term> weight = Term.byReverseWeightOrder();
		int expect = 0;
		int actual = BinarySearchDeluxe.firstIndexOf(terms, termDogBig, weight);
		
		assertEquals(expect, actual);
	}
	
	@Test
	public void testFirstIndex_ExpectOne() {
		Comparator<Term> weight = Term.byReverseWeightOrder();
		int expect = 1;
		int actual = BinarySearchDeluxe.firstIndexOf(terms, termDogCatcher, weight);
		
		assertEquals(expect, actual);
	}
	
	@Test 
	public void testLastIndex_ExpectZero() {
		Comparator<Term> weight = Term.byReverseWeightOrder();
		int expect = 0;
		int actual = BinarySearchDeluxe.lastIndexOf(terms, termDogBig, weight);
		
		assertEquals(expect, actual);
	}
	
	@Test
	public void testLastIndex_ExpectTwo() {
		Comparator<Term> weight = Term.byReverseWeightOrder();
		int expect = 2;
		int actual = BinarySearchDeluxe.lastIndexOf(terms, termDogSmall, weight);
		
		assertEquals(expect, actual);
	}

}