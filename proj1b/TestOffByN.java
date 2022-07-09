import org.junit.Test;
import static org.junit.Assert.*;
public class TestOffByN {
        @Test
        public void testOffByN(){
                CharacterComparator offby5 = new OffByN(5);
                assertTrue(offby5.equalChars('f', 'a'));
                assertFalse(offby5.equalChars('f', 'h'));
        }


}

