import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testOffByOne(){
        OffByOne offbyone = new OffByOne();
        assertTrue(offbyone.equalChars('b','a'));
        assertFalse(offbyone.equalChars('a','e'));
        assertFalse(offbyone.equalChars('c','D'));
    }
    // Your tests go here.

}
