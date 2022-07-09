public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x, char y){
        return ((int) x - (int) y == -1) || ((int) x - (int) y == 1);
    }
}
