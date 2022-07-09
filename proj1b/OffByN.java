public class OffByN implements CharacterComparator{
    private static int N;
    public OffByN(int n){
         N = n;
    }

   public boolean equalChars(char x, char y){
       return ((int) x - (int) y == -N) || ((int) x - (int) y == N);
        }
}
