import org.junit.ComparisonFailure;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome(){
        String test1 = "horse";
        boolean getByFunc = palindrome.isPalindrome(test1);
        assertFalse(getByFunc);

        String test2 = "racecar";
        getByFunc = palindrome.isPalindrome(test2);
        assertTrue(getByFunc);

        String test3 = "a";
        getByFunc = palindrome.isPalindrome(test3);
        assertTrue(getByFunc);
    }
    @Test
    public void testIsPalindrome2(){
        CharacterComparator cc = new OffByOne();
        String test100 = "12312";
        assertTrue(palindrome.isPalindrome(test100, cc));

        String test2 = "racecar";
        assertFalse(palindrome.isPalindrome(test2, cc));

        String test3 = "a";
        assertTrue(palindrome.isPalindrome(test3, cc));
    }
}
