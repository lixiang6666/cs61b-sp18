import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.*;

public class TestArrayDequeGold {
    @Test
    public void randomTestADG(){
        /*{0,1,2,3} maps to {"addFirst","addLast","removeFirst","removeLast"};*/
        for(int j = 0; j < 1000 ; j ++){
            int testLength = StdRandom.uniform(1,100);
            StudentArrayDeque<Integer> studentArray = new StudentArrayDeque<>();
            ArrayDequeSolution<Integer> solutionArray = new ArrayDequeSolution<>();
            String log = "";
            for(int i =0; i < testLength; i++) {
                if(studentArray.size() == 0){
                    int item = StdRandom.uniform(1000);
                    int methodNumber = StdRandom.uniform(2);
                    if(methodNumber == 0){
                        studentArray.addFirst(item);
                        solutionArray.addFirst(item);
                        log += "addFirst" + "(" + item + ")\n";
                    }else{
                        studentArray.addLast(item);
                        solutionArray.addLast(item);
                        log += "addLast" + "(" + item + ")\n";
                    }
                }else{
                    int methodNumber = StdRandom.uniform(4);
                    int item = StdRandom.uniform(1000);
                    Integer studentRemove = 1;
                    Integer solutionRemove = 1;
                    switch (methodNumber) {
                    case 0:
                        studentArray.addFirst(item);
                        solutionArray.addFirst(item);
                        log += "addFirst" + "(" + item + ")\n";
                        break;
                    case 1:
                        studentArray.addLast(item);
                        solutionArray.addLast(item);
                        log += "addLast" + "(" + item + ")\n";
                        break;
                    case 2:
                        studentRemove = studentArray.removeFirst();
                        solutionRemove = solutionArray.removeFirst();
                        log += "removeFirst" + "(" + ")\n";
                        break;
                    default:
                        studentRemove = studentArray.removeLast();
                        solutionRemove = solutionArray.removeLast();
                        log += "removeLast" + "(" + ")\n";
                    }
                    assertEquals(log, solutionRemove, studentRemove);
                }
            }
        }
    }
}