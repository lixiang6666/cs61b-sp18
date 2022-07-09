public class OffByN implements CharacterComparator{
    private static int N;
    public void OffByN(int n){
         N = n;
    }

   public boolean equalChars(char x, char y){
            if ((int)x - (int)y == -N){
                return true;
            }else{
                return false;
            }
        }
}
