package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author joj on 11/26/2019
 **/
public class AddBigNumbers {
    private List<BigNumber> bigNumbers;
    private List<SimpleTask> threads;
    private int noOfThreads;

    public AddBigNumbers(List<BigNumber> bigNumbers) {
        this.bigNumbers = bigNumbers;
        this.noOfThreads = bigNumbers.size() - 1;
        this.threads = new ArrayList<>(this.noOfThreads);
    }

    public void sum() {
        this.threads.set(this.noOfThreads - 1, new FinalTask());
        for (int i = 0; i <= this.bigNumbers.size() / 2; i++) {
            this.threads.set(i, new NumberTask(this.bigNumbers.get(i)));
        }
        for (int depth = 0; depth < )
    }

//    public void doSmth() {
//        int k, counter = 0;
//        for (k = 1; k < bigNumbers.size(); k *= 2) {
//            System.out.println("k = " + k);
//            for (int i = 2 * k; i <= bigNumbers.size(); i += 2 * k) {
//                if (k == 1) {
//                    GathererTask task = new GathererTask(bigNumbers.get(counter), null);
//                    this.threads.add(task);
//                }
////                else {
////                    GathererTask task = new GathererTask()
////                }
//                System.out.println("\ti = " + i);
//            }
//        }
//    }
}
