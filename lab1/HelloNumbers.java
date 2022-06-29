 public class HelloNumbers {
     public static void main(String[] args) {
         int x = 0;
         int index = 0;
         int sum = 0;
         while (x < 10){
            while(index < x){
                index =  index + 1;
                sum = sum + index;
            }
            System.out.print(sum + " ");
            index = 0;
            sum = 0;
            x = x + 1;
         }
     }

 }

