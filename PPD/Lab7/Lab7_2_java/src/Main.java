import domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 11/26/2019
 **/
public class Main {
    public static void main(String[] args) {
        BigNumber bigNumber1 = new BigNumber("1234");
        BigNumber bigNumber2 = new BigNumber("567");
        BigNumber bigNumber3 = new BigNumber("89");
        BigNumber bigNumber4 = new BigNumber("1023");
        BigNumber bigNumber5 = new BigNumber("456");
//        BigNumber bigNumber6 = new BigNumber("789");
//        BigNumber bigNumber7 = new BigNumber("456");
//        BigNumber bigNumber8 = new BigNumber("789");

        List<BigNumber> bigNumberList = new ArrayList<>();
        bigNumberList.add(bigNumber1);
        bigNumberList.add(bigNumber2);
        bigNumberList.add(bigNumber3);
        bigNumberList.add(bigNumber4);
        bigNumberList.add(bigNumber5);
//        bigNumberList.add(bigNumber6);
//        bigNumberList.add(bigNumber7);
//        bigNumberList.add(bigNumber8);

        FinalTask finalTask = new FinalTask();

//        SimpleTask simpleTask3 = new SimpleTask(finalTask);
//
//        SimpleTask simpleTask1 = new SimpleTask(simpleTask3);
//        NumberTask numberTask1 = new NumberTask(bigNumber1, simpleTask1);
//        NumberTask numberTask2 = new NumberTask(bigNumber2, simpleTask1);
//
//        SimpleTask simpleTask2 = new SimpleTask(simpleTask3);
//        NumberTask numberTask3 = new NumberTask(bigNumber3, simpleTask2);
//        NumberTask numberTask4 = new NumberTask(bigNumber4, simpleTask2);
//
//        NumberTask numberTask5 = new NumberTask(bigNumber5, finalTask);
//
//
//        try {
//            numberTask1.run();
//            numberTask2.run();
//            numberTask3.run();
//            numberTask4.run();
//            numberTask5.run();
//
//            numberTask1.join();
//            numberTask2.join();
//            simpleTask1.run();
//
//            numberTask3.join();
//            numberTask4.join();
//            simpleTask2.run();
//
//            simpleTask1.join();
//            simpleTask2.join();
//            simpleTask3.run();
//
//            numberTask5.join();
//            simpleTask3.join();
//            finalTask.run();
//
//            finalTask.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        BigNumber result = finalTask.getResult();
//        System.out.println(result.getNumber());
    }
}
